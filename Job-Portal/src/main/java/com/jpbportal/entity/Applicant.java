package com.jpbportal.entity;

import com.jpbportal.dto.ApplicantDTO;
import com.jpbportal.dto.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {

    private Long applicantId;
    private String email;
    private String name;
    private byte[] resume;
    private String website;
    private Long phone;
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;

    public ApplicantDTO toDTO() {
        return new ApplicantDTO (this.applicantId, this.email, this.name, this.resume!=null? Base64.getEncoder().encodeToString(this.resume): null, this.website, this.phone, this.coverLetter, this.timeStamp, this.applicationStatus, this.interviewTime);
    }

}
