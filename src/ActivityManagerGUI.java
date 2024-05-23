import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class ActivityManagerGUI {
    private
    FileManager fileManager = new FileManager();

    // Extracts selected activities from storage and displays them
    void extraction(String input, File dataFile, int num, int num2) throws Exceptions, IOException {
        String dateInput = input;
        if (dateInput == null || dateInput.isEmpty()) {
            dateInput = LocalDate.now().toString();
            JOptionPane.showMessageDialog(
                    tryGUI.frame, "Nothing was entered, date has been set to current local date", "Date has been set", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ActivityManagerGUI.formatValidatorDate(dateInput);
        }

        List<Activity> activities = FileManager.readDataFromFile(dataFile);
        if (activities == null || activities.isEmpty()) {
            throw new Exceptions.NotFound();
        } else {
            boolean activityExists = false;
            for (Activity activity : activities) {
                if (activity.getDate().equals(dateInput)) {
                    activityExists = true;
                    break;
                }
            }
            if (!activityExists) {
                JOptionPane.showMessageDialog(
                        tryGUI.frame, "No activity found for the entered date: " + dateInput, "Activity Not Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JFrame displayFrame = new JFrame("Matching Activities");
                displayFrame.setLayout(new GridLayout(0, 1));

                for (Activity activity : activities) {
                    if (activity.getDate().equals(dateInput)) {
                        JPanel activityPanel = new JPanel();
                        activityPanel.setBackground(Color.PINK);
                        activityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        JTextArea textArea = new JTextArea(activity.toString(), 5, 30);
                        textArea.setEditable(false);
                        textArea.setForeground(Color.BLACK);
                        textArea.setBackground(Color.PINK);


                        if (num == 1) {
                            textArea.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    try {
                                        if (num2 == 1) {
                                            openEditDialogForRemoval(activity, dataFile);
                                            displayFrame.dispose();
                                            return;
                                        }
                                        openEditDialog(activity, dataFile);
                                        displayFrame.dispose();
                                    } catch (Exceptions | IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    textArea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                }
                            });
                        }

                        activityPanel.add(textArea);
                        displayFrame.add(activityPanel);
                    }
                }
                JButton goBackButton = new JButton("Go Back");
                goBackButton.setBackground(Color.PINK);
                goBackButton.setOpaque(true);

                goBackButton.addActionListener(e -> {
                    displayFrame.dispose();
                });

                displayFrame.add(goBackButton, BorderLayout.SOUTH);

                displayFrame.setSize(700, 300);
                displayFrame.setResizable(false);
                displayFrame.setLocationRelativeTo(tryGUI.frame);
                displayFrame.setVisible(true);
            }
        }
    }


    // Editing activities
    private void openEditDialog(Activity activity, File dataFile) throws Exceptions, IOException {
        JTextField nameField = new JTextField(activity.getName());
        JTextField dateField = new JTextField(activity.getDate());
        JTextField startTimeField = new JTextField(activity.getStartTime());
        JTextField endTimeField = new JTextField(activity.getEndTime());
        JTextField commentsField = new JTextField(activity.getComments());
        JTextField interruptsField = new JTextField(activity.getInterrupts());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Start Time:"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Time:"));
        panel.add(endTimeField);
        panel.add(new JLabel("Comments:"));
        panel.add(commentsField);
        panel.add(new JLabel("Interrupts:"));
        panel.add(interruptsField);

        int result = JOptionPane.showConfirmDialog(tryGUI.frame, panel, "Edit Activity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            removeForRemoval(activity, dataFile);
            activity.setName(nameField.getText());
            activity.setDate(dateField.getText());
            activity.setStartTime(startTimeField.getText());
            activity.setEndTime(endTimeField.getText());
            activity.setComments(commentsField.getText());
            activity.setInterrupts(interruptsField.getText());

            FileManager.appendData(activity, dataFile);
            extraction(activity.getDate(), dataFile, 0, 0);
        }
    }

    // Removing activities
    private void openEditDialogForRemoval(Activity activity, File dataFile) throws Exceptions, IOException {
        JTextField nameField = new JTextField(activity.getName());
        nameField.setEditable(false);
        JTextField dateField = new JTextField(activity.getDate());
        dateField.setEditable(false);
        JTextField startTimeField = new JTextField(activity.getStartTime());
        startTimeField.setEditable(false);
        JTextField endTimeField = new JTextField(activity.getEndTime());
        endTimeField.setEditable(false);
        JTextField commentsField = new JTextField(activity.getComments());
        commentsField.setEditable(false);
        JTextField interruptsField = new JTextField(activity.getInterrupts());
        interruptsField.setEditable(false);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Start Time:"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Time:"));
        panel.add(endTimeField);
        panel.add(new JLabel("Comments:"));
        panel.add(commentsField);
        panel.add(new JLabel("Interrupts:"));
        panel.add(interruptsField);

        int result = JOptionPane.showConfirmDialog(tryGUI.frame, panel, "Are you sure?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            removeForRemoval(activity, dataFile);
            extraction(activity.getDate(), dataFile, 0, 0);
        }
    }

    void removeForRemoval(Activity activity, File dataFile) throws IOException, Exceptions {
        List<Activity> activities = FileManager.readDataFromFile(dataFile);
        String activityDate = activity.getDate();
        String activityName = activity.getName();
        boolean removed = false;

        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getDate().equals(activityDate) && activities.get(i).getName().equals(activityName)) {
                activities.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            fileManager.writeDataToFile(dataFile, activities);
            JOptionPane.showMessageDialog(tryGUI.frame, "Activity modified successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(tryGUI.frame, "No matching activities found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Validates date format
    static void formatValidatorDate(String dateInput) throws Exceptions {
        if (dateInput == null || dateInput.isEmpty()) {
            return;
        }
        try {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate parsedDate = LocalDate.parse(dateInput, formatter);
            String formattedTime = parsedDate.format(formatter);
            if (!dateInput.equals(formattedTime)) {
                throw new Exceptions.IllegalFormat();
            }
            int year = parsedDate.getYear();
            int month = parsedDate.getMonthValue();
            int day = parsedDate.getDayOfMonth();
            if (day > 31 || day < 1 || month > 12 || month < 1 || year < 1) {
                throw new Exceptions.IllegalFormat();
            }
        } catch (DateTimeException e) {
            throw new Exceptions.IllegalFormat();
        }

    }

    // Validates time format
    void formatValidatorTime(String timeInput, int flag) throws Exceptions {
        if (timeInput == null && flag == 0 || timeInput.isEmpty() && flag == 0) {
            return;
        }
        try {
            if (timeInput == null && flag == 1 || timeInput.isEmpty() && flag == 1) {
                throw new Exceptions.EmptyFieldException();
            }
            String pattern = "HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalTime parsedTime = LocalTime.parse(timeInput, formatter);
            String formattedTime = parsedTime.format(formatter);
            if (!timeInput.equals(formattedTime)) {
                throw new Exceptions.IllegalFormat();
            }
            int hour = parsedTime.getHour();
            int minute = parsedTime.getMinute();
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                throw new Exceptions.IllegalFormat();
            }
        } catch (DateTimeException e) {
            throw new Exceptions.IllegalFormat();
        }
    }
}
