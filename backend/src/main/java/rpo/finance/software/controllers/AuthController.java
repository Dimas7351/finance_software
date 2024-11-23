package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.DTO.user.UserSignInDTO;
import rpo.finance.software.entities.ConfirmationToken;
import rpo.finance.software.entities.User;
import rpo.finance.software.services.email.ConfirmationTokenService;
import rpo.finance.software.services.user.LoginService;
import rpo.finance.software.services.user.RegistrationService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "getting Account API", description = "Операции, связанные с регистрацией и авторизацией пользователей")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final ConfirmationTokenService tokenService;

    @PostMapping("/signup")
    @Operation(summary = "Данные для регистрации пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409",description = "Пользователь с таким email уже существует."),
            @ApiResponse(responseCode = "400",
                    description = "example:(\"name\": \"Имя должно содержать от 1 до 100 символов\")")
    })
    public ResponseEntity<String> signup(@Valid @RequestBody UserRegistrationDTO registration) {
        registrationService.registerUser(registration);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован." +
                " Пожалуйста, подтвердите свою учетную запись через ссылку, отправленную на вашу электронную почту.\n");
    }

    @GetMapping("/verify")
    @Operation(
            summary = "Подтвердить учетную запись по токену",
            description = "Этот эндпоинт позволяет пользователю подтвердить свою учетную запись с помощью токена, который был отправлен на почту."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Учетная запись успешно подтверждена."),
            @ApiResponse(responseCode = "400", description = "Токен неверен или срок его действия истек."),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким токеном не найден."),
    })
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
            String message = registrationService.verifyAccount(token);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Вход пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public ResponseEntity<String> signin(@Valid @RequestBody UserSignInDTO signInDTO) {
        String response = loginService.signin(signInDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-token")
    @Operation(
            summary = "Запросить новый токен для подтверждения почты",
            description = "Этот эндпоинт позволяет пользователю запросить новый токен для подтверждения учетной записи."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый токен подтверждения отправлен на вашу почту."),
            @ApiResponse(responseCode = "400", description = "Неверный email или другие ошибки при запросе токена."),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден."),
    })
    public ResponseEntity<String> resendVerificationToken(@RequestParam("email") String email) {
        registrationService.resendVerificationToken(email);
        return ResponseEntity.ok("Новый токен подтверждения был отправлен на вашу электронную почту.");
    }
}