package ndrwk.converter;

import org.junit.Test;

import ndrwk.converter.model.Currency;

import static org.junit.Assert.*;

public class CurrencyUnitTest {

    private Currency currency = new Currency();

    @Test
    public void id_isCorrect() {
        currency.setId("R01010");
        assertEquals("R01010", currency.getId());
    }

    @Test
    public void numCode_isCorrect() {
        currency.setNumCode("036");
        assertEquals("036", currency.getNumCode());
    }

    @Test
    public void charCode_isCorrect(){
        currency.setCharCode("AUD");
        assertEquals("AUD", currency.getCharCode());
    }

    @Test
    public void nominal_isCorrect(){
        currency.setNominal(1);
        assertEquals(1, currency.getNominal());
    }

    @Test
    public void name_isCorrect() {
        currency.setName("Австралийский доллар");
        assertEquals("Австралийский доллар", currency.getName());
    }

    @Test
    public void strValue_isCorrect() {
        currency.setValue("42,9608");
        assertEquals("42,9608", currency.getValue());
    }

}