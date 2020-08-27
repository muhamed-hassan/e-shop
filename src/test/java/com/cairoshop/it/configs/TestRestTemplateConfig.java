package com.cairoshop.it.configs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

@Component
public class TestRestTemplateConfig {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${server.port}")
    private int serverPort;

    @PostConstruct
    public void iniTestRestTemplate() {
        testRestTemplate = new TestRestTemplate(restTemplateBuilder.rootUri("http://localhost:" + serverPort));
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }

}
