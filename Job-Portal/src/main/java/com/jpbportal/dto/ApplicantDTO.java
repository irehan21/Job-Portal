package com.jpbportal.dto;

import com.jpbportal.entity.Applicant;
import com.jpbportal.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDTO {
    private Long applicantId;
    private String email;
    private String name;
    private String resume;
    private String website;
    private Long phone;
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;

    public Applicant toEntity() {
        return new Applicant(this.applicantId, this.email, this.name, this.resume!=null? Base64.getDecoder().decode(this.resume): null, this.website, this.phone, this.coverLetter, this.timeStamp, this.applicationStatus, this.interviewTime);
    }



}
