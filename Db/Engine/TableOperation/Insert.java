package Engine.TableOperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Insert {
    public String createRow(ArrayList<String> row){
        StringBuffer res=new StringBuffer();
        for(int i=0;i<row.size();i++){
            if(i==row.size()-1) res.append(row.get(i));
            else res.append(row.get(i)+"|");
        }
        return res.toString();
    }
    public void insertRow(File currDbDir,String tableName,String s) throws Exception{
        ArrayList<String> currRow=new ArrayList<>(Arrays.asList(s.split(",")));
        File tabFile=new File(currDbDir,tableName+".table");
        BufferedReader rw=new BufferedReader(new FileReader(tabFile));
        String line=rw.readLine();
        if(currRow.get(0).equals("null")){
            System.out.println("primary key column cannot be null");
            return;
        }
        while((line=rw.readLine())!=null){
            String fileRow[]=line.split("\\|");
            if(fileRow[0].equals(currRow.get(0))){
                System.out.println("Cannot insert duplicate id enter a valid id");
                return;
            }
        }
        BufferedWriter bw=new BufferedWriter(new FileWriter(tabFile,true));
        bw.newLine();
        bw.write(createRow(currRow));
        bw.close();
    }
}
