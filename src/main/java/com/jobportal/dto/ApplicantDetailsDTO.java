
package com.jobportal.dto;

import java.time.LocalDateTime;

public class ApplicantDetailsDTO {

    private Long id;
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private Long jobId;
    private String status;
    private LocalDateTime appliedAt;

    public ApplicantDetailsDTO() {
    }

    public ApplicantDetailsDTO(
            Long id,
            Long candidateId,
            String candidateName,
            String candidateEmail,
            Long jobId,
            String status,
            LocalDateTime appliedAt) {

        this.id = id;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.jobId = jobId;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
