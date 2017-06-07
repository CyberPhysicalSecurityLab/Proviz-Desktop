package proviz;

import javax.swing.*;

/**
 * Created by Burak on 1/14/17.
 */

    import javax.swing.SwingUtilities;
    import javax.swing.UIDefaults;
    import javax.swing.UIManager;

    public class ListLafValues {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    UIDefaults defaults = UIManager.getLookAndFeel().getDefaults();
                    System.out.println(defaults);
                    String propertyKey = "Tabbed";
                    System.out.println(UIManager.getLookAndFeel().getName() +
                            (defaults.containsKey(propertyKey) ? " contains " : " doesn't contain ") +
                            "property " + propertyKey);
                }
            });
        }

}
