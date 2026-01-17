package Engine.TableOperation;
import java.util.*;

import Engine.Model.Row;
import Engine.Parser.LogicalExpression;
import Engine.Query.Condition;
import Engine.Query.JoinClause;
import Engine.Query.QueryClause;
import Engine.Query.WhereClause;

import java.io.File;

public class Select2 {
    LogicalExpression logicalExpression=new LogicalExpression();
    Condition condition;
    WhereEvaluator whereEvaluator=new WhereEvaluator();
    public void selectDataWithLogExp(File currDbDir,QueryClause query) throws Exception{
        String tableName=query.getTable();
        ArrayList<String> selectCol=query.getSelectedColumn();
        ArrayList<JoinClause> joins=query.getJoins();
        WhereClause whereClause=query.getWhereClause();

        TableScanner tableScanner=new TableScanner();
        JoinExecutor joinExecutor=new JoinExecutor();
        ArrayList<Row> rows=tableScanner.scanTable(currDbDir,tableName);
        for(JoinClause join:joins){
            rows=joinExecutor.applyJoin(currDbDir, join, rows);
        }
        //get final table column list
        Set<String> columns=rows.get(0).columns();
        if(selectCol.get(0).equals("*")){
            selectCol.clear();
            for(String column:columns){
                selectCol.add(column);
            }
        }
        for(String str:columns){
            System.out.print(str+"|");
        }
        System.out.println();
        for(Row row:rows){
            if(whereClause==null){
                for(String col:columns){
                    if(selectCol.contains(col))
                        System.out.print(row.get(col)+"|");
                }
                System.out.println();
            }
            else{
                if(whereEvaluator.evaluateWhere(row,whereClause)==1){
                    for(String col:columns){
                        if(selectCol.contains(col))
                            System.out.print(row.get(col)+"|");
                    }
                    System.out.println();
                }
            }
        }
    }
}
