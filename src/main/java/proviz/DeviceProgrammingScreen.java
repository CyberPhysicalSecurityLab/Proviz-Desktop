package proviz;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
//import org.fxmisc.richtext.StyleSpans;
//import org.fxmisc.richtext.StyleSpansBuilder;
import proviz.asci.SensorAddDialog;
import proviz.codedistribution.CodeDistributionManager;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.codeprogramming.CodeProgrammingManager;
import proviz.devicedatalibrary.DataManager;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.uicomponents.SensorAddDetailView;
import proviz.uicomponents.rightsidebar.BoardDetailView;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.SystemColor.text;

public class DeviceProgrammingScreen extends BorderPane {

    private Stage stage;
    private MainEntrance mainEntrance;
    private Button addSensorBttn, closeBttn;
    private Button compileBttn;
    private TabPane tabbedPane1;
    private Tab visualProgrammingPanel;
    private Tab codeProgrammingPanel;
    private HBox labelBox;
    private Label flashStatusLabel;
    private Label connectionTypeStatus;
    private Pane visualSensorPane;
    private TreeView tree;
    private ToolBar toolBar;
    private Pane self;
    private SensorAddDialog sensorAddDialog;
    private boolean isOpen;
    private CodeArea codeArea;
    private SensorAddDetailView sensorAddDetailView;
    private BoardDetailView boardDetailView;

    private Accordion accordion1;
    private Accordion accordion2;
    private VBox accordionBox;

    private CodeProgrammingManager codeProgrammingManager;
    private CodeDistributionManager codeDistributionManager;

    private TreeItem<Object> rootEntry;
    private CodeGenerationTemplate codeGenerationTemplate;

    private boolean isRootClicked;

