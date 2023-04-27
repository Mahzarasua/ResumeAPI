package com.izars.resumeapi.education;

import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.education.domain.EducationDomain;
import com.izars.resumeapi.education.domain.EducationRequest;
import com.izars.resumeapi.education.domain.EducationResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DummyTestEducationData extends DummyGenericTestData {

    public static EducationResponse dummyEducationResponseData() {
        EducationResponse tmp = new EducationResponse();
        List<EducationDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummyEducationDomainData());
        }
        tmp.setEducationList(list);

        return tmp;
    }

    public static EducationRequest dummyEducationRequestData() {
        EducationRequest tmp = new EducationRequest();
        List<EducationDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummyEducationDomainData());
        }
        tmp.setEducationList(list);

        return tmp;
    }

    public static EducationDomain dummyEducationDomainData() {
        EducationDomain tmp = new EducationDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(generateRandomIdString());
        tmp.setName(generateRandomString());
        tmp.setCareer(generateRandomString());
        tmp.setDegree(generateRandomString());
        tmp.setStartDate(LocalDate.now());

        return tmp;
    }
}
