package com.obligatorio.entities;

import com.obligatorio.entities.Comment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-25T18:12:25")
@StaticMetamodel(Idea.class)
public class Idea_ extends Post_ {

    public static volatile ListAttribute<Idea, Comment> comments;
    public static volatile SingularAttribute<Idea, String> name;
    public static volatile ListAttribute<Idea, String> tags;

}