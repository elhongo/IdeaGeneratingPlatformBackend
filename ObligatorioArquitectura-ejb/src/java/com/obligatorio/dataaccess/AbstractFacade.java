package com.obligatorio.dataaccess;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author juanmartincorallo
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws DataAccessException {
        try {
            getEntityManager().persist(entity);
        } catch (EntityExistsException ee) {
            throw new DataAccessException(ee, "Cannot create duplicate entities: " + entityClass.toString());
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error creating entity: " + entityClass.toString());
        }
    }

    public void edit(T entity) throws DataAccessException {
        try {
            getEntityManager().merge(entity);
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error editing entity: " + entityClass.toString());
        }
    }

    public void remove(T entity) throws DataAccessException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error removing entity: " + entityClass.toString());
        }
    }

    public T find(Object id) throws DataAccessException {
        try {
            T ret;
            if (id == null) {
                ret = null;
            } else {
                ret = getEntityManager().find(entityClass, id);
            }
            return ret;
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error finding entity: " + entityClass.toString());
        }
    }

    public List<T> findAll() throws DataAccessException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error finding all entities: " + entityClass.toString());
        }
    }

    public List<T> findRange(int[] range) throws DataAccessException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query query = getEntityManager().createQuery(cq);
            query.setMaxResults(range[1] - range[0] + 1);
            query.setFirstResult(range[0]);
            return query.getResultList();
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error finding entities: " + entityClass.toString());
        }
    }

    public int count() throws DataAccessException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            javax.persistence.Query query = getEntityManager().createQuery(cq);
            return ((Long) query.getSingleResult()).intValue();
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error counting entities: " + entityClass.toString());
        }
    }

}
