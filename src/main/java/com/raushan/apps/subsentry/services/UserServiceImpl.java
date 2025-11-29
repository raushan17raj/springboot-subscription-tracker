package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.UserDto;
import com.raushan.apps.subsentry.entities.User;
import com.raushan.apps.subsentry.exception.ResourceNotFoundException;
import com.raushan.apps.subsentry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        // The findAll(pageable) method is built-in from JpaRepository!
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getMobileNumber()

        ));
    }

    @Override
    public void deleteUser(Long id) {
        // 1. Check if user exist
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User", "Id", id);
        }
        // 2. Delete the user
        // We must also handle deleting their subscriptions.
        // Because we used 'cascade = CascadeType.ALL' in the User entity,
        // deleting the user will automatically delete all their subscriptions.
         userRepository.deleteById(id);
    }

    //TODO
    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }
}