package proviz.uicomponents.rightsidebar;

import proviz.thirdparty.ArrowPanel;
import proviz.uicomponents.RoundedBorder;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Burak on 1/14/17.
 */
public class SideBarMenuSection extends JPanel {
    private static final long serialVersionUID = 1L;

    private int minComponentHeight = 40;
    private int minComponentWidth = 350;

    public JComponent titlePanel;

    private SideBarMenu sideBarOwner;

    public JComponent contentPane; //sidebar section's content

    private ArrowPanel arrowPanel;

    private int calculatedHeight;
    private boolean isExpanded;

    public SideBarMenuSection(SideBarMenu owner,
                          String text,
                          JComponent component, Icon icon) {
        this(owner, new JLabel(text), component, icon);
    }



    public SideBarMenuSection(SideBarMenu owner,
                          JComponent titleComponent,
                          JComponent component, Icon icon) {

        if (owner.thisMode == SideBarMenu.SideBarMode.INNER_LEVEL)
            minComponentHeight = 30;
        else
            minComponentHeight = 40;


        this.contentPane = component;

        sideBarOwner = owner;

        titlePanel = new JPanel();
        titlePanel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {

                if (!isExpanded) {
                    expand(); //expand this!
                }
                else {
                    collapse(true);
                }
            }
        });


        setLayout(new BorderLayout());

        add(titlePanel, BorderLayout.NORTH);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(this.getPreferredSize().width, minComponentHeight));
        RoundedBorder roundedBorder =  new RoundedBorder(Color.DARK_GRAY);
        roundedBorder.setRoundRadius(8);
        titlePanel.setBackground(new Color(200,221,242));
        titlePanel.setBorder(roundedBorder);



        arrowPanel = new ArrowPanel(SwingConstants.RIGHT,null,new Color(0x0, true),new Color(0x0, true),Color.BLUE);
        arrowPanel.setPreferredSize(new Dimension(40, 40));


        if (sideBarOwner.showArrow)
            titlePanel.add(arrowPanel, BorderLayout.EAST);


        titlePanel.add(new JLabel(icon), BorderLayout.WEST);

        titleComponent.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(2, 8, 2, 2), titleComponent.getBorder()));
        titlePanel.add(titleComponent);

        add(component, BorderLayout.CENTER);

        revalidate();
    }



    public void expand() {
        isExpanded = true;
        sideBarOwner.setCurrentSection(this);

        arrowPanel.changeDirection(BasicArrowButton.SOUTH);
        arrowPanel.updateUI();

        calculatedHeight = 240;

        if (this.sideBarOwner.animate) {
            SideBarMenuAnimation anim = new SideBarMenuAnimation(this, 200); // ANIMATION BIT

            anim.setStartValue(minComponentHeight);
            anim.setEndValue(calculatedHeight);
            anim.start();
        } else {
            if (sideBarOwner.thisMode == SideBarMenu.SideBarMode.INNER_LEVEL) {
                calculatedHeight = 1000;
                Dimension d = new Dimension(Integer.MAX_VALUE, calculatedHeight);
                setMaximumSize(d);
                sideBarOwner.setPreferredSize(d);
                contentPane.setVisible(true);
                revalidate();
            } else {
                setMaximumSize(new Dimension(Integer.MAX_VALUE, calculatedHeight));
                contentPane.setVisible(true);
                revalidate();
            }
        }
    }



    public void collapse(boolean animate) {
        // remove reference
        isExpanded = false;
        if (sideBarOwner.getCurrentSection() == SideBarMenuSection.this)
            sideBarOwner.setCurrentSection(null);

        arrowPanel.changeDirection(BasicArrowButton.EAST);
        arrowPanel.updateUI();

        if (animate && this.sideBarOwner.animate) {
            SideBarMenuAnimation anim = new SideBarMenuAnimation(this, 200); // ANIMATION BIT
            anim.setStartValue(calculatedHeight);
            anim.setEndValue(minComponentHeight);
            anim.start();
        } else {
            if (sideBarOwner.thisMode == SideBarMenu.SideBarMode.INNER_LEVEL) {
                setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
                contentPane.setVisible(false);
                revalidate();
            } else {
                setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
                contentPane.setVisible(false);
                revalidate();
            }
        }
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(minComponentWidth,minComponentHeight);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(minComponentWidth,minComponentHeight);
    }

    public void printDimensions() {
        System.out.println("-- DIMENSIONS -- ");

        System.out.println("sideBar height                     " + this.sideBarOwner.getSize().height);

        System.out.println("sideBarSection height              " + getSize().height);
        System.out.println("sideBarSection titlePanel height   " + titlePanel.getSize().height);
        System.out.println("sideBarSection.contentPane height  " + contentPane.getSize().height);
    }

    public int getMinComponentHeight() {
        return minComponentHeight;
    }

    public void setMinComponentHeight(int minComponentHeight) {
        this.minComponentHeight = minComponentHeight;
    }

    public int getMinComponentWidth() {
        return minComponentWidth;
    }

    public void setMinComponentWidth(int minComponentWidth) {
        this.minComponentWidth = minComponentWidth;
    }
}
