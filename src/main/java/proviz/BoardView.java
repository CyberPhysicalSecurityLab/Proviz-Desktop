package proviz;

import proviz.asci.DeviceConfigurationDialog;
import proviz.codegeneration.ArduinoTemplate;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.codegeneration.RaspberryPiTemplate;
import proviz.connection.DataReceiveListener;
import proviz.devicedatalibrary.DataManager;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.connection.ReceivedSensorData;
import proviz.models.devices.Board;
import proviz.models.uielements.LineModel;
import proviz.uicomponents.RightClickPopUpMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Burak on 9/8/16.
 */
public class BoardView extends JComponent implements ActionListener,MouseMotionListener,MouseListener,DataReceiveListener {


    private Point anchorPoint;
    private CodeGenerationTemplate codeGenerationTemplate;
    private ProjectConstants.DEVICE_TYPE type;
    private boolean isSelected;
    private String receivedData;
    private LineModel lineModel;
    private ProjectConstants.DEVICE_TYPE selectedDevice;
    private ProjectConstants.STATUS_CODE statusCode;
    private transient Image backgroundImage;
    private int backgroundImageHeight;
    private int backgroundImageWidth;
    private String deviceName;
    private transient BufferedImage status_img;
    private PView parentView;
    private int deviceNumber;
    private RightClickPopUpMenu rightClickPopUpMenu;
    private boolean isHaveProblem;
    private MainEntrance mainEntrance;
    private boolean isConfigured;



