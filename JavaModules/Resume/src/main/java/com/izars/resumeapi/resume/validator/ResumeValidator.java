package com.izars.resumeapi.resume.validator;

import com.izars.resumeapi.auth.exception.CustomBadRequestException;
import com.izars.resumeapi.auth.exception.ExceptionBody.ErrorDetails;
import com.izars.resumeapi.auth.validator.CustomValidator;
import com.izars.resumeapi.resume.domain.ExperienceDomain;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.SchoolDomain;
import com.izars.resumeapi.resume.domain.SkillDomain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.izars.resumeapi.auth.validator.CustomValidationUtils.ERROR_MESSAGE;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.INVALID_FORMAT;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.REQ_FIELD;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.isValidEmail;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.isValidPhoneNumber;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.isValidString;

@Service
public class ResumeValidator implements CustomValidator<ResumeRequest> {

    @Override
    public void validate(ResumeRequest request) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        validateRequiredField(request, errorDetails);

        if (!errorDetails.isEmpty())
            throw new CustomBadRequestException(errorDetails, ERROR_MESSAGE);
    }

    private void validateRequiredField(ResumeRequest request, List<ErrorDetails> errorDetails) {
        if (!isValidString(request.getFirstName())) errorDetails.add(newErrorDetail(REQ_FIELD, "First name"));
        if (!isValidString(request.getLastName())) errorDetails.add(newErrorDetail(REQ_FIELD, "Second name"));
        if (!isValidString(request.getEmail())) errorDetails.add(newErrorDetail(REQ_FIELD, "Email"));
        if (!isValidString(request.getPhone())) errorDetails.add(newErrorDetail(REQ_FIELD, "Phone number"));
        if (!isValidString(request.getCity())) errorDetails.add(newErrorDetail(REQ_FIELD, "City"));
        if (!isValidString(request.getCountry())) errorDetails.add(newErrorDetail(REQ_FIELD, "Country"));
        if (!isValidEmail(request.getEmail())) errorDetails.add(newErrorDetail(INVALID_FORMAT, "Email"));
        if (!isValidPhoneNumber(request.getPhone()))
            errorDetails.add(newErrorDetail(INVALID_FORMAT, "Phone number"));

        validateSkills(request, errorDetails);
        validateWorkExperience(request, errorDetails);
        validateEducation(request, errorDetails);
    }

    private void validateSkills(ResumeRequest resumeRequest, List<ErrorDetails> errorDetails) {
        // If no resumeRequest were added, there are no validations to do here.
        if (resumeRequest.getSkills() != null && !resumeRequest.getSkills().isEmpty()) {
            for (SkillDomain s : resumeRequest.getSkills()) {
                if (!isValidString(s.getName())) errorDetails.add(newErrorDetail(REQ_FIELD, "Skill name"));
                if (s.getPercentage() <= 0 || s.getPercentage() > 100)
                    errorDetails.add(newErrorDetail(INVALID_FORMAT, "Percentage for skill " + s.getName()));
            }
        }
    }

    private void validateWorkExperience(ResumeRequest resumeRequest, List<ErrorDetails> errorDetails) {
        // If no work experiences were added, there are no validations to do here.
        if (resumeRequest.getWorkExperiences() != null && !resumeRequest.getWorkExperiences().isEmpty()) {
            for (ExperienceDomain we : resumeRequest.getWorkExperiences()) {
                if (!isValidString(we.getTitle())) errorDetails.add(newErrorDetail(REQ_FIELD, "Work title"));
                if (!isValidString(we.getCompany()))
                    errorDetails.add(newErrorDetail(REQ_FIELD, "Company name for work " + we.getTitle()));
                if (we.getStartDate() == null)
                    errorDetails.add(newErrorDetail(REQ_FIELD, "Beginning date for work " + we.getTitle() + " at " + we.getCompany()));
                if (we.getCurrentJob().equals(false)) {
                    if (we.getEndDate() == null)
                        errorDetails.add(newErrorDetail(REQ_FIELD, "Ending date for work " + we.getTitle() + " at " + we.getCompany()));
                        // Verify the ending date is greater than the beginning date.
                    else if (!we.getEndDate().isAfter(we.getStartDate()))
                        errorDetails.add(newErrorDetail(REQ_FIELD, "Ending date for work " + we.getTitle() + " must be greater than beginning date"));
                }
            }
        }
    }

    private void validateEducation(ResumeRequest resumeRequest, List<ErrorDetails> errorDetails) {
        // If no education is added, there are no validations to do here.
        if (resumeRequest.getSchools() != null && !resumeRequest.getSchools().isEmpty()) {
            for (SchoolDomain e : resumeRequest.getSchools()) {
                if (!isValidString(e.getName())) errorDetails.add(newErrorDetail(REQ_FIELD, "School name"));
                if (!isValidString(e.getCareer()))
                    errorDetails.add(newErrorDetail(REQ_FIELD, "Career name at " + e.getName()));
                if (!isValidString(e.getDegree()))
                    errorDetails.add(newErrorDetail(REQ_FIELD, "Degree of your career at " + e.getName()));
                if (e.getDegree() != null && !e.getDegree().equals("Bachelor") && !e.getDegree().equals("Master") && !e.getDegree().equals("Ph"))
                    errorDetails.add(newErrorDetail(INVALID_FORMAT, "Degree of you career at " + e.getName() + " should be either 'Bachelor', 'Master', or 'Ph'"));
                if (e.getStartDate() == null)
                    errorDetails.add(newErrorDetail(REQ_FIELD, "Beginning date of your career at " + e.getName()));
                if (e.getEndDate() == null)
                    errorDetails.add(newErrorDetail(REQ_FIELD, "(Actual or expected) Ending date for your " + "career at " + e.getName()));
                if (e.getStartDate() != null && e.getEndDate() != null)
                    if (!e.getEndDate().isAfter(e.getStartDate()))
                        errorDetails.add(newErrorDetail(INVALID_FORMAT, "(Actual or expected) Ending date for your " + "career at " + e.getName() + " must be greater than beginning date"));
            }
        }
    }

    private ErrorDetails newErrorDetail(String errorMessage, String fieldName) {
        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(errorMessage);
        error.setFieldName(fieldName);
        return error;
    }
}
