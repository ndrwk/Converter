package ndrwk.converter.View;

import java.util.ArrayList;


public interface ConverterView {
    void setSourceSumm(double summ);
    void setSourceCurrency(String charCode, String name);
    void setDestCurrency(String charCode, String name);
    void setResultSumm(double summ);
    void showMessage(String message);
    void showCurrencyList(ArrayList<CurrencyView> currencyList);
    boolean checkNetwork();
    String loadXml();
    int loadSourcePos();
    int loadDestPos();
    double loadSourceSumm();
    double loadDestSumm();
    void savePrefs(String xml, int sourcePos, int destPos, double sourceSumm, double destSumm);
    void enableControls();
    void disableControls();

}
