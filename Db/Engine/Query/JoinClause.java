package Engine.Query;

public class JoinClause {
    public String joinTable;
    public String leftColumn;
    public String rightColumn;
    public JoinClause(String joinTable,String leftColumn,String rightColumn){
        this.joinTable=joinTable;
        this.leftColumn=leftColumn;
        this.rightColumn=rightColumn;
    }
    public String getJoinTable(){
        return joinTable;
    }
    public String getLeftColumn(){
        return leftColumn;
    }
    public String getRightColumn(){
        return rightColumn;
    }
}
