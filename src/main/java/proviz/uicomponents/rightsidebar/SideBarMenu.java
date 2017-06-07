package proviz.uicomponents.rightsidebar;

import proviz.thirdparty.SidebarSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by Burak on 1/14/17.
 */
public class SideBarMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    /** box layout to contain side bar sections arranged vertically */
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

    /** the currently expanded section */
    private SideBarMenuSection currentSection = null;

    SideBarMode thisMode;

    boolean showArrow;

    boolean animate = false;

    public SideBarMenu(SideBarMode mode, boolean showArrow, int preferredWidth, boolean animate) {

        this.showArrow = showArrow;
        this.thisMode = mode;
        this.animate = animate;

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        setLayout(boxLayout);

        setPreferredSize(new Dimension(preferredWidth, getPreferredSize().height));

        setFocusable(false);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resize();
            }
        });

        revalidate();
    }

    public void resize() {
        if (currentSection != null) {
            currentSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getHeight()));
            currentSection.contentPane.setVisible(true);
            currentSection.revalidate();
        }
    }

    public void addSection(SideBarMenuSection newSection, boolean collapse) {
        add(newSection);
        if(collapse){
            newSection.collapse(false);
        }else{
            setCurrentSection(newSection);
        }
    }

    public void addSection(SideBarMenuSection newSection) {
        addSection(newSection, false);
    }

    public boolean isCurrentExpandedSection(SidebarSection section) {
        return (section != null) && (currentSection != null)
                && section.equals(currentSection);
    }

    public SideBarMode getMode() {
        return thisMode;
    }

    public SideBarMenuSection getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(SideBarMenuSection section) {
        currentSection = section;
    }

    public enum SideBarMode {
        TOP_LEVEL, INNER_LEVEL;
    }

}
