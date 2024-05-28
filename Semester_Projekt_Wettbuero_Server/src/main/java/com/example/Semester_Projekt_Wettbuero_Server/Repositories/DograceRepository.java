package com.example.Semester_Projekt_Wettbuero_Server.Repositories;

import Entities.Dograce;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DograceRepository extends MongoRepository<Dograce, String> {
}
