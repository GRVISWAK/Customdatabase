package Engine;
import java.util.*;

import File.FileUtil;

import java.io.*;
import java.nio.file.Files;

public class DatabaseEngine {
    public static String basePath="DatabaseStorage";
    File baseDirectory=new File(basePath);
    public String currDb;
    public File currDbDir;
    
    public File getCurrentDatabase(){
        return currDbDir;
    }
    //to check whether database is selected
    public boolean isDatabaseSelected(){
        if(currDbDir==null) return false;
        return true;
    }

    public void createDatabase(String dbName){
        FileUtil f=new FileUtil();
        if(!baseDirectory.exists()){
            baseDirectory.mkdir();
        }
        File dbDirectory=new File(baseDirectory,dbName);
        if(dbDirectory.exists()){
            System.out.println("Database already exists");
        }
        else{
            dbDirectory.mkdir();
            f.createFile(dbDirectory,"schema.info");
            System.out.println("Database " + dbName + " created successfully");
        }
    }

    public void showDatabase(){
        //it gets list of dbs inside dbstorage
        File[] databases =baseDirectory.listFiles(File::isDirectory);
        if(databases==null||databases.length==0){
            System.out.println("No databases found");
        }
        else{
            System.out.println("List of databases :");
            for(File db:databases){
                System.out.println(db.getName());
            }
        }
    }

    public void useDatabase(String dbName){
        File currDbDir=new File(baseDirectory,dbName);
        if(currDbDir.exists()){
            this.currDb=dbName;
            this.currDbDir=currDbDir;
            System.out.println("Selected Database : "+dbName);
        }
        else{
            System.out.println("Database doesn't exist");
        }
    }
    public void deleteDataBase(){

    }

}
