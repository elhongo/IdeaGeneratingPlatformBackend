package com.obligatorio.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

/**
 *
 * @author juanmartincorallo
 */
@Entity
@Table(name = "POSTS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OWNER", nullable = false)
    private MyUser owner;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "UPVOTES", nullable = false)
    private int upvotes;

    @Column(name = "DOWNVOTES", nullable = false)
    private int downvotes;

    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLISHED_DATE", nullable = false)
    private Calendar publishedDate;

    public Post() {
        upvotes = 0;
        downvotes = 0;
        description = "";
        publishedDate = Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal;
        if (!(object instanceof Post)) {
            equal = false;
        } else {
            Post other = (Post) object;
            equal = !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
        }
        return equal;
    }

    @Override
    public String toString() {
        return "com.obligatorio.entities.Post[ id=" + id + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void upvote() {
        this.upvotes++;
    }

    public void downvote() {
        this.downvotes++;
    }

    public Calendar getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Calendar publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int calculateScore() {
        int score = upvotes - downvotes;
        long diff = Calendar.getInstance().getTimeInMillis() - publishedDate.getTimeInMillis();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        score = (int) (score - days);
        return score;
    }

}
