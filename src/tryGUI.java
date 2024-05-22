import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

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

        File filePath = new File("./JSON_files/storage.json");

    public tryGUI() {
        frame = new JFrame();

        // Main menu buttons
        image = new ImageIcon("./Icons/icon.png");
        label = new JLabel();
        label.setText("Welcome to Time Manager!");
        label.setIcon(image);

        insertButton = new JButton("Insert Activities");
        displayButton = new JButton("Display Activities");
        editButton = new JButton("Edit Activities");
        removeButton = new JButton("Remove Activities");
        exitButton = new JButton("Exit Time Manager");

        JPanel panel = new JPanel();
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
        frame.setLocationRelativeTo(null);

        // Insert button is pressed
        insertButton.addActionListener(e -> {
            String activityName = JOptionPane.showInputDialog(frame, "Enter the name of your activity", "Insert activity", JOptionPane.PLAIN_MESSAGE);
                if (activityName != null) {
                    try {
                        activity.setName(activityName);
                    } catch (Exceptions ex) {
                        throw new RuntimeException(ex);
                    }

                    String date = JOptionPane.showInputDialog(frame,
                            "<html>Enter date (YYYY-MM-DD)<br>" +
                            "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                            "Insert activity", JOptionPane.PLAIN_MESSAGE);
                    if (date != null) {
                        try {
                            ActivityManagerGUI.formatValidatorDate(date);
                            activity.setDate(date);
                        } catch (Exceptions ex) {
                            throw new RuntimeException(ex);
                        }
                        String startingTime = JOptionPane.showInputDialog(frame,
                                "<html>Insert starting time (HH:MM)<br>" +
                                "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to current time</div></html>",
                                "Insert activity", JOptionPane.PLAIN_MESSAGE);
                        if (startingTime != null) {
                            int flag = 0;
                            try {
                                activityManagerGUI.formatValidatorTime(startingTime, flag);
                                flag++;
                                activity.setStartTime(startingTime);
                            } catch (Exceptions ex) {
                                throw new RuntimeException(ex);
                            }

                            String endingTime = JOptionPane.showInputDialog(frame, "Insert ending time (HH:MM)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
                            if (endingTime != null) {
                                try {
                                    activityManagerGUI.formatValidatorTime(endingTime, flag);
                                    activity.setEndTime(endingTime);
                                } catch (Exceptions ex) {
                                    throw new RuntimeException(ex);
                                }

                                String comments = JOptionPane.showInputDialog(frame, "Insert comments (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
                                activity.setComments(comments);
                                String interrupts = JOptionPane.showInputDialog(frame, "Insert interrupts (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
                                activity.setInterrupts(interrupts);

                                try {
                                    FileManager.appendData(activity, filePath);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                }
        });

        // Display button is pressed
        displayButton.addActionListener(e -> {
            String displayActivities = JOptionPane.showInputDialog(frame,
                    "<html>Please insert date that you want to check (YYYY-MM-DD)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                    "Displaying activities", JOptionPane.PLAIN_MESSAGE);
            if (displayActivities != null) {
                try {
                    activityManagerGUI.extraction(displayActivities, filePath, 0, 0);
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
                    activityManagerGUI.extraction(editActivities, filePath, 1, 0);
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
                    activityManagerGUI.extraction(removeActivities, filePath, 1, 1);
                } catch (Exceptions | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Exit button is pressed
        exitButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

    }

    public static void main(String[] args) {
        new tryGUI();
    }

}