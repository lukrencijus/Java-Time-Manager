import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class tryGUI {
    public
        static JFrame frame;

        ImageIcon image;
        JLabel label;

        JButton insertButton;
        JButton displayButton;
        JButton editButton;
        JButton removeButton;
        JButton exitButton;

        ActivityManagerGUI activityManagerGUI = new ActivityManagerGUI();
        Activity activity = new Activity();

        File filePath = FileManager.pathOfFile().toFile();
        Path iconPath = FileManager.pathOfIcon();

        int flag = 0;

    public tryGUI() {
        frame = new JFrame();

        // Main menu buttons
        image = new ImageIcon(String.valueOf(iconPath));
        label = new JLabel();
        label.setText("Welcome to Time Manager!");
        label.setIcon(image);

        insertButton = new JButton("Insert Activities");
        displayButton = new JButton("Display Activities");
        editButton = new JButton("Edit Activities");
        removeButton = new JButton("Remove Activities");
        exitButton = new JButton("Exit Time Manager");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
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
        frame.setLocationRelativeTo(null);

        // Insert button is pressed
        insertButton.addActionListener(e -> {
                activityManagerGUI.insert(flag, filePath, activity, frame);
            }
        );

        // Display button is pressed
        displayButton.addActionListener(e -> {
            String displayActivities = JOptionPane.showInputDialog(frame,
                    "<html>Please insert date that you want to check (YYYY-MM-DD)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                    "Displaying activities", JOptionPane.PLAIN_MESSAGE);
            if (displayActivities != null) {
                try {
                    activityManagerGUI.extraction(displayActivities, filePath, false, false);
                } catch (Exceptions | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Edit button is pressed
        editButton.addActionListener(e -> {
            String editActivities = JOptionPane.showInputDialog(frame,
                    "<html>Please insert date that you want to edit (YYYY-MM-DD)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                    "Editing activities", JOptionPane.PLAIN_MESSAGE);
            if (editActivities != null) {
                try {
                    activityManagerGUI.extraction(editActivities, filePath, true, false);
                } catch (Exceptions | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Remove button is pressed
        removeButton.addActionListener(e -> {
            String removeActivities = JOptionPane.showInputDialog(frame,
                    "<html>Please insert date that you want to delete (YYYY-MM-DD)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                    "Removing activities", JOptionPane.PLAIN_MESSAGE);
            if (removeActivities != null) {
                try {
                    activityManagerGUI.extraction(removeActivities, filePath, true, true);
                } catch (Exceptions | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Exit button is pressed
        exitButton.addActionListener(e -> {
            JOptionPane optionPane = new JOptionPane("Have a good day :)", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Good Bye");
            dialog.setLocationRelativeTo(frame);

            Timer timer = new Timer(600, event -> {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                dialog.dispose();
            });
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true);
        });

    }


}