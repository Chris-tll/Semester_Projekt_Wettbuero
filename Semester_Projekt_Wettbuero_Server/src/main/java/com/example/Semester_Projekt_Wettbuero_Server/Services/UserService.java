package com.example.Semester_Projekt_Wettbuero_Server.Services;

import com.example.Semester_Projekt_Wettbuero_Server.Enums.Roles;
import com.example.Semester_Projekt_Wettbuero_Server.Repositories.UserRepository;
import Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser() { return userRepository.findAll(); }

    public User getUserById(String id) { return userRepository.findById(id).orElse(null); }

    //CheckPassword
    public boolean checkPassword(String email, String password) {
        String id = " ";
        for(User u : getAllUser()) {
            if(u.getEmail().equals(email)){
                id = u.getId();
                break;
            }
        }
        if(userRepository.existsById(id)){
            User u = getUserById(id);
            System.out.println(password);
            if(passwordEncoder.matches(password, u.getPassword())) {
                System.out.println("Hallo");
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String name) {
        System.out.println(getAllUser().get(0).getUsername());
        for (User u : getAllUser()) {
            if(u.getUsername().equals(name)){
                System.out.println("HALLO");
                return u;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (User u : getAllUser()) {
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }


    //Create
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Roles.USER);
        user.setMoney(70000);
        return userRepository.save(user);
    }

    //Put
    public ResponseEntity<String> updateUser(String id, User user) {
        if(userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return ResponseEntity.ok("True");
        }
        return ResponseEntity.ok("False");
    }

    //Delete
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
