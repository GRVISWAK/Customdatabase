package Engine.TableOperation;
import java.util.*;

import Engine.Model.Row;
import Engine.Parser.LogicalExpression;
import Engine.Query.Aggregate;
import Engine.Query.AggregateMap;
import Engine.Query.Condition;
import Engine.Query.GroupKey;
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
        //apply joins
        for(JoinClause join:joins){
            rows=joinExecutor.applyJoin(currDbDir, join, rows);
        }
        //apply where
        if(whereClause!=null)
        rows=whereEvaluator.applyWhere(rows, whereClause);
        //apply group by
        ArrayList<Row> finalRows=new ArrayList<>();
        if(!query.getGroupByColumns().isEmpty()){
        System.out.println("Entering apply grouby");
        LinkedHashMap<String,AggregateMap> groupMap=new LinkedHashMap<>();
        for(Row row :rows){
            String groupKeyStr="";
            for(String groupCol:query.groupByColumns){
                groupKeyStr += row.get(groupCol) + "|";
            }
            AggregateMap aggregateMap=groupMap.getOrDefault(groupKeyStr, new AggregateMap());
            for(Aggregate agg:query.getAggregates()){
                String column=agg.getColumn();
                String aggfunc=agg.getAggFunction();
                System.out.println("aggregate"+column+" "+aggfunc);
                switch (aggfunc) {
                    case "SUM":
                        aggregateMap.sum.merge(column,Long.parseLong(row.get(column)),Long::sum);
                        break;
                    default:
                        break;
                }
            }
            groupMap.put(groupKeyStr, aggregateMap);
        }  
        for(Map.Entry<String,AggregateMap> group:groupMap.entrySet()){
            Row row=new Row();
            AggregateMap aggregateMap=group.getValue();
            for(Map.Entry<String,Long> sum: aggregateMap.sum.entrySet()){
                row.put(sum.getKey(),sum.getValue().toString());
            }
            finalRows.add(row);
        }
        rows.clear();
        rows.addAll(finalRows);
        }
        //get final table column list
        Set<String> columns=new HashSet<>(rows.get(0).columns());
        if(selectCol.get(0).equals("*")){
            selectCol.clear();
            for(String column:columns){
                selectCol.add(column);
            }
        }
        else{
            for(String col:selectCol){
                columns.add(col);
            }
        }
        for(String str:columns){
            System.out.print(str+"|");
        }
        System.out.println();
        for(Row row:finalRows){
            for(Map.Entry<String,String> entry:row.getValues().entrySet()){
                System.out.print(entry.getValue()+"|");
            }
            System.out.println();
        }
        /*
        for(Row row:rows){
            for(String col:columns){
                if(selectCol.contains(col))
                    System.out.print(row.get(col)+"|");
            }
            System.out.println();
        }
            */
    }
}
