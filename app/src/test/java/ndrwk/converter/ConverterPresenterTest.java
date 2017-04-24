package ndrwk.converter;


import org.junit.Before;
import org.junit.Test;

import ndrwk.converter.model.ModelHolder;
import ndrwk.converter.model.ModelUtils;
import ndrwk.converter.presenter.ConverterPresenter;
import ndrwk.converter.view.ConverterView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConverterPresenterTest {

    private ConverterPresenter presenter;
    private ModelHolder model;
    private ConverterView view;
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


    @Before
    public void setup(){
        presenter = spy(new ConverterPresenter());
        view = mock(ConverterView.class);
        model = ModelHolder.getInstance();
        presenter.bindView(view);
        model.setSourceSumm(12.34);
        model.setSourceCell(0);
        model.setDestCell(2);
        model.setDestSumm(45.67);
        when(view.checkNetwork()).thenReturn(true);

        model.setXml(xml);
        ModelUtils.genListFromXml(model);
        presenter.setModel(model);
        presenter.onInit();
    }

    @Test
    public void setSourceSumm_isCorrect (){
        verify(view).setSourceSumm(12.34);
    }

    @Test
    public void setSourceCurrency_isCorrect(){
        verify(view).setSourceCurrency("AMD", "100 Армянских драмов");
    }

    @Test
    public void setDestCurrency_isCorrect(){
        verify(view).setDestCurrency("AZN", "Азербайджанский манат");
    }

    @Test
    public void setResultSumm_isCorrect(){
        verify(view).setResultSumm(45.67);
    }

    @Test
    public void savePrefs_isCorrect(){
        presenter.onStop();
        verify(view).savePrefs(xml, 0, 2, 12.34, 45.67);
    }

}
