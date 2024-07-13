package com.tutor.auth0r.service.impl.Transactional;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.repository.FollowRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.mapper.CustomTutorMapper;
import com.tutor.auth0r.service.mapper.TutorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TutorCustomService {

    private final TutorMapper tutorMapper;
    private final FollowRepository followRepository;
    private final UserService userService;
    private final AppUserService appUserService;
    private final CustomTutorMapper customTutorMapper;

    @Autowired
    public TutorCustomService(
        TutorMapper tutorMapper,
        FollowRepository followRepository,
        UserService userService,
        AppUserService appUserService,
        CustomTutorMapper customTutorMapper
    ) {
        this.tutorMapper = tutorMapper;
        this.followRepository = followRepository;
        this.userService = userService;
        this.appUserService = appUserService;
        this.customTutorMapper = customTutorMapper;
    }

    public TuTorCusTomDTO toCustomDto(Tutor tutor) {
        TuTorCusTomDTO dto = customTutorMapper.toDto(tutor);
        Long currentUserId = appUserService.getBycurrentAppUser().getId();
        boolean isFollowing = followRepository.existsByFollowerAndTutor(currentUserId, tutor.getId());
        dto.setFollow(isFollowing);
        return dto;
    }
}
