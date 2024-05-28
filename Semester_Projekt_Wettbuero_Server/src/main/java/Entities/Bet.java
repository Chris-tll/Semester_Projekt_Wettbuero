package Entities;

import com.example.Semester_Projekt_Wettbuero_Server.Enums.BetStatus;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.ParticipantType;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.RaceType;
import com.example.Semester_Projekt_Wettbuero_Server.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bet-Data")
@Getter @Setter
public class Bet {
    @JsonView(Views.Internal.class)
    @Id
    @NonNull
    private String id;
    private double money_bet;
    private ParticipantType participantType;
    private String participantName;
    private RaceType raceType;
    private String raceId;
    private String userId;
    private BetStatus status;
}
