package com.obligatorio.entities;

import com.obligatorio.entities.MyUser;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-25T18:12:25")
@StaticMetamodel(Post.class)
public abstract class Post_ { 

    public static volatile SingularAttribute<Post, MyUser> owner;
    public static volatile SingularAttribute<Post, Integer> upvotes;
    public static volatile SingularAttribute<Post, String> description;
    public static volatile SingularAttribute<Post, Long> id;
    public static volatile SingularAttribute<Post, Calendar> publishedDate;
    public static volatile SingularAttribute<Post, Integer> downvotes;

}