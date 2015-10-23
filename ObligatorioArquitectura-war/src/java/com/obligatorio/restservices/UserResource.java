package com.obligatorio.restservices;

import com.obligatorio.buisinesslogic.BusinessFacadeBean;
import com.obligatorio.obligatorioexceptions.ObligatorioException;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * REST Web Service for Logged in Users
 *
 * @author horaciotorrendell
 */
@Path("user")
public class UserResource {

    @Context
    private UriInfo context;

    @EJB
    private BusinessFacadeBean buisenessFacadeBean;

    public UserResource() {
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("postIdea")
    public Response postIdea(@FormParam("token") String token, @FormParam("username") String username,
            @FormParam("name") String name, @FormParam("description") String description) {
        Response response;
        try {
            UUID tokenId = UUID.fromString(token);
            if (!buisenessFacadeBean.authenticateUser(tokenId, username)) {
                response = Response.status(Response.Status.PRECONDITION_FAILED)
                        .entity("User not logged in").build();
            } else {
                buisenessFacadeBean.postIdea(username, name, description);
                response = Response.accepted("Idea posted correctly").build();
            }
        } catch (IllegalArgumentException iex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Token has an incorrect format").build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("postComment")
    public Response postComment(@FormParam("token") String token, @FormParam("username") String username,
            @FormParam("ideaId") String ideaIdString, @FormParam("comment") String comment) {
        Response response;
        try {
            UUID tokenId = UUID.fromString(token);
            Long ideaId = Long.parseLong(ideaIdString);
            if (!buisenessFacadeBean.authenticateUser(tokenId, username)) {
                response = Response.status(Response.Status.PRECONDITION_FAILED)
                        .entity("User not logged in").build();
            } else {
                buisenessFacadeBean.postComment(username, ideaId, comment);
                response = Response.accepted("Comment added correctly").build();
            }
        } catch (IllegalArgumentException iex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Token has an incorrect format").build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("voteIdea")
    public Response rateIdea(@FormParam("token") String token, @FormParam("username") String username,
            @FormParam("ideaId") String ideaIdString, @FormParam("vote") String voteString) {
        Response response;
        try {
            UUID tokenId = UUID.fromString(token);
            Long ideaId = Long.parseLong(ideaIdString);
            boolean vote = Boolean.getBoolean(voteString);
            if (!buisenessFacadeBean.authenticateUser(tokenId, username)) {
                response = Response.status(Response.Status.PRECONDITION_FAILED)
                        .entity("User not logged in").build();
            } else {
                buisenessFacadeBean.voteIdea(username, ideaId, vote);
                response = Response.accepted("Idea voted correctly").build();
            }
        } catch (IllegalArgumentException iex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Token has an incorrect format").build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("voteComment")
    public Response voteComment(@FormParam("token") String token, @FormParam("username") String username,
            @FormParam("commentId") String commentIdString, @FormParam("vote") String voteString) {
        Response response;
        try {
            UUID tokenId = UUID.fromString(token);
            Long commentId = Long.parseLong(commentIdString);
            boolean vote = Boolean.getBoolean(voteString);
            if (!buisenessFacadeBean.authenticateUser(tokenId, username)) {
                response = Response.status(Response.Status.PRECONDITION_FAILED)
                        .entity("User not logged in").build();
            } else {
                buisenessFacadeBean.voteComment(username, commentId, vote);
                response = Response.accepted("Comment voted correctly").build();
            }
        } catch (IllegalArgumentException iex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Token has an incorrect format").build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        } 
        return response;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("logout")
    public Response logout(@FormParam("token") String token, @FormParam("username") String username) {
        Response response;
        try {
            UUID tokenId = UUID.fromString(token);
            if (!buisenessFacadeBean.authenticateUser(tokenId, username)) {
                response = Response.status(Response.Status.PRECONDITION_FAILED)
                        .entity("User not logged in").build();
            } else {
                if (buisenessFacadeBean.logout(tokenId, username)) {
                    response = Response.status(Response.Status.OK)
                            .entity("User logged out correctly").build();
                } else {
                    response = Response.status(Response.Status.CONFLICT)
                            .entity("Error: User could not logout").build();
                }
            }
        } catch (IllegalArgumentException iex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Token has an incorrect format").build();
        }
        return response;
    }

    /**
     *
     * Get method that tests if the Rest service is working correctly
     */
    @GET
    @Produces("application/json")
    @Path("test1")
    public String test1() {
        return "Test UserServices";
    }
}
