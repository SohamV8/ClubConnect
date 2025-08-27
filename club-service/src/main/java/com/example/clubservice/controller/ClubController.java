package com.example.clubservice.controller;

import com.example.clubservice.model.Club;
import com.example.clubservice.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ClubController {

    @Autowired
    private ClubService clubService;
    
    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🏆 Club Service</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
                    .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                    h1 { color: #2c3e50; text-align: center; }
                    .nav { text-align: center; margin: 20px 0; }
                    .nav a { margin: 0 10px; padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; }
                    .nav a:hover { background: #2980b9; }
                    .club-form { background: #ecf0f1; padding: 20px; border-radius: 5px; margin: 20px 0; }
                    .club-form input, .club-form textarea { width: 100%; padding: 10px; margin: 5px 0; border: 1px solid #bdc3c7; border-radius: 3px; }
                    .club-form button { background: #27ae60; color: white; padding: 10px 20px; border: none; border-radius: 3px; cursor: pointer; }
                    .club-list { margin-top: 20px; }
                    .club-item { background: #f8f9fa; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #3498db; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🏆 Club Management Service</h1>
                    <p style="text-align: center; color: #7f8c8d;">Port: 8081</p>
                    
                    <div class="nav">
                        <a href="/clubs">View All Clubs</a>
                        <a href="/clubs/create">Create New Club</a>
                    </div>
                    
                    <div class="club-form">
                        <h3>Create New Club</h3>
                        <form action="/clubs" method="POST">
                            <input type="text" name="name" placeholder="Club Name" required>
                            <textarea name="description" placeholder="Club Description" rows="3"></textarea>
                            <input type="text" name="category" placeholder="Category">
                            <button type="submit">Create Club</button>
                        </form>
                    </div>
                    
                    <div class="club-list">
                        <h3>Available Clubs</h3>
                        <div id="clubsList">Loading clubs...</div>
                    </div>
                </div>
                
                <script>
                    // Load clubs on page load
                    fetch('/clubs')
                        .then(response => response.json())
                        .then(clubs => {
                            const clubsList = document.getElementById('clubsList');
                            if (clubs.length === 0) {
                                clubsList.innerHTML = '<p>No clubs available yet.</p>';
                            } else {
                                clubsList.innerHTML = clubs.map(club => 
                                    `<div class="club-item">
                                        <h4>${club.name}</h4>
                                        <p><strong>Category:</strong> ${club.category || 'N/A'}</p>
                                        <p>${club.description || 'No description available'}</p>
                                        <button onclick="deleteClub(${club.id})" style="background: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer;">Delete</button>
                                    </div>`
                                ).join('');
                            }
                        })
                        .catch(error => {
                            document.getElementById('clubsList').innerHTML = '<p>Error loading clubs.</p>';
                        });
                    
                    function deleteClub(id) {
                        if (confirm('Are you sure you want to delete this club?')) {
                            fetch(`/clubs/${id}`, { method: 'DELETE' })
                                .then(() => location.reload())
                                .catch(error => alert('Error deleting club'));
                        }
                    }
                </script>
            </body>
            </html>
            """;
    }

    @GetMapping("/clubs")
    @ResponseBody
    public List<Club> getClubs() {
        return clubService.getAllClubs();
    }
    
    @GetMapping("/clubs/{id}")
    @ResponseBody
    public Club getClub(@PathVariable Long id) {
        return clubService.getClubById(id).orElse(null);
    }
    
    @PostMapping("/clubs")
    @ResponseBody
    public Club createClub(@RequestBody Club club) {
        return clubService.createClub(club);
    }
    
    @PutMapping("/clubs/{id}")
    @ResponseBody
    public Club updateClub(@PathVariable Long id, @RequestBody Club club) {
        return clubService.updateClub(id, club);
    }
    
    @DeleteMapping("/clubs/{id}")
    @ResponseBody
    public String deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return "Club deleted successfully";
    }
}
