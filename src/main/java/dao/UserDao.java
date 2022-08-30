package dao;

import models.User;
import java.util.Optional;

public class UserDao extends Base implements Dao<User> {

    protected String TZ_COLUMNS = "Id, Name";

    public UserDao(){
        super();
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(tzConnection.getSObject("User", id).as(User.class));
//        return runQuery(String.format(SELECT_BASE_QUERY, TZ_COLUMNS, "User", "Id="+id), User.class)
//                .getRecords()
//                .stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
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
