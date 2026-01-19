package Ui;

import java.util.Scanner;

import javax.management.Query;

import Engine.DatabaseEngine;
import Engine.TableEngine;
import Engine.Parser.LogicalExpression;
import Engine.Query.QueryClause;
import Engine.Query.QueryType;
import Engine.QueryBuilder.ConsoleQueryBuilder;
import Engine.TableOperation.Delete;
import Engine.TableOperation.Insert;
import Engine.TableOperation.QueryRouter;
import Engine.TableOperation.Select;

public class ConsoleUI2 {
    public static DatabaseEngine databaseEngine=new DatabaseEngine();
    public static TableEngine tableEngine=new TableEngine();
    public static Delete delete=new Delete();
    public static Insert insert=new Insert();
    public static LogicalExpression logicalExpression=new LogicalExpression();
    public static Select select=new Select();
    QueryClause queryClause;
    ConsoleQueryBuilder consoleQueryBuilder=new ConsoleQueryBuilder();
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
            System.out.println("3. perform CRUD Operation");
            
            System.out.println("Choose corresponding number to perform the operation");
            int tablechoice=r.nextInt();
            r.nextLine();
            if(tablechoice==1) tableEngine.createTable(databaseEngine.getCurrentDatabase());
            if(tablechoice==2){
                System.out.println("Enter the table name");
                String tableName=r.nextLine();
                System.out.println("Enter the values to enter in comma separeted");
                String row=r.nextLine();
                insert.insertRow(databaseEngine.getCurrentDatabase(), tableName, row);
            }
            if(tablechoice==3){
                System.out.println("Choose operation using nummber:");
                System.out.println("1. SELECT");
                System.out.println("2. UPDATE");
                System.out.println("3. DELETE");
                int opChoice = r.nextInt();
                r.nextLine(); 
                QueryType type=switch(opChoice){
                    case 1 -> QueryType.SELECT;
                    case 2 -> QueryType.UPDATE;
                    case 3 -> QueryType.DELETE;
                    default -> throw new Exception("Invalid query type");
                };
                //pass the query type to consolequerybuilder to generate query
                QueryClause q=consoleQueryBuilder.build(type);
                QueryRouter dispatcher=new QueryRouter();
                dispatcher.dispatcher(q,databaseEngine.getCurrentDatabase());
            }
        }
    }
}
