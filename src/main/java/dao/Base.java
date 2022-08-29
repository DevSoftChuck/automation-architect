package dao;

import com.force.api.QueryResult;
import setup.SalesforceApi;

public class Base {

    protected static SalesforceApi tzConnection = SalesforceApi.makeConnection("tz");
    protected static SalesforceApi org62Connection = SalesforceApi.makeConnection("org62");
    protected String SELECT_BASE_QUERY = """
            Select Id, Name \s
            FROM %s       \s
            WHERE %s        \s
            """;

    protected <T> QueryResult<T> runQuery(String query, Class<T> deserializeTo){
        return tzConnection.query(query, deserializeTo);
    }
}
