package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { TutorVideoMapper.class, TutorTeachMapper.class })
public interface TutorDetailsMapper extends EntityMapper<TutorDetailsDTO, TutorDetails> {
    TutorDetailsMapper INSTANCE = Mappers.getMapper(TutorDetailsMapper.class);
    TutorTeachMapper teachINS = Mappers.getMapper(TutorTeachMapper.class);

    @Mappings(
        {
            @Mapping(target = "tutorVideo", source = "tutorVideo", qualifiedByName = "videomapper"),
            @Mapping(target = "tutorTeach", source = "tutorTeaches", qualifiedByName = "tutorTeachidd"),
        }
    )
    TutorDetailsDTO toDto(TutorDetails s);

    @Named("videomapper")
    default TutorVideoDTO videomapper(TutorVideo tutorVideo) {
        if (tutorVideo == null) {
            return null;
        }
        return TutorVideoMapper.INSTANCE.toDto(tutorVideo);
    }

    @Named("tutorTeachidd")
    default Set<TutorTeachDTO> tutorTeachidd(Set<TutorTeach> tutorTeaches) {
        if (tutorTeaches == null) {
            return null;
        }
        return tutorTeaches.stream().map(TutorTeachMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }
}
