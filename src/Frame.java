import javax.swing.*;

public class Frame {

    public static JFrame frame = new JFrame("Определение контуров");
    public static JPanel panel = new JPanel();

    public static JButton open, generation, create = new JButton();

    public static void createWindow()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setLocation(600, 250);
        frame.setContentPane(panel);
        addButtons();
    }

    private static void addButtons()
    {
        panel.add(open);
        panel.add(generation);
        panel.add(create);
    }
}
