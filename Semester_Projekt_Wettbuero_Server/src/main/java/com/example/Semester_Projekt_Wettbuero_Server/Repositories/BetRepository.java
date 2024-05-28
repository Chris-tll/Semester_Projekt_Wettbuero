package com.example.Semester_Projekt_Wettbuero_Server.Repositories;

import Entities.Bet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetRepository extends MongoRepository<Bet, String> {
}
