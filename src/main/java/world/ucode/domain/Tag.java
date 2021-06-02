package world.ucode.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tagName;
    @ManyToMany(mappedBy = "followedTag")
    private List<Profile> followers = new ArrayList<>();

    public Tag() {}

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void addProfile(Profile profile) {
        followers.add(profile);
    }

    public List<Profile> getFollowers() {
        return followers;
    }
}