    private  final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private  final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private  final String PAREN_PATTERN = "\\(|\\)";
    private  final String BRACE_PATTERN = "\\{|\\}";
    private  final String BRACKET_PATTERN = "\\[|\\]";
    private  final String SEMICOLON_PATTERN = "\\;";
    private  final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private  final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private  final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
    }


    public DeviceProgrammingScreen(MainEntrance mainEntrance, CodeGenerationTemplate codeGenerationTemplate){
        self = this;
        this.stage = mainEntrance.getStage();
this.mainEntrance = mainEntrance;
        this.codeGenerationTemplate = codeGenerationTemplate;

        initUI();

    }

    private void initUI(){
        this.setMinSize(905, 500);
        addSensorBttn = new Button();
        compileBttn = new Button();
isRootClicked = false;
codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
//                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });

        ImageView addSensorI = new ImageView(new Image(
                DeviceProgrammingScreen.class.getResourceAsStream("/icons/small/plus.png")));
        addSensorI.setFitWidth(15);
        addSensorI.setPreserveRatio(true);
        addSensorBttn.setGraphic(addSensorI);
        addSensorBttn.setText("Add Sensor");

        ImageView compileI = new ImageView(new Image(
                DeviceProgrammingScreen.class.getResourceAsStream("/icons/small/circled_play.png")));
        compileI.setFitWidth(15);
        compileI.setPreserveRatio(true);
        compileBttn.setGraphic(compileI);
        compileBttn.setText("Compile");
        toolBar = new ToolBar();
        HBox toolBox = new HBox(addSensorBttn, compileBttn);
        toolBox.setPadding(new Insets(5));
        toolBox.setAlignment(Pos.CENTER_LEFT);

        closeBttn = new Button();
        ImageView closeX = new ImageView(new Image("/icons/small/close_x.png"));
        closeX.setFitWidth(15);
        closeX.setPreserveRatio(true);
        closeBttn.setGraphic(closeX);
        HBox closeBox = new HBox(closeBttn);
        closeBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBox = new BorderPane();
        topBox.setLeft(toolBox);
        topBox.setRight(closeBox);
        this.setTop(topBox);

        tabbedPane1 = new TabPane();
        visualProgrammingPanel = new Tab();
        visualProgrammingPanel.setClosable(false);
        visualProgrammingPanel.setText("Visual");
        visualSensorPane = new Pane();
        visualProgrammingPanel.setContent(visualSensorPane);
        codeProgrammingPanel = new Tab();

        codeProgrammingPanel.setClosable(false);
        codeProgrammingPanel.setText("Code");
        codeProgrammingPanel.setContent(codeArea);
        tabbedPane1.getTabs().addAll(visualProgrammingPanel, codeProgrammingPanel);
        tabbedPane1.setSide(Side.BOTTOM);
        this.setCenter(tabbedPane1);

        labelBox = new HBox();
        labelBox.setPrefHeight(30);
        //labelBox.setMaxWidth(1024);

        flashStatusLabel = new Label();
        flashStatusLabel.setText("Not Flashed");
        flashStatusLabel.setPrefWidth(512);
        flashStatusLabel.setAlignment(Pos.CENTER);
        connectionTypeStatus = new Label();
        connectionTypeStatus.setText("Bluetooth");
        connectionTypeStatus.setPrefWidth(512);
        connectionTypeStatus.setAlignment(Pos.CENTER);
        //labelBox.getChildren().addAll(flashStatusLabel, connectionTypeStatus);
        //System.out.println(String.valueOf(labelBox.getMaxWidth()));¡
        //System.out.println(String.valueOf(flashStatusLabel.getMaxWidth()));
        //System.out.println(String.valueOf(connectionTypeStatus.getMaxWidth()));

        addButtonFunctions();

        //this.setBottom(labelBox);

        if(sensorAddDialog != null && sensorAddDialog.getAllSensors().size() > 0){
//            addSensorToScreen();
            /*
            for(Sensor s : sensorAddDialog.getAllSensors()){
                addSensorToScreen(s);
                }
             */
        }

prepareSensorListView();
        prepareSideBar();
    }


    private void addButtonFunctions(){
        addSensorBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();

                sensorAddDialog = new SensorAddDialog(mainEntrance,dialog, (DeviceProgrammingScreen) self);
                dialog.setScene(new Scene(sensorAddDialog, 860, 560));
                //dialog.show();

                mainEntrance.addToStack(false, self);
                mainEntrance.addToStack(true, sensorAddDialog);
            }
        });

        closeBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainEntrance.addToStack(false, self);
            }
        });


        compileBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(tabbedPane1.getSelectionModel().getSelectedIndex() == 1)
                {
                    String code = codeArea.getText().replace('\n', ' ');

                    codeProgrammingManager = new CodeProgrammingManager(code, codeGenerationTemplate);
                    codeProgrammingManager.compile();
                }
                codeDistributionManager = new CodeDistributionManager(codeGenerationTemplate, false);
                codeDistributionManager.flashCode2Device();
                System.out.println("Burak");
            }
        });
    }


    static class UpdateCell extends TreeCell<Object> {

        public void updateItem(Object item, boolean empty){
            super.updateItem(item, empty);
            if(empty || item == null){

            }
            else
                setText(item.toString());
            setFont(Font.font("Courier New", 15));
            setStyle("-fx-background-color: #eeeeee;");
        }

    }

    private void prepareSensorListView()
    {
        tree = new TreeView();
        tree.setCellFactory(cb -> new UpdateCell());
        tree.setPrefSize(1024, 500);
        rootEntry = new TreeItem<>(codeGenerationTemplate.getBoard());

        tree.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<Object> selectedItem = (TreeItem<Object>) newValue;
                setRight(accordionBox);
                if(selectedItem.getValue() instanceof Board)
                {
                    sensorAddDetailView.setBoard((Board) selectedItem.getValue());
                    sensorAddDetailView.hideLowerBoundFieldTextField();
                    sensorAddDetailView.hideUpperBoundFieldTextField();
                    sensorAddDetailView.showSampleRateFieldTextField();
                    System.out.println("Selected Text : " + selectedItem.getValue());
                }
                else if(selectedItem.getValue() instanceof Sensor)
                {
                    sensorAddDetailView.setBoard(((Sensor)selectedItem.getValue()).getParentBoard());
                    sensorAddDetailView.hideLowerBoundFieldTextField();
                    sensorAddDetailView.hideUpperBoundFieldTextField();
                    sensorAddDetailView.showSampleRateFieldTextField();
                    System.out.println("Selected Text : " + selectedItem.getValue());
                }
                else if(selectedItem.getValue() instanceof Variable)
                {
                    sensorAddDetailView.setVariable((Variable)selectedItem.getValue());
                    sensorAddDetailView.hideSampleRateFieldTextField();
                    sensorAddDetailView.showLowerBoundFieldTextField();
                    sensorAddDetailView.showUpperBoundFieldTextField();

                    System.out.println("Selected Text : " + selectedItem.getValue());
                }

                // do what ever you want
            }

        });

        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node node = event.getPickResult().getIntersectedNode();

                if (node instanceof  Label && ((Label) node).getText().compareTo(codeGenerationTemplate.getBoard().getUserFriendlyName())==0)  {
                    if(isRootClicked)
                    {
                        setRight(null);
                        isRootClicked = false;
                    }
                    else
                    {
                        setRight(accordionBox);
                        isRootClicked = true;
                    }
                }
            }
        });
        visualSensorPane.getChildren().add(tree);

