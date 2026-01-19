package Engine.Query;

import java.util.ArrayList;

import Engine.Parser.QueryParser;

public class WhereClause {
    public ArrayList<Condition> conditions;
    public String evalExp;
    public WhereClause(ArrayList<Condition> conditions,String evalExp){
        this.conditions=conditions;
        this.evalExp=evalExp;
    }
    public ArrayList<Condition> getConditions(){
        return conditions;
    }
    public String getevalExp(){
        return evalExp;
    }
}
