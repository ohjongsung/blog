package io.ohjongsung.blog.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ohjongsung.blog.author.entity.MemberProfile;
import io.ohjongsung.blog.author.repository.TeamRepository;

/**
 * Created by ohjongsung on 2017-05-13.
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public MemberProfile createOrUpdateMemberProfile(Long githubId, String username, String avatarUrl, String name) {
        MemberProfile profile = teamRepository.findByGithubId(githubId);

        if (profile == null) {
            profile = new MemberProfile();
            profile.setGithubId(githubId);
            profile.setUsername(username);
            profile.setHidden(true);
        }
        profile.setAvatarUrl(avatarUrl);
        profile.setName(name);
        profile.setGithubUsername(username);
        return teamRepository.save(profile);
    }
}
