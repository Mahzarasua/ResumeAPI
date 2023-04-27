package com.izars.resumeapi.skills.mapper;

import com.izars.resumeapi.auth.mapper.CustomMapper;
import com.izars.resumeapi.auth.model.Skill;
import com.izars.resumeapi.auth.utils.StringToUUIDOrikaConverter;
import com.izars.resumeapi.skills.domain.SkillDomain;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SkillMapper extends CustomMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new StringToUUIDOrikaConverter());

        factory.classMap(Skill.class, SkillDomain.class)
                .field("id.id", "id")
                .field("id.resumeId", "resumeId")
                .byDefault().mapNulls(false).register();
        factory.classMap(SkillDomain.class, Skill.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
    }
}
