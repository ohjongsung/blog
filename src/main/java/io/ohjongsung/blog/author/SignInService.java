package io.ohjongsung.blog.author;

import io.ohjongsung.blog.author.entity.MemberProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubUserProfile;

/**
 * Created by ohjongsung on 2017-05-13. GitHub SignInService
 */
public class SignInService {

    //private static final String IS_MEMBER_URL = "https://api.github.com/teams/{team}/members/{user}";
    private static final String IS_MEMBER_URL = "https://api.github.com/orgs/ohjongsung-io/members/{user}";
    private final TeamService teamService;
    private final String gitHubTeamId;

    @Autowired
    public SignInService(TeamService teamService, @Value("${github.team.id}") String gitHubTeamId) {
        this.teamService = teamService;
        this.gitHubTeamId = gitHubTeamId;
    }

    public MemberProfile getOrCreateMemberProfile(Long githubId, GitHub gitHub) {
        GitHubUserProfile remoteProfile = gitHub.userOperations().getUserProfile();

        return teamService.createOrUpdateMemberProfile(githubId, remoteProfile.getUsername(), remoteProfile
                .getProfileImageUrl(), remoteProfile.getName());
    }

    public boolean isTeamMember(String userId, GitHub gitHub) {
        ResponseEntity<Void> response =
                gitHub.restOperations().getForEntity(IS_MEMBER_URL, Void.class, gitHubTeamId, userId);
        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
