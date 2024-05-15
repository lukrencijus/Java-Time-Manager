//This project was made by Karolis Zabulis and Lukas Šerelis.
//Vilnius University Mathematics and Informatics faculty.
//Contact: karolis.zabulis@mif.stud.vu.lt or lukas.serelis@mif.stud.vu.lt
//Description of the program: A Java program that allows users to enter activity,
//date, start, end of it, interruptions, comments and represents it.
//Version: Alpha
//Change log: Implemented activites insertion, diplaying, removing, editing.
//To do: Improve quality of the code, currently more of a prototype.  
//Program was tested on Windows 11 64 bit and macOS arm64

import java.io.File;
import java.util.Scanner;


public class App {
    public static void main(String[] args){
        FileManager fileManager = new FileManager();
        ActivityManager activityMethods = new ActivityManager();
        int action;
        Scanner read = new Scanner(System.in);
        File filePath = new File("./JSON_files/storage.json");
        Activity activity = new Activity();

            try {
                while(true){
                    System.out.println("[1] Inserting activities\n[2] Displaying activities\n[3] Editing activities\n[4] Removing activites\n[5] Exit");
                    action = read.nextInt();
                    read.nextLine();

                    if(action == 1){
                        fileManager.appendData(activityMethods.insertion(read, activity), filePath);
                    }

                    else if(action == 2){
                        activityMethods.extraction(read, filePath);
                    }

                    else if(action == 3){
                        activityMethods.editing(read, filePath);
                    }

                    else if(action == 4){
                        activityMethods.remove(read, filePath);
                    }

                    else if(action == 5){
                        break;
                    }
                    else{
                        System.out.println("Illegal operation, try again");
                    }
                }
            } 
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            finally{
                read.close();
            }
    }
}
