package dev.etornam.user_demo.controller;

import java.util.ArrayList;
import java.util.List;
import dev.etornam.user_demo.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/users")
public class UserController {
    List<User> users = new ArrayList<User>();
    
    public UserController() {
        users.add(new User(1, "Sunu Bright", 21));
        users.add(new User(2, "Kofi", 24));
        users.add(new User(3, "Ama", 15));
        users.add(new User(4, "Kodjo", 30));
        users.add(new User(5, "Ben", 34));
        users.add(new User(6, "william", 40));
    }

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @GetMapping(value = "/{id}")
    public User getUsersById(@PathVariable("id") long id) {
        User found = null;

        for (User user : users) {
            if (user.getId() == id) {
                found = user;
                break;
            }
        }
        return found;
    }
    

    @GetMapping(params = "name")
    public List<User> getUsersByName(@RequestParam(value = "name") String name) {
        List<User> filteredUsers = new ArrayList<User>();

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }


    @PostMapping
    public ResponseEntity<User> add(@RequestBody User u) {
        users.add(u);
        return new ResponseEntity<User>(u, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> addOrUpdate(@RequestBody User u) {
        ResponseEntity<User> responseEntity;

        if (users.contains(u)) {
            int index = users.indexOf(u);
            users.set(index, u);
            responseEntity = new ResponseEntity<User>(u, HttpStatus.OK);
        } else {
            users.add(u);
            responseEntity = new ResponseEntity<>(u, HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        boolean found = false;

        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                found = true;
                break;
            }
        }

        if (found == false) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}