


package com.jobportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ApplicantDetailsDTO;
import com.jobportal.entity.JobApplication;
import com.jobportal.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<JobApplication> applyForJob(
            @RequestParam Long candidateId,
            @RequestParam Long jobId) {

        JobApplication application =
                applicationService.applyForJob(candidateId, jobId);

        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<JobApplication>> getCandidateApplications(
            @PathVariable Long candidateId) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByCandidate(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getJobApplications(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(jobId));
    }

    // New endpoint: applicant details for the recruiter
    @GetMapping("/job/{jobId}/details")
    public ResponseEntity<List<ApplicantDetailsDTO>> getApplicantDetails(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                applicationService.getApplicantDetailsByJob(jobId));
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<JobApplication> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        JobApplication updatedApplication =
                applicationService.updateApplicationStatus(applicationId, status);

        return ResponseEntity.ok(updatedApplication);
    }
}
