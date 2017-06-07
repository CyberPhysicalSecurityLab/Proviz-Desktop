package proviz;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import freemarker.template.TemplateException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import proviz.asci.SensorAddDialog;
import proviz.codedistribution.CodeDistributionManager;
import proviz.codegeneration.CodeGeneration;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.codeprogramming.CodeProgrammingManager;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.FloatShowHideAnimation;
import proviz.models.Bound;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Sensor;
import proviz.thirdpartyconnections.tablet.TabletConnectionCreatorDialog;
import proviz.uicomponents.BoardDetailView;
import proviz.uicomponents.SensorAddDetailView;
import proviz.uicomponents.rightsidebar.SensorListView;
import proviz.uicomponents.rightsidebar.SideBarMenu;
import proviz.uicomponents.rightsidebar.SideBarMenuSection;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * Created by Burak on 1/15/17.
 */
public class DeviceProgrammingScreen implements MouseListener {
    public JButton openFileBttn;
    public JButton saveBttn;
    public JButton addSensorBttn;
    public JButton compileBttn;
    public JTabbedPane tabbedPane1;
    public JPanel visualProgrammingPanel;
    public JPanel codeProgrammingPanel;
    public JLabel flashStatusLabel;
    public JLabel connectionTypeStatus;
    public JPanel visualRightSideBar;
    public JPanel visualSensorPanel;
    public JPanel contentPanel;
    private CodeDistributionManager codeDistributionManager;

    private RSyntaxTextArea codeTextArea;

    private SensorListView sensorListView;
    private JPanel emptySensorPanel;
    private ArrayList<JLabel> sensorListArray;
    private CodeGenerationTemplate codeGenerationTemplate;
    private Box box;
    private Sensor activeSensorForRightSideBar;
    private SensorAddDetailView sensorAddDetailView;
    private ArrayList<Sensor> sensors;
    private FloatShowHideAnimation floatShowHideAnimation;

    private CodeProgrammingManager codeProgrammingManager;

    private JTree tree;

    public DeviceProgrammingScreen(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
        sensorListArray = new ArrayList<>();
        activeSensorForRightSideBar = null;
        initializeUIComponents();
    }


    private void initializeUIComponents() {
        addSensorBttn.addMouseListener(this);
        visualSensorPanel.setLayout(new BoxLayout(visualSensorPanel, BoxLayout.Y_AXIS));
        emptySensorPanel = new JPanel();
        emptySensorPanel.setLayout(new BorderLayout());
        emptySensorPanel.setVisible(false);
        JLabel emptyLabel = new JLabel("No Sensor Found");
        visualSensorPanel.addMouseListener(this);
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emptySensorPanel.add(emptyLabel, BorderLayout.CENTER);
        GridConstraints gridConstraints = new GridConstraints();
        gridConstraints.setFill(GridConstraints.FILL_BOTH);
        visualSensorPanel.add(emptySensorPanel, gridConstraints);
        GridConstraints gridConstraints1 = new GridConstraints();
        gridConstraints1.setFill(GridConstraints.ALIGN_LEFT);
        box = Box.createVerticalBox();
        visualSensorPanel.add(box, gridConstraints);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(codeGenerationTemplate.getBoard().getUserFriendlyName());
        tree = new JTree(rootNode);
        tree.setAlignmentX(tree.LEFT_ALIGNMENT);
        Dimension dimension = new Dimension(visualSensorPanel.getWidth(), 100);
        tree.setMinimumSize(dimension);
        tree.setRootVisible(false);
        tree.setVisible(false);
        tree.setBackground(new Color(238, 238, 238));
        DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {


                JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                if (!leaf) {
                    Font font = new Font("Courier New", 2, 20);
                    label.setForeground(Color.BLACK);
                    label.setFont(font);
                    label.setOpaque(true);
                    label.setBackground(new Color(238, 238, 238));
                    this.setEnabled(false);
                    label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                } else {
                    Font font = new Font("Courier New", 2, 15);
                    label.setFont(font);
                    label.setOpaque(true);
                    label.setForeground(Color.BLACK);
                    label.setBackground(new Color(238, 238, 238));

                    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                }


                return label;

            }
        };
        defaultTreeCellRenderer.setLeafIcon(null);


