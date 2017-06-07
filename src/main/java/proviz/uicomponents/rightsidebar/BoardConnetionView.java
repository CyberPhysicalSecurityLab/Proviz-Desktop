package proviz.uicomponents.rightsidebar;

import com.intellij.uiDesigner.core.GridLayoutManager;
import proviz.ProjectConstants;
import proviz.models.uielements.BoardConnectionViewEntry;
import sun.swing.BakedArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 2/16/17.
 */
public class BoardConnetionView extends JPanel {

    private JLabel connectionImageView;

    private JPanel connectionInfoListView;
    private ArrayList<BoardConnectionViewEntry> boardConnetionViews;

    public void setConnectionType(ProjectConstants.CONNECTION_TYPE connection_type)
    {
        Icon icon = null;

        switch (connection_type)
        {
            case INTERNET:
            {
                icon = new ImageIcon(getClass().getResource("/images/wifi_64.png"));
                break;
            }
            case BLUETOOTH_CLASSIC:
            {
                icon = new ImageIcon(getClass().getResource("/images/bluetooth_64.png"));
                break;
            }
            case SERIAL:
            {
                icon = new ImageIcon(getClass().getResource("/images/usb_64.png"));
                break;
            }
        }
        connectionImageView.setIcon(icon);

    }

    public BoardConnetionView( )
    {
        this.boardConnetionViews = new ArrayList<>();
        initUI();
        changeBoard(boardConnetionViews);



    }

    private void makeEmpty()
    {
        for(BoardConnectionViewEntry boardConnectionViewEntry : boardConnetionViews)
            connectionInfoListView.remove(boardConnectionViewEntry);

        revalidate();
        repaint();
    }


    private void initUI()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        connectionInfoListView = new JPanel();
        connectionInfoListView.setLayout(new BoxLayout(connectionInfoListView,BoxLayout.Y_AXIS));
        this.setLayout(gridBagLayout);
        for(BoardConnectionViewEntry boardConnetionView :boardConnetionViews)
        {
            connectionInfoListView.add(boardConnetionView);
        }
        connectionImageView = new JLabel();
        connectionImageView.setText("");
        GridBagConstraints gridBagConstraintsforImageView = new GridBagConstraints();
        gridBagConstraintsforImageView.gridx = 0;
        gridBagConstraintsforImageView.gridy = 0;
        gridBagConstraintsforImageView.fill = GridBagConstraints.HORIZONTAL;
        GridBagConstraints gridBagConstraintsforforConnectionInformationPanel = new GridBagConstraints();
        gridBagConstraintsforforConnectionInformationPanel.gridx = 1;
        gridBagConstraintsforforConnectionInformationPanel.gridy =0;
        gridBagConstraintsforforConnectionInformationPanel.insets = new Insets(0,10,0,10);
        gridBagConstraintsforforConnectionInformationPanel.fill = GridBagConstraints.HORIZONTAL;

        add(connectionImageView,gridBagConstraintsforImageView);
        add(connectionInfoListView,gridBagConstraintsforforConnectionInformationPanel);
    }

    public void changeBoard(ArrayList<BoardConnectionViewEntry> boardConnetionViewArrayList)
    {
        makeEmpty();
        this.boardConnetionViews = boardConnetionViewArrayList;

        for(BoardConnectionViewEntry boardConnetionView :boardConnetionViews)
        {
            connectionInfoListView.add(boardConnetionView);
        }



    }



}
