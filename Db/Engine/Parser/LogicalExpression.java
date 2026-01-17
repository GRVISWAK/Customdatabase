package Engine.Parser;
import java.util.*;
import java.util.ArrayList;

public class LogicalExpression {
    public int evaluateLogicalExp(String exp){
        Stack<Character> st=new Stack<>();
        //StringBuffer tempExp=new StringBuffer();
        for(int i=0;i<exp.length();i++){
            char c=exp.charAt(i);

            if(c=='('){
                st.push(c);
            }
            else if(c==')'){
                StringBuilder str=new StringBuilder();
                while(!st.empty()&&st.peek()!='('){
                    //pass condition
                    str.append(st.pop());
                }
                str.reverse();
                String tempExp=str.toString();
                st.pop();
                int logRes=tempExp.charAt(0)-'0';
                int len=tempExp.length();
                for(int j=1;j<=len-1;j=j+2){
                    char ch=tempExp.charAt(j+1);
                    int condVal=0;
                    //if the expression is already evaluated
                    if(Character.isDigit(ch)) condVal=ch-'0';
                    //check whether next character is a condition
                    if(tempExp.charAt(j)=='&') logRes=logRes&condVal;
                    else logRes=logRes|condVal;
                }
                st.push((char)(logRes+'0'));
            }
            else{
                st.push(c);
            }
        }
        return st.peek()-'0';
    }
}
