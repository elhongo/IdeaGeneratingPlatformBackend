package com.obligatorio.dataaccess;

import com.obligatorio.entities.Comment;
import com.obligatorio.entities.Idea;
import com.obligatorio.entities.MyUser;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
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
public class CommentFacade extends AbstractFacade<Comment> {

    @EJB
    private MyUserFacade myUserFacade;
    
    @EJB
    private IdeaFacade ideaFacade;
    
    @PersistenceContext(unitName = "ObligatorioArquitectura-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }

    public List<Comment> getCommentsFromIdea(Long ideaId) throws DataAccessException {
        try {
            Idea idea = ideaFacade.find(ideaId);
            return idea.getComments();
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error getting comments from idea " + ideaId);
        }
    }

    public void addComment(String username, Long ideaId, String comment) throws DataAccessException {
        try {
            Idea idea = ideaFacade.find(ideaId);
            String queryStrUser = "select u from MyUser u "
                    + "where u.username = :pUsername";
            Object userObj = em.createQuery(queryStrUser)
                    .setParameter("pUsername", username)
                    .getSingleResult();
            MyUser user = (MyUser) userObj;
            Comment newComment = new Comment();
            newComment.setIdea(idea);
            newComment.setDescription(comment);
            newComment.setPublishedDate(Calendar.getInstance());
            newComment.setOwner(user);
            idea.addComment(newComment);
            getEntityManager().merge(idea);
            getEntityManager().merge(newComment);
        } catch (NoResultException nre) {
            throw new DataAccessException(nre, "Idea (" + ideaId + ") and/or user (" + username + ") was not found");
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error adding comment to idea " + ideaId);
        }
    }

    public void voteComment(String username, Long commentId, boolean vote) throws VotingException, DataAccessException {
        try {
            MyUser user = myUserFacade.getUser(username);
            Comment comment = this.find(commentId);
            Boolean previousVote = user.getVotes().get(comment);
            if (previousVote == null) {
                // If the user never voted for this comment, then create a new Vote:
                user.addVote(comment, vote);
            } else {
                // The user had previously voted for this comment.
                // Case 1: trying to upvote twice the comment:
                if (previousVote && vote) {
                    throw new VotingException((null), "Cannot upvote twice the same post");
                } // Case 2: trying to downvote twice the idea:
                else if (!previousVote && !vote) {
                    throw new VotingException((null), "Cannot downvote twice the same post");
                } // Case 3: one is upvote and the other downvote. Vote is removed
                else {
                    user.removeVote(comment);
                }
            }
            myUserFacade.edit(user);
            this.edit(comment);
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error while voting post");
        }
    }
}
