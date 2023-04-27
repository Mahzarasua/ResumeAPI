package com.izars.resumeapi.skills;

import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.skills.domain.SkillDomain;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DummyTestSkillsData extends DummyGenericTestData {

    public static SkillResponse dummySkillResponseData() {
        SkillResponse tmp = new SkillResponse();
        List<SkillDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummySkillDomainData());
        }
        tmp.setSkillList(list);

        return tmp;
    }

    public static SkillRequest dummySkillRequestData() {
        SkillRequest tmp = new SkillRequest();
        List<SkillDomain> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1; i++) {
            list.add(dummySkillDomainData());
        }
        tmp.setSkillList(list);

        return tmp;
    }

    public static SkillDomain dummySkillDomainData() {
        SkillDomain tmp = new SkillDomain();
        tmp.setId(generateRandomIdString());
        tmp.setResumeId(generateRandomIdString());
        tmp.setName(generateRandomString());
        tmp.setPercentage(Integer.parseInt(generateDummyNumericString(2)));
        tmp.setType(generateRandomString());

        return tmp;
    }
}
