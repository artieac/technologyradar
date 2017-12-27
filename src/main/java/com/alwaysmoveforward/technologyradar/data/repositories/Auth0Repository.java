package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.domainmodel.Auth0UserProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * Created by acorrea on 12/26/2017.
 */
@Repository
public class Auth0Repository {

    public Auth0UserProfile getUserProfile(String issuer, String accessToken) {
        Auth0UserProfile retVal = null;
        String targetUrl = issuer + "userInfo";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Auth0UserProfile> response = restTemplate.exchange(targetUrl, HttpMethod.GET, entity, Auth0UserProfile.class);

        if(response != null) {
            if (response.getBody() != null) {
                retVal = response.getBody();
            }
        }

        return retVal;
    }
}
