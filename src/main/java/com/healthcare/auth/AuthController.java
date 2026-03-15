package com.healthcare.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// Controller for handling user authentication (register and login)
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    // Password encoder using BCrypt algorithm
    private final AuthUserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthUserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo    = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // Registers a new user and returns a JWT token
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (repo.existsByEmail(req.getEmail()))
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El correo ya está registrado"));

        AuthUser user = new AuthUser();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));   // Hash the password before saving
        repo.save(user);

        return ResponseEntity.ok(Map.of(
            "token", jwtUtil.generateToken(user.getEmail()),
            "name",  user.getName(),
            "email", user.getEmail()
        ));
    }

    // Authenticates a user and returns a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return repo.findByEmail(req.getEmail())
            .filter(u -> encoder.matches(req.getPassword(), u.getPassword()))
            .map(u -> ResponseEntity.ok(Map.of(
                "token", jwtUtil.generateToken(u.getEmail()),
                "name",  u.getName(),
                "email", u.getEmail()
            )))
            .orElse(ResponseEntity.status(401)
                .body(Map.of("error", "Correo o contraseña incorrectos")));
    }

    // Verifies if a JWT token is valid
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader("Authorization") String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                return repo.findByEmail(email)
                    .map(u -> ResponseEntity.ok(Map.of("name", u.getName(), "email", u.getEmail())))
                    .orElse(ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado")));
            }
        }
        return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
    }

    public static class RegisterRequest {
        private String name, email, password;
        public String getName()     { return name; }
        public void setName(String n) { this.name = n; }
        public String getEmail()    { return email; }
        public void setEmail(String e) { this.email = e; }
        public String getPassword() { return password; }
        public void setPassword(String p) { this.password = p; }
    }

    public static class LoginRequest {
        private String email, password;
        public String getEmail()    { return email; }
        public void setEmail(String e) { this.email = e; }
        public String getPassword() { return password; }
        public void setPassword(String p) { this.password = p; }
    }
}