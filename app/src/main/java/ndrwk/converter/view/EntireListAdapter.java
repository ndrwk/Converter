package ndrwk.converter.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ndrwk.converter.R;


class EntireListAdapter extends CommonListAdapter {

    private List<CurrencyView> mainListArray;

    EntireListAdapter(IOnListItemClick callback, List<CurrencyView> list) {
        super(callback);
        mainListArray = list;
    }

    @Override
    public CommonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new EntireListViewHolder(v, viewHolderClickCallback);
    }

    @Override
    public void onBindViewHolder(CommonListViewHolder holder, int position) {
        EntireListViewHolder entireListViewHolder = (EntireListViewHolder) holder;
        entireListViewHolder.position = position;
        String currencyName = "";
        int nominal = mainListArray.get(position).getNominal();
        if (nominal != 1)
            currencyName += nominal + " ";
        currencyName += mainListArray.get(position).getTxtName();
        entireListViewHolder.charCode.setText(mainListArray.get(position).getCharCode());
        entireListViewHolder.name.setText(currencyName);
    }

    @Override
    public int getItemCount() {
        return mainListArray.size();
    }


    private static class EntireListViewHolder extends CommonListViewHolder {

        TextView charCode;
        TextView name;

        EntireListViewHolder(View v, IOnViewHolderClick listener) {
            super(v, listener);
        }

        @Override
        void findControls(View v) {
            charCode = (TextView) v.findViewById(R.id.char_code_in_list);
            name = (TextView) v.findViewById(R.id.currency_name_in_list);
        }
    }
}