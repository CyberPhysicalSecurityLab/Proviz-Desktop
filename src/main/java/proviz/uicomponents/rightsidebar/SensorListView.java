package proviz.uicomponents.rightsidebar;

import proviz.uicomponents.ThemeColors;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 1/14/17.
 */
public class SensorListView extends JPanel {

    private ArrayList<JLabel> sensorViewList;
    private JPanel emptyPanel;
    public SensorListView() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        sensorViewList = new ArrayList<>();
         emptyPanel = createEmptyJPanel();
        emptyPanel.setVisible(true);
        this.add(emptyPanel);
    }

    private JPanel createEmptyJPanel()
    {
        JPanel emptyPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        emptyPanel.setLayout(borderLayout);
        JLabel jLabel = new JLabel("No Sensor Added");
        jLabel.setVerticalAlignment(JLabel.CENTER);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        emptyPanel.add(jLabel,BorderLayout.CENTER);
        return emptyPanel;
    }

    public  void removeAllLabels()
    {
        for(JLabel label:sensorViewList)
        {
            this.remove(label);
        }
        emptyPanel.setVisible(true);
    }

    public void addSensor(String sensorName)
    {
        if(sensorViewList.size() ==0)
            emptyPanel.setVisible(false);
        JLabel sensorLabel = new JLabel(sensorName,JLabel.LEFT);
        sensorLabel.setMinimumSize(new Dimension(this.getWidth(),40));
        sensorLabel.setMaximumSize(new Dimension(this.getWidth(),40));
        sensorLabel.setSize(new Dimension(this.getWidth(),40));
        sensorLabel.setVerticalAlignment(JLabel.TOP);
        sensorLabel.setForeground(ThemeColors.textColor);
        sensorLabel.setBackground(ThemeColors.listEntryBackgorundColor);
        sensorLabel.setOpaque(true);
        Border matteBorder = new MatteBorder(0,0,3,0,ThemeColors.baseBackgroundColor);
        Border emptyBorder = new EmptyBorder(5, 10, 5, 0);

        sensorLabel.setBorder(new CompoundBorder(matteBorder,emptyBorder));
        add(sensorLabel);
        sensorViewList.add(sensorLabel);
    }


}
