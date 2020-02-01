package com.example.destek.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.destek.DB.tabels.Variant;
import com.example.destek.DB.tabels.Variant;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VariantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = VariantAdapter.class.getSimpleName();
    protected List<Variant> mOriginalData = new ArrayList<>();
    protected List<Variant> mResultData = new ArrayList<>();

    protected Activity mActivity;
    private ItemFilter mFilter = new ItemFilter();
    OnRecyclerViewClick mOnRecyclerViewClick;

    public VariantAdapter(Activity activity, OnRecyclerViewClick mOnRecyclerViewClick) {
        mActivity = activity;
        this.mOnRecyclerViewClick = mOnRecyclerViewClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_variant,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Variant data = mResultData.get(position);
        try {
            final ViewHolder viewHolder = (ViewHolder) holder;

            if (data != null) {
                viewHolder.textColor.setText(data.getColor());
                viewHolder.textSize.setText(data.getSize());
                viewHolder.textPrice.setText("Rs."+data.getPrice());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mResultData.size();
    }

    public void addItem(Variant data) {
        mOriginalData.add(data);
        mResultData.add(data);
        int index = mOriginalData.indexOf(data);
        notifyItemInserted(index);
    }

    public void addAllItem(List<Variant> data) {
        mOriginalData = data;
        mResultData = data;
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mOriginalData.remove(index);
        notifyItemRemoved(index);
    }

    public void removeItem(Variant exam) {
        int index = mOriginalData.indexOf(exam);
        mOriginalData.remove(exam);
        notifyItemRemoved(index);
    }

    public Filter getFilter() {
        return mFilter;
    }


    public Variant getItem(int index) {
        return mResultData.get(index);
    }


    public void replace(int i, Variant Variant) {
        mOriginalData.set(i, Variant);
        notifyItemChanged(i);
    }

    public void removeAll() {
        mResultData.clear();
        mOriginalData.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textColor,textSize,textPrice;
        LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);

            textColor = (TextView)itemView.findViewById(R.id.textColor);
            textSize = (TextView)itemView.findViewById(R.id.textSize);
            textPrice = (TextView)itemView.findViewById(R.id.textPrice);
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

            final ArrayList<Variant> tempFilterList = new ArrayList<Variant>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = mOriginalData.get(i).getColor();
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
            mResultData = (ArrayList<Variant>) results.values;
            notifyDataSetChanged();
        }
    }


}
