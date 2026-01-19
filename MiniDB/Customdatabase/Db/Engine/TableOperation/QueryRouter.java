package Engine.TableOperation;

import java.io.File;

import Engine.Query.QueryClause;

public class QueryRouter {
    Select2 select=new Select2();
    Delete delete=new Delete();
    public void dispatcher(QueryClause query,File currDbBir) throws Exception{
        switch (query.type) {
            case SELECT:
                select.selectDataWithLogExp(currDbBir, query);
                break;
            case DELETE:
                delete.deleteTable(currDbBir, query);
            default:
                break;
        }
    }
}
