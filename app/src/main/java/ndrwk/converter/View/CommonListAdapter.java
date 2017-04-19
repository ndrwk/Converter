package ndrwk.converter.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;


abstract class CommonListAdapter extends RecyclerView.Adapter<CommonListAdapter.CommonListViewHolder>{

    interface IOnListItemClick {
        void onClick(int pos);
    }

    interface IOnViewHolderClick {
        void onListItemClick(View v, int position);
    }

    private IOnListItemClick adapterListener;

    IOnViewHolderClick viewHolderClickCallback = new IOnViewHolderClick() {
        @Override
        public void onListItemClick(View v, int position) {
            adapterListener.onClick(position);
        }
    };

    CommonListAdapter(IOnListItemClick callback) {
        adapterListener = callback;
    }

    abstract static class CommonListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position;
        private IOnViewHolderClick clickListener;

        CommonListViewHolder(View v, IOnViewHolderClick listener) {
            super(v);
            clickListener = listener;
            findControls(v);
            v.setOnClickListener(this);
        }

        abstract void findControls (View v);

        @Override
        public void onClick(View v) {
            clickListener.onListItemClick(v, position);
        }

    }
}