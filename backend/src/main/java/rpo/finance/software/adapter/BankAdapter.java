package rpo.finance.software.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BankAdapter {

    private final static String ur = "http://localhost:7070";

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
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/generate")
                .build()
                .toUri();
        ResponseEntity<String> str = restTemplate.exchange(ur+"/generate/{userId}",
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class, userId);
        System.out.println(str.getBody());
        return str;
    }

    public ResponseEntity<String> getTransactions(Long userId){
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/getTransactions/{userId}")
                .build()
                .toUri();
        ResponseEntity<String> str = restTemplate.exchange(ur+"/getTransactions/{userId}",
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class, userId);
        System.out.println(str.getBody());
        return str;
    }

    public ResponseEntity<String> getTransactionsById(){
        URI url = UriComponentsBuilder.fromHttpUrl(ur)
                .path("/getTransactions")
                .build()
                .toUri();
        ResponseEntity<String> str = restTemplate.exchange(ur+"/getTransactions",
                HttpMethod.POST, new HttpEntity<>(new HttpHeaders()), String.class);
        System.out.println(str.getBody());
        return str;
    }
}
