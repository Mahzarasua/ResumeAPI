package com.izars.resumeapi.configuration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.domain.jwt.JwtRequest;
import com.izars.resumeapi.auth.domain.jwt.JwtResponse;
import com.izars.resumeapi.auth.domain.user.UserResponse;
import com.izars.resumeapi.auth.model.Resume;
import com.izars.resumeapi.auth.model.Role;
import com.izars.resumeapi.auth.model.School;
import com.izars.resumeapi.auth.model.School.SchoolId;
import com.izars.resumeapi.auth.model.Skill;
import com.izars.resumeapi.auth.model.Skill.SkillId;
import com.izars.resumeapi.auth.model.User;
import com.izars.resumeapi.auth.model.Userrole;
import com.izars.resumeapi.auth.model.Userrole.UserroleId;
import com.izars.resumeapi.auth.model.WorkExperience;
import com.izars.resumeapi.auth.model.WorkExperience.WorkExperienceId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.auth.utils.SpringUtils.OBJECT_MAPPER;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class DummyGenericTestData {
    private static final int count = 8;

    public static String generateDummyAlphabeticString(int count) {
        return RandomStringUtils.randomAlphabetic(count).toUpperCase();
    }

    public static String generateDummyAlphanumericString(int count) {
        return RandomStringUtils.randomAlphanumeric(count).toUpperCase();
    }

    public static String generateDummyNumericString(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public static UUID generateRandomIdString() {
//        return UUID.fromString(generateDummyAlphanumericString(count));
        return UUID.randomUUID();
    }

    public static String generateRandomString() {
        return generateDummyAlphabeticString(count);
    }

    public static School dummySchoolData() {
        School tmp = new School();
        SchoolId id = new SchoolId();
        id.setId(generateRandomIdString());
        id.setResumeId(generateRandomIdString());

        tmp.setId(id);
        tmp.setName(generateRandomString());
        tmp.setCareer(generateRandomString());
        tmp.setDegree(generateRandomString());
        tmp.setStartDate(LocalDate.now());
        tmp.setEndDate(LocalDate.now());
        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(dummyResumeData());
        tmp.setResume(new Resume());

        log.info("Education generated: {}", tmp);

        return tmp;
    }

    public static Skill dummySkillData() {
        Skill tmp = new Skill();
        Skill.SkillId id = new SkillId();
        id.setId(generateRandomIdString());
        id.setResumeId(generateRandomIdString());

        tmp.setId(id);
        tmp.setName(generateRandomString());
        tmp.setPercentage(Integer.parseInt(generateDummyNumericString(2)));
        tmp.setType(generateRandomString());
        tmp.setResume(new Resume());

        log.info("Skill generated: {}", tmp);

        return tmp;
    }

    public static WorkExperience dummyWorkExpData() {
        WorkExperience tmp = new WorkExperience();
        WorkExperience.WorkExperienceId id = new WorkExperienceId();
        id.setId(generateRandomIdString());
        id.setResumeId(generateRandomIdString());

        tmp.setId(id);
        tmp.setTitle(generateRandomString());
        tmp.setCompany(generateRandomString());
        tmp.setCurrentJob(true);
        tmp.setDescription(generateRandomString());
        tmp.setStartDate(LocalDate.now());
        tmp.setEndDate(LocalDate.now());
        tmp.setResume(new Resume());

        log.info("WorkExp generated: {}", tmp);

        return tmp;
    }

    public static Resume dummyResumeData() {
        Resume tmp = new Resume();
        tmp.setId(generateRandomIdString());
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
        tmp.setSchools(new ArrayList<>());
        tmp.setWorkExperiences(new ArrayList<>());
        tmp.setSkills(new ArrayList<>());

        return tmp;
    }

    public static Role dummyRoleData() {
        Role tmp = new Role();
        tmp.setId(generateRandomIdString());
        tmp.setRole(generateRandomString());

        return tmp;
    }

    public static User dummyUserData() {
        User tmp = new User();
        tmp.setId(generateRandomIdString());
        tmp.setUsername(generateRandomString());
        tmp.setPassword(generateRandomString());
        tmp.setActive(false);
        tmp.setRoles(singletonList(dummyRoleData()));

        return tmp;
    }

    public static UserroleId dummyUserroleIdData() {
        UserroleId tmp = new UserroleId();
        tmp.setUserId(generateRandomIdString());
        tmp.setRoleId(generateRandomIdString());

        tmp = new UserroleId(generateRandomIdString(), generateRandomIdString());

        return tmp;
    }

    public static Userrole dummyUserroleData() {
        Userrole tmp = new Userrole();
        tmp.setId(dummyUserroleIdData());

        return tmp;
    }

    public static void assertAndLogResponse(Object response) throws JsonProcessingException {
        log.info("Response: {}", OBJECT_MAPPER.writeValueAsString(response));
        assertNotNull(response);
    }

    public static GenericDeleteResponse dummyGenericDeleteData() {
        GenericDeleteResponse tmp = new GenericDeleteResponse();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(generateRandomIdString());

        return tmp;
    }

    public static JwtRequest dummyJwtRequestData() {
        JwtRequest tmp = new JwtRequest();
        tmp.setUsername(generateRandomString());
        tmp.setPassword(generateRandomString());

        tmp = new JwtRequest(generateRandomString(), generateRandomString());

        return tmp;
    }

    public static JwtResponse dummyJwtResponseData() {
        JwtResponse tmp = new JwtResponse();
        tmp.setJwtToken(generateRandomString());

        return tmp;
    }

    public static UserResponse dummyUserResponseData() {
        UserResponse tmp = new UserResponse();
        tmp.setId(generateRandomString());
        tmp.setUsername(generateRandomString());
        tmp.setPassword(generateRandomString());
        tmp.setActive(false);

        UserResponse.Role roles = new UserResponse.Role();
        roles.setRole(generateRandomString());

        roles = new UserResponse.Role(generateRandomString());

        tmp.setRoles(Collections.singletonList(roles));

        return tmp;
    }
}
