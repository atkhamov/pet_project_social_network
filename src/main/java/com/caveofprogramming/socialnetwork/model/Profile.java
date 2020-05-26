package com.caveofprogramming.socialnetwork.model;

import org.owasp.html.PolicyFactory;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = SiteUser.class)
    @JoinColumn(name = "user_id", nullable = false)
    private SiteUser user;

    @Column(name = "about", length = 5000)
    @Size(max = 5000, message = "{editprofile.about.size}")
    private String about;

    @Column(name = "photo_directory", length = 10)
    private String photoDirectory;

    @Column(name = "photo_name", length = 10)
    private String photoName;

    @Column(name = "photo_extension", length = 5)
    private String photoExtension;

    @ManyToMany
    @JoinTable(name = "profile_interests",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "interest_id")})
    @OrderColumn(name = "display_order")
    private Set<Interest> interests;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteUser getUser() {
        return user;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    //The following method helps to provide safety
    //It just makes the program not to post privacy information
    //on profile.jsp file
    /**
     * Create a profile that is suitable for displaying via JSP*/
    public void safeCopyFrom(Profile other){
        if(other.about != null){
            this.about = other.about;
        }
        if(other.interests != null){
            this.interests = other.interests;
        }
    }

    /**
     * Create a profile that is suitable for saving*/
    public void safeMergeFrom(Profile webProfile, PolicyFactory htmlPolicy) {
        if(webProfile.about != null){
            this.about = htmlPolicy.sanitize(webProfile.about);
        }
    }


    public String getPhotoDirectory() {
        return photoDirectory;
    }

    public void setPhotoDirectory(String photoDirectory) {
        this.photoDirectory = photoDirectory;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoExtension() {
        return photoExtension;
    }

    public void setPhotoExtension(String photoExtension) {
        this.photoExtension = photoExtension;
    }

    public void setPhotoDetails(FileInfo info){
        photoDirectory = info.getSubDirectory();
        photoExtension = info.getExtension();
        photoName = info.getBasename();
    }

    public Path getPhoto(String baseDirectory){
        if(photoName == null){
            return null;
        }

        return Paths.get(baseDirectory, photoDirectory, photoName + "." + photoExtension);
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
}
