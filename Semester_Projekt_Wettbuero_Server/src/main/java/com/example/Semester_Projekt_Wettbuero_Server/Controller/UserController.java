package com.example.Semester_Projekt_Wettbuero_Server.Controller;

import Entities.Bet;
import com.example.Semester_Projekt_Wettbuero_Server.Services.UserService;
import Entities.User;
import com.example.Semester_Projekt_Wettbuero_Server.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //MAPPING GET
    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUser(); }

    @GetMapping("/topUsers")
    public List<User> getTopUser() { return userService.getTopUser(); }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable String id) { return userService.getUserById(id); }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) { return userService.getUserByUsername(username); }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) { return userService.getUserByEmail(email); }

    @GetMapping("/password/{email}/{password}")
    public boolean comparePassword(@PathVariable String email, @PathVariable String password) { return userService.checkPassword(email, password); }

    @GetMapping("/Bet/username/{username}")
    public List<Bet> getBetsByUsername(@PathVariable String username) { return userService.getBetsByUsername(username); }
    //MAPPING POST
    @PostMapping
    public User createUser( @Valid @RequestBody User user) { return userService.createUser(user); }

    //MAPPING PUT
    @PutMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User user) { return userService.updateUser(id, user); }

    //MAPPING DELETE
    @DeleteMapping("/{id}") @JsonView(Views.ExtetendedPublic.class)
    public void deleteUser(@PathVariable String id) { userService.deleteUser(id); }
}
