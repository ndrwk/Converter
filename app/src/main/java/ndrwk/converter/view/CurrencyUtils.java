package ndrwk.converter.view;

import org.json.JSONException;
import org.json.JSONObject;


public class CurrencyUtils {

    public static final String CV_CHARCODE = "cv_charcode";
    public static final String CV_NAME = "cv_name";
    public static final String CV_NOMINAL = "cv_nominal";

    public static JSONObject getJSON(CurrencyView cv){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(CV_CHARCODE, cv.getCharCode());
            jsonObj.put(CV_NAME, cv.getTxtName());
            jsonObj.put(CV_NOMINAL, cv.getNominal());
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObj;
    }

}
