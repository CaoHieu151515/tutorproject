package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Follow;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.FollowRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.FollowService;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.dto.FollowCustomDTO;
import com.tutor.auth0r.service.dto.FollowDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.mapper.AppUserMapper;
import com.tutor.auth0r.service.mapper.FollowCustomMapper;
import com.tutor.auth0r.service.mapper.FollowMapper;
import com.tutor.auth0r.service.mapper.TutorMapper;
import com.tutor.auth0r.web.rest.errors.NotLoggedException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Follow}.
 */
@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    private final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    private final UserService userService;

    private final TutorRepository tutorRepository;

    private final AppUserRepository appUserRepository;

    private final TutorMapper tutorMapper;

    private final AppUserMapper appUserMapper;

    private final FollowCustomMapper followCustomMapper;

    public FollowServiceImpl(
        FollowRepository followRepository,
        FollowMapper followMapper,
        UserService userService,
        TutorRepository tutorRepository,
        AppUserRepository appUserRepository,
        TutorMapper tutorMapper,
        AppUserMapper appUserMapper,
        FollowCustomMapper followCustomMapper
    ) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
        this.userService = userService;
        this.tutorRepository = tutorRepository;
        this.appUserRepository = appUserRepository;
        this.tutorMapper = tutorMapper;
        this.appUserMapper = appUserMapper;
        this.followCustomMapper = followCustomMapper;
    }

    @Override
    public FollowDTO save(FollowDTO followDTO) {
        log.debug("Request to save Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    @Override
    public FollowDTO update(FollowDTO followDTO) {
        log.debug("Request to update Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    @Override
    public Optional<FollowDTO> partialUpdate(FollowDTO followDTO) {
        log.debug("Request to partially update Follow : {}", followDTO);

        return followRepository
            .findById(followDTO.getId())
            .map(existingFollow -> {
                followMapper.partialUpdate(existingFollow, followDTO);

                return existingFollow;
            })
            .map(followRepository::save)
            .map(followMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowDTO> findAll() {
        log.debug("Request to get all Follows");
        return followRepository.findAll().stream().map(followMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FollowDTO> findOne(Long id) {
        log.debug("Request to get Follow : {}", id);
        return followRepository.findById(id).map(followMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Follow : {}", id);
        followRepository.deleteById(id);
    }

    @Override
    public void FollowAndUnFollow(Long id) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        AppUser appUser = appUserRepository.findByUser(user);
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        Optional<Follow> existingFollow = followRepository.findByFollowerAppUserAndFollowedTutor(appUser, tutor);

        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
        } else {
            Follow follow = new Follow();
            follow.setFollowerAppUser(appUser);
            follow.setFollowedTutor(tutor);
            follow.setCreateDate(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
            followRepository.save(follow);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowCustomDTO> viewListOfFollowedTutors() {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        AppUser appUser = appUserRepository.findByUser(user);
        List<Follow> follows = followRepository.findByFollowerAppUser(appUser);
        return follows.stream().map(Follow::getFollowedTutor).map(followCustomMapper::toFollowCustomDTO).collect(Collectors.toList());
    }
}
