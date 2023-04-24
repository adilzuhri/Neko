package nus.iss.Neko.Server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.Neko.Server.models.CatUser;
import nus.iss.Neko.Server.services.UserService;
import nus.iss.Neko.Server.utils.UserNotCreatedException;

@RestController
public class LoginController {

    @Autowired
    private UserService userSvc;

    @GetMapping("/explore")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(null);
    }

    @PostMapping(path="/signUp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody CatUser user) 
            throws UserNotCreatedException {

        Optional<String> userCreatedMsgOpt;
        try {
            userCreatedMsgOpt = userSvc.createUser(user);
        } catch (Exception e) {
            throw new UserNotCreatedException();
        }

        if (userCreatedMsgOpt.isEmpty()) {
            throw new UserNotCreatedException("CatUser account exists; please login.");
        } else {
            throw new UserNotCreatedException();
        }

    }

    @ExceptionHandler(UserNotCreatedException.class)
    ResponseEntity<String> createUserError(final UserNotCreatedException e) {
        if (e.getMessage().contains("exists")) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    