package com.obligatorio.sessionbeans;

import com.obligatorio.dataaccess.CommentFacade;
import com.obligatorio.dataaccess.DataAccessException;
import com.obligatorio.dataaccess.IdeaFacade;
import com.obligatorio.dataaccess.MyUserFacade;
import com.obligatorio.dataaccess.VotingException;
import com.obligatorio.entities.Comment;
import com.obligatorio.entities.Idea;
import com.obligatorio.entities.MyUser;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author horaciotorrendell
 */
@Stateless
@LocalBean
public class IdeasBean {

    @EJB
    private MyUserFacade myUserFacade;
    @EJB
    private IdeaFacade ideaFacade;
    @EJB
    private CommentFacade commentFacade;
    
    public List<Idea> getIdeas() throws DataAccessException {
        return ideaFacade.findAll();
    }

    public Idea getIdea(Long ideaId) throws DataAccessException {
        return ideaFacade.find(ideaId);
    }

    public List<Idea> getIdeasWithTag(String tag) throws DataAccessException {
        return ideaFacade.getIdeasWithTag(tag);
    }

    public List<Comment> getCommentsFromIdea(Long ideaId) throws DataAccessException {
        return commentFacade.getCommentsFromIdea(ideaId);
    }

    public void createIdea(String username, String name, String description) throws DataAccessException {
        MyUser user = myUserFacade.getUser(username);
        Idea idea = new Idea();
        idea.setName(name);
        idea.setDescription(description);
        idea.setPublishedDate(Calendar.getInstance());
        idea.setOwner(user);
        user.addIdea(idea);
        ideaFacade.create(idea);
        myUserFacade.edit(user);
    }

    public void addComment(String username, Long ideaId, String comment) throws DataAccessException {
        commentFacade.addComment(username, ideaId, comment);
    }
    
    public void voteIdea(String username, Long ideaId, boolean vote) throws VotingException, DataAccessException {
        ideaFacade.voteIdea(username, ideaId, vote);
    }
    
    public void voteComment(String username, Long commentId, boolean vote) throws VotingException, DataAccessException {
        commentFacade.voteComment(username, commentId, vote);
    }
}
