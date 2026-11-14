

package com.jobportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobportal.dto.ApplicantDetailsDTO;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.User;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;

@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobApplicationService(
            JobApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Apply for a job
    @Transactional
    public JobApplication applyForJob(Long candidateId, Long jobId) {

        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException("Job not found");
        }

        if (applicationRepository.existsByJobIdAndCandidateId(
                jobId, candidateId)) {
            throw new IllegalArgumentException(
                    "You have already applied for this job");
        }

        JobApplication application =
                new JobApplication(candidateId, jobId);

        return applicationRepository.save(application);
    }

    // Get all applications submitted by a candidate
    public List<JobApplication> getApplicationsByCandidate(
            Long candidateId) {

        return applicationRepository.findByCandidateId(candidateId);
    }

    // Get all applications for a job
    public List<JobApplication> getApplicationsByJob(Long jobId) {

        return applicationRepository.findByJobId(jobId);
    }

    // Get applicants with their names and email addresses
    public List<ApplicantDetailsDTO> getApplicantDetailsByJob(
            Long jobId) {

        List<JobApplication> applications =
                applicationRepository.findByJobId(jobId);

        List<ApplicantDetailsDTO> applicants = new ArrayList<>();

        for (JobApplication application : applications) {

            User candidate = null;

            if (application.getCandidateId() != null) {
                candidate = userRepository
                        .findById(application.getCandidateId())
                        .orElse(null);
            }

            String candidateName = "Unknown candidate";
            String candidateEmail = "Not available";

            if (candidate != null) {

                if (candidate.getName() != null) {
                    candidateName = candidate.getName();
                }

                if (candidate.getEmail() != null) {
                    candidateEmail = candidate.getEmail();
                }
            }

            ApplicantDetailsDTO applicant =
                    new ApplicantDetailsDTO(
                            application.getId(),
                            application.getCandidateId(),
                            candidateName,
                            candidateEmail,
                            application.getJobId(),
                            application.getStatus(),
                            application.getAppliedAt()
                    );

            applicants.add(applicant);
        }

        return applicants;
    }

    // Update application status
    @Transactional
    public JobApplication updateApplicationStatus(
            Long applicationId, String newStatus) {

        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException(
                    "Application status is required");
        }

        String status = newStatus.trim().toUpperCase(Locale.ROOT);

        if (!status.equals("PENDING")
                && !status.equals("SHORTLISTED")
                && !status.equals("REJECTED")) {

            throw new IllegalArgumentException(
                    "Status must be PENDING, SHORTLISTED, or REJECTED");
        }

        JobApplication application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Application not found"));

        application.setStatus(status);

        return applicationRepository.save(application);
    }
}
