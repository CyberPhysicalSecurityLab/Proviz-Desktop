package proviz.thirdpartyconnections.tablet;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import proviz.ProjectConstants;
import proviz.codedistribution.CodeDistributionManager;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.connection.client.TabletConnectionManager;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.SwipePanelsAnimation;
import proviz.models.client.request.CreateConnectionRequest;
import proviz.models.client.request.TopologyRequest;
import proviz.models.client.response.CreateConnectionResponse;
import proviz.models.client.response.TopologyResponse;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.models.tablet.Cluster;
import proviz.models.tablet.ClusterBoard;
import proviz.models.tablet.ClusterSensor;
import proviz.models.tablet.ClusterSensorVariable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

public class TabletConnectionCreatorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonPositive;
    private JButton buttonNegative;
    private JPanel mainPanel;

    private IpAddressFormPanel ipAddressFormPanel;
    private TabletConnectionFinalPanel tabletConnectionFinalPanel;

    private SwipePanelsAnimation swipePanelsAnimation;

    public TabletConnectionCreatorDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonPositive);

        initializePanels();
        buttonPositive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonNegative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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


        ProjectConstants.init().setSessionUUDIwithTablet(sessionIDCreator());
    }

    private void initializePanels() {
        ipAddressFormPanel = new IpAddressFormPanel();
        tabletConnectionFinalPanel = new TabletConnectionFinalPanel();

        ipAddressFormPanel.initiliazeUI();
        tabletConnectionFinalPanel.initiliazeUI();

        Dimension dimension = new Dimension(860, 560);
        mainPanel.setPreferredSize(dimension);
        mainPanel.setMaximumSize(dimension);
        mainPanel.setMinimumSize(dimension);

        ArrayList<JPanel> panels = new ArrayList<>();
        panels.add(ipAddressFormPanel);
        panels.add(tabletConnectionFinalPanel);
        final Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                swipePanelsAnimation = new SwipePanelsAnimation(mainPanel, panels);
            }
        });
        t0.setDaemon(true);
        t0.start();

    }

    private String sessionIDCreator() {
        String sessionUUIDstr = "";
        boolean isAcceptable = true;
        // todo in future, we need to be sure that every uuid values are unique..
        do {
            UUID sessionUUID = UUID.randomUUID();
            sessionUUIDstr = sessionUUID.toString();
        } while (!isAcceptable);
        return sessionUUIDstr;
    }

    private void wrongIPAddress() {
        JOptionPane.showMessageDialog(this, "IP Address is not valid or is not reachable at moment.");
        if (swipePanelsAnimation.getActivePanel() instanceof TabletConnectionFinalPanel) {
            swipePanelsAnimation.RightSwipe();
        }
    }

    private void onOK() {


        if (swipePanelsAnimation.getActivePanel() instanceof IpAddressFormPanel) {
            try {
                swipePanelsAnimation.LeftSwipe();
                if (checkIPAddressIfItIsReachable(ipAddressFormPanel.getIpAddress())) {
                    tabletConnectionFinalPanel.SearchingTargetDeviceFinished(true);
                    ProjectConstants.init().setTabletIpAddress(ipAddressFormPanel.getIpAddress());
                    shareSessionIdWithTablet();


                } else {
                    tabletConnectionFinalPanel.SearchingTargetDeviceFinished(false);
                    wrongIPAddress();

                }
            } catch (UnknownHostException unknownException) {
                wrongIPAddress();
                unknownException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (swipePanelsAnimation.getActivePanel() instanceof TabletConnectionFinalPanel) {
            dispose();
        }

    }

    private void onCancel() {
        if (swipePanelsAnimation.getActivePanel() instanceof IpAddressFormPanel) {
            dispose();
        } else if (swipePanelsAnimation.getActivePanel() instanceof TabletConnectionFinalPanel) {
            swipePanelsAnimation.RightSwipe();
        }

    }

    private boolean checkIPAddressIfItIsReachable(String ipAddress) throws IOException {

        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        if (inetAddress.isReachable(5000)) {
            return true;
        } else {
            return false;
        }
    }

    private void shareSessionIdWithTablet() throws IOException {
        CreateConnectionRequest createConnectionRequest = new CreateConnectionRequest();
        createConnectionRequest.setSessionId(ProjectConstants.init().getSessionUUDIwithTablet());
        Call<CreateConnectionResponse> responseCall = TabletConnectionManager.getInstance().getTabletConnectionMethods().createConnectionBySendingSessionID(createConnectionRequest);

        responseCall.enqueue(new Callback<CreateConnectionResponse>() {
            @Override
            public void onResponse(Call<CreateConnectionResponse> call, Response<CreateConnectionResponse> response) {
                try {
                    CreateConnectionResponse createConnectionResponse = response.body();
                    if (createConnectionResponse.getResponse() == ProjectConstants.OPERATION_RESULT.SUCCESS) {
                        tabletConnectionFinalPanel.SessionInitiationFinished(true);
                        syncTopologyWithTablet();
                    } else if (createConnectionResponse.getResponse() == ProjectConstants.OPERATION_RESULT.FAIL) {
                        tabletConnectionFinalPanel.SessionInitiationFinished(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CreateConnectionResponse> call, Throwable t) {
                tabletConnectionFinalPanel.SessionInitiationFinished(false);
            }
        });



    }

    private void syncTopologyWithTablet() throws IOException {
        Cluster cluster = createTopologyClusterForTablet();

        TopologyRequest topologyRequest = new TopologyRequest();
        topologyRequest.setSessionId(ProjectConstants.init().getSessionUUDIwithTablet());
        topologyRequest.setCluster(cluster);

        Call<TopologyResponse> responseCall = TabletConnectionManager.getInstance().getTabletConnectionMethods().sendTopology(topologyRequest);

        responseCall.enqueue(new Callback<TopologyResponse>() {
            @Override
            public void onResponse(Call<TopologyResponse> call, Response<TopologyResponse> response) {
                TopologyResponse topologyResponse = response.body();
                if (topologyResponse.getOperationResult() == ProjectConstants.OPERATION_RESULT.SUCCESS) {
                    tabletConnectionFinalPanel.SynchingCurrentTopologyFinished(true);
                    informingOtherBoards();

                } else if (topologyResponse.getOperationResult() == ProjectConstants.OPERATION_RESULT.FAIL) {
                    tabletConnectionFinalPanel.SynchingCurrentTopologyFinished(false);
                }

            }

            @Override
            public void onFailure(Call<TopologyResponse> call, Throwable t) {
                tabletConnectionFinalPanel.SynchingCurrentTopologyFinished(false);

            }
        });


    }

    private void informingOtherBoards() {
        //todo it works only for raspberry pi..
        try {
            for (Board board : DataManager.getInstance().getActiveBoards()) {
                if (board.getDevice_type() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI) {

                    CodeGenerationTemplate codeGenerationTemplate = board.getCodeGenerationTemplate();
                    CodeDistributionManager codeDistributionManager = new CodeDistributionManager(codeGenerationTemplate, board.isProgrammed());
                    codeDistributionManager.flashCode2Device();

                } else {
                    continue;
                }
            }
            tabletConnectionFinalPanel.InformingBoardsFinished(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            tabletConnectionFinalPanel.InformingBoardsFinished(false);
        }

    }

    private Cluster createTopologyClusterForTablet() {
        Cluster cluster = new Cluster();
        cluster.setSessionId(ProjectConstants.init().getSessionUUDIwithTablet());
        ArrayList<Board> activeBoards = DataManager.getInstance().getActiveBoards();
        ArrayList<ClusterBoard> clusterBoards = new ArrayList<>();
        for (Board board : activeBoards) {
            ClusterBoard clusterBoard = new ClusterBoard();
            clusterBoard.setBoardId(board.getUniqueId());
            clusterBoard.setBoardName(board.getName());
            clusterBoard.setBoardType(board.getDevice_type());
            ArrayList<ClusterSensor> clusterSensors = new ArrayList<>();
            for (Sensor sensor : board.getSensors()) {
                ClusterSensor clusterSensor = new ClusterSensor();
                ArrayList<Variable> variables = sensor.getVariables();
                ArrayList<ClusterSensorVariable> clusterSensorVariables = new ArrayList<>();
                for (Variable variable : variables) {
                    if (variable.isCommunicationVariable() == true) {
                        ClusterSensorVariable clusterSensorVariable = new ClusterSensorVariable();
                        //todo threshold ayarlamasi yok yapmak lazim, simdilik arbitrary verdim.
                        clusterSensorVariable.setMaxThreshold(100);
                        clusterSensorVariable.setMinThreshold(20);
                        clusterSensorVariable.setVariableName(variable.getPreferredName());
                        clusterSensorVariables.add(clusterSensorVariable);
                    }

                }

                clusterSensor.setSensorVariables(clusterSensorVariables);
                clusterSensor.setSensorId(sensor.getUniqueIdWithUnderScore());
                clusterSensor.setSensorName(sensor.getName());
                clusterSensors.add(clusterSensor);
            }
            clusterBoard.setSensors(clusterSensors);
            clusterBoards.add(clusterBoard);
        }
        cluster.setBoards(clusterBoards);
        return cluster;
    }


    public static void main(String[] args) {
        TabletConnectionCreatorDialog dialog = new TabletConnectionCreatorDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
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
        contentPane.setMinimumSize(new Dimension(980, 600));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonNegative = new JButton();
        buttonNegative.setText("Back");
        panel2.add(buttonNegative, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPositive = new JButton();
        buttonPositive.setText("Next");
        panel2.add(buttonPositive, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
