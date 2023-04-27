package com.izars.resumeapi.experience.mapper;

import com.izars.resumeapi.auth.mapper.CustomMapper;
import com.izars.resumeapi.auth.model.WorkExperience;
import com.izars.resumeapi.auth.utils.StringToUUIDOrikaConverter;
import com.izars.resumeapi.experience.domain.ExperienceDomain;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ExperienceMapper extends CustomMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new StringToUUIDOrikaConverter());

        factory.classMap(WorkExperience.class, ExperienceDomain.class)
                .field("id.id", "id")
                .field("id.resumeId", "resumeId")
                .byDefault().mapNulls(false).register();
        factory.classMap(ExperienceDomain.class, WorkExperience.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
    }
}
