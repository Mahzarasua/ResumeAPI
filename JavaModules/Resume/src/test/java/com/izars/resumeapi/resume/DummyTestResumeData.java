package com.izars.resumeapi.resume;

import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.resume.domain.ExperienceDomain;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.domain.SchoolDomain;
import com.izars.resumeapi.resume.domain.SkillDomain;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
public class DummyTestResumeData extends DummyGenericTestData {

    private static final UUID resumeId = generateRandomIdString();
    private static final List<SkillDomain> skillDomainList = Collections.singletonList(dummySkillDomainData());
    private static final List<SchoolDomain> schoolDomainList = Collections.singletonList(dummySchoolDomainData());
    private static final List<ExperienceDomain> experienceDomainList = Collections.singletonList(dummyExperienceDomainData());

    public static ResumeRequest dummyResumeRequestData() {
        ResumeRequest tmp = new ResumeRequest();
        tmp.setId(resumeId);
        tmp.setFirstName(generateRandomString());
        tmp.setLastName(generateRandomString());
        tmp.setTitle(generateRandomString());
        tmp.setCity(generateRandomString());
        tmp.setState(generateRandomString());
        tmp.setCountry(generateRandomString());
        tmp.setEmail(generateRandomString());
        tmp.setPhone(generateRandomString());
        tmp.setSummary(generateRandomString());
        tmp.setCreationDate(LocalDate.now());

        tmp.setSkills(skillDomainList);
        tmp.setSchools(schoolDomainList);
        tmp.setWorkExperiences(experienceDomainList);

        return tmp;
    }

    public static ResumeResponse dummyResumeResponseData() {
        ResumeResponse tmp = new ResumeResponse();
        tmp.setId(resumeId);
        tmp.setFirstName(generateRandomString());
        tmp.setLastName(generateRandomString());
        tmp.setTitle(generateRandomString());
        tmp.setCity(generateRandomString());
        tmp.setState(generateRandomString());
        tmp.setCountry(generateRandomString());
        tmp.setEmail(generateRandomString());
        tmp.setPhone(generateRandomString());
        tmp.setSummary(generateRandomString());
        tmp.setCreationDate(LocalDate.now());

        tmp.setSkills(skillDomainList);
        tmp.setSchools(schoolDomainList);
        tmp.setWorkExperiences(experienceDomainList);

        return tmp;
    }

    public static SkillDomain dummySkillDomainData() {
        SkillDomain tmp = new SkillDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(resumeId);
        tmp.setName(generateRandomString());
        tmp.setPercentage(Integer.parseInt(generateDummyNumericString(2)));
        tmp.setType(generateRandomString());

        return tmp;
    }

    public static SchoolDomain dummySchoolDomainData() {
        SchoolDomain tmp = new SchoolDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(resumeId);
        tmp.setName(generateRandomString());
        tmp.setCareer(generateRandomString());
        tmp.setStartDate(LocalDate.now());
        tmp.setEndDate(LocalDate.now());
        tmp.setDegree(generateRandomString());

        return tmp;
    }

    public static ExperienceDomain dummyExperienceDomainData() {
        ExperienceDomain tmp = new ExperienceDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(resumeId);
        tmp.setTitle(generateRandomString());
        tmp.setCompany(generateRandomString());
        tmp.setStartDate(LocalDate.now());
        tmp.setEndDate(LocalDate.now());
        tmp.setCurrentJob(true);
        tmp.setDescription(generateRandomString());

        return tmp;
    }

}
