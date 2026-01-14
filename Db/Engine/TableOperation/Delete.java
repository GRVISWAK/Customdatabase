package Engine.TableOperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import Engine.TableEngine;

public class Delete {
    TableEngine tableEngine=new TableEngine();
    Insert insert=new Insert();
    public void deleteTable(File currDbDir,String tableName,ArrayList<String> conditionCol,ArrayList<String> operator,ArrayList<String> conditionVal,ArrayList<String> logicalOperator) throws Exception{
        File tablename=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tablename));
        int noOfCon=conditionCol.size();
        String line=br.readLine();
        String header=line;
        String columnNameTemp[]=line.split("\\|");
        int colSize=columnNameTemp.length;
        //contains the tables columns name 
        ArrayList<String> columnName=new ArrayList<>();
        //contains the list of index of the columns to be included int the result
        ArrayList<ArrayList<String>> table=new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\\|");
            ArrayList<String> temp=new ArrayList<>();
            for(String s:values){
                temp.add(s);
            }
            table.add(temp);
        }
        int rowSize=table.size();
        //boolean [] boolRow=new boolean[rowSize];
        boolean [] boolRowResult=new boolean[rowSize];

        //to check whether all colummns or specific columns
        boolRowResult=tableEngine.whereCondition(table, columnName, conditionCol, operator, conditionVal, logicalOperator);
        File tempFile=new File(currDbDir,tableName+".temp");
        BufferedWriter bw=new BufferedWriter(new FileWriter(tempFile));
        bw.write(header);
        bw.newLine();
        for(int i=0;i<rowSize;i++){
            if(!boolRowResult[i]){  
                bw.write(insert.createRow(table.get(i)));
            }
            bw.newLine();
        }
        bw.close();
        File origFile=new File(currDbDir,tableName+".table");
        origFile.delete();
        tempFile.renameTo(new File(currDbDir,tableName+".table"));
        File tableFileName=new File(currDbDir,tableName+".table");
        if(tableFileName.exists()){
            System.out.println("Delete operation performed successfully\n");
            System.out.println("Table: "+tableName);
            System.out.println(header);
            for(int i=0;i<rowSize;i++){
            if(!boolRowResult[i]){  
                System.out.println(insert.createRow(table.get(i)));
            }
        }
        }
        }
}