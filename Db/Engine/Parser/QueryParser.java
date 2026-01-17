package Engine.Parser;
import java.util.*;
import java.util.regex.*;

import Engine.Query.Condition;
import Engine.Query.WhereClause;
public class QueryParser {
    public WhereClause parseQuery(String query){
        StringBuilder queryTemp=new StringBuilder(query);
        queryTemp.insert(0,"(");
        queryTemp.append(")");
        query=queryTemp.toString();
        Pattern p=Pattern.compile("(\\(|\\)|\\band\\b|\\bor\\b|>=|<=|!=|>|<|=|[^()\\s]+)");
        ArrayList<String> tokens=new ArrayList<>();
        Matcher m=p.matcher(query);
        while(m.find()){
            tokens.add(m.group());
        }
        ArrayList<Condition> conditions=new ArrayList<>();
        //ArrayList<String> reformatedExp=new ArrayList<>();
        StringBuffer reformatedExp=new StringBuffer();
        int ind=2;
        for(String token:tokens){
            if(token.equals("(")||token.equals(")")){
                reformatedExp.append(token);
            }
            else if(token.equals("and")){
                reformatedExp.append("&");
            }
            else if(token.equals("or")){
                reformatedExp.append("|");
            }
            else{
                ArrayList<String> temp=new ArrayList<>();
                Pattern p1=Pattern.compile(">=|<=|!=|=|>|<");
                Matcher m1=p1.matcher(token);
                String op="";
                while(m1.find()){
                    //System.out.println(m1.group());
                    op=m1.group();
                }
                String cond[]=token.split("[>=|<=|!=|=|>|<]");
                reformatedExp.append((ind));
                Condition condition=new Condition(cond[0], op, cond[1]);
                conditions.add(condition);
                ind++;
            }
        }
        return new WhereClause(conditions, reformatedExp.toString());
    }
}
