package pjatk.tpo.demo.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;
import pjatk.tpo.demo.model.User;
import pjatk.tpo.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestParam String name){
        return userService.updateUser(id, name);
    }

    @PutMapping("/{id}/password")
    public User changePassword(@PathVariable Integer id, @RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.changePassword(id, oldPassword, newPassword);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password){
        return userService.login(email, password);
    }
}
