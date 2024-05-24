import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileManager {
    void appendData(Activity activity, File dataFile) throws IOException{
            List<Activity> activities = readDataFromFile(dataFile);

                activities.add(activity);


            try(FileWriter writer = new FileWriter(dataFile)){
                Gson gson = new Gson();
                gson.toJson(activities, writer);
            }
            
        }
        //general writing to files, used for removal and editing
        void writeDataToFile(File dataFile, List<Activity> activities) throws IOException{
            try(FileWriter writer = new FileWriter(dataFile)){
                Gson gson = new Gson();
                gson.toJson(activities, writer);
            }
        }

        //reads all contents from the file into an array list
        List<Activity> readDataFromFile(File dataFile) throws IOException{
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
