package rpo.finance.software.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rpo.finance.software.DTO.DateTransactionDTO;
import rpo.finance.software.entities.Transaction;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAdapter {

    private final static String ur = "http://bank-microservice:7070";

    private final RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<String> bankTest(){
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/test")
                .build()
                .toUri();
        ResponseEntity<String> str = restTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        System.out.println(str.getBody());
        return str;
    }

    public ResponseEntity<String> generate(Long userId){

        ResponseEntity<String> str = restTemplate.exchange(ur+"/generate/{userId}",
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class, userId);
        System.out.println(str.getBody());
        return str;
    }

    public ResponseEntity<List<Transaction>> getTransactions(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/getTransactions/{userId}") // Шаблон с параметром {userId}
                .buildAndExpand(userId) // Подставляем значение userId в шаблон
                .toUri(); // Строим финальный URI

        ResponseEntity<List<Transaction>> str = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<>() {} // Указываем тип данных в ответе
        );

        System.out.println(str.getBody()); // Выводим тело ответа (список транзакций)
        return str; // Возвращаем результат
    }



    public ResponseEntity<List<Transaction>> getTransactionsWithDate(DateTransactionDTO dto){
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/getTransactions")
                .build()
                .toUri();

        HttpEntity<DateTransactionDTO> requestEntity = new HttpEntity<>(dto, new HttpHeaders());

        ResponseEntity<List<Transaction>> str = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println(str.getBody());
        return str;
    }
}
