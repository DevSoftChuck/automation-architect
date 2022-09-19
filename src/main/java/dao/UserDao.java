package dao;

import models.sObjects.User;
import setup.SalesforceApi;

import java.util.List;
import java.util.Optional;

public class UserDao extends Base implements Dao<User> {

    protected String SELECT_BASE_QUERY = """
            SELECT Id, Name \s
            FROM User       \s
            WHERE %s
            """;

    public UserDao from(String environment){
        connection = SalesforceApi.getOrCreateInstance(environment);
        return this;
    }

    public List<User> getAllWhere(String condition){
        return connection.query(String.format(SELECT_BASE_QUERY, condition), User.class).getRecords();
    }

    public User getWhere(String condition){
        return connection.query(String.format(SELECT_BASE_QUERY, condition), User.class)
                .getRecords().stream().findFirst().orElse(null);
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(connection.getSObject("User", id).as(User.class));
    }

    @Override
    public String create(User user) {
        return connection.createSObject("User", user);
    }

    @Override
    public void update(String id, User user) {
        connection.updateSObject("User", id, user);
    }

    @Override
    public void delete(User user) {
        connection.deleteSObject("User", user.getId());
    }
}
