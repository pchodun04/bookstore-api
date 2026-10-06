package bookstore.service;

import org.springframework.stereotype.Service;
import bookstore.model.User;
import bookstore.repository.UserRepository;
import bookstore.ApiException;

import java.util.List;

import static bookstore.ApiException.notFound;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id){
        return userRepository.findById(id).orElseThrow(() -> notFound("Nie ma takiego uzytkownika"));
    }

    public User saveUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw ApiException.invalidData("Taki użytkownik juz istnieje");
        }
        return userRepository.save(user);
    }

    public User updateUser(Integer id, String name){
        User user = userRepository.findById(id).orElseThrow(() -> notFound("Nie ma takiego uzytkownika"));
        user.setName(name);
        return userRepository.save(user);
    }

    public User changePassword(Integer id, String oldPassword, String newPassword){
        User user = userRepository.findById(id).orElseThrow(() -> notFound("Nie ma takiego uzytkownika"));
        if(!user.getPassword().equals(oldPassword)){
            throw ApiException.invalidData("Bledne haslo");
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> notFound("Nie ma takiego uzytkownika"));
        if(!user.getPassword().equals(password)){
            throw ApiException.invalidData("Bledne haslo");
        }
        return user;
    }
}
