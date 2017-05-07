package io.ohjongsung.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ohjongsung.blog.support.Link;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Type;

import org.springframework.util.StringUtils;

/**
 * Created by ohjongsung on 2017-05-06. 작성자 정보
 */
public class AuthorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String jobTitle;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    @Type(type = "text")
    private String bio;

    @Column(nullable = true)
    private String githubUsername;

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private Long githubId;

    @Column
    private boolean hidden;

    public AuthorProfile() {
    }

    /** For unit testing purposes */
    AuthorProfile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getFullName() {
        return name == null ? getUsername() : name;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public Long getGithubId() {
        return githubId;
    }

    public void setGithubId(Long githubId) {
        this.githubId = githubId;
    }

    @JsonIgnore
    public Link getGithubLink() {
        if (StringUtils.isEmpty(getGithubUsername())) {
            return null;
        }
        String pathAndHost = String.format("github.com/%s", getGithubUsername());
        return new Link("https://" + pathAndHost, pathAndHost);
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorProfile that = (AuthorProfile) o;

        if (hidden != that.hidden) {
            return false;
        }
        if (bio != null ? !bio.equals(that.bio) : that.bio != null) {
            return false;
        }
        if (githubId != null ? !githubId.equals(that.githubId) : that.githubId != null) {
            return false;
        }
        if (githubUsername != null ? !githubUsername.equals(that.githubUsername) : that.githubUsername != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (jobTitle != null ? !jobTitle.equals(that.jobTitle) : that.jobTitle != null) {
            return false;
        }
        if (location != null ? !location.equals(that.location) : that.location != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (!username.equals(that.username)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (githubUsername != null ? githubUsername.hashCode() : 0);
        result = 31 * result + username.hashCode();
        result = 31 * result + (githubId != null ? githubId.hashCode() : 0);
        result = 31 * result + (hidden ? 1 : 0);
        return result;
    }
}
