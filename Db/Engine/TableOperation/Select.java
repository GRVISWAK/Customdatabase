package Engine.TableOperation;
import java.util.*;

import Engine.Parser.LogicalExpression;
import Engine.Parser.ParseResult;
import Engine.Parser.QueryParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
public class Select {
    LogicalExpression logicalExpression=new LogicalExpression();
    public void selectDataWithLogExp(File currDbDir,String tableName,ArrayList<String> selectCol,String whereQuery) throws Exception{
        File tabFile=new File(currDbDir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tabFile));
        String line=br.readLine();
        String colName[]=line.split("\\|");
        String headerContent=line;
        QueryParser parser=new QueryParser();
        ParseResult parseResult1=parser.parseQuery(whereQuery);
        String expTemp=parseResult1.getReformulatedExp();
        ArrayList<ArrayList<String>> conditions=parseResult1.getConditons();
        int colSize=colName.length;
        System.out.println("expTemp "+expTemp);
        
        for(int i=0;i<colName.length;i++){
                if(selectCol.contains(colName[i])){
                        System.out.print(colName[i]+"|");
                }
        }
        System.out.println();
        while((line=br.readLine())!=null){
            StringBuffer exp=new StringBuffer(expTemp);
            String rowContent=line;
            String row[]=line.split("\\|");
            //evaluate condition and store it in reformulatedexp
            for(int i=0;i<exp.length();i++){
                char ch=exp.charAt(i);
                if(Character.isDigit(ch)){
                    int ind=(int)ch-50;
                    int res=evaluateCondition(row, colName, conditions.get(ind));
                    exp.setCharAt(i, (char)(res+'0'));
                }
            }
            //System.out.println(exp);
            int logicalExpRes=logicalExpression.evaluateLogicalExp(exp.toString());
            System.out.println(exp+" "+logicalExpRes);
            if(logicalExpRes==1){
                for(int i=0;i<row.length;i++){
                    if(selectCol.contains(colName[i])){
                        System.out.print(row[i]+"|");
                    }
                }
                System.out.println();
            }
        }
    }
    int evaluateCondition(String row[],String colName[],ArrayList<String> condition){
        for(int i=0;i<row.length;i++){
                if(condition.get(0).equals(colName[i])){
                    String value=condition.get(2);
                    switch(condition.get(1)){
                        case "=":{
                            if(row[i].equals(value))
                                return 1;
                            break;
                        }
                        case ">":{
                            if(Integer.parseInt(row[i])>Integer.parseInt(value))
                                return 1;
                            break;
                        }
                        case "<":{
                             if(Integer.parseInt(row[i])<Integer.parseInt(value))
                                return 1;
                            break;
                        }
                    }
                }
        }
        return 0;
    }
}
