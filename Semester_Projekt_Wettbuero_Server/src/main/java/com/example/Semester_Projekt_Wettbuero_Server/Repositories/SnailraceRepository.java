package com.example.Semester_Projekt_Wettbuero_Server.Repositories;

import Entities.Horserace;
import Entities.Snail;
import Entities.Snailrace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SnailraceRepository extends MongoRepository<Snailrace, String> {
}
