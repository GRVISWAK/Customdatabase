package Ui;
import java.io.IOException;
import java.util.*;

import Engine.DatabaseEngine;
import Engine.TableEngine;
import Engine.Parser.LogicalExpression;
import Engine.Parser.QueryParser;
import Engine.TableOperation.Delete;
import Engine.TableOperation.Insert;
import Engine.TableOperation.Select;
import Engine.TableOperation.Update;
import Metadata.ColumnMetadata;
public class ConsoleUI {

    public static DatabaseEngine databaseEngine=new DatabaseEngine();
    public static TableEngine tableEngine=new TableEngine();
    public static Delete delete=new Delete();
    public static Insert insert=new Insert();
    public static LogicalExpression logicalExpression=new LogicalExpression();
    public static Select select=new Select();
    Scanner r=new Scanner(System.in);
    public void start() throws Exception{
        System.out.println("Db");
        System.out.println("Menu");
        System.out.println("Choose a option");
        System.out.println("1.Create a database");
        System.out.println("2.Use database");
        Integer choice = r.nextInt();
        r.nextLine();
        if(choice==1){
            System.out.println("Enter database name");  
            String dbName=r.nextLine();
            databaseEngine.createDatabase(dbName);
        }
        if(choice==2){
            databaseEngine.showDatabase();
            System.out.println("Which database do you want to use");
            String dbName=r.nextLine();
            databaseEngine.useDatabase(dbName);
            //do basic crud inside this db
            System.out.println("1. Create Table ");
            System.out.println("2. Insert into Table");
            System.out.println("3. Select data from table");
            System.err.println("4. select data with logical expression conditon");
            System.out.println("5. Update Table");
            System.out.println("6. Delete rows from table");
            int tablechoice=r.nextInt();
            r.nextLine();
            if(tablechoice==1) createTable();
            if(tablechoice==2){
                System.out.println("Enter the table name");
                String tableName=r.nextLine();
                System.out.println("Enter the values to enter in comma separeted");
                String row=r.nextLine();
                insert.insertRow(databaseEngine.getCurrentDatabase(), tableName, row);
            }
            if(tablechoice==3){
                System.out.println("Enter the table name");
                String tableName=r.nextLine();
                System.out.println("Enter the list of columns comma separated (if all column enter '*')");
                String listCol=r.nextLine();
                ArrayList<String> selectedColumn=new ArrayList<>(Arrays.asList(listCol.split(",")));
                ArrayList<String> conditionCol=new ArrayList<>();
                ArrayList<String> operator=new ArrayList<>();
                ArrayList<String> conditionVal=new ArrayList<>();
                ArrayList<String> logicalOperator=new ArrayList<>();
                System.out.println("Do you want add conditions");
                String isConditon=r.nextLine();
                while(isConditon.equals("yes")){
                    System.out.println("Enter the column name,operator,condition value in comma separated");
                    String condInput=r.nextLine();
                    String condInTemp[]=condInput.split(",");
                    conditionCol.add(condInTemp[0]);
                    operator.add(condInTemp[1]);
                    conditionVal.add(condInTemp[2]);
                    System.out.println("Do you want add another conditon");
                    isConditon=r.nextLine();
                    if(isConditon.equals("yes")){
                        System.out.println("what logical operator would you like add between condtitions");
                        String logicalOp=r.nextLine();
                        logicalOperator.add(logicalOp);
                    }
                }
                tableEngine.selectData(databaseEngine.getCurrentDatabase(),tableName,selectedColumn,conditionCol,operator,conditionVal,logicalOperator);
            }
            if(tablechoice==5){
                Update update=new Update();
                System.out.println("Enter the table name");
                String tableName=r.nextLine();
                System.out.println("Enter the list of columns need to be updated in comma separated (if all column enter '*')");
                String listCol=r.nextLine();
                ArrayList<String> selectedColumn=new ArrayList<>(Arrays.asList(listCol.split(",")));
                System.out.println("Enter the list of values with comma separated");
                String listVal=r.nextLine();
                ArrayList<String> selectedValue=new ArrayList<>(Arrays.asList(listVal.split(",")));
                ArrayList<String> conditionCol=new ArrayList<>();
                ArrayList<String> operator=new ArrayList<>();
                ArrayList<String> conditionVal=new ArrayList<>();
                ArrayList<String> logicalOperator=new ArrayList<>();
                System.out.println("Do you want add conditions");
                String isConditon=r.nextLine();
                while(isConditon.equals("yes")){
                    System.out.println("Enter the column name,operator,condition value in comma separated");
                    String condInput=r.nextLine();
                    String condInTemp[]=condInput.split(",");
                    conditionCol.add(condInTemp[0]);
                    operator.add(condInTemp[1]);
                    conditionVal.add(condInTemp[2]);
                    System.out.println("Do you want add another conditon");
                    isConditon=r.nextLine();
                    if(isConditon.equals("yes")){
                        System.out.println("what logical operator would you like add between condtitions");
                        String logicalOp=r.nextLine();
                        logicalOperator.add(logicalOp);
                    }
                }
                update.updateTable(databaseEngine.getCurrentDatabase(),tableName,selectedColumn,selectedValue,conditionCol,operator,conditionVal,logicalOperator);
            }
            if(tablechoice==6){
                Update update=new Update();
                System.out.println("Enter the table name to perform delete operation");
                String tableName=r.nextLine();
                System.out.println("Enter the where conditon (enter the conditon without space like age=5) (If no where condition enter nil)");
                String query=r.nextLine();
               // delete.deleteTable(databaseEngine.getCurrentDatabase(),tableName,query);
            }
            if(tablechoice==4){
                System.out.println("Enter the table name");
                String tableName=r.nextLine();
                System.out.println("Enter the list of columns need to be updated in comma separated (if all column enter '*')");
                String listCol=r.nextLine();
                ArrayList<String> selectedColumn=new ArrayList<>(Arrays.asList(listCol.split(",")));
                System.out.println("Enter the where conditon (enter the conditon without space like age=5)(If no where condition enter nil)");
                String query=r.nextLine();
                //select.selectDataWithLogExp(databaseEngine.getCurrentDatabase(), tableName, selectedColumn, query);
            }
        }
    }
    public void createTable() throws Exception{
        r.nextLine();
        System.out.println("Enter name of the table");
        String tableName=r.nextLine();
        System.out.println("Enter number of columns");
        ArrayList<ColumnMetadata> tablesmMetadatas=new ArrayList<>();
        int noOfCol=r.nextInt();
        for(int i=0;i<noOfCol;i++){
            r.nextLine();
            System.out.println("Enter "+ i + "colummn details");
            System.out.println();
            System.out.println("Enter the column name");
            String colName=r.nextLine();
            System.out.println("Enter the column datatype");
            String colType=r.nextLine();
            System.out.println("is this column is primary key->Enter true or false");
            boolean isPk=r.nextBoolean();
            ColumnMetadata colmetadata=new ColumnMetadata(colName,colType,isPk);
            tablesmMetadatas.add(colmetadata);
        }
        tableEngine.createTableMetaData(databaseEngine.getCurrentDatabase(),tableName,tablesmMetadatas);
    }
}
