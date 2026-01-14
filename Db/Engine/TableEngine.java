package Engine;
import java.util.*;
import java.io.*;
import java.nio.file.Files;

import Engine.DatabaseEngine;
import Metadata.ColumnMetadata;

public class TableEngine{
    //public static DatabaseEngine dbEngine=new DatabaseEngine();
    public void createTable(File currDbDir,String tableName,ArrayList<ColumnMetadata> tablesMetadatas) throws Exception{
        
        //File currDbDir=dbEngine.getCurrentDatabase();
        File metaFile=new File(currDbDir,tableName+".meta");

        if(metaFile.exists()){
            System.out.println("Table already exists");
            return;
        }
        StringBuffer data=new StringBuffer();
        data.append("table=").append(tableName+",").append("\n");
        data.append("columns=");
        for(int i=0;i<tablesMetadatas.size();i++){
            ColumnMetadata col=tablesMetadatas.get(i);
            data.append(col.getName())
            .append(":")
            .append(col.getType());
        
            if (col.isPrimarykey()) {
                data.append(":pk");
            }
        
            if (i < tablesMetadatas.size() - 1) {
                data.append(",");
            }
        }
        //create metadata file
        Files.write(metaFile.toPath(), data.toString().getBytes());
        File tableFile = new File(currDbDir, tableName + ".table");
        File schemaFile = new File(currDbDir, "schema.info");
        Files.write(schemaFile.toPath(),tableName.getBytes());
        tableFile.createNewFile();
        System.out.println("Table is cretaed at "+ currDbDir);
    }
    public void selectAllData(File currDbDir,String tableName) throws Exception{
        File tablename=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tablename));
        String line;
        while ((line = br.readLine()) != null) {
        String[] values = line.split("\\|");
        System.out.println(Arrays.toString(values));
        }
    }
    public boolean[] whereCondition(ArrayList<ArrayList<String>> table,ArrayList<String> columnName,ArrayList<String> conditionCol,ArrayList<String> operator,ArrayList<String> conditionVal,ArrayList<String> logicalOperator) throws Exception{
        int rowSize=table.size();
        int noOfCon=conditionCol.size();
        boolean [] boolRow=new boolean[rowSize];
        boolean [] boolRowResult=new boolean[rowSize];
        for(int i=0;i<noOfCon;i++){
                Arrays.fill(boolRow, false);
                switch(operator.get(i)){
                    case "=":{
                        int colInd=columnName.indexOf(conditionCol.get(i));
                        for(int j=0;j<rowSize;j++){
                            if(table.get(j).get(colInd).equals(conditionVal.get(i))){
                                boolRow[j]=true;
                            }
                        }
                        break;
                    }
                    case ">":{
                        int colInd1=columnName.indexOf(conditionCol.get(i));
                        for(int j=0;j<rowSize;j++){
                            if(Integer.parseInt(table.get(j).get(colInd1))>(Integer.parseInt(conditionVal.get(i)))){
                                boolRow[j]=true;
                            }
                        }
                        break;
                    }
                    case "<":{
                        int colInd2=columnName.indexOf(conditionCol.get(i));
                        for(int j=0;j<rowSize;j++){
                            if(Integer.parseInt(table.get(j).get(colInd2))<(Integer.parseInt(conditionVal.get(i)))){
                                boolRow[j]=true;
                            }
                        }
                        break;
                    }
                }
                if (i == 0) {
                    System.arraycopy(boolRow, 0, boolRowResult, 0, rowSize);
                    continue;
                }
                switch(logicalOperator.get(i-1)){
                    case "and":
                        for(int k=0;k<rowSize;k++){
                            boolRowResult[k]=boolRowResult[k]&&boolRow[k];
                        }
                        break;
                    case "or":
                        for(int k=0;k<rowSize;k++){
                            boolRowResult[k]=boolRowResult[k]||boolRow[k];
                        }
                        break;
                }
                
            } 
        return boolRowResult;
    }
    public void selectData(File currDbDir,String tableName,ArrayList<String>selectedColumn,ArrayList<String> conditionCol,ArrayList<String> operator,ArrayList<String> conditionVal,ArrayList<String> logicalOperator) throws Exception{
        File tablename=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tablename));
        String line=br.readLine();
        String columnNameTemp[]=line.split("\\|");
        int colSize=columnNameTemp.length;

        //contains the tables columns name 
        ArrayList<String> columnName=new ArrayList<>();

        for(String s: columnNameTemp) columnName.add(s);

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
        int noOfCon=conditionCol.size();

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
        //to print the rows
        if(noOfCon==0){
            for(int i=0;i<rowSize;i++){
                for(int j=0;j<colSize;j++){
                    if(indColList.contains(j)){
                        System.out.print(table.get(i).get(j)+"|");
                    }
                }
                System.out.println();
            }
        }  
        else{
            boolRowResult=whereCondition(table, columnName, conditionCol, operator, conditionVal, logicalOperator);
            for(int i=0;i<rowSize;i++){
                if(!boolRowResult[i]) continue;
                for(Integer j:indColList){
                    System.out.print(table.get(i).get(j)+"|");
                }
                System.out.println();
            }
        }
        }
}