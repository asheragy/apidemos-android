package org.cerion.apidemos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private static final String TAG = RecyclerViewActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int SIZE = 100;

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

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                mListData.set(msg.what,new ColorData());
                mAdapter.notifyDataSetChanged();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < 100; i++) {
                    mHandler.sendEmptyMessage(i);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void refresh()
    {
        List<ColorData> data = new ArrayList<>();
        for(int i = 0; i < SIZE; i++)
            data.add(null);

        mAdapter.refresh(data);
    }

    private List<ColorData> mListData;
    private Handler mHandler;

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private static final int RESOURCE_ID = R.layout.recycler_view_list_item;

        protected class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView image;

            public ViewHolder(View v) {
                super(v);
                textView = (TextView)v.findViewById(R.id.textView);
                image = (ImageView)v.findViewById(R.id.imageView);
            }
        }

        public ListAdapter() {
            mListData = new ArrayList<>();
        }

        public void refresh(List<ColorData> data)
        {
            mListData.clear();
            mListData.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(RESOURCE_ID, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            ColorData data = mListData.get(position);
            if (data != null) {
                holder.textView.setText(data.toString());
                holder.image.setImageBitmap(data.getBitmap(200,200));
            }

        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }




    }

}
