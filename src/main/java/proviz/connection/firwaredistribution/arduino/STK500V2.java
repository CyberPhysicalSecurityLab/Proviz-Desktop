package proviz.connection.firwaredistribution.arduino;

import java.io.*;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created by Burak on 1/27/17.
 */
public class STK500V2 {
    private BufferedInputStream reader;
    private DataOutputStream writer;
    public byte sequenceNumber;
    private StringBuilder data ;
    private ArrayList<Line> sketchFileLine;
    public STK500V2(BufferedInputStream reader, OutputStream outputStream)
    {
        sketchFileLine = new ArrayList<>();
        sequenceNumber = 0x01;
        writer = new DataOutputStream(outputStream);
        data = new StringBuilder();

        this.reader = reader;
    }


    private void IncreaseSequenceNumber()
    {
        sequenceNumber++;
    }

    private byte GetSequenceNumber()
    {
        return sequenceNumber;
    }



    private byte[] WrapWithMessageFormat(byte[] message)
    {
        int packet_size = message.length;
        byte[]packet  = new byte[6+packet_size];
        packet[0] = Constants.MESSAGE_START;
        packet[1] = GetSequenceNumber();
        packet[2] = (byte)(packet_size / 256);
        packet[3] = (byte)(packet_size % 256);
        packet[4] = Constants.TOKEN;
        for(int i = 0; i< message.length; i++)
            packet[5+i] = message[i];
        packet[5+message.length] = Checksum(packet,4+message.length);
        IncreaseSequenceNumber();
        return packet;
    }

