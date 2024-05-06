//This project was made by Karolis Zabulis and Lukas Šerelis.
//Vilnius University Mathematics and Informatics faculty.
//Contact: karolis.zabulis@mif.stud.vu.lt or lukas.serelis@mif.stud.vu.lt
//Description of the program: A Java program that allows users to enter activity,
//date, start, end of it, interruptions, comments and represents it.
//Version: V0.1
//Change log:
//Program was tested on Windows 11 64 bit and macOS arm64

import java.io.File;
import java.util.Scanner;


public class App {
    public static void main(String[] args){

        ActivityMethods activityMethods = new ActivityMethods();
        int action;
        Scanner read = new Scanner(System.in);
        File filePath = new File("./JSON_files/storage.json");

            try {
                while(true){
                    System.out.println("[1] Inserting activities\n[2] Displaying activities\n[3] Editing activities\n[4] Exit");
                    action = read.nextInt();
                    read.nextLine();

                    if(action == 1){
                        activityMethods.insertion(read, filePath);
                    }

                    else if(action == 2){
                        activityMethods.extraction(read, filePath);
                    }

                    else if(action == 3){

                    }

                    else if(action == 4){
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
