package ndrwk.converter.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ndrwk.converter.presenter.ConverterPresenter;
import ndrwk.converter.R;

public class MainActivity extends AppCompatActivity implements FloatingList.IOnDialogListItemClick, ConverterView{

    private final static String CURRENCY_LIST = "list";
    private final static String SOURCE_POS = "source_pos";
    private final static String DEST_POS = "dest_pos";
    private final static String SOURCE_SUMM = "source_summ";
    private final static String DEST_SUMM = "dest_summ";

    private EditText sourceSumm;
    private TextView sourceCharCode;
    private TextView sourceName;
    private TextView destCharCode;
    private TextView destName;
    private TextView destSumm;
    private CardView source;
    private CardView dest;

    private ConverterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ConverterPresenter();
        setContentView(R.layout.activity_main);
        sourceSumm = (EditText) findViewById(R.id.source_sum);
        sourceSumm.setFocusableInTouchMode(true);
        sourceSumm.requestFocus();
        sourceSumm.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String txt = sourceSumm.getText().toString();
                    if (!txt.equals("")) {
                        presenter.onSourceSummClick(Double.parseDouble(txt));
                    }
                    return true;
                }
                return false;
            }
        });
        sourceCharCode = (TextView) findViewById(R.id.source_char_code_text);
        sourceName = (TextView) findViewById(R.id.source_currency_name_in_card);
        destCharCode = (TextView) findViewById(R.id.dest_char_code_text);
        destName = (TextView) findViewById(R.id.dest_currency_name_in_card);
        destSumm = (TextView) findViewById(R.id.dest_sum);
        source = (CardView) findViewById(R.id.source_currency_place);
//        source.setRadius(2);
        dest = (CardView) findViewById(R.id.dest_currency_place);
//        dest.setRadius(2);
        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSourceCurrencyClick();
            }
        });
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDestCurrencyClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
        presenter.onInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onStop();
        presenter.unbindView();
    }

    @Override
    public void dialogListClickCallback(int pos) {
        presenter.onListItemClick(pos);
    }

    @Override
    public void setSourceSumm(double summ) {
        sourceSumm.setText(String.format("%d", (int)summ));

    }

    @Override
    public void setSourceCurrency(String charCode, String name) {
        sourceCharCode.setText(charCode);
        sourceName.setText(name);
    }

    @Override
    public void setDestCurrency(String charCode, String name) {
        destCharCode.setText(charCode);
        destName.setText(name);
    }

    @Override
    public void setResultSumm(double summ) {
        destSumm.setText(String.format("%.4f", summ));

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    @Override
    public void showCurrencyList(ArrayList<CurrencyView> currencyList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = FragmentFabric.newDialogInstance(currencyList);
        dialogFragment.show(ft, "dialog");
    }

    @Override
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public String loadXml() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getString(CURRENCY_LIST, "");
    }

    @Override
    public int loadSourcePos() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(SOURCE_POS, 0);
    }

    @Override
    public int loadDestPos() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(DEST_POS, 0);
    }

    @Override
    public double loadSourceSumm() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return Double.longBitsToDouble(sp.getLong(SOURCE_SUMM, 1));
    }

    @Override
    public double loadDestSumm() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return Double.longBitsToDouble(sp.getLong(DEST_SUMM, 0));
    }

    @Override
    public void savePrefs(String xml, int sourcePos, int destPos, double sourceSumm, double destSumm) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SOURCE_POS, sourcePos);
        editor.putInt(DEST_POS, destPos);
        editor.putLong(SOURCE_SUMM, Double.doubleToRawLongBits(sourceSumm));
        editor.putLong(DEST_SUMM, Double.doubleToRawLongBits(destSumm));
        editor.putString(CURRENCY_LIST, xml);
        editor.apply();
    }

    @Override
    public void enableControls() {
        sourceSumm.setEnabled(true);
        sourceCharCode.setEnabled(true);
        sourceName.setEnabled(true);
        destCharCode.setEnabled(true);
        destName.setEnabled(true);
        destSumm.setEnabled(true);
    }

    @Override
    public void disableControls() {
        sourceSumm.setEnabled(false);
        sourceCharCode.setEnabled(false);
        sourceName.setEnabled(false);
        destCharCode.setEnabled(false);
        destName.setEnabled(false);
        destSumm.setEnabled(false);
    }


    public static class FragmentFabric extends Fragment {
        private static DialogFragment dialogFragmentInstance;
        public static final String CURRENCY_LIST = "currency_list";
        public static DialogFragment newDialogInstance(ArrayList<CurrencyView> currencyList) {
            dialogFragmentInstance = new FloatingList();
            Bundle args = new Bundle();
            args.putParcelableArrayList(CURRENCY_LIST, (ArrayList<? extends Parcelable>) currencyList);
            dialogFragmentInstance.setArguments(args);
            return dialogFragmentInstance;
        }
    }

}
