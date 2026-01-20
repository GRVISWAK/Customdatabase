package Engine.Router;

import java.io.File;

import Engine.Query.QueryClause;
import Engine.TableOperation.Delete;
import Engine.TableOperation.Select;

public class QueryRouter {
    Select select=new Select();
    Delete delete=new Delete();
    public void dispatcher(QueryClause query,File currDbBir) throws Exception{
        switch (query.type) {
            case SELECT:
                select.selectData(currDbBir, query);
                break;
            case DELETE:
                delete.deleteTable(currDbBir, query);
            case UPDATE:
                
            default:
                break;
        }
    }
}
