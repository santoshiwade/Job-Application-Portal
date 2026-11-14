
package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByRecruiterId(Long recruiterId);

    @Query("""
        SELECT j FROM Job j
        WHERE (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        AND (
            :location IS NULL OR :location = ''
            OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))
        )
        """)
    List<Job> searchJobs(
            @Param("keyword") String keyword,
            @Param("location") String location
    );
}
