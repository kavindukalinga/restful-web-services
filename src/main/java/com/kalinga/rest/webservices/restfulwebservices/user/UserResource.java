package com.kalinga.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {


    private UserRepository userRepository;

    public UserResource(UserDaoService service, UserRepository userRepository){
        this.userRepository =userRepository;
    }

    @Value("${app.name}")
    private static String appName;

    @GetMapping("/app")
    public String getVal() {
        return appName;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("id: "+id);

        return user.orElse(null);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Autowired

    EncryptDecryptService encryptDecryptService;

    @GetMapping("/createKeys")
    public void createPrivatePublickey() {
        encryptDecryptService.createKeys(false);
//        return encryptDecryptService.kkkey();
    }

    @PostMapping("/encrpyt")
    public String encryptMessage(@RequestBody String plainString) {
        return encryptDecryptService.encryptMessage(plainString);
    }


    @PostMapping("/decrpyt")
    public String decryptMessage(@RequestBody String encryptString) {
        return encryptDecryptService.decryptMessage(encryptString);
    }

}
