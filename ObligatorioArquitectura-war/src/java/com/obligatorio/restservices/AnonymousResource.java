package com.obligatorio.restservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.obligatorio.buisinesslogic.BusinessFacadeBean;
import com.obligatorio.buisinesslogic.DateFormatException;
import com.obligatorio.entities.Comment;
import com.obligatorio.entities.Idea;
import com.obligatorio.obligatorioexceptions.ObligatorioException;
import com.obligatorio.sessionbeans.LoginException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service for Anonymous Users
 *
 * @author horaciotorrendell
 */
@Path("anonymous")
public class AnonymousResource {

    @Context
    private UriInfo context;

    @EJB
    private BusinessFacadeBean businessFacadeBean;

    /**
     * Creates a new instance of AnonymousResource
     */
    public AnonymousResource() {
    }

    @GET
    @Produces("application/json")
    @Path("posts")
    public Response getPosts() {
        Response response;
        try {
            List<Idea> ideas = businessFacadeBean.getIdeas();
            Gson gsonResponse = new GsonBuilder().create();
            response = Response.accepted(gsonResponse.toJson(ideas)).build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @GET
    @Produces("application/json")
    @Path("posts/{ideaId}")
    public Response getPosts(@PathParam("ideaId") String ideaIdString) {
        Response response;
        try {
            Long ideaId = Long.parseLong(ideaIdString);
            Idea result = businessFacadeBean.getIdea(ideaId);
            Gson gsonResponse = new GsonBuilder().create();
            response = Response.accepted(gsonResponse.toJson(result)).build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @GET
    @Produces("application/json")
    @Path("posts/search/{tag}")
    public Response getIdeasWithTag(@PathParam("tag") String tag) {
        Response response;
        try {
            List<Idea> result = businessFacadeBean.getIdeasWithTag(tag);
            Gson gsonResponse = new GsonBuilder().create();
            response = Response.accepted(gsonResponse.toJson(result)).build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    @GET
    @Produces("application/json")
    @Path("comments/{postId}")
    public Response getCommentsFromPost(@PathParam("postId") String postId) {
        Response response;
        try {
            Long id = Long.parseLong(postId);
            List<Comment> comments = businessFacadeBean.getCommentsFromIdea(id);
            Gson gsonResponse = new GsonBuilder().create();
            response = Response.accepted(gsonResponse.toJson(comments)).build();
        } catch (NumberFormatException nex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("Id Format incorrect").build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        }
        return response;
    }

    /**
     * The method login, validates the username and password. Returns a token
     * that will be used for user rest services
     *
     * @param username
     * @param password
     * @return UUID token as a json
     */
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    @Path("login")
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password) {
        Response response;
        try {
            UUID token = businessFacadeBean.login(username, password);
            Gson gson = new GsonBuilder().create();
            response = Response.accepted(gson.toJson(token)).build();
        } catch (LoginException lex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(lex.getMessage()).build();
        } catch (ObligatorioException oe) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity(oe.getMessage()).build();
        } 
        return response;
    }

    /**
     * PST method that registers a new user to the system
     *
     * @param username
     * @param birthday
     * @param password
     * @param email(YYYY-MM-DD)
     * @param gender
     * @param country
     * @return Response
     * @throws java.lang.Exception
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("register")
    public Response register(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email,
            @FormParam("birthday") String birthday,
            @FormParam("gender") String gender,
            @FormParam("country") String country) {
        Response response;
        try {
            char genderChar = gender.charAt(0);
            businessFacadeBean.register(username, password, email, birthday,
                    genderChar, country);
            response = Response.accepted("User registered correctly").build();
        } catch (DateFormatException dex) {
            response = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity("DateFormatError: " + dex.getMessage()).build();
        }
        return response;
    }

    /**
     * Get method that runs System tests
     *
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("runtests")
    public String runtests() {
        return "test";
    }

    @POST
    @Path("test2")
    //@Consumes("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String test2(@FormParam("test") String test) {
        return "resultado da: " + test;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("test3")
    public Response test3(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email,
            @FormParam("birthday") String birthday,
            @FormParam("gender") String gender,
            @FormParam("country") String country) throws Exception {
        Gson gson = new GsonBuilder().create();
        List<String> list = new LinkedList<>();
        list.add(username);
        list.add(password);
        list.add(email);
        list.add(birthday);
        list.add(gender);
        list.add(country);
        return Response.accepted(gson.toJson(list)).build();
    }

}
