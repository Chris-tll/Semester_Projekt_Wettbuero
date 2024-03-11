package com.example.Semester_Projekt_Wettbuero_Server.Repositories;

import com.example.Semester_Projekt_Wettbuero_Server.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
