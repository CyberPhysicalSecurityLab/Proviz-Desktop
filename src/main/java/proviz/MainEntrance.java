package proviz;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.models.uielements.BoardConnectionViewEntry;
import proviz.thirdpartyconnections.tablet.TabletConnectionCreatorDialog;
import proviz.uicomponents.rightsidebar.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Burak on 9/4/16.
 */
public class MainEntrance implements ActionListener, DragGestureListener, MouseListener {
    private JButton hideLeftPanelBttn;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JToolBar toolBar;
    private JToolBar firstTopMenu;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JToolBar deviceStatusToolbar;
    public JPanel rightBarPanel;
    private JButton button1;
    private JButton tabletButton;
    private JPanel dummy;
    private JMenuBar jMenuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu toolsMenu;
    private JMenu helpMenu;
    private LiveDataTableModel liveDataTableModel;
    private SensorListView sensorListView;
    private BoardDetailView boardDetailView;
    private BoardConnetionView boardConnetionView;

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), (DeviceLabel) dge.getComponent());
    }

    public MainEntrance(JFrame window) {

        $$$setupUI$$$();
        window.setJMenuBar(initializeMenu());


        PView pView = new PView(this);
        pView.setPreferredSize(new Dimension(640, 480));


        GridBagLayout gridBagLayout = new GridBagLayout();
        toolBar.setLayout(gridBagLayout);
        Border border = BorderFactory.createEtchedBorder();
        toolBar.setBorder(border);
        toolBar.setMargin(new Insets(5, 10, 10, 5));
tabletButton.addMouseListener(this);
        toolBar.setOrientation(SwingConstants.VERTICAL);
        GridBagConstraints gridBagConstraintsArduino = new GridBagConstraints();
        gridBagConstraintsArduino.insets = new Insets(10, 10, 10, 10);
        gridBagConstraintsArduino.gridx = 0;
        gridBagConstraintsArduino.fill = GridBagConstraints.VERTICAL;
        gridBagConstraintsArduino.gridy = 0;

        GridBagConstraints gridBagConstraintsBeagle = new GridBagConstraints();
        gridBagConstraintsBeagle.insets = new Insets(10, 10, 10, 10);
        gridBagConstraintsBeagle.fill = GridBagConstraints.VERTICAL;
        gridBagConstraintsBeagle.gridx = 0;
        gridBagConstraintsBeagle.gridy = 1;


        GridBagConstraints gridBagConstraintsRaspb = new GridBagConstraints();
        gridBagConstraintsRaspb.insets = new Insets(10, 10, 10, 10);
        gridBagConstraintsRaspb.gridx = 0;
        gridBagConstraintsRaspb.fill = GridBagConstraints.VERTICAL;
        gridBagConstraintsRaspb.gridy = 2;

        DeviceLabel deviceLabel = new DeviceLabel(DeviceLabel.DEVICE_TYPES.Ardunio);

        DeviceLabel deviceLabel_rasp = new DeviceLabel(DeviceLabel.DEVICE_TYPES.RaspberryPI);
        DeviceLabel deviceLabel_beagle = new DeviceLabel(DeviceLabel.DEVICE_TYPES.BeagleBone);
        deviceLabel.setPview(pView);
        deviceLabel_rasp.setPview(pView);
        deviceLabel_beagle.setPview(pView);


        SideBarMenu rightsideBar = new SideBarMenu(SideBarMenu.SideBarMode.TOP_LEVEL, false, 240, true);
        sensorListView = new SensorListView();
        sensorListView.setSize(new Dimension(260, pView.getPreferredSize().height / 3));
        sensorListView.setLayout(new BorderLayout(240, pView.getPreferredSize().height / 3));


        boardConnetionView = new BoardConnetionView();

        boardDetailView = new BoardDetailView();
        boardDetailView.setSize(new Dimension(240, pView.getPreferredSize().height / 3));


        SideBarMenuSection sidebarSection = new SideBarMenuSection(rightsideBar, "Sensors", sensorListView, null);
        sidebarSection.expand();
        SideBarMenuSection sidebarSection1 = new SideBarMenuSection(rightsideBar, "Connection", boardConnetionView, null);
        sidebarSection1.expand();
        SideBarMenuSection sidebarSection2 = new SideBarMenuSection(rightsideBar, "Device", boardDetailView, null);


        rightsideBar.addSection(sidebarSection);
        rightsideBar.addSection(sidebarSection1);
        rightsideBar.addSection(sidebarSection2);
        rightBarPanel.add(rightsideBar, BorderLayout.CENTER);
        rightBarPanel.setVisible(false);



        toolBar.add(deviceLabel, gridBagConstraintsArduino);
        toolBar.add(deviceLabel_beagle, gridBagConstraintsBeagle);
        toolBar.add(deviceLabel_rasp, gridBagConstraintsRaspb);
        tabbedPane1.add(pView, "Topology");
        setLiveDataTableModel(new LiveDataTableModel());
        LiveDataTable liveDataTable = new LiveDataTable(getLiveDataTableModel());

        tabbedPane1.add(new JScrollPane(liveDataTable), "Data Table");


        new PDropListener(pView, liveDataTableModel);
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(deviceLabel, DnDConstants.ACTION_MOVE, this);
        DragSource ds1 = new DragSource();
        ds1.createDefaultDragGestureRecognizer(deviceLabel_beagle, DnDConstants.ACTION_MOVE, this);
        DragSource ds2 = new DragSource();
        ds2.createDefaultDragGestureRecognizer(deviceLabel_rasp, DnDConstants.ACTION_MOVE, this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareTo(ActionCommands.hideLeftPanelClicked) == 0) {

        }


    }


    public void setBoardForRightSideBar(Board board) {
        if (board == null) {


        } else {
            ArrayList<Sensor> sensors = board.getSensors();
            sensorListView.removeAllLabels();

            for (Sensor sensor : sensors) {
                sensorListView.addSensor(sensor.getName());
            }
            ArrayList<BoardConnectionViewEntry> boardConnectionViewEntries = new ArrayList<>();
            boardConnetionView.setConnectionType(board.getConnectionType());

            switch (board.getConnectionType()) {
                case INTERNET: {
                    BoardConnectionViewEntry boardConnectionViewEntry = new BoardConnectionViewEntry("Ip Address: ", ProjectConstants.init().getIpAddress());
                    boardConnectionViewEntries.add(boardConnectionViewEntry);
                    break;
                }
                case BLUETOOTH_CLASSIC: {

                    BoardConnectionViewEntry boardConnectionViewEntry = new BoardConnectionViewEntry("BT Address: ", "3C-15-C2-CB-F1-09");
                    boardConnectionViewEntries.add(boardConnectionViewEntry);

                    break;
                }
                case SERIAL: {
                    break;
                }
            }


            boardConnetionView.changeBoard(boardConnectionViewEntries);


            boardDetailView.changeData(board);

            rightBarPanel.revalidate();
        }
    }

    public void showRightSideBar() {

        rightBarPanel.setVisible(true);
    }

    public void hideRightSideBar() {
        rightBarPanel.setVisible(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void createUIComponents() {
        FlowLayout flowLayoutTopMenu = new FlowLayout(10);
        flowLayoutTopMenu.setAlignment(FlowLayout.LEFT);
        FlowLayout flowLayoutDeviceInfoMenu = new FlowLayout(10);
        flowLayoutDeviceInfoMenu.setAlignment(FlowLayout.LEFT);
        firstTopMenu = new JToolBar(JToolBar.HORIZONTAL);

        deviceStatusToolbar = new JToolBar(JToolBar.HORIZONTAL);
        deviceStatusToolbar.setLayout(flowLayoutDeviceInfoMenu);
        deviceStatusToolbar.setVisible(false);
        firstTopMenu.setLayout(flowLayoutTopMenu);
    }


    private JMenuBar initializeMenu() {
        jMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        toolsMenu = new JMenu("Tools");
        helpMenu = new JMenu("Help");
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(toolsMenu);
        jMenuBar.add(helpMenu);
        return jMenuBar;
    }

    public LiveDataTableModel getLiveDataTableModel() {
        return liveDataTableModel;
    }

    public void setLiveDataTableModel(LiveDataTableModel liveDataTableModel) {
        this.liveDataTableModel = liveDataTableModel;
    }

    public SensorListView getSensorListView() {
        return sensorListView;
    }

    public void setSensorListView(SensorListView sensorListView) {
        this.sensorListView = sensorListView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tabletButton) {

            TabletConnectionCreatorDialog dialog = new TabletConnectionCreatorDialog();
            dialog.pack();
            dialog.setVisible(true);
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setMinimumSize(new Dimension(1024, 768));
        mainPanel.setPreferredSize(new Dimension(1024, 768));
        mainPanel.setRequestFocusEnabled(false);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1118482));
        panel1.setVisible(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel1, gbc);
        firstTopMenu.setBackground(new Color(-2368549));
        firstTopMenu.setBorderPainted(false);
        firstTopMenu.setFloatable(false);
        firstTopMenu.setFocusCycleRoot(true);
        firstTopMenu.setFocusTraversalPolicyProvider(true);
        firstTopMenu.setInheritsPopupMenu(false);
        firstTopMenu.setMargin(new Insets(5, 10, 5, 10));
        firstTopMenu.setRollover(true);
        firstTopMenu.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        panel1.add(firstTopMenu, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 1, false));
        firstTopMenu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-10830936)));
        button1 = new JButton();
        button1.setIcon(new ImageIcon(getClass().getResource("/icons/small/opened_folder.png")));
        button1.setText("");
        firstTopMenu.add(button1);
        button2 = new JButton();
        button2.setIcon(new ImageIcon(getClass().getResource("/icons/small/save.png")));
        button2.setText("");
        firstTopMenu.add(button2);
        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        firstTopMenu.add(toolBar$Separator1);
        button3 = new JButton();
        button3.setIcon(new ImageIcon(getClass().getResource("/icons/small/print.png")));
        button3.setText("");
        firstTopMenu.add(button3);
        final JToolBar.Separator toolBar$Separator2 = new JToolBar.Separator();
        firstTopMenu.add(toolBar$Separator2);
        tabletButton = new JButton();
        tabletButton.setIcon(new ImageIcon(getClass().getResource("/icons/small/android_tablet.png")));
        tabletButton.setText("");
        firstTopMenu.add(tabletButton);
        final JSeparator separator1 = new JSeparator();
        firstTopMenu.add(separator1);
        button4 = new JButton();
        button4.setIcon(new ImageIcon(getClass().getResource("/icons/small/settings.png")));
        button4.setText("");
        firstTopMenu.add(button4);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setTabPlacement(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(tabbedPane1, gbc);
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setFocusCycleRoot(true);
        toolBar.setMargin(new Insets(5, 5, 0, 5));
        toolBar.setOrientation(1);
        toolBar.setRollover(true);
        toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.02;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        panel2.add(toolBar, gbc);
        rightBarPanel = new JPanel();
        rightBarPanel.setLayout(new BorderLayout(0, 0));
        rightBarPanel.setBackground(new Color(-12828863));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(rightBarPanel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer1, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}

