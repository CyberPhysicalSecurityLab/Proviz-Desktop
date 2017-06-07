package proviz.models.uielements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Burak on 2/16/17.
 */
public class BoardConnectionViewEntry extends JPanel{

    private JLabel descriptionLabel;
    private JLabel valueLabel;

   public BoardConnectionViewEntry(String descriptionText, String value)
   {
       descriptionLabel=new JLabel(descriptionText+":");
       valueLabel = new JLabel(value);
       valueLabel.setBorder(new EmptyBorder(5,10,5,0));
       BorderLayout borderLayout = new BorderLayout();

       setLayout(borderLayout);
        initializeViews();

   }

    private void initializeViews()
    {
        this.add(descriptionLabel,BorderLayout.NORTH);
        this.add(valueLabel,BorderLayout.SOUTH);
    }

    public static void main (String[] args) {
        JFrame window = new JFrame("");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit CurrentToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = CurrentToolkit.getScreenSize();
        window.setSize(screenSize.width,screenSize.height);
        window.setResizable(true);
        window.setBounds(0,0,(int)screenSize.getWidth(),(int)screenSize.getHeight());
        BoardConnectionViewEntry boardConnectionViewEntry = new BoardConnectionViewEntry ("Bluetooth Addres","ouofhhapdjhioahipds");
        window.add(boardConnectionViewEntry);
        window.pack();
        window.setVisible(true);


    }
}
