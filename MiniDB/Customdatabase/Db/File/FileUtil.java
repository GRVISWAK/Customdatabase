package File;
import java.util.*;
import java.io.*;
public class FileUtil {
    public void createFile(File folderPath,String fileName){
        File file=new File(folderPath,fileName);
       try{
        file.createNewFile();
       }
       catch(Exception e){
        System.out.println("Error creating file");
       }
    }
}
