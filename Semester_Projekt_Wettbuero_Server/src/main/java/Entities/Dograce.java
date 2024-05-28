package Entities;

import com.example.Semester_Projekt_Wettbuero_Server.Enums.RaceStatus;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.RaceType;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.Terrain;
import com.example.Semester_Projekt_Wettbuero_Server.Enums.Weather;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Dograce-Data")
@Getter @Setter
public class Dograce {
    @Id
    @NonNull
    private String id;

    private String name;

    private String location;

    private int num_of_participants;

    private List<Dog> participants;

    private int length;

    private Weather weather;

    private Terrain terrain;

    private LocalDateTime start;

    private LocalDateTime end;

    private int estimatedDuration;

    private RaceStatus status;

    private RaceType type;

    private Dog winner;
}
