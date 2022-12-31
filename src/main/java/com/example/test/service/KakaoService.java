package com.example.test.service;
import com.example.test.model.Test;
import com.example.test.repository.AuthRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {
//    private MultiValueMap<String, String> body;
//    private HttpComponentsClientHttpRequestFactory factory;
//    private StringBuilder urlBuilder;
//    private boolean queryStringToken;
//    private String url;
    private final AuthRepository authRepository;

    @Transactional
    public void kakaoLogin(String code) throws IOException {
        // grant_type : authorization_code
        // REST API 키 : 58605b86f2774acfcd4a6b7390cd736d
        // redirect_uri : //http://localhost:8080/member/kakao
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        String url = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type","authorization_code");
        map.add("client_id","58605b86f2774acfcd4a6b7390cd736d");
        map.add("redirect_uri","http://localhost:8080/member/kakao");
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        System.out.println(entity);
        response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> stringMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {
        });

        String access_token = stringMap.get("access_token");

        headers.add("Authorization", "Bearer " + access_token);
        HttpEntity<String> map2 = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me", map2, String.class);
        System.out.println(responseEntity.getBody());

        Map<String, Object> o = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {});
        saveUser(o);

    }

    private void saveUser(Map<String, Object> o){
        Test db = new Test();
        Object properties = o.get("kakao_account");
        System.out.println("#######################" + properties);
    }


}
