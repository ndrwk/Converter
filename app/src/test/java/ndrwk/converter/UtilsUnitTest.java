package ndrwk.converter;

import org.junit.Test;

import ndrwk.converter.model.Currency;
import ndrwk.converter.model.ModelHolder;
import ndrwk.converter.model.ModelUtils;

import static org.junit.Assert.assertEquals;


public class UtilsUnitTest {

    private static final String xml =
            "<?xml version=\"1.0\" encoding=\"windows-1251\" ?>\n" +
            "<ValCurs Date=\"15.04.2017\" name=\"Foreign Currency Market\">\n" +
            "<Valute ID=\"R01010\">\n" +
            "<NumCode>036</NumCode>\n" +
            "<CharCode>AUD</CharCode>\n" +
            "<Nominal>1</Nominal>\n" +
            "<Name>Австралийский доллар</Name>\n" +
            "<Value>42,5868</Value>\n" +
            "</Valute>\n" +
            "<Valute ID=\"R01020A\">\n" +
            "<NumCode>944</NumCode>\n" +
            "<CharCode>AZN</CharCode>\n" +
            "<Nominal>1</Nominal>\n" +
            "<Name>Азербайджанский манат</Name>\n" +
            "<Value>32,7160</Value>\n" +
            "</Valute>\n" +
            "<Valute ID=\"R01035\">\n" +
            "<NumCode>826</NumCode>\n" +
            "<CharCode>GBP</CharCode>\n" +
            "<Nominal>1</Nominal>\n" +
            "<Name>Фунт стерлингов Соединенного королевства</Name>\n" +
            "<Value>70,4413</Value>\n" +
            "</Valute>\n" +
            "<Valute ID=\"R01060\">\n" +
            "<NumCode>051</NumCode>\n" +
            "<CharCode>AMD</CharCode>\n" +
            "<Nominal>100</Nominal>\n" +
            "<Name>Армянских драмов</Name>\n" +
            "<Value>11,5737</Value>\n" +
            "</Valute>" +
            "</ValCurs>";

    private static final String notXml = "khvghcgfxdz";

    private ModelHolder model = ModelHolder.getInstance();

    @Test
    public void numValue_isCorrect(){
        Currency currency = new Currency("1111", "111", "XXXX", 1, "Some_Currency", "35,8745");
        assertEquals(35.8745, ModelUtils.getNumValue(currency), 0.0001);
    }


    @Test
    public void convert_isCorrect(){
        model.setSourceCell(0);
        model.setDestCell(1);
        model.setSourceSumm(1);
        model.setXml(xml);
        ModelUtils.genListFromXml(model);
        ModelUtils.convert(model);
        assertEquals(11.5737/42.5868, model.getDestSumm(), 0.0001);
    }

    @Test
    public void genListFromXml_isCorrect(){
        model.setXml(xml);
        ModelUtils.genListFromXml(model);
        assertEquals("Армянских драмов", model.getCurrencyList().get(0).getName());
    }

    @Test
    public void addRuble_isCorrect(){
        model.setXml(xml);
        ModelUtils.genListFromXml(model);
        assertEquals("Российский рубль", model.getCurrencyList().get(model.getCurrencyList().size()-1).getName());
    }


}
