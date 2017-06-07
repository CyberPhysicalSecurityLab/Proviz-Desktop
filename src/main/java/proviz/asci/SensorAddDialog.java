package proviz.asci;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import proviz.DeviceProgrammingScreen;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.library.utilities.SwipePanelsAnimation;
import proviz.models.devices.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SensorAddDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonFinish;
    private JButton buttonCancel;
    private CodeGenerationTemplate codeGenerationTemplate;

    private FinalStageSensorAddPanel finalStageSensorAddPanel;
    private PinSelectionForSensorPanel pinSelectionForSensorPanel;
    private SensorSelectionDialogPanel sensorSelectionDialogPanel;
    public JPanel mainPanel;
    public JButton backBttn;
    public JButton nextbttn;
    private Sensor selectedSensor;
    private SwipePanelsAnimation swipePanelsAnimation;
    private int activePanelIndex = 0;

    private DeviceProgrammingScreen deviceProgrammingScreen;


    public SensorAddDialog(DeviceProgrammingScreen deviceProgrammingScreen, CodeGenerationTemplate codeGenerationTemplate) {
        setContentPane(contentPane);
        this.deviceProgrammingScreen = deviceProgrammingScreen;
        this.codeGenerationTemplate = codeGenerationTemplate;
        setModal(true);
        getRootPane().setDefaultButton(buttonFinish);
        initializePanels();
        activePanelIndex = 0;
        makeButtonUIOperation(0);
        buttonFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        backBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });

        nextbttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNext();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {
        selectedSensor.setParentBoard(codeGenerationTemplate.getBoard());
        deviceProgrammingScreen.addSensor(selectedSensor, false);

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onBack() {

        swipePanelsAnimation.RightSwipe();
        if (activePanelIndex > 0)
            activePanelIndex--;
        makeButtonUIOperation(activePanelIndex);

    }

    private void onNext() {

        swipePanelsAnimation.LeftSwipe();
        if (activePanelIndex != 2)
            activePanelIndex++;
        if (activePanelIndex == 1) {
            selectedSensor = sensorSelectionDialogPanel.getCurrentSelection();
            pinSelectionForSensorPanel.setSelectedSensor(sensorSelectionDialogPanel.getCurrentSelection());
        } else if (activePanelIndex == 2) {
            pinSelectionForSensorPanel.makeChangesOnSelectedSensor();
            finalStageSensorAddPanel.setSensor(selectedSensor);

        }
        makeButtonUIOperation(activePanelIndex);

    }

    private void makeButtonUIOperation(int activePanelIndex) {
        if (activePanelIndex == 0) {
            backBttn.setVisible(false);
            buttonFinish.setVisible(false);
            nextbttn.setVisible(true);
            buttonCancel.setVisible(true);
        } else if (activePanelIndex == (2)) {
            backBttn.setVisible(true);
            buttonFinish.setVisible(true);
            nextbttn.setVisible(false);
            buttonCancel.setVisible(false);
        } else {
            backBttn.setVisible(true);
            buttonFinish.setVisible(false);
            nextbttn.setVisible(true);
            buttonCancel.setVisible(false);
        }
    }

    private void initializePanels() {
        finalStageSensorAddPanel = new FinalStageSensorAddPanel();
        pinSelectionForSensorPanel = new PinSelectionForSensorPanel(codeGenerationTemplate);
        sensorSelectionDialogPanel = new SensorSelectionDialogPanel();

        Dimension dimension = new Dimension(860, 560);
        mainPanel.setPreferredSize(dimension);
        mainPanel.setMaximumSize(dimension);
        mainPanel.setMinimumSize(dimension);
        //mainPanel.setBackground(Color.blue);

        ArrayList<JPanel> panels = new ArrayList<>();
        panels.add(sensorSelectionDialogPanel);
        panels.add(pinSelectionForSensorPanel);
        panels.add(finalStageSensorAddPanel);
        // mainPanel.add(sensorSelectionDialogPanel, BorderLayout.CENTER);
        final Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                swipePanelsAnimation = new SwipePanelsAnimation(mainPanel, panels);
            }
        });
        t0.setDaemon(true);
        t0.start();


    }




    public int getActivePanelIndex() {
        return activePanelIndex;
    }

    public void setActivePanelIndex(int activePanelIndex) {
        this.activePanelIndex = activePanelIndex;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonFinish = new JButton();
        buttonFinish.setText("Finish");
        panel2.add(buttonFinish, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextbttn = new JButton();
        nextbttn.setText("Next");
        panel2.add(nextbttn, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backBttn = new JButton();
        backBttn.setText("Back");
        panel2.add(backBttn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        contentPane.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
