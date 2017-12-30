package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class PasswordEntry extends AbstractPersistable<Long> {

    private String owner;
    private String username;
    private String password;
    private String comments;


    public PasswordEntry() {
        super();
    }

    public PasswordEntry(String owner,String username, String password, String comments) {
        this();
        this.comments = comments;
        this.owner = owner;
        this.password = password;
        this.username=username;
    }

    public String getComments() {
        return comments;
    }

    public String getOwner() {
        return owner;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