    public ProjectConstants.STATUS_CODE getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ProjectConstants.STATUS_CODE statusCode) {
        this.statusCode = statusCode;
        try {
            if(statusCode == ProjectConstants.STATUS_CODE.CONNECTION_LOST)
            {
                status_img = ImageIO.read(ClassLoader.getSystemResource("icons/nerd.png"));
            }
            else if(statusCode == ProjectConstants.STATUS_CODE.CONNECTION_NOT_ESTABLISHED)
            {
                status_img = ImageIO.read(ClassLoader.getSystemResource("icons/confused.png"));

            }
            else if(statusCode == ProjectConstants.STATUS_CODE.CONNECTION_OK)
            {
                status_img = ImageIO.read(ClassLoader.getSystemResource("icons/cool.png"));

            }
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDeviceName() {
        return deviceName;
    }





    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        if(deviceName.compareTo("Computer") != 0 ) {
            if(codeGenerationTemplate.getBoard() != null)
            codeGenerationTemplate.getBoard().setName(deviceName);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() ==1 && e.getButton() == MouseEvent.BUTTON1 && ((deviceName == null) || deviceName.compareTo("Computer") != 0) )
        {


            if(codeGenerationTemplate.getBoard() != null){
            mainEntrance.showRightSideBar();
                mainEntrance.setBoardForRightSideBar(codeGenerationTemplate.getBoard());}

            isSelected = true;
            ProjectConstants.init().setBoardView(this);
            parentView.setSelectedBoardView(this);
            repaint(50L);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger()   && ((deviceName == null) || deviceName.compareTo("Computer") != 0))
        {
            showPopUpMenu(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()  &&  ((deviceName == null) || deviceName.compareTo("Computer") != 0))
        {
            showPopUpMenu(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public void showPopUpMenu(MouseEvent e)
    {
        rightClickPopUpMenu = new RightClickPopUpMenu();
        rightClickPopUpMenu.addMenuItem("Program", this);
        rightClickPopUpMenu.addMenuItem("Configure", this);
        rightClickPopUpMenu.addMenuItem("Details", this);

        rightClickPopUpMenu.show(this,e.getX(),e.getY());
    }


    public PView getParentView() {
        return parentView;
    }

    public void setParentView(PView parentView) {
        this.parentView = parentView;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;

    }

    public LineModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineModel lineModel) {
        this.lineModel = lineModel;
    }





    public BoardView(PView parentView, ProjectConstants.DEVICE_TYPE selectedDevice,LiveDataTableModel liveDataTableModel)
    {
this.addMouseListener(this);
this.addMouseMotionListener(this);

        mainEntrance  = parentView.getParentView();

        isHaveProblem = false;
        this.parentView = parentView;
        this.isSelected = false;
        type = selectedDevice;
        this.selectedDevice = selectedDevice;
        try {
            if(selectedDevice == ProjectConstants.DEVICE_TYPE.ARDUINO) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("arduino_img.jpg"));
                codeGenerationTemplate = new ArduinoTemplate(this,liveDataTableModel);

            }
            else if(selectedDevice == ProjectConstants.DEVICE_TYPE.BEAGLEBONE) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("beaglebone_img.jpg"));
            }
else if (selectedDevice == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("raspberrypi_img.png"));
                codeGenerationTemplate = new RaspberryPiTemplate(this,liveDataTableModel);
            }
            else if (selectedDevice == ProjectConstants.DEVICE_TYPE.Server)
            {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("notebook.png"));
            }

            setStatusCode(ProjectConstants.STATUS_CODE.CONNECTION_NOT_ESTABLISHED);
        } catch (IOException e) {
            e.printStackTrace();
        }



        backgroundImageHeight = backgroundImage.getHeight(null);
        backgroundImageWidth = backgroundImage.getWidth(null);
        setPreferredSize(new Dimension(backgroundImageWidth+10,backgroundImageHeight+35));




    }

    public BoardView(PView parentView, ProjectConstants.DEVICE_TYPE selectedDevice)
    {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        mainEntrance = parentView.getParentView();
        isHaveProblem = false;
        this.parentView = parentView;
        this.isSelected = false;
        type = selectedDevice;
        this.selectedDevice = selectedDevice;
        try {
            if(selectedDevice == ProjectConstants.DEVICE_TYPE.ARDUINO) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("arduino_img.jpg"));
                codeGenerationTemplate = new ArduinoTemplate(this);

            }
            else if(selectedDevice == ProjectConstants.DEVICE_TYPE.BEAGLEBONE) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("beaglebone_img.jpg"));
            }
            else if (selectedDevice == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI) {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("raspberrypi_img.png"));
                codeGenerationTemplate = new RaspberryPiTemplate(this);
            }
            else if (selectedDevice == ProjectConstants.DEVICE_TYPE.Server)
            {
                backgroundImage = ImageIO.read(ClassLoader.getSystemResource("notebook.png"));
            }

            setStatusCode(ProjectConstants.STATUS_CODE.CONNECTION_NOT_ESTABLISHED);
        } catch (IOException e) {
            e.printStackTrace();
        }



        backgroundImageHeight = backgroundImage.getHeight(null);
        backgroundImageWidth = backgroundImage.getWidth(null);
        setPreferredSize(new Dimension(backgroundImageWidth+10,backgroundImageHeight+35));




    }

    @Override
    protected void paintComponent(Graphics g) {


            if (isSelected && type != ProjectConstants.DEVICE_TYPE.Server) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setColor(Color.RED);
                graphics2D.setStroke(new BasicStroke(2));

                graphics2D.drawRect(0, 0, getWidth(), getHeight());


            }
        if(isHaveProblem)
        {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(Color.BLUE);
            graphics2D.setStroke(new BasicStroke(2));

            graphics2D.drawRect(0, 0, getWidth(), getHeight());
        }
            g.drawImage(backgroundImage, 10, 25, null);
        if(ProjectConstants.DEVICE_TYPE.Server != type)
            g.drawImage(status_img, 10, backgroundImageHeight - status_img.getHeight(null) + 25, null);
        if(receivedData != null && !receivedData.isEmpty())
            g.drawString(receivedData,10,15);
        if(deviceName == null || deviceName.length() == 0)
        {
            g.drawString(("Device "+deviceNumber), 10, backgroundImageHeight + 35);

        }
        else{
            g.drawString(deviceName, 10, backgroundImageHeight + 35);

        }





    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = anchorPoint.x;
        int y = anchorPoint.y;
        Point mouseLocation = e.getLocationOnScreen();

        Point desiredLocation = new Point(mouseLocation.x-backgroundImageWidth/2,mouseLocation.y-getParent().getLocationOnScreen().y - backgroundImageHeight/2);

        setLocation(desiredLocation);
        parentView.getConnectionLinesComponent().removeLine(lineModel);
        parentView.getConnectionLinesComponent().addLine(lineModel);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        anchorPoint = e.getLocationOnScreen();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }




    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand().compareTo("Program") == 0)
       {
           if(codeGenerationTemplate.getBoard() == null)
           {
               JOptionPane.showMessageDialog(null, "You must configure board first.");
           }
           else {
               DeviceProgrammingScreen deviceProgrammingScreen = new DeviceProgrammingScreen(codeGenerationTemplate);
               deviceProgrammingScreen.show();
           }

       }
        else if(e.getActionCommand().compareTo("Configure") == 0)
       {
           DeviceConfigurationDialog deviceConfigurationDialog = new DeviceConfigurationDialog(codeGenerationTemplate);
           deviceConfigurationDialog.setPreferredSize(new Dimension(980,600));
           deviceConfigurationDialog.setMinimumSize(new Dimension(980,600));


           deviceConfigurationDialog.pack();
           deviceConfigurationDialog.setVisible(true);
       }
        else if(e.getActionCommand().compareTo("Details") == 0)
       {

       }
    }

    public ProjectConstants.DEVICE_TYPE getType() {
        return type;
    }

    public void setType(ProjectConstants.DEVICE_TYPE type) {
        this.type = type;
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }



    @Override
    public void onReceivedData(Board board,ReceivedDataModel receivedData) {
        isHaveProblem =false;
        for(ReceivedSensorData receivedSensorData:receivedData.getSensors())
        {
            if((receivedSensorData.getSensorStatus() == ProjectConstants.SENSOR_STATUS.UPPERTHRESHOLDEXCEED) || (receivedSensorData.getSensorStatus() == ProjectConstants.SENSOR_STATUS.LOWERTHRESHOLDEXCEED))
                isHaveProblem = true;
            repaint();
        }
    }

    @Override
    public void connectionLost(Board board) {

    }

    @Override
    public void connectionEstablished(Board board) {

    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public void setConfigured(boolean configured) {
        isConfigured = configured;
    }
}
