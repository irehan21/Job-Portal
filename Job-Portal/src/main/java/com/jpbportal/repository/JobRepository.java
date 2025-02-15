package com.jpbportal.repository;

import com.jpbportal.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job,Long> {
    public List<Job> findByPostedBy(Long postedBy);
}
