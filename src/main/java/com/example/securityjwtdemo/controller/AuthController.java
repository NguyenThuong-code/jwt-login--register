package com.example.securityjwtdemo.controller;

import com.example.securityjwtdemo.dto.request.SignInForm;
import com.example.securityjwtdemo.dto.request.SignUpForm;
import com.example.securityjwtdemo.dto.response.JwtResponse;
import com.example.securityjwtdemo.dto.response.ResponseMessage;
import com.example.securityjwtdemo.model.Role;
import com.example.securityjwtdemo.model.RoleName;
import com.example.securityjwtdemo.model.Users;
import com.example.securityjwtdemo.security.jwt.JwtProvider;
import com.example.securityjwtdemo.security.userprincipal.UserPrinciple;
import com.example.securityjwtdemo.service.impl.RoleServiceImpl;
import com.example.securityjwtdemo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("The username is existed"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("The email is existed"), HttpStatus.OK);
        }
        Users users = new Users(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                     Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        users.setRoles(roles);
        userService.save(users);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public  ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple= (UserPrinciple) authentication.getPrincipal();
return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(),userPrinciple.getAuthorities()));
    }
}
