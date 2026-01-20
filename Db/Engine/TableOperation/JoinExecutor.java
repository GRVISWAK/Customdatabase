package Engine.TableOperation;

import Engine.Model.Row;
import Engine.Query.JoinClause;

import java.io.File;
import java.util.*;

public class JoinExecutor {
    TableScanner tableScanner=new TableScanner();
    public ArrayList<Row> applyJoin(File currDbBir,JoinClause join,ArrayList<Row> leftRows)throws Exception{
        String leftColumn=join.getLeftColumn();
        String rightColumn=join.getRightColumn();
        ArrayList<Row> result=new ArrayList<>();
        ArrayList<Row> rigthRows=tableScanner.scanTable(currDbBir,join.getJoinTable());
        for(Row leftRow:leftRows){
            for(Row rightRow:rigthRows){
                String leftValue=leftRow.get(leftColumn);
                String rightValue=rightRow.get(rightColumn);
                if(leftValue!=null&&leftValue.equals(rightValue)){
                    Row newRow=new Row();
                    //add right table row into new table
                    newRow.getValues().putAll(leftRow.getValues());
                    newRow.getValues().putAll(rightRow.getValues());
                    result.add(newRow);
                }
            }
        }   
        return result;
    }
}
