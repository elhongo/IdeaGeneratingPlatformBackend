package com.obligatorio.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author juanmartincorallo
 */
@Entity
@Table(name = "IDEAS")
@PrimaryKeyJoinColumn(name = "IDEA_ID", referencedColumnName = "POST_ID")
public class Idea extends Post implements Serializable {

    @OneToMany(mappedBy = "idea", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ElementCollection
    private List<String> tags;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal;
        if (!(object instanceof Idea)) {
            equal = false;
        } else {
            Idea other = (Idea) object;
            equal = !((this.getId() == null && other.getId() != null)
                    || (this.getId() != null && !this.getId().equals(other.getId())));
        }
        return equal;
    }

    @Override
    public String toString() {
        return "com.obligatorio.entities.Idea[ id=" + getId() + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
