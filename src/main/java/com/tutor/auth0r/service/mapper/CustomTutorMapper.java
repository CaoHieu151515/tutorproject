package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.domain.enumeration.HireStatus;
import com.tutor.auth0r.service.dto.RatingCustomDTO;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { RatingMapper.class })
public interface CustomTutorMapper {
    CustomTutorMapper INSTANCE = Mappers.getMapper(CustomTutorMapper.class);
    TutorMapper tutorIns = Mappers.getMapper(TutorMapper.class);
    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);
    CustomRatingMapper CusrateIns = Mappers.getMapper(CustomRatingMapper.class);
    TutorDetailsMapper tuDetailINS = Mappers.getMapper(TutorDetailsMapper.class);
    RatingMapper ratingINS = Mappers.getMapper(RatingMapper.class);
    TutorTeachMapper teachINS = Mappers.getMapper(TutorTeachMapper.class);
    TuTorContactWithMapper contactINS = Mappers.getMapper(TuTorContactWithMapper.class);

    @Mapping(source = "id", target = "tutorID")
    @Mapping(target = "totalHoursHired", source = "hireTutors", qualifiedByName = "totalHoursHired")
    @Mapping(target = "percentSuccess", expression = "java(calculatePercentSuccess(tutor))")
    @Mapping(source = "appUser.user.firstName", target = "firstName")
    @Mapping(source = "appUser.user.lastName", target = "lastName")
    @Mapping(source = "appUser.user.email", target = "email")
    @Mapping(source = "appUser.user.imageUrl", target = "img")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "averageRating", target = "averageRate")
    @Mapping(source = "tutorDetails.tutorTeaches", target = "teach", qualifiedByName = "TuTormapToTeach")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "tutorDetails.information", target = "information")
    @Mapping(source = "tutorDetails.tutorContacts", target = "contact", qualifiedByName = "TuTormapToContact")
    @Mapping(source = "tutorDetails.tutorVideo.media.url", target = "videoUrl")
    @Mapping(target = "cusrating", source = "ratings", qualifiedByName = "ratingUnestTuTor")
    TuTorCusTomDTO toDto(Tutor tutor);

    // @Named("tutorDetailsId")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    // TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);

    @Named("tutorDetailsId")
    static TutorDetailsDTO mediamapper(TutorDetails tutorDetails) {
        if (tutorDetails == null) {
            return null;
        }
        return TutorDetailsMapper.INSTANCE.toDto(tutorDetails);
    }

    default double calculatePercentSuccess(Tutor tutor) {
        int totalHires = tutor.getHireTutors().size();
        if (totalHires == 0) {
            return 0.0;
        }
        long successfulHires = tutor.getHireTutors().stream().filter(hireTutor -> hireTutor.getStatus() == HireStatus.DONE).count();
        return ((double) successfulHires / totalHires) * 100;
    }

    @Named("totalHoursHired")
    default int calculateTotalHoursHired(Set<HireTutor> hireTutors) {
        return hireTutors.stream().mapToInt(HireTutor::getTimeHire).sum();
    }

    @Named("ratingUnestTuTor")
    default Set<RatingCustomDTO> ratingUnestTuTor(Set<Rating> ratings) {
        return ratings.stream().map(CustomRatingMapper.INSTANCE::toRatingCustomDTO).collect(Collectors.toSet());
    }

    @Named("TuTormapToTeach")
    default Set<TutorTeachDTO> TuTormapToTeach(Set<TutorTeach> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(TutorTeachMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }

    @Named("TuTormapToContact")
    default Set<TuTorContactWithDTO> TuTormapToContact(Set<TuTorContactWith> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(TuTorContactWithMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }
}
