package ndrwk.converter.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import ndrwk.converter.R;


public class FloatingList extends DialogFragment implements CommonListAdapter.IOnListItemClick  {

    @Override
    public void onClick(int pos) {
        eventCallback.dialogListClickCallback(pos);
        dismiss();
    }

    interface IOnDialogListItemClick {
        void dialogListClickCallback(int pos);
    }

    private IOnDialogListItemClick eventCallback;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<CurrencyView> currencyViewList = getArguments().getParcelableArrayList(MainActivity.FragmentFabric.CURRENCY_LIST);
        CommonListAdapter listAdapter = new EntireListAdapter(this, currencyViewList);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.entirelist_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(listAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        eventCallback = (IOnDialogListItemClick) context;
    }

}