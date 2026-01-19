package Engine.TableOperation;
import java.util.*;

import Engine.Parser.LogicalExpression;
import Engine.Parser.ParseResult;
import Engine.Parser.QueryParser;
import Engine.Query.Condition;
import Engine.Query.WhereClause;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
public class Select {
    LogicalExpression logicalExpression=new LogicalExpression();
    Condition condition;
    public void selectDataWithLogExp(File currDbDir,String tableName,ArrayList<String> selectCol,String whereQuery) throws Exception{
        File tabFile=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tabFile));
        String line=br.readLine();
        String colName[]=line.split("\\|");
        StringBuilder queryTemp=new StringBuilder(whereQuery);
        queryTemp.insert(0,"(");
        queryTemp.append(")");
        int colSize=colName.length;
        whereQuery=queryTemp.toString();
        if(selectCol.get(0).equals("*")){
            for(int i=0;i<colSize;i++){
                selectCol.add(colName[i]);
            }
        }
        for(int i=0;i<colName.length;i++){
                if(selectCol.contains(colName[i])){
                        System.out.print(colName[i]+"|");
                }
        }
        System.out.println();
        if(whereQuery.equals("(nil)")){
            while((line=br.readLine())!=null){
            String row[]=line.split("\\|");
            for(int i=0;i<row.length;i++){
                    if(selectCol.contains(colName[i])){
                        System.out.print(row[i]+"|");
                    }
                }
                System.out.println();
            }
            return;
        }
        else{
        QueryParser parser=new QueryParser();
  
        WhereClause whereClause=parser.parseQuery(whereQuery);
        String expTemp=whereClause.getevalExp();
        ArrayList<Condition> conditions=whereClause.getConditions();
        //System.out.println("expTemp "+expTemp);
        while((line=br.readLine())!=null){
            StringBuffer exp=new StringBuffer(expTemp);
            String rowContent=line;
            ArrayList<String> row=new ArrayList<>(Arrays.asList(line.split("\\|")));
            //evaluate condition and store it in reformulatedexp
            for(int i=0;i<exp.length();i++){
                char ch=exp.charAt(i);
                if(Character.isDigit(ch)){
                    int ind=(int)ch-50;
                    int res=condition.evaluateCondition(row, colName, conditions.get(ind));
                    exp.setCharAt(i, (char)(res+'0'));
                }
            }
            //System.out.println(exp);
            int logicalExpRes=logicalExpression.evaluateLogicalExp(exp.toString());
            //System.out.println(exp+" "+logicalExpRes);
            if(logicalExpRes==1){
                for(int i=0;i<row.size();i++){
                    if(selectCol.contains(colName[i])){
                        System.out.print(row.get(i)+"|");
                    }
                }
                System.out.println();
            }
        }
        }
    }
}
