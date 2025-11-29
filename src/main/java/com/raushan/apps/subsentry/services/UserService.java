package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    /**
     * Fetches a paginated list of all users.
     * @param pageable Object containing pagination info (page number, size).
     * @return A Page object containing the list of users for that page.
     */
    Page<UserDto> getAllUsers(Pageable pageable);
    void deleteUser(Long id);
    UserDto updateUser(UserDto userDto);
}