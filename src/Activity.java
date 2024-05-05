import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Activity{
    private
        String name;
        String date;
        String startTime;
        String endTime;
        String interrupts;
        String comments;
    public Activity(String name, String date, String startTime, String endTime, String interrupts, String comments) {
            this.name = name;
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.interrupts = interrupts;
            this.comments = comments;
        }
    public
        String getName() {
            return name;
        }
        void setName(String name) throws Exceptions{
            if(name == null || name.isEmpty()){
                throw new Exceptions.EmptyFieldException();
            }
            this.name = name;
        }
        String getDate(){
            return date;
        }
        void setDate(String date) throws Exceptions{
            if(date == null || date.isEmpty()){
                System.out.println("Local date has been set");
                this.date = LocalDate.now().toString();
            }
            else{
                this.date = date;
            }
        }
        String getInterrupts() {
            return interrupts;
        }
        void setInterrupts(String interrupts) {
            this.interrupts = interrupts;
        }
        String getComments() {
            return comments;
        }
        void setComments(String comments) {
            this.comments = comments;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            if(startTime == null || startTime.isEmpty()){
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                this.startTime = LocalTime.now().format(format);
                System.out.println("Time has been set to current local time");
            
            }
            else{
                this.startTime = startTime;
            }
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            if(endTime == null){
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                this.startTime = LocalTime.now().format(format);
                System.out.println("Time has been set to current local time");
            }
            else{
                this.endTime = endTime;
            }
        }
    
}
