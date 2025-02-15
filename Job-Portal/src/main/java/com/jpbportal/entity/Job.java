package com.jpbportal.entity;

import com.jpbportal.dto.ApplicantDTO;
import com.jpbportal.dto.JobDTO;
import com.jpbportal.dto.JopStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
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
    private List<Applicant> applicants;
    private JopStatus jobStatus;
    private Long postedBy;

    public JobDTO toDTO() {
        return new JobDTO(this.id, this.jobTitle, this.company, this.location, this.description, this.skills, this.experience, this.about, this.certificate, this.postTime, this.packageOffered, this.applicants!=null? this.applicants.stream().map((x) -> x.toDTO()).toList():null, this.jobStatus, this.postedBy);
    }



}
