package nus.iss.Neko.Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.Neko.Server.config.JwtUtil;
import nus.iss.Neko.Server.models.JwtRequest;
import nus.iss.Neko.Server.models.JwtResponse;
import nus.iss.Neko.Server.services.JwtUserDetailsService;
import nus.iss.Neko.Server.services.UserService;



@RestController
public class JwtAuthenticateRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsSvc;

    @Autowired
    private UserService userSvc;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthentication(@RequestBody JwtRequest jwtRequest)
            throws Exception {

        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();

        try {
            authenticate(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        final UserDetails userDetails = jwtUserDetailsSvc
                .loadUserByUsername(email);

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userSvc.getUser(email).get().getUsername()));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
