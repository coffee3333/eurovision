package app.v2.eurovisionproject.controller;

import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.service.ShowAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shows")
public class ShowAdminController {

    private final ShowAdminService showAdminService;

    public ShowAdminController(ShowAdminService showAdminService) {
        this.showAdminService = showAdminService;
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<?> startVoting(@PathVariable int id) {
        try {
            Show show = showAdminService.startVoting(id);
            return ResponseEntity.ok("Voting started for: " + show.getName());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<?> stopVoting(@PathVariable int id) {
        try {
            Show show = showAdminService.stopVoting(id);
            return ResponseEntity.ok("Voting stopped for: " + show.getName());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
