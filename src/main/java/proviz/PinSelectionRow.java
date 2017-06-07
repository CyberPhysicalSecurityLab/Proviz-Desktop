package proviz;

import proviz.models.devices.Pin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 10/8/16.
 */
public class PinSelectionRow extends JPanel {

    private JLabel pinNameLabel;
    private JComboBox<Pin> pinSelectionComboBox;
    private Pin pin;
    public PinSelectionRow(Pin pin, ArrayList<Pin> pinCandidates)
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
this.pin = pin;
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setLayout(gridBagLayout);
        pinNameLabel = new JLabel("test",SwingConstants.LEFT);
        pinNameLabel.setText(pin.getName());





        pinSelectionComboBox = new JComboBox(pinCandidates.toArray());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.1;

        gridBagConstraints.gridx =0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.gridx =1;
        gridBagConstraints2.gridy=0;
        gridBagConstraints2.anchor = GridBagConstraints.CENTER;


        this.add(pinNameLabel,gridBagConstraints);
        this.add(pinSelectionComboBox,gridBagConstraints2);


        }
        public Pin getSelectedPin()
        {
            return (Pin)pinSelectionComboBox.getSelectedItem();
        }
        public String getPinName(){return pinNameLabel.getText();}

    public Pin getSensorPin() {
        return pin;
    }

    public void setSensorPin(Pin pin) {
        this.pin = pin;
    }
}
