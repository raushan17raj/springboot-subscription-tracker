package com.raushan.apps.subsentry.controllers;

import com.raushan.apps.subsentry.dto.UserDto;
import com.raushan.apps.subsentry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users
     * Fetches all users with pagination.
     * * You can call this with:
     * /api/users?page=0&size=10
     * /api/users?page=1&size=5
     * /api/users (will use the default page=0, size=10)
     */
    @GetMapping
    public ResponseEntity<Page<UserDto>> fetchAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "email") Pageable pageable) {
        
        Page<UserDto> userPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // Return 204 No Content, which is standard for a successful delete
        return ResponseEntity.noContent().build();
    }
}