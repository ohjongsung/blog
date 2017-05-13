package io.ohjongsung.blog.author;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.GitHubUserProfile;
import org.springframework.social.github.api.UserOperations;
import org.springframework.web.client.RestOperations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by ohjongsung on 2017-05-13.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInServiceTest {

    @Mock
    private GitHub gitHub;

    @Mock
    private TeamService teamService;

    private SignInService signInService;
    private String username = "ohjongsung";
    private String name = "Full Name";
    private String location = "Korea";
    private String email = "user@example.com";
    private String avatarUrl = "http://gravatar.com/avatar/ABC";

    @Before
    public void setUp() throws Exception {
        signInService = new SignInService(teamService, "ohjongsung-io");
    }

    @Test
    public void createOrUpdateMemberProfileOnLogin() {
        GitHubUserProfile userProfile =
                new GitHubUserProfile(1234L, username, name, location, "", "", email, avatarUrl, null);
        UserOperations userOperations = mock(UserOperations.class);

        given(userOperations.getUserProfile()).willReturn(userProfile);
        given(gitHub.userOperations()).willReturn(userOperations);

        signInService.getOrCreateMemberProfile(1234L, gitHub);

        verify(teamService).createOrUpdateMemberProfile(1234L, username, avatarUrl, name);
    }

    @Test
    public void isTeamMember() {
        mockIsMemberOfTeam(true);

        assertThat(signInService.isTeamMember("ohjongsung", gitHub), is(true));
    }

    @Test
    public void isNotTeamMember() {
        mockIsMemberOfTeam(false);

        assertThat(signInService.isTeamMember("ohjong", gitHub), is(false));
    }

    private void mockIsMemberOfTeam(boolean isMember) {
        RestOperations restOperations = mock(RestOperations.class);
        given(gitHub.restOperations()).willReturn(restOperations);
        BDDMockito.BDDMyOngoingStubbing<ResponseEntity<Void>> expectedResult =
                given(restOperations.getForEntity(anyString(), argThat(new ArgumentMatcher<Class<Void>>() {
                    @Override
                    public boolean matches(Object argument) {
                        return true;
                    }
                }), anyString(), anyString()));

        HttpStatus statusCode = isMember ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        expectedResult.willReturn(new ResponseEntity<>(statusCode));
    }
}