package proviz.uicomponents.rightsidebar;

import proviz.thirdparty.Animation;

import java.awt.*;

/**
 * Created by Burak on 1/14/17.
 */
public class SideBarMenuAnimation extends Animation {
    private SideBarMenuSection sideBarSection;

    public SideBarMenuAnimation(SideBarMenuSection sidebarSection, int durationMs) {
        super(durationMs);
        this.sideBarSection = sidebarSection;
    }

    @Override
    public void starting () {
        sideBarSection.contentPane.setVisible(true);
    }

    @Override
    protected void render(int value) {
        sideBarSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));
        sideBarSection.contentPane.setVisible(true);
        sideBarSection.revalidate();
    }

    @Override
    public void stopped () {
        sideBarSection.contentPane.setVisible(true);
        sideBarSection.revalidate();
    }
}
