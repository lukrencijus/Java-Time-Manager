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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class App {
    public static void main(String[] args){
        FileManager fileManager = new FileManager();
        ActivityManager activityMethods = new ActivityManager();
        int action;
        Scanner read = new Scanner(System.in);
        File filePath = pathOfFile().toFile();

        System.out.println(filePath);
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
    

    private static Path pathOfFile(){
        Path currentDir = Paths.get(System.getProperty("user.dir"));

        Path parrentDir = currentDir.getParent();
        System.out.println(parrentDir);

        Path targetDir = parrentDir.resolve("Java-Time-Manager");

        Path targetDir1 = targetDir.resolve("JSON_files");

        Path filePath = targetDir1.resolve("storage.json");
        

        return filePath.toAbsolutePath();
    }
}
