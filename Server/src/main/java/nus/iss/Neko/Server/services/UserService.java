package nus.iss.Neko.Server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.Neko.Server.models.User;
import nus.iss.Neko.Server.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepo;
    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getUser(String email) {
        return userRepo.checkUserExistsByEmail(email);
    }

    public Optional<String> createUser(User user) {

        Optional<User> UserOpt = getUser(user.getEmail());
        if (UserOpt.isPresent()) {
            return Optional.empty();
        }

        if (userRepo.createNewUser(user)) {
            return Optional.of("User created");
        } else {
            return Optional.of("Internal error! User not created. Please try again.");
        }
    }
}
