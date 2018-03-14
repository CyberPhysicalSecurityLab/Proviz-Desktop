package proviz.thirdpartyconnections.tablet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import proviz.MainEntrance;
import proviz.PView;
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

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class TabletConnectionCreatorDialog extends BorderPane {
    private ButtonBar buttonBar;
    private javafx.scene.control.Button negativeButton;
    private javafx.scene.control.Button postiveButton;
    private javafx.scene.control.Button closeButton;
    private IpAddressFormPanel ipAddressFormPanel;
    private TabletConnectionFinalPanel tabletConnectionFinalPanel;
    private String ipAddress;
    private SwipePanelsAnimation spa;
    private StackPane root;
    private PView pView;
    private Pane self;
    private MainEntrance mainEntrance;

    public TabletConnectionCreatorDialog(MainEntrance mainEntrance) {
        this.mainEntrance = mainEntrance;
        this.self = this;
        this.pView = pView;
        initUI();
        ProjectConstants.init().setSessionUUDIwithTablet(sessionIDCreator());
    }

    private void initializePanels() {

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
        Alert wrongIPAdress = new Alert(Alert.AlertType.ERROR);
        wrongIPAdress.setHeaderText("Wrong IP Adress");
        wrongIPAdress.setContentText("IP Address is not valid or is not reachable at moment.");
        wrongIPAdress.showAndWait();
        if (spa.getActivePane() instanceof TabletConnectionFinalPanel) {
            spa.switchScreenB(ipAddressFormPanel,tabletConnectionFinalPanel,root);
        }
    }

    private void onOK() {


//        if (swipePanelsAnimation.getActivePanel() instanceof IpAddressFormPanel) {
//            try {
//                swipePanelsAnimation.LeftSwipe();
//                if (checkIPAddressIfItIsReachable(ipAddressFormPanel.getIpAddress())) {
//                    tabletConnectionFinalPanel.SearchingTargetDeviceFinished(true);
//                    ProjectConstants.init().setTabletIpAddress(ipAddressFormPanel.getIpAddress());
//                    shareSessionIdWithTablet();
//
//
//                } else {
//                    tabletConnectionFinalPanel.SearchingTargetDeviceFinished(false);
//                    wrongIPAddress();
//
//                }
//            } catch (UnknownHostException unknownException) {
//                wrongIPAddress();
//                unknownException.printStackTrace();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        } else if (swipePanelsAnimation.getActivePanel() instanceof TabletConnectionFinalPanel) {
//            dispose();
//        }

    }

    private void onCancel() {
//        if (swipePanelsAnimation.getActivePanel() instanceof IpAddressFormPanel) {
//            dispose();
//        } else if (swipePanelsAnimation.getActivePanel() instanceof TabletConnectionFinalPanel) {
//            swipePanelsAnimation.RightSwipe();
//        }

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


//    public static void main(String[] args) {
//        TabletConnectionCreatorDialog dialog = new TabletConnectionCreatorDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    private void buildButtons(){
        postiveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(spa.getActivePane() instanceof TabletConnectionFinalPanel)
                {
                    mainEntrance.addToStack(false, self);
                }
                else {
                    postiveButton.setText("Finish");
                    spa.switchScreenF(tabletConnectionFinalPanel, ipAddressFormPanel, root);

                }
            }
        });



        negativeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(spa.getActivePane() instanceof IpAddressFormPanel){
                    mainEntrance.addToStack(false, self);
                }
                else if(spa.getActivePane() instanceof TabletConnectionFinalPanel){
                    postiveButton.setText("Next");
                    spa.switchScreenB(ipAddressFormPanel, tabletConnectionFinalPanel, root);
                }
            }
        });


        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainEntrance.addToStack(false, self);
            }
        });
    }
    private void initUI() {

        postiveButton = new Button("Next");
        negativeButton = new Button("Back");
        closeButton = new Button();


        buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(postiveButton, negativeButton);
        buttonBar.setPadding(new javafx.geometry.Insets(10));
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(buttonBar);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPrefHeight(Double.MIN_VALUE);
        setBottom(buttonBox);

        ImageView closeX = new javafx.scene.image.ImageView(new javafx.scene.image.Image("/icons/small/close_x.png"));
        closeX.setFitWidth(15);
        closeX.setPreserveRatio(true);
        closeButton.setGraphic(closeX);
        HBox closeBox = new HBox(closeButton);
        closeBox.setPadding(new javafx.geometry.Insets(5, 5, 0, 0));
        closeBox.setAlignment(Pos.CENTER_RIGHT);
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(closeBox);
        setTop(borderPane);

        ipAddressFormPanel = new IpAddressFormPanel();
        tabletConnectionFinalPanel = new TabletConnectionFinalPanel();

        root = new StackPane();
        root.getChildren().add(ipAddressFormPanel);
        setCenter(root);

        ArrayList<Pane> panes = new ArrayList<>();
        panes.add(ipAddressFormPanel);
        panes.add(tabletConnectionFinalPanel);
        spa = new SwipePanelsAnimation(buttonBar);

        buildButtons();
    }
}