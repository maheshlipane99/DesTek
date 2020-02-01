package com.example.destek.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Product;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;
import com.example.destek.Utils.Common;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ProductAdapter.class.getSimpleName();
    protected List<Product> mOriginalData = new ArrayList<>();
    protected List<Product> mResultData = new ArrayList<>();

    protected Activity mActivity;
    private ItemFilter mFilter = new ItemFilter();
    OnRecyclerViewClick mOnRecyclerViewClick;

    public ProductAdapter(Activity activity, OnRecyclerViewClick mOnRecyclerViewClick) {
        mActivity = activity;
        this.mOnRecyclerViewClick = mOnRecyclerViewClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Product data = mResultData.get(position);
        try {
            final ViewHolder viewHolder = (ViewHolder) holder;

            if (data != null) {
                viewHolder.textName.setText(data.getName());
                viewHolder.textDate.setText(Common.getStringDate(data.getDate_added()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mResultData.size();
    }

    public void addItem(Product data) {
        mOriginalData.add(data);
        mResultData.add(data);
        int index = mOriginalData.indexOf(data);
        notifyItemInserted(index);
    }

    public void addAllItem(List<Product> data) {
        mOriginalData = data;
        mResultData = data;
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mOriginalData.remove(index);
        notifyItemRemoved(index);
    }

    public void removeItem(Product exam) {
        int index = mOriginalData.indexOf(exam);
        mOriginalData.remove(exam);
        notifyItemRemoved(index);
    }

    public Filter getFilter() {
        return mFilter;
    }


    public Product getItem(int index) {
        return mResultData.get(index);
    }


    public void replace(int i, Product Product) {
        mOriginalData.set(i, Product);
        notifyItemChanged(i);
    }

    public void removeAll() {
        mResultData.clear();
        mOriginalData.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textName,textDate;
        LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = (TextView)itemView.findViewById(R.id.textName);
            textDate = (TextView)itemView.findViewById(R.id.textDate);
            rootView = (LinearLayout)itemView.findViewById(R.id.rootView);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnRecyclerViewClick.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }


    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            int count = mOriginalData.size();

            final ArrayList<Product> tempFilterList = new ArrayList<Product>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = mOriginalData.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    tempFilterList.add(mOriginalData.get(i));
                }
            }

            results.values = tempFilterList;
            results.count = tempFilterList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mResultData.clear();
            mResultData = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }


}
