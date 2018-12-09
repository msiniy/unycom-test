package com.unycom.example.codingexample;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;


import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.github.javafaker.Faker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.unycom.example.codingexample.models.*;

public class JsonSerializationTests {

    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private static final Faker faker = new Faker();

    Customer buildTestCustomer() {
        Customer c = new Customer();
        c.setCode(faker.code().asin());
        c.setName(faker.name().fullName());
        c.setLocation(faker.address().fullAddress());
        c.setRegistrationDate(LocalDate.now().minusDays(faker.number().numberBetween(10, 1000)));
        c.setActive(faker.bool().bool());
        c.setType(faker.options().option(CustomerType.class));
        return c;
    }

    Order buildTestOrder() {
        Order o = new Order();
        o.setId(faker.number().randomNumber());
        o.setProduct(faker.commerce().productName());
        o.setPrice(faker.number().numberBetween(1, (long)Math.pow(10.0, 12.0) - 1));
        o.setStatus(faker.options().option(OrderStatus.class));
        o.setOrderDate(LocalDateTime.now().minusDays(faker.number().numberBetween(10, 100)));
        switch(o.getStatus()) {
            case CONFIRMED:
                o.setConfirmationDate(LocalDateTime.now().minusDays(faker.number().numberBetween(10, 100)));
            case DELIVERED:
                o.setConfirmationDate(LocalDateTime.now().minusDays(faker.number().numberBetween(10, 100)));
                o.setCompletionDate(LocalDateTime.now().minusDays(faker.number().numberBetween(10, 100)));
            default:
            	break;
        }
        return o;
    }

    @Test
    public void testOrderSerialization() throws Exception {
        Order o = buildTestOrder();
        o.setCustomer(buildTestCustomer());
        String result = mapper.writeValueAsString(o);
        JsonNode json = mapper.readTree(result);
        assertEquals(o.getId(), new Long(json.get("id").asLong()));
        assertEquals(o.getOrderDate(),
                LocalDateTime.parse(json.get("orderDate").asText(), DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(o.getProduct(), json.get("product").asText());
        assertEquals(o.getStatus(), OrderStatus.valueOf(json.get("status").asText()));
        assertEquals(o.getPrice(), new BigDecimal(json.get("price").asText()));
        assertEquals(o.getFinalPrice(), new BigDecimal(json.get("finalPrice").asText()));
        assertNotNull(json.get("customer"));
        assertNull(json.get("customer").get("orders"));
    }

    @Test
    public void testCustomerSerialization() throws Exception {
        Customer c = buildTestCustomer();
        c.addOrder(buildTestOrder());
        c.addOrder(buildTestOrder());
        String result = mapper.writeValueAsString(c);
        JsonNode json = mapper.readTree(result);
        assertEquals(c.getCode(), json.get("code").asText());
        assertEquals(c.getName(), json.get("name").asText());
        assertEquals(c.getLocation(), json.get("location").asText());
        assertEquals(c.getRegistrationDate().format(DateTimeFormatter.ISO_DATE),
                json.get("registrationDate").asText());
        assertEquals(c.isActive(), json.get("active").asBoolean());
        assertEquals(c.getType(), CustomerType.valueOf(json.get("type").asText()));
        assertNotNull(json.get("orders"));
        assertNull(json.get("orders").get("customer"));
    }
}
