package Engine.TableOperation;
import java.util.*;

import Engine.Model.Row;
import Engine.Query.Aggregate;
import Engine.Query.AggregateMap;
import Engine.Query.QueryClause;

public class GroupBy {
    public ArrayList<Row> applyGroupBy(ArrayList<Row> rows,QueryClause query){
        LinkedHashMap<String,AggregateMap> groupMap=new LinkedHashMap<>();
        ArrayList<Row> result=new ArrayList<>();
        for(Row row :rows){
            String groupKeyStr="";
            for(String groupCol:query.groupByColumns){
                groupKeyStr += row.get(groupCol) + "|";
            }
            AggregateMap aggregateMap=groupMap.getOrDefault(groupKeyStr, new AggregateMap());
            for(Aggregate agg:query.getAggregates()){
                String column=agg.getColumn();
                String rowCol=column.substring(column.indexOf('[')+1,column.indexOf(']'));
                String aggfunc=agg.getAggFunction();
                Long rowVal=0L;
                if(!aggfunc.equals("CNT"))
                rowVal=Long.parseLong(row.get(rowCol));
                switch (aggfunc) {
                    case "SUM":{
                        aggregateMap.sum.merge(column,rowVal,Long::sum);
                        break;
                    }
                    case "MIN":{
                        Long value=aggregateMap.min.getOrDefault(column,Long.MAX_VALUE);
                        aggregateMap.min.put(column,Math.min(value,rowVal));
                        break;
                    }
                    case "MAX":{
                        Long value=aggregateMap.max.getOrDefault(column,Long.MIN_VALUE);
                        aggregateMap.min.put(column,Math.max(value,rowVal));
                        break;      
                    }
                    case "CNT":{
                        Long value=aggregateMap.cnt.getOrDefault(column, 0L);
                        aggregateMap.cnt.put(column, value+1);
                        break;
                    }
                    case "AVG":{
                        aggregateMap.avgSum.merge(column,rowVal,Long::sum);
                        Long value=aggregateMap.avgCnt.getOrDefault(column, 0L);
                        aggregateMap.avgCnt.put(column, value+1);
                        aggregateMap.avg.put(column,aggregateMap.avgSum.get(column)/aggregateMap.avgCnt.get(column));
                    }
                    default:
                        break;
                }
            }
            groupMap.put(groupKeyStr, aggregateMap);
        }  
        for(Map.Entry<String,AggregateMap> group:groupMap.entrySet()){

            Row row=new Row();
            String groupByValues[]=group.getKey().split("\\|");
            for(int i=0;i<groupByValues.length;i++){
                row.put(query.getGroupByColumns().get(i),groupByValues[i]);
            }
            AggregateMap aggregateMap=group.getValue();
            for(Map.Entry<String,Long> sum: aggregateMap.sum.entrySet()){
                row.put(sum.getKey(),sum.getValue().toString());
            }

            for(Map.Entry<String,Long> min: aggregateMap.min.entrySet()){
                row.put(min.getKey(),min.getValue().toString());
            }

            for(Map.Entry<String,Long> max: aggregateMap.max.entrySet()){
                row.put(max.getKey(),max.getValue().toString());
            }

            for(Map.Entry<String,Long> cnt: aggregateMap.cnt.entrySet()){
                row.put(cnt.getKey(),cnt.getValue().toString());
            }

            for(Map.Entry<String,Long> avg: aggregateMap.avg.entrySet()){
                row.put(avg.getKey(),avg.getValue().toString());
            }

            result.add(row);

        }
        return result;
        } 
}
