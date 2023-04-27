package com.izars.resumeapi.education.mapper;

import com.izars.resumeapi.auth.mapper.CustomMapper;
import com.izars.resumeapi.auth.model.School;
import com.izars.resumeapi.auth.utils.StringToUUIDOrikaConverter;
import com.izars.resumeapi.education.domain.EducationDomain;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EducationMapper extends CustomMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new StringToUUIDOrikaConverter());

        factory.classMap(School.class, EducationDomain.class)
                .field("id.id", "id")
                .field("id.resumeId", "resumeId")
                .byDefault().mapNulls(false).register();
        factory.classMap(EducationDomain.class, School.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
    }
}
