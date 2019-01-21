package no.nav.tag.kontaktskjema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GsakKlient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gsak.url}")
    private String gsakUrl;

    public String opprettGsakOppgave(GsakOppgave gsakOppgave) {
        ResponseEntity<GsakOppgave> respons = restTemplate.postForEntity(gsakUrl, gsakOppgave, GsakOppgave.class);

        if (HttpStatus.OK.equals(respons.getStatusCode())) {
            return respons.getBody().getId();
        } else {
            log.info(respons.getStatusCode().toString());
            throw new KontaktskjemaException("Kall til Gsak feilet.");
        }
    }
}
