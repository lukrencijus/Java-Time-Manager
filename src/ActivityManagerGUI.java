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

    //Inserts activities into storage
    public void insert(int flag, File filePath, Activity activity, JFrame frame){
        while(true){
            String activityName = JOptionPane.showInputDialog(frame, "Enter the name of your activity", "Insert activity", JOptionPane.PLAIN_MESSAGE);
            try {
                if(activityName == null){
                    return;
                }
                activity.setName(activityName);
                if(!activity.getName().isEmpty() && activity.getName() != null){
                    break;
                }
            } catch (Exceptions ex) {

            }
        }

        while(true){
            String date = JOptionPane.showInputDialog(frame,
                    "<html>Enter date (YYYY-MM-DD)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to today's</div></html>",
                    "Insert activity", JOptionPane.PLAIN_MESSAGE);
            try {
                if(date == null){
                    return;
                }
                ActivityManagerGUI.formatValidatorDate(date);
                activity.setDate(date);
                if(!activity.getDate().isEmpty() && activity.getDate() != null){
                    break;
                }
            } catch (Exceptions ex) {

            }
        }
        while(true){
            flag = 0;
            String startingTime = JOptionPane.showInputDialog(frame,
                    "<html>Insert starting time (HH:MM)<br>" +
                    "<div style='color: gray; font-size: small; text-align: center;'>If nothing is entered, it will be set to current time</div></html>",
                    "Insert activity", JOptionPane.PLAIN_MESSAGE);
            try {
                if(startingTime == null){
                    return;
                }
                formatValidatorTime(startingTime, flag);
                flag++;
                activity.setStartTime(startingTime);
                if(!activity.getStartTime().isEmpty() && activity.getStartTime() != null){
                    break;
                }
            } catch (Exceptions ex) {

            }
        }

        while(true){
            String endingTime = JOptionPane.showInputDialog(frame, "Insert ending time (HH:MM)", "Insert activity", JOptionPane.PLAIN_MESSAGE);

            try {
                formatValidatorTime(endingTime, flag);
                activity.setEndTime(endingTime);
                if(endingTime == null){
                    return;
                }
                if(activity.getEndTime() != null && !activity.getEndTime().isEmpty()){
                    break;
                }
            } catch (Exceptions ex){

            }

        }

        String comments = JOptionPane.showInputDialog(frame, "Insert comments (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
        activity.setComments(comments);
        if(comments == null){
            return;
        }
        String interrupts = JOptionPane.showInputDialog(frame, "Insert interrupts (optional)", "Insert activity", JOptionPane.PLAIN_MESSAGE);
        activity.setInterrupts(interrupts);
        if(interrupts == null){
            return;
        }

        try {
            fileManager.appendData(activity, filePath);
        } catch (IOException ex) {
        }
    }

    // Extracts selected activities from storage and displays them
    void extraction(String input, File dataFile, boolean isEdit, boolean isRemove) throws Exceptions, IOException {
        String dateInput = input;
        if (dateInput == null || dateInput.isEmpty()) {
            dateInput = LocalDate.now().toString();
            JOptionPane.showMessageDialog(
                    tryGUI.frame, "Nothing was entered, date has been set to current local date", "Date has been set", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ActivityManagerGUI.formatValidatorDate(dateInput);
        }

        List<Activity> activities = fileManager.readDataFromFile(dataFile);
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
                displayFrame.setLayout(new BorderLayout());

                JPanel activitiesPanel = new JPanel();
                activitiesPanel.setLayout(new GridLayout(4, 3));

                for (Activity activity : activities) {
                    if (activity.getDate().equals(dateInput)) {
                        JPanel activityPanel = new JPanel();
                        activityPanel.setBackground(Color.PINK);
                        activityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        JTextArea textArea = new JTextArea(activity.toString(), 6, 10);
                        textArea.setEditable(false);
                        textArea.setForeground(Color.BLACK);
                        textArea.setBackground(Color.PINK);


                        if (isEdit == true) {
                            edit(isRemove, textArea, activity, displayFrame, dataFile);
                        }

                        activityPanel.add(textArea);
                        activitiesPanel.add(activityPanel);
                    }
                }
                JScrollPane scrollPane = new JScrollPane(activitiesPanel);


                JButton goBackButton = new JButton("Go Back");
                goBackButton.setBackground(Color.PINK);
                goBackButton.setOpaque(true);

                goBackButton.addActionListener(e -> {
                    displayFrame.dispose();
                });

                displayFrame.add(scrollPane, BorderLayout.CENTER);
                displayFrame.add(goBackButton, BorderLayout.SOUTH);

                displayFrame.setSize(602, 400);
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
        panel.add(new JLabel("Interrupts:"));
        panel.add(interruptsField);
        panel.add(new JLabel("Comments:"));
        panel.add(commentsField);

        int result = JOptionPane.showConfirmDialog(tryGUI.frame, panel, "Edit Activity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            removeForRemoval(activity, dataFile);
            activity.setName(nameField.getText());
            activity.setDate(dateField.getText());
            activity.setStartTime(startTimeField.getText());
            activity.setEndTime(endTimeField.getText());
            activity.setComments(commentsField.getText());
            activity.setInterrupts(interruptsField.getText());

            fileManager.appendData(activity, dataFile);
            extraction(activity.getDate(), dataFile, true, false);
        }
        else{
            extraction(activity.getDate(), dataFile, true, false);
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
        JTextField interruptsField = new JTextField(activity.getInterrupts());
        interruptsField.setEditable(false);
        JTextField commentsField = new JTextField(activity.getComments());
        commentsField.setEditable(false);

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
            extraction(activity.getDate(), dataFile, true, true);
        }
        else {
            extraction(activity.getDate(), dataFile, true, true);
        }
    }

    void removeForRemoval(Activity activity, File dataFile) throws IOException, Exceptions {
        List<Activity> activities = fileManager.readDataFromFile(dataFile);
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
    private void edit(boolean isRemove, JTextArea textArea, Activity activity, JFrame displayFrame, File dataFile){
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            try {
                if (isRemove == true) {
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
}
