package org.juansefdz.Entity;

import org.juansefdz.Exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) indica que la instancia de la clase de test es única para todos los métodos de prueba
class AccountTest {
    Account account;

    @BeforeEach
        // se ejecuta antes de cada test
    void initMethodTest() {
        this.account = new Account("", new BigDecimal("1000.123"));
        System.out.println("Inicializando el método de prueba");
    }

    @AfterEach
        //se ejecuta después de cada test
    void endMethodTest() {
        System.out.println("Finalizando el método de prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void afterAll() { //se elimina el Static gracias al @TestInstance*
        System.out.println("Finalizando el test");

    }

    @Test
    @DisplayName("Test de nombre de usuario")
    void testUser() {
        Account account = new Account("", new BigDecimal("1000.123"));
        account.setUser("Juan");  //al comentar y ejecutar el test, falla expected: Juan, actual: null
        String expectedName = "Juan";
        String actualName = account.getUser();
        assertNotNull(actualName, () -> "El nombre del usuario no puede ser nulo");
        assertEquals(expectedName, actualName, () -> "El nombre del usuario debe ser el esperado");
        assertTrue(actualName.equals("Juan")); //al cambiar Juan, por otro valor, el test fallara


    }

    @Test
    @DisplayName("Test de saldo")
    void testBalance() {

        assertNotNull(account.getBalance()); // el saldo no puede ser nulo
        assertEquals(1000.123, account.getBalance().doubleValue()); //el saldo tiene que ser igual a 1000.12345
        assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0); // el saldo no puede ser menor a 0
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0); // el saldo tien que ser mayor a 0
    }


    @Test
    //@Disabled("Test deshabilitado temporalmente") // --> se deshabilita el test
    @DisplayName("Test de referencia de cuenta")
    void testAccountReference() {
        Account account = new Account("Juan", new BigDecimal("12345.54321"));
        Account account2 = new Account("juan", new BigDecimal("12345.54321"));

        assertNotEquals(account, account2);

        assertEquals(account, account2); // --> va a fallar, ya que al hacer el Override del método equals, se compara por atributos y no por referencia y los valores de los atributos son distintos

    }

    @Test
    @DisplayName("Test de debito de cuenta")
    void TestDebitAccount() {

        account.debit(new BigDecimal("100.00"));
        assertNotNull(account.getBalance());
        assertEquals(900.123, account.getBalance().doubleValue());
        assertEquals("800.123", account.getBalance().toPlainString()); // verifica que el saldo sea igual a 900.123 y que sea un string


    }

    @Test
    @DisplayName("Test de credito a cuenta")
    void TestCreditAccount() {


        account.credit(new BigDecimal("2000.00"));
        assertNotNull(account.getBalance());
        assertEquals(1100.123, account.getBalance().doubleValue());
        assertEquals("1200.123", account.getBalance().toPlainString()); // verifica que el saldo sea igual a 900.123 y que sea un string
    }

    @Test
    @DisplayName("Test de saldo insuficiente")
    void TestDebitAccountInsufficientBalance() {


        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {

            account.debit(new BigDecimal(100));
        });// verifica que al intentar debitar un saldo mayor al saldo actual, se lance la excepción InsufficientBalanceException

        String expectedMessage = "Insufficient balance";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage); // --> verifica que el mensaje de la excepción sea "Saldo insuficiente" va a fallar, ya que el mensaje de la excepción es "Insufficient balance"
    }

    @Test
    @DisplayName("Test de transferencia de dinero")
    void TestTransferMoneyAccount() {
        Account account1 = new Account("Juan", new BigDecimal("1000"));
        Account account2 = new Account("Antonio", new BigDecimal("1000"));

        Bank bank = new Bank();
        bank.setName("Banco de la gente");
        bank.transfer(account1, account2, new BigDecimal("500"));
        assertEquals("500", account1.getBalance().toPlainString());
        assertEquals("1500", account2.getBalance().toPlainString());
    }

    @Test
    @DisplayName("Test de relaciones de cuenta de banco")
    void TestBankAccountRelations() {

        //fail(); -> con esto se fuerza a que el test falle
        Account account1 = new Account("Juan", new BigDecimal("1000"));
        Account account2 = new Account("Antonio", new BigDecimal("1000"));

        Bank bank = new Bank();
        bank.setName("Banco de la gente");
        bank.addAccount(account1);
        bank.addAccount(account2);


        bank.transfer(account1, account2, new BigDecimal("500"));

        /*el AssertAll permite que todas las aserciones se ejecuten y se muestren en el reporte
         * aunque una de ellas falle va a seguir ejecutando las demás y mostrando los resultados
         * */

        assertAll(
                () -> assertEquals("500", account1.getBalance().toPlainString(),
                        () -> "El saldo de la cuenta 1 debe ser 500"),

                () -> assertEquals("1500", account2.getBalance().toPlainString(),
                        () -> "El saldo de la cuenta 2 debe ser 1500"),

                () -> assertEquals(2, bank.getAccounts().size(),
                        () -> "El banco debe tener 2 cuentas"),
                //indica que hay 2 cuentas en el banco, debe arrojar que aprueba el test

                () -> assertEquals("Banco de la gente", account1.getBank().getName(),
                        () -> "El nombre del banco de la cuenta 1 debe ser Banco de la gente"),
                    /*
                        - indica que el nombre del banco es "Banco de la gente", debe arrojar que aprueba el test
                        - si se elimina la relación account.setBank(this); en bank.java debería fallar el test
                     */

                /* Verifica que el usuario de la primera cuenta encontrada con el nombre "Juan" sea "Juan"*/
                () -> assertEquals("Juan", bank.getAccounts().stream()
                                .filter(a -> a.getUser().equals("Juan"))
                                .findFirst().get().getUser(),
                        () -> "El usuario de la cuenta Juan debe ser Juan"),
                /*Verifica que existe al menos una cuenta en el banco cuyo usuario es "Juan"*/
                () -> assertTrue(bank.getAccounts().stream()
                        .anyMatch(a -> a.getUser().equals("juan"))) // --> va a fallar, ya que el nombre del usuario es "Juan" y no "juan"

        );
    }

    //CONDICIONALES
    // Test condicionales para diferentes sistemas operativos
    @Test
    @EnabledOnOs(OS.WINDOWS)
    // Este test solo se ejecutará en sistemas operativos Windows
    void onlyInWindowsTest() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
        // Este test solo se ejecutará en sistemas operativos Linux y Mac
    void onlyLinuxAndMacTest() {
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
        // Este test no se ejecutará en sistemas operativos Windows
    void notInWindowsTest() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
        // Este test solo se ejecutará en la versión 8 de Java
    void onlyOnJdk8() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_11)
        // Este test no se ejecutará en la versión 11 de Java
    void onlyOnJdk11() {
    }

    @Test
    void printSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "17.*") // se ejecuta si la versión de java es 17
    void testJavaVersion() {
        System.out.println(System.getProperty("java.version"));
    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*64.*") // se ejecuta si el sistema no es de 64 bits
    void testSystem64Bits() {
        System.out.println(System.getProperty("os.arch"));
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*") // se ejecuta si el sistema es de 64 bits
    void testNoSystem64Bits() {
        System.out.println(System.getProperty("os.arch"));
    }

    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "miusuario") // se ejecuta si el nombre de usuario es igual a miusuario
    void TestUserNameComputer() {
        System.out.println(System.getProperty("user.name"));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "dev") // se ejecuta si la variable de entorno ENV es igual a dev
    void testDevEnvironment() {
        System.out.println(System.getenv("ENV"));
    }
}