//        rootEntry = new TreeItem<>("");


        rootEntry.setExpanded(true);

//        rootEntry.getGraphic().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if(isRootClicked)
//                {
//                    setRight(null);
//                    isRootClicked = false;
//                }
//                else
//                {
//                    setRight(accordionBox);
//                    isRootClicked = true;
//                }
//            }
//        });


//        rootEntry.addEventHandler(Click, new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
//            @Override
//            public void handle(TreeItem.TreeModificationEvent<Object> event) {
//                if(sensorAddDetailView == null){
//                    setRight(accordionBox);
//
//                }
//
//                isOpen = !isOpen;
//                rootEntry.setExpanded(true);
//                tree.setCellFactory(e -> new UpdateCell());
//
//
//            }
//        });
//
//        rootEntry.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
//            @Override
//            public void handle(TreeItem.TreeModificationEvent<Object> event) {
//                rootEntry.setExpanded(false);
//                tree.setCellFactory(e -> new UpdateCell());
//            }
//        });

        tree.setRoot(rootEntry);
    }

    private void prepareSideBar()
    {
        accordion1 = new Accordion();
        accordion2 = new Accordion();
        sensorAddDetailView = new SensorAddDetailView();
        boardDetailView = new BoardDetailView();
        boardDetailView.setBoard(codeGenerationTemplate.getBoard());

        accordionBox = new VBox();
        TitledPane sensorDetails = new TitledPane();
        sensorDetails.setText("Sensor Details");
        sensorDetails.setContent(sensorAddDetailView);
        TitledPane boardDetails = new TitledPane();
        boardDetails.setText("Board Details");
        boardDetails.setContent(boardDetailView);
        boardDetails.setStyle("-fx-highlight-text-fill: lightblue");

        accordion1.getPanes().add(sensorDetails);
        accordion2.getPanes().add(boardDetails);


        accordionBox.getChildren().addAll(accordion1, accordion2);

    }

    public void addSensorToScreen(Sensor sensor){
        TreeItem<Object> root = new TreeItem<>(sensor);

        for(Variable variable:sensor.getVariables())
        {
            if(variable.isCommunicationVariable()) {
                TreeItem<Object> item1 = new TreeItem<>();
                item1.setValue(variable);
                root.getChildren().add(item1);
                item1.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
                    @Override
                    public void handle(TreeItem.TreeModificationEvent<Object> event) {
                        System.out.println("Burak");
                    }
                });
            }
        }


        rootEntry.getChildren().add(root);
        isOpen = false;

        root.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                root.setExpanded(true);
                tree.setCellFactory(e -> new UpdateCell());


            }
        });

        root.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                root.setExpanded(false);
                tree.setCellFactory(e -> new UpdateCell());
            }
        });
        codeGenerationTemplate.getBoard().getSensors().add(sensor);
        DataManager.getInstance().activeSensors.add(sensor);


