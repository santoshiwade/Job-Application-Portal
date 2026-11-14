
package com.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobportal.entity.Job;
import com.jobportal.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Job not found with ID: " + id)
                );
    }

    public List<Job> searchJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> searchJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with ID: " + id);
        }

        jobRepository.deleteById(id);
    }
    
public List<Job> searchJobs(String keyword, String location) {
    return jobRepository.searchJobs(keyword, location);
}

}

