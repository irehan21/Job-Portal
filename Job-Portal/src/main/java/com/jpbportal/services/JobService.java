package com.jpbportal.services;

import com.jpbportal.dto.ApplicantDTO;
import com.jpbportal.dto.Application;
import com.jpbportal.dto.JobDTO;
import com.jpbportal.exception.JobPortalException;
import jakarta.validation.Valid;

import java.util.List;

public interface JobService {
    public JobDTO postJOb(@Valid JobDTO jobDTO) throws JobPortalException;

    public List<JobDTO> getAllJobs();


   public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException;

    public JobDTO getJob(Long id) throws JobPortalException;

    public List<JobDTO> getJobsPostedBy(Long id) ;

    public void chageAppStatus(Application application) throws JobPortalException;
}
