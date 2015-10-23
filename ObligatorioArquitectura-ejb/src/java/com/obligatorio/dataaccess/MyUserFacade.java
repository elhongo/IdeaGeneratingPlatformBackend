package com.obligatorio.dataaccess;

import com.obligatorio.entities.MyUser;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author juanmartincorallo
 */
@Stateless
public class MyUserFacade extends AbstractFacade<MyUser> {

    @PersistenceContext(unitName = "ObligatorioArquitectura-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyUserFacade() {
        super(MyUser.class);
    }

    public MyUser getUser(String username) throws DataAccessException {
        String queryStr = "select u from MyUser u "
                + "where u.username = :pUsername";
        MyUser ret = null;
        try {
            Object res = em.createQuery(queryStr)
                    .setParameter("pUsername", username)
                    .getSingleResult();
            ret = (MyUser) res;
        } catch (NoResultException nre) {
            ret = null;
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error accessing User: " + username);
        }
        return ret;
    }

    public Boolean validPassword(String username, String password) throws DataAccessException {
        Boolean valid;
        String queryStr = "select u from MyUser u "
                + "where (u.username) = :pUsername";
        try {
            Object res = em.createQuery(queryStr)
                    .setParameter("pUsername", username)
                    //.setParameter("pPassword", username)
                    .getSingleResult();
            if (res == null) {
                valid = false;
            } else {
                MyUser aux = (MyUser) res;
                valid = aux.getPassword().equals(password);
            }
        } catch (NoResultException nre) {
            valid = false;
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Could not access user");
        }
        return valid;
    }

}
