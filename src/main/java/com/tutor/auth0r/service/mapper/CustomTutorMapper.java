package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.enumeration.HireStatus;
import com.tutor.auth0r.service.dto.RatingCustomDTO;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
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

    @Mapping(source = "appUser.user.firstName", target = "firstName")
    @Mapping(source = "appUser.user.lastName", target = "lastName")
    @Mapping(source = "appUser.user.imageUrl", target = "img")
    @Mapping(target = "totalHoursHired", source = "hireTutors", qualifiedByName = "totalHoursHired")
    @Mapping(target = "percentSuccess", expression = "java(calculatePercentSuccess(tutor))")
    @Mapping(target = "cusrating", source = "ratings", qualifiedByName = "ratingUnestTuTor")
    TuTorCusTomDTO toDto(Tutor tutor);

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
}
