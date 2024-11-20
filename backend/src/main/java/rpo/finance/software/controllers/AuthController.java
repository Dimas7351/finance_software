package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.DTO.user.UserSignInDTO;
import rpo.finance.software.services.UserService;

@RestController
@RequestMapping("/auth")
@Tag(name = "getting Account API", description = "Операции, связанные с регистрацией и авторизацией пользователей")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    @Operation(summary = "Данные для регистрации пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409",description = "Пользователь с таким email уже существует."),
            @ApiResponse(responseCode = "400",
                    description = "example:(\"name\": \"Имя должно содержать от 1 до 100 символов\")")
    })
    public ResponseEntity<String> signup(@Valid @RequestBody UserRegistrationDTO registration) {
        userService.registerUser(registration);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PostMapping("/login/{id}/=YouTubeWeb&tab=rk&utm_medium=act&gar=WzgwLCIyMzMzODciXQ&tab=rk&hl=ru&pli=1&nlr=1")
    @Operation(summary = "Вход пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public ResponseEntity<String> signin(@Valid @RequestBody UserSignInDTO signInDTO, @PathVariable int id) {
        String response = userService.signin(signInDTO);
        return ResponseEntity.ok(response);
    }
}