        tree.setCellRenderer(defaultTreeCellRenderer);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode mutableTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (mutableTreeNode != null) {

                    if (mutableTreeNode.isLeaf()) {
                        Bound bound = getBoundFrom(mutableTreeNode);
                        if (bound == null)
                            throw new NullPointerException("Bound was not be founded. Check!");
                        showRightSideBar(bound, true);
                    } else {
                        showRightSideBar(false);
                    }
                }
            }
        });

        box.add(tree);



        compileBttn.addMouseListener(this);
        sensors = codeGenerationTemplate.getBoard().getSensors();

        if (!sensors.isEmpty()) {
            for (Sensor sensor : sensors) {
                sensor.setParentBoard(codeGenerationTemplate.getBoard());
                addSensor(sensor, true);
            }
        } else {
            emptySensorPanel.setVisible(true);
        }

        connectionTypeStatus.setText(codeGenerationTemplate.getConnectionType().toString());
        SideBarMenu sideBarMenu = new SideBarMenu(SideBarMenu.SideBarMode.TOP_LEVEL, true, 160, true);
        sensorAddDetailView = new SensorAddDetailView(codeGenerationTemplate.getBoard(), 160, 240, false);
        SideBarMenuSection sideBarMenuSection = new SideBarMenuSection(sideBarMenu, "Sensor Detail", sensorAddDetailView, null);
        sideBarMenu.addSection(sideBarMenuSection);

        proviz.uicomponents.rightsidebar.BoardDetailView boardDetailView = new proviz.uicomponents.rightsidebar.BoardDetailView();
        boardDetailView.changeData(codeGenerationTemplate.getBoard());
        SideBarMenuSection boardDetailSection = new SideBarMenuSection(sideBarMenu, "Board Detail", boardDetailView, null);
        sideBarMenu.addSection(boardDetailSection);
        sideBarMenuSection.expand();
        boardDetailSection.expand();
        visualRightSideBar.setLayout(new BoxLayout(visualRightSideBar, BoxLayout.Y_AXIS));

        visualRightSideBar.add(sideBarMenu);
        visualProgrammingPanel.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                floatShowHideAnimation = new FloatShowHideAnimation(visualProgrammingPanel, visualRightSideBar);

            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {

            }
        });
        hideRightSideBar();
        initializeCodeArea();


    }


    private Bound getBoundFrom(DefaultMutableTreeNode mutableTreeNode) {
        String rootNodeValue = mutableTreeNode.getParent().toString();

        String selectedValue = (String) mutableTreeNode.getUserObject();

        for (Sensor sensor : DataManager.getInstance().activeSensors) {
            if (sensor.getName().compareTo(rootNodeValue) == 0) {
                for (Variable variable : sensor.getVariables()) {
                    if (variable.getPreferredName().compareTo(selectedValue) == 0) {
                        return variable.getBound();
                    }
                }
            }
        }

        return null;
    }


    public void addSensor(Sensor sensor, boolean isOnlyViewAdd) {
        if (emptySensorPanel.isVisible())
            emptySensorPanel.setVisible(false);

        codeGenerationTemplate.getBoardView().getParentView().getParentView().getSensorListView().addSensor(sensor.getName());
        DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(sensor.getName());
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();

        for (Variable variable : sensor.getVariables()) {

            if (variable.isCommunicationVariable() == true) {

                DefaultMutableTreeNode innerNode = new DefaultMutableTreeNode(variable.getPreferredName());
                sensorNode.add(innerNode);
            }
        }
        rootNode.add(sensorNode);
        ((DefaultTreeModel) tree.getModel()).reload();
        tree.setVisible(true);
        if (isOnlyViewAdd == false)
            codeGenerationTemplate.getBoard().getSensors().add(sensor);

        DataManager.getInstance().activeSensors.add(sensor);
    }


    private void showRightSideBar(Bound bound, boolean isLeaf) {
        visualRightSideBar.setVisible(true);
        sensorAddDetailView.setViewChange(bound, isLeaf);
        visualRightSideBar.revalidate();
    }

    private void showRightSideBar(boolean isLeaf) {
        visualRightSideBar.setVisible(true);
        sensorAddDetailView.setViewChange(isLeaf);
        visualRightSideBar.revalidate();
    }

    private void hideRightSideBar() {
        visualRightSideBar.setVisible(false);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == addSensorBttn) {
            SensorAddDialog sensorAddDialog = new SensorAddDialog(this, codeGenerationTemplate);

            sensorAddDialog.setResizable(false);
            sensorAddDialog.pack();

            sensorAddDialog.setVisible(true);

        } else if (e.getSource() == compileBttn) {

            if (tabbedPane1.getSelectedIndex() == 1) {
                String code = codeTextArea.getText().replace('\n', ' ');

                codeProgrammingManager = new CodeProgrammingManager(code, codeGenerationTemplate);
                codeProgrammingManager.compile();
            }

            codeDistributionManager = new CodeDistributionManager(codeGenerationTemplate, false);
            codeDistributionManager.flashCode2Device();


        }


        if (e.getSource() == visualSensorPanel) {
            hideRightSideBar();
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void initializeCodeArea() {
        codeTextArea = new RSyntaxTextArea(25, 60);
        codeTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        codeTextArea.setCodeFoldingEnabled(true);
        codeTextArea.setMarkOccurrences(true);
        RTextScrollPane scrollPane = new RTextScrollPane(codeTextArea);
        GridConstraints constraints = new GridConstraints();
        constraints.setFill(GridConstraints.FILL_BOTH);
        codeProgrammingPanel.add(scrollPane, constraints);
    }

    public void show() {
        JFrame window = new JFrame("Device Programming Window");
        window.setSize(1024, 768);
        window.setContentPane(contentPanel);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);
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
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.setMinimumSize(new Dimension(1024, 768));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        contentPanel.add(toolBar1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        openFileBttn = new JButton();
        openFileBttn.setIcon(new ImageIcon(getClass().getResource("/icons/small/opened_folder.png")));
        openFileBttn.setLabel("");
        openFileBttn.setText("");
        toolBar1.add(openFileBttn);
        saveBttn = new JButton();
        saveBttn.setIcon(new ImageIcon(getClass().getResource("/icons/small/save.png")));
        saveBttn.setLabel("");
        saveBttn.setText("");
        toolBar1.add(saveBttn);
        addSensorBttn = new JButton();
        addSensorBttn.setIcon(new ImageIcon(getClass().getResource("/icons/small/plus.png")));
        addSensorBttn.setLabel("");
        addSensorBttn.setText("");
        toolBar1.add(addSensorBttn);
        compileBttn = new JButton();
        compileBttn.setIcon(new ImageIcon(getClass().getResource("/icons/small/circled_play.png")));
        compileBttn.setLabel("");
        compileBttn.setText("");
        toolBar1.add(compileBttn);
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setTabPlacement(3);
        contentPanel.add(tabbedPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 692), null, 0, false));
        visualProgrammingPanel = new JPanel();
        visualProgrammingPanel.setLayout(new GridBagLayout());
        visualProgrammingPanel.setBackground(new Color(-12508113));
        visualProgrammingPanel.setEnabled(true);
        tabbedPane1.addTab("Visual", visualProgrammingPanel);
        visualRightSideBar = new JPanel();
        visualRightSideBar.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        visualRightSideBar.setBackground(new Color(-14675647));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        visualProgrammingPanel.add(visualRightSideBar, gbc);
        visualSensorPanel = new JPanel();
        visualSensorPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        visualProgrammingPanel.add(visualSensorPanel, gbc);
        codeProgrammingPanel = new JPanel();
        codeProgrammingPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Code", codeProgrammingPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        flashStatusLabel = new JLabel();
        flashStatusLabel.setHorizontalAlignment(10);
        flashStatusLabel.setText("Not Flashed");
        panel1.add(flashStatusLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        connectionTypeStatus = new JLabel();
        connectionTypeStatus.setHorizontalAlignment(10);
        connectionTypeStatus.setHorizontalTextPosition(4);
        connectionTypeStatus.setText("Bluetooth");
        panel1.add(connectionTypeStatus, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }
}
