import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;


public class tryGUI
{

    private JFrame frame;
    private JPanel panel;
    private ImageIcon image;

    private JButton insertButton;
    private JButton displayButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton exitButton;

    private JLabel label;

    public tryGUI()
    {
        frame = new JFrame();

        insertButton = new JButton("Insert Activities");
        displayButton = new JButton("Display Activities");
        editButton = new JButton("Edit Activities");
        removeButton = new JButton("Remove Activities");
        exitButton = new JButton("Exit Time Manager");

        image = new ImageIcon("../Icons/icon.png");

        label = new JLabel();
        label.setText("Welcome to Time Manager!");
        label.setIcon(image);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        panel.setLayout(new GridLayout(6, 1));

        panel.add(label);
        panel.add(insertButton);
        panel.add(displayButton);
        panel.add(editButton);
        panel.add(removeButton);
        panel.add(exitButton);
        panel.setBackground(Color.PINK);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Time Manager");
        frame.pack();
        frame.setVisible(true);
        frame.setIconImage(image.getImage());

        insertButton.addActionListener(e -> {
            JOptionPane.showInputDialog(frame, "Enter the name of your activity", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showInputDialog(frame, "Enter date (YYYY-MM-DD)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showInputDialog(frame, "Insert starting time (HH:MM)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showInputDialog(frame, "Insert ending time (HH:MM)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showInputDialog(frame, "Insert comments (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showInputDialog(frame, "Insert interrupts (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
        });


        exitButton.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

    }

    public static void main(String[] args)
    {
        new tryGUI();
    }

}