package com.obligatorio.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author juanmartincorallo
 */
@Entity
@Table(name = "MYUSERS")
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @NotNull
    @NotBlank
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    // TODO encriptar password

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
            + "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE")
    private Calendar birthdate;

    @Column(name = "GENDER")
    private char gender;
    
    @Column(name = "COUNTRY")
    private String country;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Post> ideas;
    
    /**
     * Boolean value: true = upvote, false = downvote
     */
    @ElementCollection
    private Map<Post, Boolean> votes;
    
    public MyUser() {
        ideas = new ArrayList<>();
        votes = new HashMap<>();
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
        if (!(object instanceof MyUser)) {
            return false;
        }
        MyUser other = (MyUser) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.obligatorio.entities.User[ id=" + id + " ]";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Calendar birthdate) {
        this.birthdate = birthdate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Post> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Post> ideas) {
        this.ideas = ideas;
    }
    
    public void addIdea(Post idea) {
        this.ideas.add(idea);
    }
    
    public Map<Post, Boolean> getVotes() {
        return votes;
    }

    public void setVotes(Map<Post, Boolean> votes) {
        this.votes = votes;
    }

    /**
     * Adds the pair post-vote without checking if it already exists
     * @param post the post
     * @param vote true if upvote, false if downvote
     */
    public void addVote(Post post, Boolean vote) {
        this.votes.put(post, vote);
    }
    
    /**
     * Removes the vote from post without checking if the item existed
     * @param post post to have vote removed
     */
    public void removeVote(Post post) {
        this.votes.remove(post);
    }
}
