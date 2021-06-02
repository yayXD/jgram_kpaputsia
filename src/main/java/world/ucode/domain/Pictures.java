package world.ucode.domain;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    private String pictureName;

    public Pictures() {}

    public Pictures(Profile profile, String pictureName) {
        this.profile = profile;
        this.pictureName = pictureName;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
