package proviz.uicomponents.rightsidebar;

import proviz.models.devices.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Burak on 2/16/17.
 */
public class BoardDetailView extends JPanel {

    private JLabel makeAndNameLabel;
    private JLabel totalAvailableTitleLabel;
    private JLabel analogInputLabel;
    private JLabel digitalInputLabel;
    private JLabel i2cInputLabel;
    private JLabel spiBusLabel;
    private JLabel serialPortPairsLabel;
    private JLabel powerSupportTitleLabel;
    private JLabel powerSupportLabel;

    public BoardDetailView()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        setBorder(new EmptyBorder(10,10,0,0));


        makeAndNameLabel = new JLabel("");
        analogInputLabel = new JLabel("");
        digitalInputLabel = new JLabel("");
        totalAvailableTitleLabel = new JLabel("Total Available Pins");

        i2cInputLabel = new JLabel("");
        spiBusLabel = new JLabel("");
        serialPortPairsLabel = new JLabel("");
        powerSupportTitleLabel = new JLabel("");

        powerSupportLabel = new JLabel("");

        add(makeAndNameLabel);
        add(analogInputLabel);
        add(digitalInputLabel);
        add(totalAvailableTitleLabel);
        add(i2cInputLabel);
        add(spiBusLabel);
        add(serialPortPairsLabel);
        add(powerSupportTitleLabel);
        add(powerSupportLabel);



    }


    public void changeData(Board board)
    {
        makeAndNameLabel.setText(board.getMake() +" "+ board.getName());
        totalAvailableTitleLabel.setText("Total Available Pins");

        analogInputLabel.setText("Analog: "+board.AvailableAnalogPinNumber());
        digitalInputLabel.setText("Digital: "+ board.AvailableDigitalPinNumber());
        i2cInputLabel.setText("I2C: "+board.Availablei2cPinNumber());
        spiBusLabel.setText("SPI: "+board.getSpiBusNumber());
        serialPortPairsLabel.setText("Serial Port: "+board.getSpiBusNumber());
        powerSupportTitleLabel.setText("Supported Power Sources");
        String powerText = "";
        if(board.isHas3_3v())
            powerText += "3.3V";
        if(board.isHas5v())
            powerText +=",5V";
        powerSupportLabel.setText(powerText);

        revalidate();
    }


}
