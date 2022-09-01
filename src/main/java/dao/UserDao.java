package dao;

import models.sObjects.User;
import java.util.Optional;

public class UserDao extends Base implements Dao<User> {

    public UserDao(){
        super();
    }

    public Optional<User> getBySoql(String id){
        return runQuery(String.format(SELECT_BASE_QUERY, "Id, Name", "User", "Id="+id), User.class)
                .getRecords()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(tzConnection.getSObject("User", id).as(User.class));
    }

    @Override
    public String create(User user) {
        return tzConnection.createSObject("User", user);
    }

    @Override
    public void update(String id, User user) {
        tzConnection.updateSObject("User", id, user);
    }

    @Override
    public void delete(User user) {
        tzConnection.deleteSObject("User", user.getId());
    }
}
