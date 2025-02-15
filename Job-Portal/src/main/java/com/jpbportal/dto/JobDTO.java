package com.jpbportal.dto;

import com.jpbportal.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private Long id;
    private String jobTitle;
    private String company;
    private String location;
    private String description;
    private List<String> skills;
    private String experience;
    private String about;
    private String certificate;
    private LocalDateTime postTime;
    private Long packageOffered;
    private List<ApplicantDTO> applicants;
    private JopStatus jobStatus;
    private Long postedBy;

    public Job toEntity() {
        return new Job(this.id, this.jobTitle, this.company, this.location, this.description, this.skills, this.experience, this.about, this.certificate, this.postTime, this.packageOffered, this.applicants!=null ? this.applicants.stream().map((x)-> x.toEntity().toDTO().toEntity()).toList():null, this.jobStatus, this.postedBy );
    }
}
