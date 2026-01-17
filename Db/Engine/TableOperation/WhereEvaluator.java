package Engine.TableOperation;

import Engine.Model.Row;
import Engine.Parser.LogicalExpression;
import Engine.Query.Condition;
import Engine.Query.WhereClause;
import java.util.*;

public class WhereEvaluator {
    public int evaluateWhere(Row row,WhereClause whereClause){
        String evalExp=whereClause.getevalExp();
        StringBuffer exp=new StringBuffer(evalExp);
        ArrayList<Condition> conditions=whereClause.getConditions();
        for(int i=0;i<exp.length();i++){
                char ch=exp.charAt(i);
                if(Character.isDigit(ch)){
                    int ind=(int)ch-50;
                    int res=conditions.get(ind).evaluateCondition2(row)==true?1:0;
                    exp.setCharAt(i, (char)(res+'0'));
                }
        }
        LogicalExpression logicalExpression=new LogicalExpression();
        return logicalExpression.evaluateLogicalExp(exp.toString());
    }
}
