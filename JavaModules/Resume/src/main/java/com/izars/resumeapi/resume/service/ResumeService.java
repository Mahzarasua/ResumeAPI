package com.izars.resumeapi.resume.service;

import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;

import java.util.List;
import java.util.UUID;

public interface ResumeService {

    List<ResumeResponse> getAllResumes();

    ResumeResponse getResumeById(UUID id);

    ResumeIdResponse saveResume(ResumeRequest request);

    ResumeIdResponse saveResume(ResumeRequest request, UUID id);

    ResumeIdResponse deleteResumeById(UUID id);
}
