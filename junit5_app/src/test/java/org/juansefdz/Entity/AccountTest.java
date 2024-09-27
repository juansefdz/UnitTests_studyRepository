package org.juansefdz.Entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void  testUser(){
        Account account = new Account("", new BigDecimal("10000000.123"));
       account.setUser("Juan");  //                         --> al comentar y ejecutar el test, falla expected: Juan, actual: null
        String expected = "Juan";
        String actual = account.getUser();
        assertEquals(expected, actual);
        assertTrue(actual.equals("Juan"));
        assertTrue(actual.equals("Antonio"));            // --> va a fallar debido a que el valor actual es Juan

    }

    @Test
    void testBalance(){
        Account account = new Account("Juan", new BigDecimal("1000.12345"));
        assertEquals(1000.12345,account.getBalance().doubleValue()); //el saldo tiene que ser igual a 1000.12345
        assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) <0 ); // el saldo no puede ser menor a 0
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) >0 ); // el saldo tien que ser mayor a 0
    }


    @Test
    void testAccountReference(){
       Account account= new Account("Juan", new BigDecimal("12345.54321"));
       Account account2 = new Account("juan", new BigDecimal("12345.54321"));

       assertNotEquals(account, account2);

       assertEquals(account, account2); // --> va a fallar, ya que al hacer el Override del método equals, se compara por atributos y no por referencia y los valores de los atributos son distintos

    }

}