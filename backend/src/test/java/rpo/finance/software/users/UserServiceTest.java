package rpo.finance.software.users;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.user_exceptions.EmailAlreadyExistException;
import rpo.finance.software.mappers.ClientRegistrationMapper;
import rpo.finance.software.repositories.UserRepository;
import rpo.finance.software.services.UserService;

import java.time.LocalDate;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ClientRegistrationMapper clientRegistrationMapper;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDTO registrationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registrationDTO = new UserRegistrationDTO(
                "karam",
                "karam@example.com",
                "password123",
                LocalDate.of(1990, 1, 1),
                "+71234567890"
        );
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.findUserByEmail(registrationDTO.email())).thenReturn(Optional.of(new User()));

        EmailAlreadyExistException exception = assertThrows(EmailAlreadyExistException.class, () -> {
            userService.registerUser(registrationDTO);
        });

        assertEquals("Пользователь с таким email уже существует " + registrationDTO.email(), exception.getMessage());
    }

    @Test
    void testRegisterUser_Success() {

        when(userRepository.findUserByEmail(registrationDTO.email())).thenReturn(Optional.empty());
        User user = new User(
                null, registrationDTO.name(), registrationDTO.phone(),
                registrationDTO.birthday(), registrationDTO.email(),
                registrationDTO.password(), "USD", null, null, null
        );
        when(clientRegistrationMapper.apply(registrationDTO)).thenReturn(user);
        when(passwordEncoder.encode(registrationDTO.password())).thenReturn("hashedPassword");

        userService.registerUser(registrationDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
