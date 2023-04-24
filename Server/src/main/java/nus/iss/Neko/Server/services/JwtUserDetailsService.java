package nus.iss.Neko.Server.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nus.iss.Neko.Server.models.CatUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userSvc;
    
    @Autowired
    public void setUserSvc(UserService userSvc) {
        this.userSvc = userSvc;
    }
    public UserService getUserSvc() {
        return this.userSvc;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<CatUser> userOpt = userSvc.getUser(email);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist!");
        }

        CatUser user = userOpt.get();

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }
    
}
