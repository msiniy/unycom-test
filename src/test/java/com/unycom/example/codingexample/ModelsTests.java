package com.unycom.example.codingexample;

import org.junit.Test;

import com.unycom.example.codingexample.models.Order;
import com.unycom.example.codingexample.models.Customer;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ModelsTests {
    @Test
    public void testAddOrderToCustomer() {
        Customer c = new Customer();
        assertNotNull(c.getOrders());
        assertTrue(c.getOrders().isEmpty());

        Order o = new Order();
        c.addOrder(o);
        assertEquals(c.getOrders().get(0), o);
        assertEquals(c, o.getCustomer());
    }

    @Test
    public void testRemoveOrderFromCustomer() {
        Customer c = new Customer();
        Order o = new Order();
        c.addOrder(o);
        assertEquals(1, c.getOrders().size());
        // try to remove null
        c.removeOrder(null);
        assertEquals(1, c.getOrders().size());
        c.removeOrder(o);
        assertTrue(c.getOrders().isEmpty());
        assertNull(o.getCustomer());
    }

    @Test
    public void testSetCustomerToOrder() {
        Customer c = new Customer();
        Order o = new Order();
        assertTrue(c.getOrders().isEmpty());
        o.setCustomer(c);
        assertEquals(o, c.getOrders().get(0));
        assertEquals(c, o.getCustomer());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testIfOrdersListIfUnmodifiable() {
        Customer c = new Customer();
        c.getOrders().add(new Order());
    }

    @Test
    public void testDiscountCalculation() {
        Order o = new Order();
        o.setPrice(0L);
        assertEquals(BigDecimal.ZERO, o.getFinalPrice());

        o.setPrice(10000L); // 100 EUR,  expected -1% (N < 1000)
        assertEquals(new BigDecimal("99.00"), o.getFinalPrice());

        o.setPrice(99900L); // 999 EUR, expected -1%  (N < 1000)
        assertEquals(new BigDecimal("989.01"), o.getFinalPrice());

        o.setPrice(100000L); // 1000 EUR, expected -2% (1000<=N<5000)
        assertEquals(new BigDecimal("980.00"), o.getFinalPrice());

        o.setPrice(499999L); // 4999.99 EUR, expected -2%, (1000<=N<5000)
        assertEquals(new BigDecimal("4899.99"), o.getFinalPrice());

        o.setPrice(500000L); // 5000 EUR, expected -5% (5000<=N<20000)
        assertEquals(new BigDecimal("4750.00"), o.getFinalPrice());

        o.setPrice(2000000L); // expected -10%, (N>=20000)
        assertEquals(new BigDecimal("18000.00"), o.getFinalPrice());

        o.setPrice(2199900L); // 21999 EUR, expected -10%, (N>=20000)
        assertEquals(new BigDecimal("19799.10"), o.getFinalPrice());
    }


}
