package Engine.Query;

import java.util.ArrayList;

import Engine.Model.Row;

public class Condition {
    public String conditionCol;
    public String operator;
    public String conditionVal;
    public Condition(String conditionCol,String operator,String conditionVal){
        this.conditionCol=conditionCol;
        this.operator=operator;
        this.conditionVal=conditionVal;
    }
    public String getCondtionCol(){
        return conditionCol;
    }
    public String getOperator(){
        return operator;
    }
    public String getConditionalVal(){
        return conditionVal;
    }
    public boolean evaluateCondition(Row row){
        String currCell=row.get(conditionCol);
        switch(operator){
            case "=":{
                return currCell.equals(conditionVal);
            }
            case ">":{
                return Integer.parseInt(currCell)>Integer.parseInt(conditionVal);
            }
            case "<":{
                return Integer.parseInt(currCell)<Integer.parseInt(conditionVal);
            }
            case ">=":{
                return Integer.parseInt(currCell)>=Integer.parseInt(conditionVal);
            }
            case "<=":{
                return Integer.parseInt(currCell)<=Integer.parseInt(conditionVal);
            }
            case "!=":{
                return !(currCell.equals(conditionVal));
            }
            case "like":{
                String newVal=conditionVal.replaceAll("%",".*");
                return currCell.matches(newVal);
            }
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
