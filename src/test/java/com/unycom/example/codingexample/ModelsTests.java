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
        assertEquals(0L, o.getFinalPrice());

        o.setPrice(100L); // expected -1%
        assertEquals(99L, o.getFinalPrice());

        o.setPrice(999L); // expected -1%
        assertEquals(989, o.getFinalPrice());

        o.setPrice(1000L); // expected -2%
        assertEquals(980L, o.getFinalPrice());

        o.setPrice(4999L); // expected -2%
        assertEquals(4899L, o.getFinalPrice());

        o.setPrice(5000L); // expected -5%
        assertEquals(4750L, o.getFinalPrice());

        o.setPrice(19999L); // expected -5%
        assertEquals(18999L, o.getFinalPrice());

        o.setPrice(20000L); // expected -10%
        assertEquals(18000L, o.getFinalPrice());

        o.setPrice(21999L); // expected -10%
        assertEquals(19799L, o.getFinalPrice());
    }


}
