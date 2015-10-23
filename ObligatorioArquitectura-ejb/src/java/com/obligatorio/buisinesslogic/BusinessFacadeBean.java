package com.obligatorio.buisinesslogic;

import com.obligatorio.dataaccess.DataAccessException;
import com.obligatorio.dataaccess.VotingException;
import com.obligatorio.entities.Comment;
import com.obligatorio.entities.Idea;
import com.obligatorio.sessionbeans.AuthenticationBean;
import com.obligatorio.sessionbeans.IdeasBean;
import com.obligatorio.sessionbeans.LoginException;
import com.obligatorio.sessionbeans.UsersBean;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class BusinessFacadeBean {

    @EJB
    private AuthenticationBean authenticationBean;

    @EJB
    private UsersBean usersBean;

    @EJB
    private IdeasBean ideasBean;

    /**
     *
     * @return list of all the ideas in the system
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public List<Idea> getIdeas() throws DataAccessException {
        return ideasBean.getIdeas();
    }

    public Idea getIdea(Long ideaId) throws DataAccessException {
        return ideasBean.getIdea(ideaId);
    }

    public List<Comment> getCommentsFromIdea(Long ideaId) throws DataAccessException {
        return ideasBean.getCommentsFromIdea(ideaId);
    }

    /**
     *
     * @param tag
     * @return the idea that was searched
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public List<Idea> getIdeasWithTag(String tag) throws DataAccessException {
        return ideasBean.getIdeasWithTag(tag);
    }

    /**
     * registers a new user into the system
     *
     * @param username
     * @param password
     * @param email
     * @param birthdate
     * @param gender
     * @param country
     * @return
     * @throws DateFormatException if date input is incorrect throws
     * RegisterException if register could not be completed
     */
    public Boolean register(String username, String password, String email,
            String birthdate, char gender, String country) throws DateFormatException {
        try {
            Calendar birthdayDate = parseDate(birthdate);
            return usersBean.registerUser(username, password, email, birthdayDate,
                    gender, country);
        } catch (NumberFormatException nex) {
            throw new DateFormatException(nex, "Date has an incorrect format."
                    + " The expected format is: YYYY-MM-DD");
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return UUID token
     * @throws com.obligatorio.sessionbeans.LoginException
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public UUID login(String username, String password) throws LoginException, DataAccessException {
        if (usersBean.validateUsernamePassword(username, password)) {
            UUID token = authenticationBean.loginUser(username);
            return token;
        } else {
            throw new LoginException(null, "Username or pasword is incorrect.");
        }
    }

    public boolean authenticateUser(UUID token, String username) {
        return authenticationBean.authenticateUser(token, username);
    }

    public boolean logout(UUID token, String username) {
        return authenticationBean.logout(token, username);
    }

    public void postIdea(String username, String name, String description) throws DataAccessException {
        ideasBean.createIdea(username, name, description);
    }

    public void postComment(String username, Long ideaId, String comment) throws DataAccessException {
        ideasBean.addComment(username, ideaId, comment);
    }

    public void voteIdea(String username, Long ideaId, boolean vote) throws VotingException, DataAccessException {
        ideasBean.voteIdea(username, ideaId, vote);
    }

    public void voteComment(String username, Long commentId, boolean vote) throws VotingException, DataAccessException {
        ideasBean.voteComment(username, commentId, vote);
    }

    private Calendar parseDate(String birthday) {
        String[] ymd = birthday.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);
        Calendar birthdayDate = new GregorianCalendar(year, month, day);
        return birthdayDate;
    }

}
