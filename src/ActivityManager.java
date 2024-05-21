import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


import com.google.gson.JsonIOException;

//methods dedicated to managing activities
public class ActivityManager {
    private
    FileManager fileManager = new FileManager();

    public
        //inserting activity's data into Activity class
        Activity insertion(Scanner inputRead, Activity activity) throws Exceptions, JsonIOException, IOException{
            String timeInput = null;
            String dateInput = null;
            int flag = 0;


            //filling activity data
            System.out.println("Insert name of the activity");
            activity.setName(inputRead.nextLine());
            
            System.out.println("Insert date (yyyy-MM-dd)");
            dateInput = inputRead.nextLine();
            formatValidatorDate(dateInput);
            activity.setDate(dateInput);

            System.out.println("Insert starting time (HH:mm)");
            timeInput = inputRead.nextLine();
            formatValidatorTime(timeInput, flag);
            flag++;
            activity.setStartTime(timeInput);

            timeInput = null;

            System.out.println("Insert ending time (HH:mm)");
            timeInput = inputRead.nextLine();
            formatValidatorTime(timeInput, flag);
            activity.setEndTime(timeInput);

            System.out.println("Insert comments (optional)");
            activity.setComments(inputRead.nextLine());

            System.out.println("Insert interrupts (optional)");
            activity.setInterrupts(inputRead.nextLine());

            return activity;

        }

        //extracts selected activities from storage and displays them
        String extraction(Scanner inputRead, File dataFile) throws Exceptions, IOException{
            String dateInput = null;

            System.out.println("Please insert date that you want to check (yyyy-MM-dd)");
            dateInput = inputRead.nextLine();
            if(dateInput == null || dateInput.isEmpty()){
                dateInput = LocalDate.now().toString();
                System.out.println("Current local date has been set");
            }
            else{
                formatValidatorDate(dateInput);
            }

            List<Activity> activities = fileManager.readDataFromFile(dataFile);
            System.out.println("Matching activities found: ");
            for(Activity activity : activities){

                if(activity.getDate().equals(dateInput)){
                    System.out.println(activity);
                }

            }

            return dateInput;
        }

        //activity that edits activity if certain matches are found(name, date)
        void editing(Scanner inputRead, File dataFile) throws IOException, Exceptions{
            List<Activity> activities = fileManager.readDataFromFile(dataFile);
            String activityName;
            String activityDate;
            activityDate = extraction(inputRead, dataFile);

            System.out.println("Please insert activity name from the displayed");
            activityName = inputRead.nextLine();
            for(int i = 0; i < activities.size(); i++){
                if(activities.get(i).getDate().equals(activityDate) && activities.get(i).getName().equals(activityName)){
                    activities.set(i, insertion(inputRead, activities.get(i)));
                    fileManager.writeDataToFile(dataFile, activities);
                    return;
                }
            }
            System.out.println("No matching activities found");
        }

        //method that removes activity if certain matches are found(name, date)
        void remove(Scanner inputRead, File dataFile) throws IOException, Exceptions{
            List<Activity> activities = fileManager.readDataFromFile(dataFile);
            String activityName;
            String activityDate;

            activityDate = extraction(inputRead, dataFile);

            System.out.println("Please insert activity name from the displayed");
            activityName = inputRead.nextLine();
            for(int i = 0; i < activities.size(); i++){
                if(activities.get(i).getDate().equals(activityDate) && activities.get(i).getName().equals(activityName)){
                    activities.remove(i);
                    fileManager.writeDataToFile(dataFile, activities);
                    return;
                }
            }
            System.out.println("No matching activities found");
        }


        public//Validates time format
        void formatValidatorTime(String timeInput, int flag) throws Exceptions{
            if(timeInput == null  && flag == 0|| timeInput.isEmpty() && flag == 0){
                return;
            }
            try{
                if(timeInput == null && flag == 1  || timeInput.isEmpty() && flag == 1){
                    throw new Exceptions.EmptyFieldException();
                }
                String pattern = "HH:mm";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

                LocalTime parsedTime = LocalTime.parse(timeInput, formatter);

                String formattedTime = parsedTime.format(formatter);

                if(!timeInput.equals(formattedTime)){
                    throw new Exceptions.IllegalFormat();
                }

                int hour = parsedTime.getHour();
                int minute = parsedTime.getMinute();

                if(hour < 0 || hour > 23 || minute < 0 || minute > 59){
                    throw new Exceptions.IllegalFormat();
                }
            }
            catch(DateTimeException e){
                throw new Exceptions.IllegalFormat();
            }
            
        }
        //validates date format
        void formatValidatorDate(String dateInput) throws Exceptions{

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
