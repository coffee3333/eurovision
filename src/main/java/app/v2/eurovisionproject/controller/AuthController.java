package app.v2.eurovisionproject.controller;

import app.v2.eurovisionproject.dto.LoginRequest;
import app.v2.eurovisionproject.dto.RegisterRequest;
import app.v2.eurovisionproject.entities.Country;
import app.v2.eurovisionproject.entities.User;
import app.v2.eurovisionproject.entities.UserRole;
import app.v2.eurovisionproject.repositories.CountryRepository;
import app.v2.eurovisionproject.repositories.UserRepository;
import app.v2.eurovisionproject.repositories.UserRoleRepository;
import app.v2.eurovisionproject.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final UserRoleRepository userRoleRepo;
    private final CountryRepository countryRepo;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo,
                          UserRoleRepository userRoleRepo,
                          CountryRepository countryRepo,
                          JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.countryRepo = countryRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !user.getPasswordHash().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        Country country = countryRepo.findById(request.getCountryId())
                .orElse(null);
        if (country == null) {
            return ResponseEntity.badRequest().body("Country not found: " + request.getCountryId());
        }

        UserRole publicRole = userRoleRepo.findByName("PUBLIC")
                .orElseThrow(() -> new IllegalStateException("PUBLIC role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(request.getPassword());
        user.setRole(publicRole);
        user.setCountry(country);
        userRepo.save(user);

        return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user)));
    }
}
