package bookstore.controller;

import org.springframework.web.bind.annotation.*;
import bookstore.dto.dto;
import bookstore.model.User;
import bookstore.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<dto.UserResponse> getUsers(){
        return userService.getUsers().stream().map(u -> new dto.UserResponse(u.getId(), u.getName(), u.getEmail())).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public dto.UserResponse getUser(@PathVariable int id){
        User user = userService.getUser(id);
        return new dto.UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @PostMapping
    public dto.UserResponse createUser(@RequestBody dto.UserRequest user){
        User newUser = new User();
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setPassword(user.password());
        User savedUser = userService.saveUser(newUser);
        return new dto.UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody dto.UserUpdateRequest user){
        return userService.updateUser(id, user.name());
    }

    @PutMapping("/{id}/password")
    public User changePassword(@PathVariable Integer id, @RequestBody dto.ChangePasswordRequest password){
        return userService.changePassword(id, password.oldPassword(), password.newPassword());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody dto.LoginRequest loginRequest){
        return userService.login(loginRequest.email(), loginRequest.password());
    }
}
