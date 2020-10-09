package javaProject.macro.DAO;

import javaProject.macro.factory.EntityFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class GenericDAOImpl<T> implements GenericDAO<T> {


    private Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T create(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = EntityFactory.criarSessao();
            tx = em.getTransaction();
            tx.begin();

            em.persist(entity);

            tx.commit();

            return entity;
        } catch ( RuntimeException e ) {
            if (Objects.nonNull(tx)) {
                try {
                    tx.rollback();
                } catch (RuntimeException ignored) {}
            }
            throw e;
        } finally {
            em.close();
        }
//        executeTransaction(() -> this.em.persist(entity));
    }

    @Override
    public void delete(Object id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        executeTransaction(() -> em.remove(em.getReference(type, id)));
    }

    @Override
    public T find(Object id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = EntityFactory.criarSessao();
            T entity = (T) em.find(type, id);
            if (Objects.isNull(entity)) throw new EntityNotFoundException("Entidade não encontrada: >>>>>>" + type);
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        executeTransaction(() -> em.merge(entity));
        return entity;
    }

    private void executeTransaction(Runnable function) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = EntityFactory.criarSessao();
            tx = em.getTransaction();
            tx.begin();

            function.run();

            tx.commit();
        } catch ( RuntimeException e ) {
            if (Objects.nonNull(tx)) {
                try {
                    tx.rollback();
                } catch (RuntimeException ignored) {}
            }
        } finally {
            em.close();
        }
    }
}
