package com.example.Semester_Projekt_Wettbuero_Server.Repositories;

import Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername();
}
