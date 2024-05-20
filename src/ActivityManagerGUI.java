import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityManagerGUI {

    // Extracts selected activities from storage and displays them
    void extraction(String input, File dataFile) throws Exceptions, IOException {
        String dateInput = input;
        boolean found = false;
        if (dateInput == null || dateInput.isEmpty()) {
            dateInput = LocalDate.now().toString();
            JOptionPane.showMessageDialog(
                    tryGUI.frame, "Nothing was entered, date has been set to current local date", "Date has been set", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            ActivityManagerGUI.formatValidatorDate(dateInput);
            if (!found) throw new Exceptions.NotFound();
        }

        List<Activity> activities = FileManager.readDataFromFile(dataFile);
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

                activityPanel.add(textArea);
                displayFrame.add(activityPanel);
            }
        }

        displayFrame.setSize(700, 300);
        displayFrame.setResizable(false);
        displayFrame.setLocationRelativeTo(tryGUI.frame);
        displayFrame.setVisible(true);
    }

    //validates date format
    static void formatValidatorDate(String dateInput) throws Exceptions{

        if(dateInput == null || dateInput.isEmpty()){
            return;
        }
        try{
            String pattern = "yyyy-MM-dd";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            LocalDate parsedDate = LocalDate.parse(dateInput, formatter);

            String formattedTime = parsedDate.format(formatter);

            if(!dateInput.equals(formattedTime)){
                throw new Exceptions.IllegalFormat();
            }

            int year = parsedDate.getYear();
            int month = parsedDate.getMonthValue();
            int day = parsedDate.getDayOfMonth();

            if(day > 31 || day < 1 || month > 12 || month < 1 || year < 1){
                throw new Exceptions.IllegalFormat();
            }

        }
        catch(DateTimeException e){
            throw new Exceptions.IllegalFormat();
        }

    }
}
