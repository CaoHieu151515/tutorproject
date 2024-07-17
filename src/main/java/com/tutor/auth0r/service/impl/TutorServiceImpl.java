package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.enumeration.Teach;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.repository.UserRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.TutorService;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfTutorDTO;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.impl.Transactional.TutorCustomService;
import com.tutor.auth0r.service.mapper.CustomTutorMapper;
import com.tutor.auth0r.service.mapper.TutorMapper;
import com.tutor.auth0r.web.rest.errors.NotLoggedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Tutor}.
 */
@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private static final Logger log = LoggerFactory.getLogger(TutorServiceImpl.class);

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    private final CustomTutorMapper customTutorMapper;

    private final AppUserService appUserService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AppUserRepository appUserRepository;

    private final TutorCustomService tutorCustomService;

    public TutorServiceImpl(
        TutorRepository tutorRepository,
        TutorMapper tutorMapper,
        CustomTutorMapper customTutorMapper,
        UserRepository userRepository,
        AppUserRepository appUserRepository,
        TutorCustomService tutorCustomService,
        AppUserService appUserService,
        UserService userService
    ) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
        this.customTutorMapper = customTutorMapper;
        this.userRepository = userRepository;
        this.appUserRepository = appUserRepository;
        this.tutorCustomService = tutorCustomService;
        this.appUserService = appUserService;
        this.userService = userService;
    }

    @Override
    public TutorDTO save(TutorDTO tutorDTO) {
        log.debug("Request to save Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public TutorDTO update(TutorDTO tutorDTO) {
        log.debug("Request to update Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public Optional<TutorDTO> partialUpdate(TutorDTO tutorDTO) {
        log.debug("Request to partially update Tutor : {}", tutorDTO);

        return tutorRepository
            .findById(tutorDTO.getId())
            .map(existingTutor -> {
                tutorMapper.partialUpdate(existingTutor, tutorDTO);

                return existingTutor;
            })
            .map(tutorRepository::save)
            .map(tutorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorDTO> findAll() {
        log.debug("Request to get all Tutors");
        return tutorRepository.findAll().stream().map(tutorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the tutors where AppUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findAllWhereAppUserIsNull() {
        log.debug("Request to get all tutors where AppUser is null");
        return StreamSupport.stream(tutorRepository.findAll().spliterator(), false)
            .filter(tutor -> tutor.getAppUser() == null)
            .map(tutorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorDTO> findOne(Long id) {
        log.debug("Request to get Tutor : {}", id);
        return tutorRepository.findById(id).map(tutorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tutor : {}", id);
        tutorRepository.deleteById(id);
    }

    @Override
    public Tutor save(Tutor tutor) {
        log.debug("Request to save Wallet : {}", tutor);
        tutor = tutorRepository.save(tutor);
        return tutor;
    }

    @Transactional
    @Override
    public Optional<TuTorCusTomDTO> findOneCustom(Long id) {
        return tutorRepository.findById(id).map(customTutorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListOfTutorDTO> getTutorsBySubject(String subject) {
        log.debug("Request to get tutors by subject: {}", subject);

        List<Teach> subjects = getSubjectsBySubjectName(subject);

        Long appUserID = null;
        try {
            appUserID = getCurrentAppUserId();
        } catch (Exception e) {
            log.warn("Failed to get current user. Treating as guest user. Error: {}", e.getMessage());
        }

        List<Tutor> tutors;
        if (appUserID == null) {
            tutors = tutorRepository.findBySubjects(subjects);
        } else {
            tutors = tutorRepository.findBySubjectsAndIdNot(subjects, appUserID);
        }

        return tutors.stream().map(tutorMapper::toListDTO).collect(Collectors.toList());
    }

    private List<Teach> getSubjectsBySubjectName(String subject) {
        switch (subject.toUpperCase()) {
            case "MATH":
                return Arrays.asList(Teach.MATH_10, Teach.MATH_11, Teach.MATH_12);
            case "PHYSIC":
                return Arrays.asList(Teach.PHYSIC_10, Teach.PHYSIC_11, Teach.PHYSIC_12);
            case "CHEMISTRY":
                return Arrays.asList(Teach.CHEMISTRY_10, Teach.CHEMISTRY_11, Teach.CHEMISTRY_12);
            case "ENGLISH":
                return Arrays.asList(Teach.ENGLISH_10, Teach.ENGLISH_11, Teach.ENGLISH_12);
            default:
                throw new IllegalArgumentException("Invalid subject: " + subject);
        }
    }

    private Long getCurrentAppUserId() throws Exception {
        User currentUser = userService.getCurrentUser().orElseThrow(NotLoggedException::new);
        AppUser appUser = appUserRepository.findByUser(currentUser);

        if (appUser != null && appUser.getTutor() != null) {
            return appUser.getTutor().getId();
        }
        return null;
    }

    // public void updateTutorStatusOnline(String login) {
    //     Optional<User> userOptional = userRepository.findOneByLogin(login);
    //     userOptional.ifPresent(user -> {
    //         Optional<AppUser> appUserOptional = appUserRepository.findOneByUser(user);
    //         appUserOptional.ifPresent(appUser -> {
    //             Optional<Tutor> tutorOptional = tutorRepository.findByAppUser(appUser);
    //             tutorOptional.ifPresent(tutor -> {
    //                 tutor.setStatus(TuStatus.READY);
    //                 tutorRepository.save(tutor);
    //             });
    //         });
    //     });
    // }

    // public void updateTutorStatusOffline(String login) {
    //     Optional<User> userOptional = userRepository.findOneByLogin(login);
    //     userOptional.ifPresent(user -> {
    //         Optional<AppUser> appUserOptional = appUserRepository.findOneByUser(user);
    //         appUserOptional.ifPresent(appUser -> {
    //             Optional<Tutor> tutorOptional = tutorRepository.findByAppUser(appUser);
    //             tutorOptional.ifPresent(tutor -> {
    //                 tutor.setStatus(TuStatus.OFFLINE);
    //                 tutorRepository.save(tutor);
    //             });
    //         });
    //     });
    // }

    @Override
    public void updateTutorStatusOnline(String login) {
        Optional<Tutor> tutorOptional = tutorRepository.findTutorByUserLogin(login);
        tutorOptional.ifPresent(tutor -> {
            tutor.setStatus(TuStatus.READY);
            tutorRepository.save(tutor);
        });
    }

    @Override
    public void updateTutorStatusOffline(String login) {
        Optional<Tutor> tutorOptional = tutorRepository.findTutorByUserLogin(login);
        tutorOptional.ifPresent(tutor -> {
            tutor.setStatus(TuStatus.OFFLINE);
            tutorRepository.save(tutor);
        });
    }

    @Override
    public void updateTutorStatusConFirming(Long id) {
        Optional<Tutor> tutorOptional = tutorRepository.findByAppUserId(id);
        tutorOptional.ifPresent(tutor -> {
            tutor.setStatus(TuStatus.CONFIRMING);
            tutorRepository.save(tutor);
        });
    }
}
