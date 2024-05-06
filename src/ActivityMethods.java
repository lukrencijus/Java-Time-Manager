import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

//methods dedicated to managing activities
public class ActivityMethods {
    public
        //inserting activity's data into Activity class
        void insertion(Scanner inputRead, File dataFile) throws Exceptions, JsonIOException, IOException{
            String timeInput = null;
            String dateInput = null;
            int flag = 0;


            //filling activity data
            Activity activity = new Activity(null, null, null, null, null, null);
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
            formatValidatorTime(timeInput,flag);
            activity.setEndTime(timeInput);

            System.out.println("Insert comments (optional)");
            activity.setComments(inputRead.nextLine());

            System.out.println("Insert interrupts (optional)");
            activity.setInterrupts(inputRead.nextLine());

            //saving data to file
            appendData(activity, dataFile);

        }

        //extracts selected activities from storage and displays them
        void extraction(Scanner inputRead, File dataFile) throws Exceptions, IOException{
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
            
            List<Activity> activities = readActivitiesFromFile(dataFile);
            System.out.println("Matching activities found: ");
            for(Activity activity : activities){

                if(activity.getDate().equals(dateInput)){
                    System.out.println(activity);
                }

            }
            

        }

        


    private
        //Validates time format
        void formatValidatorTime(String timeInput, int flag) throws Exceptions{
            if(timeInput == null  && flag == 0|| timeInput.isEmpty() && flag == 0){
                return;
            }
            try{
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
        //appends data to json storage file
        void appendData(Activity activity, File dataFile) throws IOException{
            List<Activity> activities = readActivitiesFromFile(dataFile);

            activities.add(activity);

            try(FileWriter writer = new FileWriter(dataFile)){
                Gson gson = new Gson();
                gson.toJson(activities, writer);
            }
            
        }

        //reads all contents from the file into an array list
        List<Activity> readActivitiesFromFile(File dataFile) throws IOException{
            List<Activity> activities = new ArrayList<>();
            if(dataFile.exists() && dataFile.length() > 0){
                try(FileReader reader = new FileReader(dataFile)){
                    Gson gson = new Gson();
                    java.lang.reflect.Type activityListType = new TypeToken<List<Activity>>() {}.getType();
                    activities = gson.fromJson(reader, activityListType);
                    
                }
            }
            return activities;

        }
}
