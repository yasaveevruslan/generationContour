import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Frame implements ActionListener{

    public GenerationContours contour = new GenerationContours();
    public static JFrame frame = new JFrame("Определение контуров");
    public static JPanel panel = new JPanel();

    public static JButton open = new JButton("открыть");
    public static JButton generation = new JButton("генерация");
    public static JButton create = new JButton("создать");

    public Frame()
    {
        createWindow();
    }

    public void createWindow()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setLocation(600, 250);
        frame.setContentPane(panel);

        addButtons();
    }

    private void addButtons()
    {
        panel.add(open);
        open.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
            }

            final JFrame frame = new JFrame("Open File Example");

            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                contour.addressOpen = chooser.getSelectedFile().getAbsolutePath();
                JOptionPane.showMessageDialog(null, chooser.getSelectedFile().getAbsolutePath(), "File name", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(generation);
        generation.addActionListener(e -> {
            try {
                contour.openImage();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel.add(create);
        create.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
            }

            final JFrame frame = new JFrame("Save");

            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    contour.createDocs(chooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, chooser.getSelectedFile().getAbsolutePath(), "File name", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

