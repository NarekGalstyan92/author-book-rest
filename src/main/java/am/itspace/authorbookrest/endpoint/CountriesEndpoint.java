package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.CountryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountriesEndpoint {

    private final RestTemplate restTemplate;

    @Value("${rapid.getAllCountries.url}")
    private String getAllCountriesUrl;

    @Value("${rapid.getAllCountries.key.apiKey}")
    private String AllCountriesKeyAPIKey;

    @Value("${rapid.getAllCountries.key.value}")
    private String getAllCountriesKeyValue;

    @Value("${rapid.getAllCountries.host.key}")
    private String getAllCountriesHostKey;

    @Value("${rapid.getAllCountries.host.value}")
    private String getAllCountriesHostValue;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AllCountriesKeyAPIKey, getAllCountriesKeyValue);
        httpHeaders.add(getAllCountriesHostKey, getAllCountriesHostValue);

        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<CountryDTO[]> exchange = restTemplate.exchange(getAllCountriesUrl, HttpMethod.GET, httpEntity, CountryDTO[].class);
        List<CountryDTO> result = List.of(exchange.getBody());
        return ResponseEntity.ok(result);
    }
}
