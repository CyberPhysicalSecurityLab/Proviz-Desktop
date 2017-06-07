package proviz.uicomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Burak on 1/15/17.
 */
public class RightClickPopUpMenu extends JPopupMenu {
    private ActionListener actionListener;
    private ArrayList<JMenuItem> jMenuItems;

    public RightClickPopUpMenu()
    {
        jMenuItems = new ArrayList<>();
        setSize(new Dimension(160,240));
        setMaximumSize(new Dimension(160,240));
        setMinimumSize(new Dimension(160,240));
    }
    public void addMenuItem(String name,ActionListener actionListener)
    {
        JMenuItem jMenuItem = new JMenuItem(name);
        jMenuItem.addActionListener(actionListener);
        this.add(jMenuItem);
        jMenuItems.add(jMenuItem);
    }
}
