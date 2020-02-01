package com.example.destek.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.destek.DB.tabels.Category;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = CategoryAdapter.class.getSimpleName();
    protected List<Category> mOriginalData = new ArrayList<>();
    protected List<Category> mResultData = new ArrayList<>();

    protected Activity mActivity;
    private ItemFilter mFilter = new ItemFilter();
    OnRecyclerViewClick mOnRecyclerViewClick;

    public CategoryAdapter(Activity activity, OnRecyclerViewClick mOnRecyclerViewClick) {
        mActivity = activity;
        this.mOnRecyclerViewClick = mOnRecyclerViewClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Category data = mResultData.get(position);
        try {
            final ViewHolder viewHolder = (ViewHolder) holder;

            if (data != null) {
                viewHolder.textName.setText(data.getName());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mResultData.size();
    }

    public void addItem(Category data) {
        mOriginalData.add(data);
        mResultData.add(data);
        int index = mOriginalData.indexOf(data);
        notifyItemInserted(index);
    }

    public void addAllItem(List<Category> data) {
        mOriginalData = data;
        mResultData = data;
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mOriginalData.remove(index);
        notifyItemRemoved(index);
    }

    public void removeItem(Category exam) {
        int index = mOriginalData.indexOf(exam);
        mOriginalData.remove(exam);
        notifyItemRemoved(index);
    }

    public Filter getFilter() {
        return mFilter;
    }


    public Category getItem(int index) {
        return mResultData.get(index);
    }


    public void replace(int i, Category Category) {
        mOriginalData.set(i, Category);
        notifyItemChanged(i);
    }

    public void removeAll() {
        mResultData.clear();
        mOriginalData.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textName;
        LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = (TextView)itemView.findViewById(R.id.textTitle);
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

            final ArrayList<Category> tempFilterList = new ArrayList<Category>(count);

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
            mResultData = (ArrayList<Category>) results.values;
            notifyDataSetChanged();
        }
    }


}
