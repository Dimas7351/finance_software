package rpo.finance.software.jwt;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    // Секретный ключ для подписи токенов (замените на свой уникальный ключ)
    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret";

    // Время жизни токена (1 день в миллисекундах)
    private static final long EXPIRATION_TIME = 86400000;

    // Генерация ключа для подписи с использованием секретного ключа
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Метод для генерации JWT токена.
     * @param email - Email пользователя, который будет записан в токен.
     * @return строка JWT токена.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Устанавливаем email как "субъект" токена
                .setIssuedAt(new Date()) // Дата создания токена
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Дата истечения срока действия токена
                .signWith(key, SignatureAlgorithm.HS256) // Подписываем токен с использованием алгоритма HMAC SHA-256
                .compact(); // Генерируем токен как строку
    }

    /**
     * Метод для проверки валидности токена.
     * @param token - JWT токен, который нужно проверить.
     * @return значение "субъекта" токена (обычно email пользователя).
     */
    public String validateToken(String token) {
        try {
            // Создаем JWT парсер для проверки подписи и извлечения данных
            Claims claims = Jwts.parser() // Инициализируем билдер для парсера
                    .setSigningKey(key) // Указываем ключ для проверки подписи токена
                    .build() // Создаем парсер
                    .parseClaimsJws(token) // Проверяем токен и извлекаем JWS Claims
                    .getBody(); // Получаем тело токена (Claims)

            return claims.getSubject(); // Возвращаем субъект (email или другую информацию)
        } catch (JwtException | IllegalArgumentException e) {
            // Обрабатываем исключения: неправильный или просроченный токен
            throw new RuntimeException("Невалидный или просроченный токен", e);
        }
    }
}
