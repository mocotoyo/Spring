package com.example.demo;

import com.example.demo.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.profiles.active=prod"})
class ItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetItems() {
        ResponseEntity<Item[]> response = restTemplate.getForEntity("/items", Item[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testPostItem() {
        Item newItem = new Item(0, "Nuevo");
        ResponseEntity<Item> response = restTemplate.postForEntity("/items", newItem, Item.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Nuevo");
    }
}
