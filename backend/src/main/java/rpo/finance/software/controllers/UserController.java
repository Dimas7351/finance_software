package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.user.PasswordRequest;
import rpo.finance.software.DTO.user.UserInfoDTO;
import rpo.finance.software.services.user.UserService;

@RestController
@RequestMapping("/user")
@Tag(name = "Users Account Management API", description = "Operations for managing user accounts")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}/change-password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change Password", description = "Allows a user to update their password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request data."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public String changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(userId, passwordRequest);
        return "Password changed successfully.";
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Account", description = "Allows a user to delete their account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return "Account deleted successfully.";
    }
}
