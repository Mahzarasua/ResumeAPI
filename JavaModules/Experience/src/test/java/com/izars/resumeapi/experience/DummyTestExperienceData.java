package com.izars.resumeapi.experience;

import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.experience.domain.ExperienceDomain;
import com.izars.resumeapi.experience.domain.ExperienceRequest;
import com.izars.resumeapi.experience.domain.ExperienceResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DummyTestExperienceData extends DummyGenericTestData {

    public static ExperienceResponse dummyExperienceResponseData() {
        ExperienceResponse tmp = new ExperienceResponse();
        List<ExperienceDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummyExperienceDomainData());
        }
        tmp.setExperienceList(list);

        return tmp;
    }

    public static ExperienceRequest dummyExperienceRequestData() {
        ExperienceRequest tmp = new ExperienceRequest();
        List<ExperienceDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummyExperienceDomainData());
        }
        tmp.setExperienceList(list);

        return tmp;
    }

    public static ExperienceDomain dummyExperienceDomainData() {
        ExperienceDomain tmp = new ExperienceDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(generateRandomIdString());
        tmp.setTitle(generateRandomString());
        tmp.setCompany(generateRandomString());
        tmp.setCurrentJob(true);
        tmp.setDescription(generateRandomString());
        tmp.setStartDate(LocalDate.now());

        return tmp;
    }
}
