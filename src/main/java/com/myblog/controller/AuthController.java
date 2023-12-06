package com.myblog.controller;

import com.myblog.entity.Role;
import com.myblog.entity.User;
import com.myblog.payload.JWTAuthResponse;
import com.myblog.payload.LoginDto;
import com.myblog.payload.SignUpDto;
import com.myblog.repository.RoleRepository;
import com.myblog.repository.UserRepository;
import com.myblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto){
       if( userRepo.existsByUsername(signUpDto.getUsername())){
           return  new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
       }
        if( userRepo.existsByEmail(signUpDto.getEmail())){
            return  new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_USER").get();
        Set<Role> role = new HashSet<>();
        role.add(roles);
        user.setRoles(role);

        User saveUser = userRepo.save(user);
        SignUpDto dto = new SignUpDto();
        dto.setName(saveUser.getName());
        dto.setEmail(saveUser.getEmail());
        dto.setUsername(saveUser.getUsername());

        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
    }




