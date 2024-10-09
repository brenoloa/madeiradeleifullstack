package com.fabricamoveis.controller;

import com.fabricamoveis.config.JwtTokenUtil;
import com.fabricamoveis.dto.AuthRequest;
import com.fabricamoveis.dto.AuthResponse;
import com.fabricamoveis.dto.SignupRequest;
import com.fabricamoveis.model.ERole;
import com.fabricamoveis.model.Role;
import com.fabricamoveis.model.User;
import com.fabricamoveis.repository.UserRepository;
import com.fabricamoveis.service.UserDetailsImpl;
import com.fabricamoveis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String username = userDetails.getUsername();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

            String token = jwtTokenUtil.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Usuário já existe!");
        }


        Set<Role> roles = new HashSet<>();
        if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty() || signUpRequest.getRole().equalsIgnoreCase("cliente")) {
            roles.add(new Role(ERole.ROLE_CLIENTE));
        } else if (signUpRequest.getRole().equalsIgnoreCase("admin")) {
            roles.add(new Role(ERole.ROLE_ADMIN));
        } else {
            return ResponseEntity.badRequest().body("Erro: Role inválida!");
        }

        User user = new User(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()), roles);

        userRepository.save(user);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}