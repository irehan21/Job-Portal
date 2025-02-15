package com.jpbportal.dto;

import com.jpbportal.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String profilePicture;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certification> certificates;

    public Profile toEntity() {
        return new Profile(this.id, this.email, this.jobTitle, this.company, this.location, this.about, this.profilePicture != null ? Base64.getDecoder().decode(this.profilePicture) : null ,
        this.skills, this.experiences, this.certificates);
    }
}
