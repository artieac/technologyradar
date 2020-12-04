package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.domainmodel.Auth0UserProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Created by acorrea on 12/26/2017.
 */
@Repository
public class Auth0Repository {

    public Optional<Auth0UserProfile> getUserProfile(String issuer, String accessToken) {
        String targetUrl = issuer + "userInfo";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Auth0UserProfile> response = restTemplate.exchange(targetUrl, HttpMethod.GET, entity, Auth0UserProfile.class);

        if(response != null) {
            if (response.getBody() != null) {
                return Optional.ofNullable(response.getBody());
            }
        }

        return Optional.empty();
    }
}
