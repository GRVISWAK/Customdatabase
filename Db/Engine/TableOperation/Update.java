package Engine.TableOperation;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import Engine.TableEngine;

public class Update {
    
    TableEngine tableEngine=new TableEngine();
    Insert insert=new Insert();
    public void updateTable(File currDbDir,String tableName,ArrayList<String>selectedColumn,ArrayList<String> selectedValue,ArrayList<String> conditionCol,ArrayList<String> operator,ArrayList<String> conditionVal,ArrayList<String> logicalOperator) throws Exception{
        File tablename=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tablename));
        int noOfCon=conditionCol.size();
        String line=br.readLine();
        String header=line;
        String columnNameTemp[]=line.split("\\|");
        int colSize=columnNameTemp.length;
        int noOfUpd=selectedColumn.size();
        //contains the tables columns name 
        ArrayList<String> columnName=new ArrayList<>();

        for(String s: columnNameTemp) columnName.add(s);
        HashMap<String,String> colValueMap=new HashMap<>();
        for(int i=0;i<noOfUpd;i++){
            colValueMap.putIfAbsent(selectedColumn.get(i), selectedValue.get(i));
        }
        //contains the list of index of the columns to be included int the result
        ArrayList<Integer> indColList=new ArrayList<>();
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
        if(selectedColumn.get(0).equals("*")){
            for(int i=0;i<colSize;i++){
                indColList.add(i);
            }
        }
        else{
            for(int i=0;i<colSize;i++){
                if(selectedColumn.contains(columnName.get(i))){
                    indColList.add(i);
                }
            }
        }
        
        boolRowResult=tableEngine.whereCondition(table, columnName, conditionCol, operator, conditionVal, logicalOperator);
        File tempFile=new File(currDbDir,tableName+".temp");
        BufferedWriter bw=new BufferedWriter(new FileWriter(tempFile));
        bw.write(header);
        bw.newLine();
        for(int i=0;i<rowSize;i++){
            if(!boolRowResult[i]){  
                bw.write(insert.createRow(table.get(i)));
            }
            else{
                for(Integer j:indColList){
                    String valuetoUpdate=colValueMap.get(columnName.get(j));
                    table.get(i).set(j,valuetoUpdate);
                }
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
            System.out.println("Table updated successfully\n");
            System.out.println("Table: "+tableName);
            System.out.println(header);
            for(ArrayList<String> row:table){
                System.out.println(insert.createRow(row));
            }
        }
        }
    }
