package Engine.TableOperation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import Engine.Model.Row;
public class TableScanner {
    public ArrayList<Row> scanTable(File currDbBir,String tableName) throws Exception{
        ArrayList<Row> rows=new ArrayList<>();
        File tableFile=new File(currDbBir,tableName+".table");
        BufferedReader br=new BufferedReader(new FileReader(tableFile));
        String line=br.readLine();
        String colName[]=line.split("\\|");
        while((line=br.readLine())!=null){
            String rowVal[]=line.split("\\|");
            Row row=new Row();
            for(int i=0;i<rowVal.length;i++){
                row.put(colName[i],rowVal[i]);
            }
            rows.add(row);
        }
        br.close();
        return rows;
    }
}
