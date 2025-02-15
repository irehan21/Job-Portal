package com.jpbportal.services;

import com.jpbportal.dto.ApplicantDTO;
import com.jpbportal.dto.Application;
import com.jpbportal.dto.ApplicationStatus;
import com.jpbportal.dto.JobDTO;
import com.jpbportal.entity.Applicant;
import com.jpbportal.entity.Job;
import com.jpbportal.exception.JobPortalException;
import com.jpbportal.repository.JobRepository;
import com.jpbportal.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("jobService")
public class JobServiceImpl implements JobService{
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Utilities utilities;

    @Override
    public JobDTO postJOb(JobDTO jobDTO) throws JobPortalException {
        jobDTO.setId(utilities.getNextSequence(("jobs")));
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream().map((x) -> x.toDTO()).toList();
    }

    @Override
    public JobDTO getJob(Long id) throws JobPortalException {
        return jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")).toDTO();
    }

    @Override
    public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));

        List<Applicant> applicants = new ArrayList<>(Optional.ofNullable(job.getApplicants()).orElse(new ArrayList<>()));

        // 🔥 Fix: Swap equals() & add null check
        boolean alreadyApplied = applicants.stream()
                .anyMatch(x -> x.getApplicantId() != null && x.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new JobPortalException("Applied Already");
        }

        applicants.add(applicantDTO.toEntity());
        job.setApplicants(applicants);
        jobRepository.save(job);
    }

    @Override
    public List<JobDTO> getJobsPostedBy(Long id) {
        return jobRepository.findByPostedBy(id).stream().map((x) -> x.toDTO()).toList();
    }

    @Override
    public void chageAppStatus(Application application) throws JobPortalException {
        Job job = jobRepository.findById(application.getId())
                .orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));

        List<Applicant> applicants = job.getApplicants().stream().map((x) -> {
            if (x.getApplicantId().equals(application.getApplicantId())) {
                x.setApplicationStatus(application.getApplicationStatus());
                if (application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING))x.setInterviewTime(application.getInterviewTime());

            }
            return x;
        }).toList();
        job.setApplicants(applicants);
        jobRepository.save(job);
    }

}





