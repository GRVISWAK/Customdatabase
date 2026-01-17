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
    public int evaluateCondition(ArrayList<String> row,String colName[],Condition condition){
        for(int i=0;i<row.size();i++){
                if(condition.getCondtionCol().equals(colName[i])){
                    String value=condition.getConditionalVal();
                    switch(condition.getOperator()){
                        case "=":{
                            if(row.get(i).equals(value))
                                return 1;
                            break;
                        }
                        case ">":{
                            if(Integer.parseInt(row.get(i))>Integer.parseInt(value))
                                return 1;
                            break;
                        }
                        case "<":{
                             if(Integer.parseInt(row.get(i))<Integer.parseInt(value))
                                return 1;
                            break;
                        }
                    }
                }
        }
        return 0;
    }
    public boolean evaluateCondition2(Row row){
        String currCell=row.get(conditionCol);
        switch(operator){
            case "=":{
                return currCell.equals(conditionVal);
            }
            case" >":{
                return Integer.parseInt(currCell)>Integer.parseInt(conditionVal);
            }
            case "<":{
                return Integer.parseInt(currCell)<Integer.parseInt(conditionVal);
            }
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
