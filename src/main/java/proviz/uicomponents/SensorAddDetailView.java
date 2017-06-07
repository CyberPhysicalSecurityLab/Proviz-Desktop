package proviz.uicomponents;

import proviz.models.Bound;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

/**
 * Created by Burak on 1/15/17.
 */
public class SensorAddDetailView extends JPanel implements DocumentListener {

    private JPanel mainPanel;
    private Board board;
    private Bound bound;

    private JTextField upperBoundTextField;
    private JTextField lowerBoundTextField;
    private JTextField sampleRateField;
    JPanel upperBoundPanel,lowerBoundPanel,sampleRatePanel;

    public SensorAddDetailView(Board board,int width, int height,boolean isLeaf)
    {
        this.board = board;
        GridBagLayout  boxLayout = new GridBagLayout();
        this.setLayout(boxLayout);
        Dimension dimension = new Dimension(width,height);
        mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setMaximumSize(dimension);

        mainPanel.setBackground(Color.RED);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill= GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        this.add(mainPanel,gridBagConstraints);

        upperBoundPanel = new JPanel();
        lowerBoundPanel = new JPanel();
        sampleRatePanel = new JPanel();

        upperBoundTextField = initializeView(upperBoundPanel,"Upper Bound");
        lowerBoundTextField = initializeView(lowerBoundPanel,"Lower Bound");
        sampleRateField = initializeView(sampleRatePanel,"Sample Rate");
    }

    private JTextField initializeView(JPanel panel ,String text)
    {
        BorderLayout borderLayout = new BorderLayout(5,0);
        panel.setLayout(borderLayout);

        EmptyBorder border = new EmptyBorder(10,10,10,10);
        panel.setBorder(border);
        JLabel label1 = new JLabel(text);
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setToolTipText(text);
        textField.getDocument().putProperty("owner", textField);
        textField.getDocument().addDocumentListener(this);
        panel.add(label1, BorderLayout.WEST);
        panel.add(textField, BorderLayout.EAST);
        panel.setBackground(new Color(219,219,219));
        panel.setAlignmentX(panel.LEFT_ALIGNMENT);
        mainPanel.add(panel);
        return textField;

    }
    public void setViewChange(boolean isLeaf)
    {
        if(!isLeaf)
        {
            rootNode();
        }

    }
    public void setViewChange(Bound bound,boolean isLeaf)
    {
        this.bound = bound;
        if(isLeaf)
        {
            leafMode(bound);
        }
    }

    private void leafMode(Bound bound)
    {
        upperBoundTextField.setText(Double.toString(bound.getUpperValue()));
        lowerBoundTextField.setText(Double.toString(bound.getLowerValue()));
        sampleRatePanel.setVisible(false);
        upperBoundPanel.setVisible(true);
        lowerBoundPanel.setVisible(true);
    }

    private void rootNode()
    {
        sampleRateField.setText(Integer.toString(board.getSampleRate()));
        sampleRatePanel.setVisible(true);
        upperBoundPanel.setVisible(false);
        lowerBoundPanel.setVisible(false);
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        takeActionForTextField(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
       takeActionForTextField(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private void takeActionForTextField(DocumentEvent e) {

        JTextField textField = (JTextField) e.getDocument().getProperty("owner");
        if (textField != null) {
            String stringValue = null;
            try {
                stringValue = e.getDocument().getText(0, e.getDocument().getLength());
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            if (stringValue.length() > 0) {
                double value = -1;
                    value = Double.parseDouble(stringValue);

                if (value != -1) {
                    if (textField.getToolTipText().compareTo("Upper Bound") == 0) {
                        bound.setUpperValue(value);
                    } else if (textField.getToolTipText().compareTo("Lower Bound") == 0) {
                        bound.setLowerValue(value);
                    } else if (textField.getToolTipText().compareTo("Sample Rate") == 0) {
                        board.setSampleRate((int) value);
                    }
                }
            }
        }
    }
}
