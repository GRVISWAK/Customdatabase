package Engine.Parser;
import java.util.*;

public class ParseResult {
    public String reformatedExp;
    public ArrayList<ArrayList<String>> conditons=new ArrayList<>();
    ParseResult(String reformatedExp,ArrayList<ArrayList<String>> conditions){
        this.reformatedExp=reformatedExp;
        this.conditons=conditions;
    }
    public String getReformulatedExp(){
        return reformatedExp;
    }
    public ArrayList<ArrayList<String>> getConditons(){
        return conditons;
    }
}
