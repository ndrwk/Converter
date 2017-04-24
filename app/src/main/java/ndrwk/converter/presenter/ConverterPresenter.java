package ndrwk.converter.presenter;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ndrwk.converter.model.Currency;
import ndrwk.converter.model.ModelHolder;
import ndrwk.converter.model.ModelUtils;
import ndrwk.converter.view.ConverterView;
import ndrwk.converter.view.CurrencyView;

public class ConverterPresenter extends CommonPresenter<ModelHolder, ConverterView> {

    private final static String xmlUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private final static int SOURCE = 1;
    private final static int DEST = 2;
    private final static int NOTHING = 0;

    public void onInit() {
        setModel(ModelHolder.getInstance());
        view().disableControls();
        if (model.getXml().equals("")) {
            if (view().checkNetwork()) {
                final RatesDownloader downloadTask = new RatesDownloader();
                downloadTask.execute(xmlUrl);
                view().showMessage("Загрузка справочника");
            } else {
                view().showMessage("Используется сохраненный справочник, проверьте доступ в Интернет");
                model.setXml(view().loadXml());
                ModelUtils.genListFromXml(model);
                updateModel();
                updateControls();
                view().enableControls();
            }
        } else {
            updateControls();
            view().enableControls();
        }
    }

    private void updateModel() {
        model.setSourceSumm(view().loadSourceSumm());
        model.setDestSumm(view().loadDestSumm());
        model.setSourceCell(view().loadSourcePos());
        model.setDestCell(view().loadDestPos());
    }

    private void updateControls() {
        String currencyName = "";
        int nominal = model.getCurrencyList().get(model.getSourceCell()).getNominal();
        if (nominal != 1)
            currencyName += nominal + " ";
        currencyName += model.getCurrencyList().get(model.getSourceCell()).getName();

        view().setSourceCurrency(model.getCurrencyList().get(model.getSourceCell()).getCharCode(), currencyName);
        currencyName = "";
        nominal = model.getCurrencyList().get(model.getDestCell()).getNominal();
        if (nominal != 1)
            currencyName += nominal + " ";
        currencyName += model.getCurrencyList().get(model.getDestCell()).getName();
        view().setDestCurrency(model.getCurrencyList().get(model.getDestCell()).getCharCode(), currencyName);
        view().setSourceSumm(model.getSourceSumm());
        view().setResultSumm(model.getDestSumm());
    }

    public void onSourceSummClick(double summ){
        model.setSourceSumm(summ);
        ModelUtils.convert(model);
        view().setResultSumm(model.getDestSumm());
    }

    public void onSourceCurrencyClick(){
        model.setDialogFlag(SOURCE);
        view().showCurrencyList(getCurrencyViewList());
    }

    public void onDestCurrencyClick(){
        model.setDialogFlag(DEST);
        view().showCurrencyList(getCurrencyViewList());
    }

    public void onListItemClick(int pos){
        switch (model.getDialogFlag()){
            case SOURCE:
                model.setSourceCell(pos);
                break;
            case DEST:
                model.setDestCell(pos);
                break;
        }
        model.setDialogFlag(NOTHING);
        ModelUtils.convert(model);
        updateControls();
    }

    public void onStop(){
        view().savePrefs(model.getXml(), model.getSourceCell(), model.getDestCell(), model.getSourceSumm(), model.getDestSumm());
    }

    private ArrayList<CurrencyView> getCurrencyViewList() {
        ArrayList<CurrencyView> viewList = new ArrayList<>();
        for (Currency c : model.getCurrencyList()) {
            CurrencyView cv = new CurrencyView(c.getCharCode(), c.getNominal(), c.getName());
            viewList.add(cv);
        }
        return viewList;
    }

    private class RatesDownloader extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... txtUrl) {
            BufferedReader in;
            StringBuilder response;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(txtUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "windows-1251"));
                response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                in.close();
            } catch (Exception e) {
                return e.toString();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            model.setXml(result);
            ModelUtils.genListFromXml(model);
            if (!(view() == null)) {
                view().enableControls();
                updateModel();
                updateControls();
            }
        }

    }

}
