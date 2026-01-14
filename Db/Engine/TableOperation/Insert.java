package Engine.TableOperation;

import java.util.ArrayList;

public class Insert {
    public String createRow(ArrayList<String> row){
        StringBuffer res=new StringBuffer();
        for(int i=0;i<row.size();i++){
            if(i==row.size()-1) res.append(row.get(i));
            else res.append(row.get(i)+"|");
        }
        return res.toString();
    }
}
