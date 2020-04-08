/*
package com.soni.usermanagement.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soni.usermanagement.model.AuthenticationRequest;
import com.soni.usermanagement.model.AuthenticationResponse;
import com.soni.usermanagement.services.MyUserDetailsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloResource {

    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/hello")
    public String hello() { return "hello world"; }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) 
    throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));        
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);            
        }

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    
}
*/