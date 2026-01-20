package Engine.Query;

public class Aggregate {
    String aggFunction;
    String column;
    public Aggregate(String aggFunction,String column){
        this.aggFunction=aggFunction;
        this.column=column;
    }
    public String getAggFunction(){
        return aggFunction;
    }
    public String getColumn(){
        return column;
    }
}