    public int[] Receive()
    {
        long timestart = System.currentTimeMillis();

        IntBuffer intBuffer = IntBuffer.allocate(Constants.MAX_RECEIVED_PACKETSIZE);
        while((timestart + Constants.TIMEOUT) > System.currentTimeMillis())
        {
            int b;
            try {
                b = reader.read();
                System.out.println(b);
                intBuffer.put(b);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return intBuffer.array();
    }
    public boolean isRecived(byte[] byteArray) throws IOException
    {

        long timestart = System.currentTimeMillis();

        while((timestart + 2000) > System.currentTimeMillis())
        {

            for(int i=0;i<byteArray.length;i++)
            {
                int b = reader.read();
                System.out.println(b);
                if(b == 90)
                    return true;
            }


        }
        return false;
    }

    public boolean Acknowledgement(byte[] desiredResponse) throws Exception
    {
        if(desiredResponse == null)
            throw new NullPointerException("Desired Response Argument can not be null");
        if(desiredResponse.length == 0)
            throw new Exception("Desired Response Argument's length can not be zero");

        long timestart = System.currentTimeMillis();
        int index = 0;
        IntBuffer intBuffer = IntBuffer.allocate(Constants.MAX_RECEIVED_PACKETSIZE);

        while((timestart + Constants.TIMEOUT) > System.currentTimeMillis())
        {
            int b;
            try {
                b = reader.read();
                if(desiredResponse[index] == b)
                {
                    index++;
                    intBuffer.put( b);
                    if(index == (desiredResponse.length-1))
                    {
                        return  true;
                    }
                }
                else
                {
                    index = 0;
                    intBuffer.clear();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    public void Send(byte[] packet)
    {
        try {
            writer.write(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte Checksum(byte[] packet, int endIndex)
    {
        byte result = 0x00;
        for(int i=0; i<(endIndex+1); i++)
        {
            result ^= packet[i];
        }
        return result;
    }

    public boolean GetSync()
    {
        try {
            byte[] sendingArray = new byte[1];
            sendingArray[0] = STK500V2Commands.CMD_SIGN_ON;
            Send(WrapWithMessageFormat(sendingArray));
            byte[] receivingArray = new byte[2];
            receivingArray[0] = STK500V2Commands.CMD_SIGN_ON;
            receivingArray[1] = STK500V2Commands.STATUS_CMD_OK;
            return Acknowledgement(receivingArray);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean EnableProgrammingMode()
    {
        try{
            byte[] sendingArray = new byte[12];
            sendingArray[0] = STK500V2Commands.CMD_ENTER_PROGMODE_ISP;
            sendingArray[1] = (byte)200;
            sendingArray[2] = (byte)100;
            sendingArray[3] = (byte)25;
            sendingArray[4] = (byte)32;
            sendingArray[5] = (byte)0;
            sendingArray[6] =(byte) 0x53;
            sendingArray[7] =(byte) 3;
            sendingArray[8] = (byte)0xAC;
            sendingArray[9] =(byte) 0x53;
            sendingArray[10] =(byte) 0;
            sendingArray [11] = (byte)0;
            Send(WrapWithMessageFormat(sendingArray));
            byte[] receivingArray = new byte[2];
            receivingArray[0] = STK500V2Commands.CMD_ENTER_PROGMODE_ISP;
            receivingArray[1] = STK500V2Commands.STATUS_CMD_OK;
            return Acknowledgement(receivingArray);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    private void parseSketchFile(String filePath)
    {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String text  = null;
            while ((text = bufferedReader.readLine()) != null)
            {
                Line line = new Line(text);
                sketchFileLine.add(line);
                data.append(line.getData());

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void LoadAddress(byte[] address)
    {
        if(address.length != 4)
        {
            System.out.println("There is a problem");
        }
        byte [] sendingArray = new byte[5];
        sendingArray[0] = STK500V2Commands.CMD_LOAD_ADDRESS;
        sendingArray[1] = address[0];
        sendingArray[2] = address[1];
        sendingArray[3] = address[2];
        sendingArray[4] = address[3];
        Send(WrapWithMessageFormat(sendingArray));
        byte[] receivingArray = new byte[2];
        receivingArray[0] = 0x06;
        receivingArray[1] = 0x00;
        try {
            Acknowledgement(receivingArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadSketch(String filePath)
    {
        parseSketchFile(filePath);
        char[] wholeData = data.toString().toCharArray();

        ArrayList<String> stringArrayList = new ArrayList<>();
        Integer tst = new Integer(-2147483648);







        int a1 = wholeData.length/256;
        for(int i=0; i<=a1;i++)
        {
            String processed = (Integer.toBinaryString(tst));
            Long l1 = Long.parseLong(processed,2);
            int parts = Long.toHexString(l1).length()/4;
            byte[] addresses = new byte[4];
            String[] pieces =Long.toHexString(l1).split("(?<=\\G.{"+parts+"})");
            addresses[0] = (byte) Integer.parseInt(pieces[0],16);
            addresses[1] = (byte) Integer.parseInt(pieces[1],16);
            addresses[2] = (byte) Integer.parseInt(pieces[2],16);
            addresses[3] = (byte) Integer.parseInt(pieces[3],16);
            int size = -1;
            byte[] packet = null;
            LoadAddress(addresses);
            if(i !=a1) {

                size =256;
                packet = new byte[size+10];
                packet[1] = (byte)(size >>8);
                packet[2] = (byte)(size & 0xff);


            }
            else if(i == a1)
            {
                size = (wholeData.length%256);
                packet = new byte[size+10];
                packet[1] = (byte)(size >>8);
                packet[2] = (byte)(size& 0xff);

            }
            if(packet != null) {
                packet[0] = 0x13;
                packet[3] = (byte)0xc1;
                packet[4] = 0x0a;
                packet[5] = 0x40;
                packet[6] = 0x4c;
                packet[7] = 0x20;
                packet[8] = 0x00;
                packet[9] = 0x00;

                for(int index =0; index <size ;index++)
                {
                    packet[index+10] = (byte) wholeData[i*256+index];
                }


            }
            Send(WrapWithMessageFormat(packet));
            byte[] receivingArray = new byte[2];
            receivingArray[0] = 0x13;
            receivingArray[1] = 0x00;
            try {
                Acknowledgement(receivingArray);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tst += 128;

        }

    }

    public boolean closeProgrammingMode()
    {
        byte[]  sendingArray = new byte[3];
        sendingArray[0] = 0x11;
        sendingArray[1] = 0x1;
        sendingArray[2] = 0x1;
        Send(WrapWithMessageFormat(sendingArray));
        byte[]  receivingArray = new byte[2];
        receivingArray[0] = 0x11;
        receivingArray[1] = 0x00;
        try {
            return Acknowledgement(receivingArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
