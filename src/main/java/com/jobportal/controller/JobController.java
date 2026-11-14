
package com.jobportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.entity.Job;
import com.jobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job savedJob = jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Job>> searchByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(jobService.searchJobsByTitle(title));
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<Job>> searchByLocation(
            @RequestParam String location) {
        return ResponseEntity.ok(jobService.searchJobsByLocation(location));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiter(
            @PathVariable Long recruiterId) {
        return ResponseEntity.ok(
                jobService.getJobsByRecruiter(recruiterId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
    
@GetMapping("/search")
public ResponseEntity<List<Job>> searchJobs(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String location) {

    return ResponseEntity.ok(
            jobService.searchJobs(keyword, location));
}

}

