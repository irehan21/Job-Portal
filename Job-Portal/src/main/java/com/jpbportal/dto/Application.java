package com.jpbportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    private Long id;
    private String applicantId;
    private LocalDateTime interviewTime;
    private ApplicationStatus applicationStatus;
}
