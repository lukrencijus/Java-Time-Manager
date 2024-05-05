import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class ActivityFunctions {

    public void insertion(Scanner inputRead) throws Exceptions{
        String timeInput = null;
        String dateInput = null;
        int flag = 0;

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

    }


    private
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
