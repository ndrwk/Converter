package ndrwk.converter.Model;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


public class ModelUtils {

    private static Rates rates;


    private static void addRuble(){
        if (rates == null) {
            rates = new Rates();
        }
        rates.getExchangeRates().add(new Currency("R09999", "643", "RUB", 1, "Российский рубль", "1"));
        Collections.sort(rates.getExchangeRates(), new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return o1.getCharCode().compareTo(o2.getCharCode());
            }
        });

    }

    public static void genListFromXml(ModelHolder holder){
        Reader reader = new StringReader(holder.getXml());
        Persister serializer = new Persister();
        try
        {
            rates = serializer.read(Rates.class, reader, false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rates = new Rates();
        }
        addRuble();
        holder.setCurrencyList(rates.getExchangeRates());
    }

    public static double getNumValue(Currency currency){
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = null;
        try {
            number = format.parse(currency.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
            number = 0;
        }
        return number.doubleValue();
    }


    public static void convert(ModelHolder holder){
        int source = holder.getSourceCell();
        int dest = holder.getDestCell();
        holder.setDestSumm(holder.getSourceSumm() *
                           getNumValue(holder.getCurrencyList().get(source)) /
                           getNumValue(holder.getCurrencyList().get(dest)));
    }
}
