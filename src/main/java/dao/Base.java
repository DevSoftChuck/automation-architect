package dao;

import com.force.api.QueryResult;
import setup.SalesforceApi;

public class Base {

    protected static SalesforceApi tzConnection;
    protected static SalesforceApi org62Connection;
    protected String SELECT_BASE_QUERY = """
            SELECT %s \s
            FROM %s       \s
            WHERE %s        \s
            """;

    public Base(){
        if (tzConnection == null)
            tzConnection = SalesforceApi.makeConnection("tz");
        if (org62Connection == null)
            org62Connection = SalesforceApi.makeConnection("org62");
    }

    protected <T> QueryResult<T> runQuery(String query, Class<T> deserializeTo){
        return tzConnection.query(query, deserializeTo);
    }
}
