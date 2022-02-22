package com.smatoo.api;

import com.smatoo.counter.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class Accept {

    private static Logger LOG = LoggerFactory.getLogger(Accept.class);

    @Autowired
    private Counter counter;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api/smaato/accept")
    public ResponseEntity accept(@RequestParam int id, @RequestParam(required = false) String endpoint) {
        counter.addCount(id);

        if (endpoint != null) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("request", counter.getUniqueCountForCurrentMinute());
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint + "?request={request}", String.class, params);
            LOG.info("Response code from endpoint {} is {}", endpoint, response.getStatusCode());
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body("ok");
    }
}
