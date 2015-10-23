package com.obligatorio.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author juanmartincorallo
 */
@Entity
@Table(name = "COMMENTS")
@PrimaryKeyJoinColumn(name = "COMMENT_ID", referencedColumnName = "POST_ID")

public class Comment extends Post implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_IDEA", nullable = false)
    private Idea idea;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal;
        if (!(object instanceof Comment)) {
            equal = false;
        } else {
            Comment other = (Comment) object;
            equal = !((this.getId() == null && other.getId() != null)
                    || (this.getId() != null && !this.getId().equals(other.getId())));
        }
        return equal;
    }

    @Override
    public String toString() {
        return "com.obligatorio.entities.Comment[ id=" + getId() + " ]";
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }
}
