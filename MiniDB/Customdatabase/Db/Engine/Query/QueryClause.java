package Engine.Query;
import java.util.*;

public class QueryClause {
    public QueryType type;
    public String table;
    public ArrayList<String> groupByColumns=new ArrayList<>();
    public ArrayList<String> selectedColumn;
    public WhereClause whereClause;
    public ArrayList<JoinClause> joins=new ArrayList<>();
    public ArrayList<Aggregate> aggregates=new ArrayList<>();
    public QueryClause (QueryType type ,String table){
        this.type=type;
        this.table=table;
    }
    public QueryClause aggregate(Aggregate agg){
        aggregates.add(agg);
        return this;
    }
    public QueryClause select(ArrayList<String> cols){
        this.selectedColumn=new ArrayList<>(cols);
        return this;
    }

    public QueryClause where(WhereClause where){
        this.whereClause=where;
        return this;
    }

    public QueryClause join(JoinClause join){
        this.joins.add(join);
        return this;
    } 

    public QueryType getType() {
        return type;
    }

    public String getTable() {
        return table;
    }
    public ArrayList<String> getSelectedColumn() {
        return selectedColumn;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    public ArrayList<JoinClause> getJoins() {
        return joins;
    }
    public ArrayList<Aggregate> getAggregates(){
        return aggregates;
    }
    public ArrayList<String> getGroupByColumns() {
        return groupByColumns;
    }
}
