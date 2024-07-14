package com.tutor.auth0r.service.impl;

import static com.tutor.auth0r.security.SecurityUtils.AUTHORITIES_KEY;

import com.tutor.auth0r.domain.AcademicRank;
import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Authority;
import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.AuthorityRepository;
import com.tutor.auth0r.repository.IdentityCardRepository;
import com.tutor.auth0r.repository.MediaRepository;
import com.tutor.auth0r.repository.TuTorContactWithRepository;
import com.tutor.auth0r.repository.TutorTeachRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.AllRecommendDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfConfirmingDTO;
import com.tutor.auth0r.service.dto.CustomDTO.RankwithImageDTO;
import com.tutor.auth0r.service.dto.CustomDTO.TutorEditProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UpdatecertificateDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UserProfileDTO;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import com.tutor.auth0r.service.mapper.AcademicRankMapper;
import com.tutor.auth0r.service.mapper.AllRecommendMapper;
import com.tutor.auth0r.service.mapper.AppUserMapper;
import com.tutor.auth0r.service.mapper.TuTorContactWithMapper;
import com.tutor.auth0r.service.mapper.TutorTeachMapper;
import com.tutor.auth0r.web.rest.errors.InvalidInputException;
import com.tutor.auth0r.web.rest.errors.NotLoggedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.AppUser}.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private static final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;
    private final UserService userService;

    private final IdentityCardRepository identityCardRepository;

    private final MediaRepository mediaRepository;

    private final AllRecommendMapper allRecommendMapper;

    private final AcademicRankMapper academicRankMapper;

    private final AuthorityRepository authorityRepository;

    private final TutorTeachRepository tutorTeachRepository;

    private final TuTorContactWithRepository tuTorContactWithRepository;

    public AppUserServiceImpl(
        AppUserRepository appUserRepository,
        AppUserMapper appUserMapper,
        UserService userService,
        IdentityCardRepository identityCardRepository,
        MediaRepository mediaRepository,
        AllRecommendMapper allRecommendMapper,
        AcademicRankMapper academicRankMapper,
        AuthorityRepository authorityRepository,
        TutorTeachRepository tutorTeachRepository,
        TuTorContactWithRepository tuTorContactWithRepository
    ) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.userService = userService;
        this.identityCardRepository = identityCardRepository;
        this.mediaRepository = mediaRepository;
        this.allRecommendMapper = allRecommendMapper;
        this.academicRankMapper = academicRankMapper;
        this.authorityRepository = authorityRepository;
        this.tutorTeachRepository = tutorTeachRepository;
        this.tuTorContactWithRepository = tuTorContactWithRepository;
    }

    @Override
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    @Override
    public AppUserDTO update(AppUserDTO appUserDTO) {
        log.debug("Request to update AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    @Override
    public Optional<AppUserDTO> partialUpdate(AppUserDTO appUserDTO) {
        log.debug("Request to partially update AppUser : {}", appUserDTO);

        return appUserRepository
            .findById(appUserDTO.getId())
            .map(existingAppUser -> {
                appUserMapper.partialUpdate(existingAppUser, appUserDTO);

                return existingAppUser;
            })
            .map(appUserRepository::save)
            .map(appUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUserDTO> findAll() {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll().stream().map(appUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the appUsers where Wallet is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppUserDTO> findAllWhereWalletIsNull() {
        log.debug("Request to get all appUsers where Wallet is null");
        return StreamSupport.stream(appUserRepository.findAll().spliterator(), false)
            .filter(appUser -> appUser.getWallet() == null)
            .map(appUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findById(id).map(appUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }

    @Override
    public List<AllRecommendDTO> AllAppUsersWithRecommend() {
        log.debug("Request to get all AppUsers");
        return appUserRepository
            .findAllAppUsersWithTutorStatusReady()
            .stream()
            .map(allRecommendMapper::appUserToAllRecommendDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<AppUserDTO> getBycurrentUser() {
        return userService
            .getCurrentUser()
            .flatMap(user -> {
                Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUser(user));
                return appUserOptional.map(appUserMapper::currenttoDTO);
            });
    }

    @Override
    public AppUser getBycurrentAppUser() {
        User currentUser = userService.getCurrentUser().orElseThrow(NotLoggedException::new);
        return appUserRepository.findByUser(currentUser);
    }

    @Override
    public AppUserDTO AdminConFirmTutor(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("AppUser not found"));
        appUser.setBeTutor(true);
        appUser.getTutor().setStatus(TuStatus.OFFLINE);

        Authority newRole = authorityRepository.findById("ROLE_TUTOR").orElseThrow(() -> new RuntimeException("Role not found"));
        appUser.getUser().getAuthorities().add(newRole);
        appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    @Override
    public AppUserDTO AdminRejectTutor(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("AppUser not found"));
        appUser.setBeTutor(false);
        appUser.getTutor().setStatus(TuStatus.NOT_TUTOR);
        appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    @Override
    public AppUserDTO updateVerify(AppUserDTO appUserDTO) {
        if (appUserDTO == null) {
            throw new InvalidInputException("Input data is null");
        }

        AppUser appUserData = appUserRepository.findById(appUserDTO.getId()).orElseThrow(() -> new RuntimeException("AppUser not found"));
        log.debug("Request to update AppUser : {}", appUserDTO);

        updateUserVerify(appUserData, appUserDTO.getUserVerify());

        appUserRepository.save(appUserData);
        return appUserMapper.toDto(appUserData);
    }

    private void updateUserVerify(AppUser appUser, UserVerifyDTO userVerifyDTO) {
        if (userVerifyDTO == null) return;

        UserVerify userVerify = Optional.ofNullable(appUser.getUserVerify()).orElseGet(() -> {
            UserVerify newUserVerify = new UserVerify();
            appUser.setUserVerify(newUserVerify);
            return newUserVerify;
        });

        userVerify.setRating(userVerifyDTO.getRating());
        userVerify.setSchool(userVerifyDTO.getSchool());
        userVerify.setStudentID(userVerifyDTO.getStudentID());
        userVerify.setMajor(userVerifyDTO.getMajor());
        userVerify.setGraduationYear(userVerifyDTO.getGraduationYear());

        if (userVerifyDTO.getIdentityCard() != null) {
            updateIdentityCard(userVerify, userVerifyDTO.getIdentityCard());
        }
    }

    private void updateIdentityCard(UserVerify userVerify, IdentityCardDTO identityCardDTO) {
        IdentityCard identityCard = Optional.ofNullable(userVerify.getIdentityCard()).orElseGet(() -> {
            IdentityCard newIdentityCard = new IdentityCard();
            userVerify.setIdentityCard(newIdentityCard);
            return newIdentityCard;
        });

        identityCardRepository.save(identityCard);

        Set<Media> existingMedia = identityCard.getMedia();

        Set<Long> updatedMediaIds = identityCardDTO.getMedia().stream().map(MediaDTO::getId).collect(Collectors.toSet());

        existingMedia.removeIf(media -> !updatedMediaIds.contains(media.getId()));

        identityCardDTO
            .getMedia()
            .forEach(mediaDTO -> {
                Media media = existingMedia.stream().filter(m -> m.getId().equals(mediaDTO.getId())).findFirst().orElse(new Media());
                media.setUrl(mediaDTO.getUrl());
                media.setIdentityCard(identityCard);
                existingMedia.add(mediaRepository.save(media));
            });

        identityCard.setMedia(existingMedia);

        identityCardRepository.save(identityCard);
    }

    public List<ListOfConfirmingDTO> GetAllConfirming() {
        log.debug("Request to get all AppUsers with Tutors in CONFIRMING status");
        return appUserRepository
            .findByTutorStatus(TuStatus.CONFIRMING)
            .stream()
            .map(appUserMapper::toListOfConfirmingDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<UpdatecertificateDTO> findOneWithAllCetitycate() {
        return userService
            .getCurrentUser()
            .flatMap(user -> {
                Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUser(user));
                return appUserOptional.map(appUserMapper::toDetailsOfConfirmingDTO);
            });
    }

    @Override
    public Optional<UpdatecertificateDTO> updateCertificate(UpdatecertificateDTO updateCertificateDTO) {
        if (updateCertificateDTO == null) {
            throw new InvalidInputException("Input data is null");
        }

        return userService
            .getCurrentUser()
            .flatMap(user -> {
                AppUser appUser = appUserRepository.findByUser(user);
                if (appUser == null) {
                    return Optional.empty();
                }

                updateCertificates(appUser, updateCertificateDTO);

                appUserRepository.save(appUser);

                return Optional.of(appUserMapper.toDetailsOfConfirmingDTO(appUser));
            });
    }

    private void updateCertificates(AppUser appUser, UpdatecertificateDTO updateCertificateDTO) {
        appUser.getUserVerify().setSchool(updateCertificateDTO.getSchool());
        appUser.getUserVerify().setStudentID(updateCertificateDTO.getStudentID());
        appUser.getUserVerify().setMajor(updateCertificateDTO.getMajor());
        appUser.getUserVerify().setGraduationYear(updateCertificateDTO.getYear());

        Set<RankwithImageDTO> existingRanks = academicRankMapper.toRankwithImageDTOs(appUser.getUserVerify().getAcademicRanks());

        Set<String> updatedRankUrls = updateCertificateDTO
            .getRankwithImage()
            .stream()
            .map(RankwithImageDTO::getUrl)
            .collect(Collectors.toSet());

        existingRanks.removeIf(rank -> !updatedRankUrls.contains(rank.getUrl()));

        updateCertificateDTO
            .getRankwithImage()
            .forEach(rankDTO -> {
                RankwithImageDTO rank = existingRanks
                    .stream()
                    .filter(r -> r.getUrl().equals(rankDTO.getUrl()))
                    .findFirst()
                    .orElse(new RankwithImageDTO());
                rank.setRank(rankDTO.getRank());
                rank.setUrl(rankDTO.getUrl());

                Media media = new Media();
                media.setUrl(rankDTO.getUrl());
                mediaRepository.save(media);

                AcademicRank academicRank = academicRankMapper.toAcademicRank(rank);
                academicRank.setMedia(media);
                academicRank.setUserVerify(appUser.getUserVerify());
                existingRanks.add(rank);
            });

        Set<AcademicRank> updatedAcademicRanks = academicRankMapper.toAcademicRanks(existingRanks);
        updatedAcademicRanks.forEach(academicRank -> academicRank.setUserVerify(appUser.getUserVerify()));

        appUser.getUserVerify().getAcademicRanks().clear();
        appUser.getUserVerify().getAcademicRanks().addAll(updatedAcademicRanks);
    }

    @Override
    public Optional<UserProfileDTO> findUserProfile() {
        return userService
            .getCurrentUser()
            .flatMap(user -> {
                Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUser(user));
                return appUserOptional.map(appUserMapper::toUserProfileDTO);
            });
    }

    @Override
    public Optional<UserProfileDTO> updateUserProfile(UserProfileDTO userProfileDTO) {
        if (userProfileDTO == null) {
            throw new InvalidInputException("Input data is null");
        }
        return userService
            .getCurrentUser()
            .flatMap(user -> {
                Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUser(user));
                if (appUserOptional.isPresent()) {
                    AppUser appUser = appUserOptional.get();
                    appUserMapper.updateAppUserFromDto(userProfileDTO, appUser);
                    appUserRepository.save(appUser);
                    return Optional.of(appUserMapper.toUserProfileDTO(appUser));
                }
                return Optional.empty();
            });
    }

    @Override
    public TutorEditProfileDTO findTutorProfile() {
        User currentUser = userService.getCurrentUser().orElseThrow(NotLoggedException::new);
        AppUser appUser = appUserRepository.findByUser(currentUser);
        return AppUserMapper.INSTANCE.toTutorEditProfileDTO(appUser);
    }

    @Transactional
    @Override
    public TutorEditProfileDTO updateTutorProfile(TutorEditProfileDTO dto) {
        if (dto == null) {
            throw new InvalidInputException("Input data is null");
        }
        User currentUser = userService.getCurrentUser().orElseThrow(NotLoggedException::new);
        AppUser appUser = appUserRepository.findByUser(currentUser);

        // Cập nhật các trường đơn giản
        appUserMapper.updateAppUserFromDto(dto, appUser);

        // Cập nhật thủ công các trường contacts và teachs
        if (dto.getContacts() != null) {
            updateContacts(appUser, dto.getContacts());
        }

        if (dto.getTeachs() != null) {
            updateTeachs(appUser, dto.getTeachs());
        }

        appUser = appUserRepository.save(appUser);
        return appUserMapper.toTutorEditProfileDTO(appUser);
    }

    private void updateContacts(AppUser appUser, Set<TuTorContactWithDTO> contactDTOs) {
        TutorDetails tutorDetails = appUser.getTutor().getTutorDetails();
        Set<TuTorContactWith> currentContacts = tutorDetails.getTutorContacts();
        Set<TuTorContactWith> updatedContacts = contactDTOs
            .stream()
            .map(dto -> {
                TuTorContactWith contact = new TuTorContactWith();
                contact.setUrlContact(dto.getUrlContact());
                contact.setType(dto.getType());
                contact.setTutorDetails(tutorDetails);
                return contact;
            })
            .collect(Collectors.toSet());

        currentContacts.clear();
        currentContacts.addAll(updatedContacts);
        // updatedContacts.forEach(tuTorContactWithRepository::save);
        tutorDetails.setTutorContacts(currentContacts);
        appUser.getTutor().setTutorDetails(tutorDetails);
    }

    private void updateTeachs(AppUser appUser, Set<TutorTeachDTO> teachDTOs) {
        TutorDetails tutorDetails = appUser.getTutor().getTutorDetails();
        Set<TutorTeach> currentTeachs = tutorDetails.getTutorTeaches();
        Set<TutorTeach> updatedTeachs = teachDTOs
            .stream()
            .map(dto -> {
                TutorTeach teach = new TutorTeach();
                teach.setSubject(dto.getSubject());
                teach.setTutorDetails(tutorDetails);
                return teach;
            })
            .collect(Collectors.toSet());

        currentTeachs.clear();
        currentTeachs.addAll(updatedTeachs);
        // updatedTeachs.forEach(tutorTeachRepository::save);
        tutorDetails.setTutorTeaches(currentTeachs);
        appUser.getTutor().setTutorDetails(tutorDetails);
    }
}
