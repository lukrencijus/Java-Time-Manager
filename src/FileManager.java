import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileManager {
    public void appendData(Activity activity, File dataFile) throws IOException{
        List<Activity> activities = readDataFromFile(dataFile);
            activities.add(activity);
        try(FileWriter writer = new FileWriter(dataFile)){
            Gson gson = new Gson();
            gson.toJson(activities, writer);
        }
    }

    //general writing to files, used for removal and editing
    public void writeDataToFile(File dataFile, List<Activity> activities) throws IOException{
        try(FileWriter writer = new FileWriter(dataFile)){
            Gson gson = new Gson();
            gson.toJson(activities, writer);
        }
    }

    //reads all contents from the file into an array list
    public List<Activity> readDataFromFile(File dataFile) throws IOException{
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

    public static Path pathOfFile(){
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path parrentDir = currentDir.getParent();
        Path targetDir = parrentDir.resolve("Java-Time-Manager");
        Path targetDir1 = targetDir.resolve("JSON_files");
        Path filePath = targetDir1.resolve("storage.json");
        return filePath.toAbsolutePath();
    }

    public static Path pathOfIcon(){
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path parentDir = currentDir.getParent();
        Path targetDir = parentDir.resolve("Java-Time-Manager");
        return targetDir.resolve("Icons").resolve("icon.png").toAbsolutePath();
    }
}
