package com.obligatorio.sessionbeans;

import com.obligatorio.dataaccess.DataAccessException;
import com.obligatorio.dataaccess.MyUserFacade;
import com.obligatorio.entities.MyUser;
import com.obligatorio.obligatorioexceptions.ObligatorioException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author juanmartincorallo
 */
@Stateless
@LocalBean
public class UsersBean {

    @EJB
    private MyUserFacade userAccess;

    public List<MyUser> getAllUsers() throws DataAccessException {
        return userAccess.findAll();
    }

    public MyUser getUser(String username) throws DataAccessException {
        return userAccess.getUser(username);
    }

    /**
     * Creates and saves the user.
     *
     * @param username
     * @param pass
     * @param email
     * @param birthdate
     * @param gender
     * @param country
     * @return true = OK, false = could not save user
     */
    public boolean registerUser(String username, String pass, String email,
            Calendar birthdate, char gender, String country) {
        boolean registered;
        MyUser newUser = new MyUser();
        newUser.setUsername(username);
        newUser.setPassword(pass);
        newUser.setGender(gender);
        newUser.setEmail(email);
        newUser.setBirthdate(birthdate);
        newUser.setCountry(country);
        try {
            MyUser exists = userAccess.getUser(username);
            if (exists != null) {
                registered = false; // user already exists
            } else {
                userAccess.create(newUser);
                registered = true;
            }
        } catch (ObligatorioException oe) {
            registered = false;
        }
        return registered;
    }

    /**
     * Returns true if username-password exist and match, false otherwise
     *
     * @param username
     * @param password
     * @return
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public boolean validateUsernamePassword(String username, String password) throws DataAccessException {
        return userAccess.validPassword(username, password);
    }
}
