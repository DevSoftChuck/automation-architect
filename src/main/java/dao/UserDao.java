package dao;

import models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends Base implements Dao<User> {

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> get(String id) {
        return runQuery(String.format(SELECT_BASE_QUERY, "User", "Id="+id), User.class)
                .getRecords()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return runQuery(String.format(SELECT_BASE_QUERY, "User", "1=1"), User.class)
                .getRecords();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
