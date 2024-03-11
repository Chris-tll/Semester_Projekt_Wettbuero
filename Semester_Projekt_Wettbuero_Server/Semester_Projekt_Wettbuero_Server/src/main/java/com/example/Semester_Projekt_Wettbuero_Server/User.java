package com.example.Semester_Projekt_Wettbuero_Server;

import com.example.Semester_Projekt_Wettbuero_Server.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User-Data")
@Getter @Setter
public class User {
    @JsonView(Views.Internal.class)
    @Id @NonNull
    private String id;

    private String firstname;

    private String lastname;

    private String username;

    private String gender;

    private int age;

    private Roles role;

    private String phone;

    @Email
    private String email;

    private String password;
}
