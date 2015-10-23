package com.obligatorio.dataaccess;

import com.obligatorio.entities.Idea;
import com.obligatorio.entities.MyUser;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author juanmartincorallo
 */
@Stateless
public class IdeaFacade extends AbstractFacade<Idea> {

    @EJB
    private MyUserFacade myUserFacade;

    @PersistenceContext(unitName = "ObligatorioArquitectura-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdeaFacade() {
        super(Idea.class);
    }

    public List<Idea> getIdeasWithTag(String tag) throws DataAccessException {
        try {
            //String queryStr = "select ideatags from Idea i "
            //        + "inner join i.tags ideatags "
            //        + "where ideatags.tags = :pTag";
            String queryStr = "select i "
                    + "from Idea i "
                    + "where :pTag IN (i.tags)";
            Object res = em.createQuery(queryStr)
                    .setParameter("pTag", tag)
                    .getResultList();
            return (List<Idea>) res;
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error getting ideas with tag");
        }
    }

    /**
     * Returns ideas published between these two dates
     *
     * @param start - starting date (has to be earlier than end date)
     * @param end - end date (has to be after than start date)
     * @return the ideas (or empty list)
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public List<Idea> getIdeasPublishedBetween(Calendar start, Calendar end) throws DataAccessException {
        List<Idea> ret = new ArrayList<>();
        if (!start.after(end)) {
            try {
                String queryStr = "select i from Idea i "
                        + "where i.publishedDate > start and "
                        + "i.publishedDate < end";
                Object res = em.createQuery(queryStr)
                        .getResultList();
                ret = (List<Idea>) res;
            } catch (PersistenceException | EJBException pe) {
                throw new DataAccessException(pe, "Error getting ideas between dates");
            }
        }
        return ret;
    }

    /**
     * User username votes the idea with id = ideaId.
     *
     * @param username
     * @param ideaId
     * @param vote
     * @throws VotingException if trying to upvote or downvote twice the same
     * post
     * @throws com.obligatorio.dataaccess.DataAccessException
     */
    public void voteIdea(String username, Long ideaId, boolean vote) throws VotingException, DataAccessException {
        try {
            MyUser user = myUserFacade.getUser(username);
            Idea idea = this.find(ideaId);
            Boolean previousVote = user.getVotes().get(idea);
            if (previousVote == null) {
                // If the user never voted for this idea, then create a new Vote:
                user.addVote(idea, vote);
                if (vote) {
                    idea.upvote();
                } else {
                    idea.downvote();
                }
            } else {
                // The user had previously voted for this idea.
                // Case 1: trying to upvote twice the idea:
                if (previousVote && vote) {
                    throw new VotingException((null), "Cannot upvote twice the same post");
                } // Case 2: trying to downvote twice the idea:
                else if (!previousVote && !vote) {
                    throw new VotingException((null), "Cannot downvote twice the same post");
                } // Case 3: one is upvote and the other downvote. Vote is removed
                else {
                    user.removeVote(idea);
                    if (vote) {
                        idea.setUpvotes(idea.getUpvotes() - 1);
                    } else {
                        idea.setDownvotes(idea.getDownvotes() - 1);
                    }
                }
            }
            myUserFacade.edit(user);
            this.edit(idea);
        } catch (PersistenceException | EJBException pe) {
            throw new DataAccessException(pe, "Error while voting post");
        }
    }
}
