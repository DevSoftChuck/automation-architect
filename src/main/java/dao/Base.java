package dao;

import setup.SalesforceApi;

public class Base {

    protected SalesforceApi connection;

    public Base(SalesforceApi salesforceConnection){
        connection = salesforceConnection;
    }
}
