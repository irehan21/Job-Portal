package com.jpbportal.repository;

import com.jpbportal.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile,Long> {
}
