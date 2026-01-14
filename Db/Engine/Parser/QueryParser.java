package Engine.Parser;
import java.util.*;
import java.util.regex.*;
public class QueryParser {
    public ParseResult parseQuery(String query){
        Pattern p=Pattern.compile("(\\(|\\)|\\band\\b|\\bor\\b|>=|<=|!=|>|<|=|[^()\\s]+)");
        ArrayList<String> tokens=new ArrayList<>();
        Matcher m=p.matcher(query);
        while(m.find()){
            tokens.add(m.group());
        }
        ArrayList<ArrayList<String>> conditions=new ArrayList<>();
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
                temp.add(cond[0]);
                temp.add(op);
                temp.add(cond[1]);
                reformatedExp.append((ind));
                conditions.add(temp);
                ind++;
            }
        }
        for(int i=0;i<reformatedExp.length();i++){
            System.out.print(reformatedExp.charAt(i)+" ");
        }
        return new ParseResult(reformatedExp.toString(), conditions);
    }
}
