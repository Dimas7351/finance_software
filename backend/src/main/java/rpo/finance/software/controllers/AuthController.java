package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.user.RegistrationStep1DTO;
import rpo.finance.software.DTO.user.RegistrationStep2DTO;
import rpo.finance.software.DTO.user.RegistrationStep3DTO;
import rpo.finance.software.DTO.user.UserSignInDTO;
import rpo.finance.software.adapter.BankAdapter;
import rpo.finance.software.repositories.UserRepository;
import rpo.finance.software.services.user.LoginService;
import rpo.finance.software.services.user.RegistrationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "getting Account API", description = "Операции, связанные с регистрацией и авторизацией пользователей")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final BankAdapter bankAdapter;
    private final UserRepository userRepository;

    @PostMapping("/signup/first")
    @Operation(summary = "Данные для регистрации пользователя, Шаг 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шаг 1 завершён"),
            @ApiResponse(responseCode = "409",description = "Пользователь с таким email уже существует."),
            @ApiResponse(responseCode = "400",
                    description = "example:(\"name\": \"Имя должно содержать от 1 до 100 символов\")")
    })
    public ResponseEntity<String> reg1(@Valid @RequestBody RegistrationStep1DTO step1DTO, HttpSession session) {
        registrationService.registerStep1(step1DTO,session);

        return ResponseEntity.ok("Шаг 1 завершён");
    }


    @PostMapping("/signup/second")
    @Operation(summary = "Данные для регистрации пользователя, Шаг 2",
            description = "Здесь обрабатываются данные, введённые пользователем на шаге 2 регистрации. currency, uploadType.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Шаг 2 завершён. Данные успешно сохранены в сессии."),
            @ApiResponse(responseCode = "409",
                    description = "Invalid currency. Allowed values: RUB, USD, EUR, GBP."),
            @ApiResponse(responseCode = "400",
                    description = "Invalid currency. Allowed values: RUB, USD, EUR, GBP.")
    })    public ResponseEntity<String> registerStep2(
            @Valid @RequestBody RegistrationStep2DTO step2DTO, HttpSession session) {
        registrationService.registerStep2(step2DTO, session);
        return ResponseEntity.ok("Шаг 2 завершён.");
    }

    @PostMapping("/signup/third")
    @Operation(summary = "Данные для регистрации пользователя, Шаг 3",
            description = "Здесь обрабатываются данные, введённые пользователем на шаге 3 регистрации. bankName, phoneNumber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Шаг 2 завершён. Данные успешно сохранены в сессии."),
            @ApiResponse(responseCode = "409",
                    description = "Конфликт данных. Например, пользователь уже зарегистрирован с такими данными."),
            @ApiResponse(responseCode = "400",
                    description = "Неверные или неполные данные. Возможно, некоторые поля не были заполнены или данные не соответствуют формату.")
    })
    public ResponseEntity<String> registerStep3(
            @Valid @RequestBody RegistrationStep3DTO step3DTO, HttpSession session) {
        registrationService.registerStep3(step3DTO, session);
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

    public ResponseEntity<Map<String, String>> signin(@Valid @RequestBody UserSignInDTO signInDTO) {

        // Метод service, который возвращает JWT токен
        String token = loginService.signin(signInDTO);
        bankAdapter.generate(userRepository.findUserByEmail(signInDTO.email()).get().getUserId());

        // Ответ с токеном
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

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