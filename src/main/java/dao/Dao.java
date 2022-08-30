package dao;

import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(String id);

    String create(T t);

    void update(String id, T t);

    void delete(T t);
}