package ndrwk.converter;

import org.junit.Test;

import ndrwk.converter.Model.Currency;
import ndrwk.converter.Model.Rates;

import static org.junit.Assert.*;


public class RatesUnitTest {

    private Rates rates = new Rates();

    @Test
    public void date_isCorrect() throws Exception {
        rates.setDate("15.04.2017");
        assertEquals("15.04.2017", rates.getDate());
    }

    @Test
    public void name_isCorrect() throws Exception {
        rates.setName("15.04.2017");
        assertEquals("15.04.2017", rates.getName());
    }

    @Test
    public void ratesList_isCorrect() throws Exception {
        assertNotEquals(null, rates.getExchangeRates());
    }

}
