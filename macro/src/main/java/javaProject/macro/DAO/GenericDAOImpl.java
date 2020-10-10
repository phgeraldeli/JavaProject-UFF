package javaProject.macro.DAO;

import javaProject.macro.factory.EntityFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class GenericDAOImpl<T> implements GenericDAO<T> {

    protected EntityManager em = null;
    protected EntityTransaction tx = null;
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T create(T entity) {
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
    }

    @Override
    public void delete(Object id) {
        try {
            em = EntityFactory.criarSessao();
            tx = em.getTransaction();
            tx.begin();

            T obj = em.getReference(type, id);

            if (Objects.isNull(obj)) {
                tx.rollback();
                throw new EntityNotFoundException("Entidade não encontrada");
            }

            em.remove(obj);
            tx.commit();
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
    }

    @Override
    public T find(Object id) {
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
    public T update(final T entity) {
        try {
            em = EntityFactory.criarSessao();
            tx = em.getTransaction();
            tx.begin();

            this.em.merge(entity);

            tx.commit();
        } catch (OptimisticLockException e) {
            if (Objects.nonNull(tx)) {
                tx.rollback();
                throw new OptimisticLockException("Objeto foi modificado em tempo de execução");
            }
        } catch (RuntimeException e) {
            if (Objects.nonNull(tx)) {
                try {
                    tx.rollback();
                } catch (RuntimeException ignored) {}
            }
        } finally {
            em.close();
        }
        return entity;
    }

}
