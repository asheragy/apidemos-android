package org.cerion.apidemos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);


        //Setup recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        refresh();
    }


    private void refresh()
    {
        mAdapter.refresh( ListData.getList(1000) );
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private static final int RESOURCE_ID = R.layout.recycler_view_list_item;
        private List<ListData> mListData;

        protected class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View v) {
                super(v);
               textView = (TextView)v.findViewById(R.id.textView);
            }
        }

        public ListAdapter() {
            mListData = new ArrayList<>();
        }

        public void refresh(List<ListData> listData)
        {
            mListData.clear();
            mListData.addAll(listData);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(RESOURCE_ID, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ListData listData = mListData.get(position);
            holder.textView.setText(listData.mText);
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }

    }

}
