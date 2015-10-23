package com.obligatorio.entities;

import com.obligatorio.entities.Post;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-25T18:12:25")
@StaticMetamodel(MyUser.class)
public class MyUser_ { 

    public static volatile SingularAttribute<MyUser, String> country;
    public static volatile SingularAttribute<MyUser, String> password;
    public static volatile SingularAttribute<MyUser, Calendar> birthdate;
    public static volatile SingularAttribute<MyUser, Character> gender;
    public static volatile ListAttribute<MyUser, Post> ideas;
    public static volatile MapAttribute<MyUser, Post, Boolean> votes;
    public static volatile SingularAttribute<MyUser, Long> id;
    public static volatile SingularAttribute<MyUser, String> email;
    public static volatile SingularAttribute<MyUser, String> username;

}