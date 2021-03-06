package com.soni.usermanagement.controller;
import com.soni.usermanagement.model.AuthenticationRequest;
import com.soni.usermanagement.model.AuthenticationResponse;
import com.soni.usermanagement.security.JwtTokenUtils;
import com.soni.usermanagement.services.MyUserDetailsService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
	
    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    // @Autowired
    // private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @RequestMapping("/hello")
    public String hello() { return "hello world"; }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) 
    throws Exception{

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());
        
        try {
             
            if(!encoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Incorrect password");
            }
            // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            //     authenticationRequest.getUsername(), 
            //     authenticationRequest.getPassword()));     

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);            
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
