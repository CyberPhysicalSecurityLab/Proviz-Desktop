package proviz;

import proviz.models.devices.Board;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import javafx.scene.shape.Line;
import proviz.models.uielements.LineModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Burak on 9/5/16.
 */ public class PView extends JLayeredPane implements DropTargetListener, MouseListener,ActionListener,ComponentListener {

    private DottedJPanel canvas;
    private BoardView selectedBoardView;
    private ArrayList<BoardView> boardViews;
    private boolean isFirstTime;
    private BoardView computerView;
    private ConnectionLinesComponent connectionLinesComponent;
    private MainEntrance mainEntrance;


    public MainEntrance getParentView()
    {
        return mainEntrance;
    }

    public PView(MainEntrance mainEntrance)
    {
        this.setMainEntrance(mainEntrance);
        addMouseListener(this);
        isFirstTime = true;
        boardViews = new ArrayList<>();
        canvas = new DottedJPanel();
        canvas.setLayout(null);
        canvas.setSize(getWidth(),getHeight());
        canvas.setOpaque(true);
        this.setBorder(BorderFactory.createTitledBorder("Topology"));
        this.setOpaque(true);
        addComponentListener(this);
        this.add(canvas,new Integer(1));
        setLayout(null);
        connectionLinesComponent = new ConnectionLinesComponent();


    }

    public void bindLine2Computer(BoardView boardView)
    {
        LineModel lineModel = new LineModel(boardView,computerView,3);
        boardView.setLineModel(lineModel);

        connectionLinesComponent.addLine(lineModel);

    }
    public void insertSensorViewList(BoardView component)
    {
        this.boardViews.add(component);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void drop(DropTargetDropEvent dtde) {


    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragExit(DropTargetEvent dte) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }


    @Override
    public void componentResized(ComponentEvent e) {
        System.out.print(true);
        canvas.setSize(new Dimension(e.getComponent().getWidth(),e.getComponent().getHeight()));
        canvas.repaint(50L);
        connectionLinesComponent.setSize(new Dimension(getWidth(),getHeight()));
        if(isFirstTime && e.getComponent().getWidth()>0 && e.getComponent().getHeight()>0) {

            add(connectionLinesComponent,new Integer(2));
            BoardView computer = new BoardView(this, ProjectConstants.DEVICE_TYPE.Server);
            computer.setDeviceName("Computer");
            BufferedImage computerImage = null;
            try {
                computerImage = ImageIO.read(ClassLoader.getSystemResource("notebook.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            computer.setSize(96, 96);
            computer.setLocation(((getWidth() / 2) - computerImage.getWidth() / 2), ((getHeight() / 2) - computerImage.getHeight() / 2));
            this.add(computer, new Integer(3));
            computerView = computer;
            repaint(50L);
            isFirstTime = false;
        }
    }



    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public BoardView getSelectedBoardView() {
        return selectedBoardView;
    }

    public void setSelectedBoardView(BoardView selectedBoardView) {
        this.selectedBoardView = selectedBoardView;
        for(BoardView boardView : boardViews)
        {
            if(selectedBoardView == boardView)
                continue;
            else {
                if(boardView.isSelected() == true)
                {
                    boardView.setSelected(false);
                    boardView.repaint(50L);
                }

            }

        }
    }

    public BoardView getComputerView() {
        return computerView;
    }

    public ConnectionLinesComponent getConnectionLinesComponent() {
        return connectionLinesComponent;
    }

    public void setConnectionLinesComponent(ConnectionLinesComponent connectionLinesComponent) {
        this.connectionLinesComponent = connectionLinesComponent;
    }



    public MainEntrance getMainEntrance() {
        return mainEntrance;
    }

    public void setMainEntrance(MainEntrance mainEntrance) {
        this.mainEntrance = mainEntrance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mainEntrance.hideRightSideBar();
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
}
