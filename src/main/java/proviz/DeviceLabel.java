package proviz;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Burak on 9/8/16.
 */
public class DeviceLabel extends JComponent implements Transferable {

    private BufferedImage deviceImage;
    private PView pview;

private DataFlavor dataFlavor;




    public void setMargin( int top,int left ,int bottom, int right)
    {
        Border dummyBorder = new EmptyBorder(top,left,bottom,right);
        setBorder(dummyBorder);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] dataFlavors = new DataFlavor[1];
        dataFlavors[0] = dataFlavor;
        return dataFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if(flavor.equals(dataFlavor) || flavor.equals(DataFlavor.stringFlavor))
            return true;
        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(dataFlavor))
        return this;
    else if (flavor.equals(DataFlavor.stringFlavor))
        return this.toString();
    else
        throw new UnsupportedFlavorException(flavor);
    }

    enum DEVICE_TYPES{
        RaspberryPI,
        Ardunio,
        BeagleBone
    }
    public DeviceLabel(DEVICE_TYPES deviceTypes)
    {


        try {
            if(deviceTypes == DEVICE_TYPES.Ardunio){
                deviceImage = ImageIO.read(getClass().getResource("/icons/arduino.png"));
                dataFlavor = new DataFlavor(DeviceLabel.class,"Ardunio");}
            else if (deviceTypes == DEVICE_TYPES.BeagleBone) {
                deviceImage = ImageIO.read(getClass().getResource("/icons/beagle.png"));
                dataFlavor = new DataFlavor(DeviceLabel.class,"Beaglebone");
            }
            else if (deviceTypes == DEVICE_TYPES.RaspberryPI) {
                deviceImage = ImageIO.read(getClass().getResource("/icons/raspberry-pi.png"));
                dataFlavor = new DataFlavor(DeviceLabel.class,"Raspberry");
            }

        }
        catch (Exception ex)
        {
            ex.toString();
        }

    }
    public BufferedImage getDeviceImage() {
        return deviceImage;
    }

    public void setDeviceImage(BufferedImage deviceImage) {
        this.deviceImage = deviceImage;
        setPreferredSize(new Dimension(deviceImage.getWidth(null),deviceImage.getHeight(null)));
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(deviceImage.getWidth(null), deviceImage.getHeight(null));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    g.drawImage(deviceImage,0,0,deviceImage.getWidth(null),deviceImage.getHeight(null),null);

    }

    public PView getPview() {
        return pview;
    }

    public void setPview(PView pview) {
        this.pview = pview;
    }





}
