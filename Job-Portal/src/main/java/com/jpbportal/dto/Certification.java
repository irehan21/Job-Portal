package com.jpbportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certification {
    private String title;
    private String issuer;
    private LocalDateTime issuedDate;
    private String certificateId;

}