//        tree.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("click");
//
//                if(sensorAddDetailView == null){
//                    sensorAddDetailView = new SensorAddDetailView();
//                    accordionBox.setPrefWidth(200);
//                    setRight(accordionBox);
//                }
//
//                if (!isOpen) {
//                    sensorDetails.setExpanded(true);
//                    boardDetails.setExpanded(true);
//
//                    System.out.println(isOpen);
//                    isOpen = true;
//
//                } else if (isOpen) {
//                    sensorDetails.setExpanded(false);
//                    boardDetails.setExpanded(false);
//
//                    System.out.println(isOpen);
//                    isOpen = false;
//                }
//
//                Node node = event.getPickResult().getIntersectedNode();
//                // Accept clicks only on node cells, and not on empty spaces of the TreeView
//                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
//                    String name = (String) ((TreeItem)tree.getSelectionModel().getSelectedItem()).getValue();
//                    boolean isLeaf = ((TreeItem) tree.getSelectionModel().getSelectedItem()).isLeaf();
//                    System.out.println("Node click: " + name);
//
//                    BoardDetailView boardDetailView = new BoardDetailView();
//                    boardDetails.setContent(boardDetailView);
//
//                    SensorAddDetailView sensorAddDetailView = new SensorAddDetailView();
//                    sensorDetails.setContent(sensorAddDetailView);
//
//                    if(isLeaf){
//
//                        sensorAddDetailView.setContent(true);
//                        //sensorDetails.setContent();
//
//                    }
//                    else{
//                        sensorAddDetailView.setContent(false);
//                    }
//
//                }
//
//            }
//        });
    }

//    public void addSensor(Sensor sensor, boolean isOnlyViewAdd) {
//
//
//        codeGenerationTemplate.getBoardView().getParentView().getParentView().getSensorListView().addSensor(sensor.getName());
//        DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(sensor.getName());
//        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
//
//        for (Variable variable : sensor.getVariables()) {
//
//            if (variable.isCommunicationVariable() == true) {
//
//                DefaultMutableTreeNode innerNode = new DefaultMutableTreeNode(variable.getPreferredName());
//                sensorNode.add(innerNode);
//            }
//        }
//        rootNode.add(sensorNode);
//        ((DefaultTreeModel) tree.getModel()).reload();
//        tree.setVisible(true);
//        if (isOnlyViewAdd == false)
//            codeGenerationTemplate.getBoard().getSensors().add(sensor);
//
//        DataManager.getInstance().activeSensors.add(sensor);
//    }

    public VBox addDummyVBox(){
        VBox dummyBox = new VBox();
        Text text = new Text("Blah blah blah");
        dummyBox.getChildren().add(text);

        return dummyBox;
    }

//    private  StyleSpans<Collection<String>> computeHighlighting(String text) {
//        Matcher matcher = PATTERN.matcher(text);
//        int lastKwEnd = 0;
//        StyleSpansBuilder<Collection<String>> spansBuilder
//                = new StyleSpansBuilder<>();
//        while(matcher.find()) {
//            String styleClass =
//                    matcher.group("KEYWORD") != null ? "keyword" :
//                            matcher.group("PAREN") != null ? "paren" :
//                                    matcher.group("BRACE") != null ? "brace" :
//                                            matcher.group("BRACKET") != null ? "bracket" :
//                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
//                                                            matcher.group("STRING") != null ? "string" :
//                                                                    matcher.group("COMMENT") != null ? "comment" :
//                                                                            null; /* never happens */ assert styleClass != null;
//            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
//            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
//            lastKwEnd = matcher.end();
//        }
//        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
//        return spansBuilder.create();
//    }
}