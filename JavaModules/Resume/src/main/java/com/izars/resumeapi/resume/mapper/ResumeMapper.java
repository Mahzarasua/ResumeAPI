package com.izars.resumeapi.resume.mapper;

import com.izars.resumeapi.auth.mapper.CustomMapper;
import com.izars.resumeapi.auth.model.Resume;
import com.izars.resumeapi.auth.model.School;
import com.izars.resumeapi.auth.model.Skill;
import com.izars.resumeapi.auth.model.WorkExperience;
import com.izars.resumeapi.auth.utils.StringToUUIDOrikaConverter;
import com.izars.resumeapi.resume.domain.ExperienceDomain;
import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.domain.SchoolDomain;
import com.izars.resumeapi.resume.domain.SkillDomain;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ResumeMapper extends CustomMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new StringToUUIDOrikaConverter());

//        factory.classMap(User.class, UserResponse.class)
//                .byDefault().mapNulls(false).register();
//        factory.classMap(User.class, MyUserDetails.class)
//                .byDefault().mapNulls(false).register();

        factory.classMap(Resume.class, ResumeResponse.class)
                .byDefault().mapNulls(false).register();
        factory.classMap(Resume.class, ResumeIdResponse.class)
                .byDefault().mapNulls(false).register();
        factory.classMap(ResumeRequest.class, Resume.class)
                .byDefault().mapNulls(false).register();
        factory.classMap(SchoolDomain.class, School.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
        factory.classMap(ExperienceDomain.class, WorkExperience.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
        factory.classMap(SkillDomain.class, Skill.class)
                .field("id", "id.id")
                .field("resumeId", "id.resumeId")
                .byDefault().mapNulls(false).register();
    }
}
