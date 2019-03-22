package model;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private long id;
    private String content;
    private Timestamp date;
    private User user;
    private Discovery discovery;

    public Comment() {
    }

    public Comment (Comment comment){
        this.id = comment.id;
        this.content = comment.content;
        this.date = new Timestamp(comment.date.getTime());
        this.user = new User(comment.user);
        this.discovery = new Discovery(comment.discovery);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", discovery=" + discovery +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                content.equals(comment.content) &&
                date.equals(comment.date) &&
                user.equals(comment.user) &&
                discovery.equals(comment.discovery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, date, user, discovery);
    }
}
