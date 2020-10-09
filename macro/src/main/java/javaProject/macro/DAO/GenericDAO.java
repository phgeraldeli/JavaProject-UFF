package javaProject.macro.DAO;

public interface GenericDAO<T> {
    T create(T entity);
    void delete(Object id);
    T find(Object id);
    T update(T entity);

}
