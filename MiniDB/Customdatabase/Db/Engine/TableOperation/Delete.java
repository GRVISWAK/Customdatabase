package Engine.TableOperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import Engine.TableEngine;
import Engine.Model.Row;
import Engine.Parser.LogicalExpression;
import Engine.Query.Condition;
import Engine.Query.QueryClause;
import Engine.Query.WhereClause;

public class Delete {
    LogicalExpression logicalExpression=new LogicalExpression();
    Condition condition;
    WhereEvaluator whereEvaluator=new WhereEvaluator();
    public void deleteTable(File currDbDir,QueryClause query) throws Exception{
        String tableName=query.getTable();
        File tablename=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tablename));
        String line=br.readLine();
        String header=line;
        String colName[]=line.split("\\|");
        WhereClause whereClause=query.getWhereClause();
        int colSize=colName.length;
        File tempFile=new File(currDbDir,tableName+".temp");
        BufferedWriter bw=new BufferedWriter(new FileWriter(tempFile));
        bw.write(header);
        bw.newLine();
        while((line=br.readLine())!=null){
            ArrayList<String> rowList=new ArrayList<>(Arrays.asList(line.split("\\|")));
            //evaluate condition and store it in reformulatedexp
            Row row=new Row();
            for(int i=0;i<rowList.size();i++){
                row.put(colName[i], rowList.get(i));
            }
            if(whereEvaluator.evaluateWhere(row,whereClause)==0){
                StringBuilder temp=new StringBuilder();
                for(int i=0;i<colSize;i++){
                    if(i-1!=colSize)
                    temp.append(row.get(colName[i])).append("|");
                    else{
                        temp.append(row.get(colName[i]));
                    }
                }
                System.out.println(temp);
                bw.write(temp.toString());
                bw.newLine();
            }
        }
        br.close();
        bw.close();
        File origFile=new File(currDbDir,tableName+".table");
        origFile.delete();
        tempFile.renameTo(new File(currDbDir,tableName+".table"));
        File tableFileName=new File(currDbDir,tableName+".table");
        if(tableFileName.exists()){
                System.out.println("Delete operation performed successfully\n");
                System.out.println("Table: "+tableName);
                File tabFile=new File(currDbDir,tableName+".table");
                BufferedReader br2=new BufferedReader(new FileReader(tabFile));
                String line2;
                System.out.println();
                while((line2=br2.readLine())!=null){
                    System.out.println(line2);
                }
                br2.close();
            }
    }
}