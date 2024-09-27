package org.juansefdz.Entity;

import org.juansefdz.Exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void  testUser(){
        Account account = new Account("", new BigDecimal("10000000.123"));
       account.setUser("Juan");  //                         --> al comentar y ejecutar el test, falla expected: Juan, actual: null
        String expectedName = "Juan";
        String actualName = account.getUser();
        assertNotNull(actualName);
        assertEquals(expectedName, actualName);
        assertTrue(actualName.equals("Juan"));
        assertTrue(actualName.equals("Antonio"));            // --> va a fallar debido a que el valor actual es Juan

    }

    @Test
    void testBalance(){
        Account account = new Account("Juan", new BigDecimal("1000.12345"));
        assertNotNull(account.getBalance()); // el saldo no puede ser nulo
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

    @Test
    void TestDebbitAccount (){

       Account account= new Account("Juan", new BigDecimal("1000.123"));

       account.debit(new BigDecimal("100.00"));
       assertNotNull(account.getBalance());
       assertEquals(900.123, account.getBalance().doubleValue());
       assertEquals("900.123", account.getBalance().toPlainString()); // verifica que el saldo sea igual a 900.123 y que sea un string


    }

    @Test
    void TestCredditAccount (){

        Account account= new Account("Juan", new BigDecimal("1000.123"));

        account.credit(new BigDecimal("100.00"));
        assertNotNull(account.getBalance());
        assertEquals(1100.123, account.getBalance().doubleValue());
        assertEquals("1100.123", account.getBalance().toPlainString()); // verifica que el saldo sea igual a 900.123 y que sea un string
    }

    @Test
    void TestDebbitAccountInsufficientBalance () {

        Account account = new Account("Juan", new BigDecimal("1000.123"));

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {

            account.debit(new BigDecimal(2000));
        });// verifica que al intentar debitar un saldo mayor al saldo actual, se lance la excepción InsufficientBalanceException

        String expectedMessage = "Saldo insuficiente";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }




}