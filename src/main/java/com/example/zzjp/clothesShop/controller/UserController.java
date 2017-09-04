package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.model.UpdateUserPasswordDto;
import com.example.zzjp.clothesShop.model.UpdateUsernameDto;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.model.UserDto;
import com.example.zzjp.clothesShop.service.UserService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> users = userService.getAll();

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable final long id) {
        try {
            User user = userService.getById(id);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid final UserDto userDto) {
        try {
            User user = userService.register(userDto);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addAdmin(@RequestBody @Valid final UserDto userDto) {
        try {
            User user = userService.addAdmin(userDto);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyUser(principal, #id)")
    public HttpEntity remove(@PathVariable final long id) {
        try {
            userService.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/password")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyUser(principal, #id) ")
    public ResponseEntity<User> updatePassword(@PathVariable final long id,
                                               @RequestBody @Valid final UpdateUserPasswordDto updateUserPasswordDto) {
        try {
            User user = userService.updatePassword(id, updateUserPasswordDto.getPassword());

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/username")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyUser(principal, #id) ")
    public ResponseEntity<User> updateUsername(@PathVariable final long id,
                                               @RequestBody @Valid final UpdateUsernameDto updateUsernameDto) {
        try {
            User user = userService.updateUsername(id, updateUsernameDto.getUsername());

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
