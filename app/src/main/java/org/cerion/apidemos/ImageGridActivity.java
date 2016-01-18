package org.cerion.apidemos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Map;
import java.util.WeakHashMap;

/* - Example of large grid of high res images which would exceed memory if not handled correctly
   - Images simulated by large single color bitmaps

    //TODO review http://developer.android.com/training/displaying-bitmaps/display-bitmap.html
    Future: Anyway to improve, weakhashmap does not seem to do much here
      RecyclerView, if there is anything different to demonstrate there
 */
public class ImageGridActivity extends Activity {

    private static final String TAG = ImageGridActivity.class.getSimpleName();
    private static final int INITIAL_SIZE = 200;
    private static final int BITMAP_DIMS = 1000; //3 bytes per pixel, so 1000x1000 is about 3mb

    private GridView mGridView;
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_grid_activity);

        mGridView = (GridView)findViewById(R.id.gridView);

        mAdapter = new GridAdapter(INITIAL_SIZE);
        mGridView.setAdapter(mAdapter);
    }

    private class GridAdapter extends ArrayAdapter<ColorData> {

        private static final int RES = R.layout.color_grid_item;
        private Map<String,Bitmap> mImageMap = new WeakHashMap<>();

        public GridAdapter(int size) {
            super(ImageGridActivity.this, RES);

            for(int i = 0; i < size; i ++)
                this.add(new ColorData() );
        }

        private class ViewHolder {
            TextView mText;
            ImageView mImage;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridView grid = (GridView) parent;
            int width = grid.getColumnWidth(); //for setting cells to be square shaped

            ColorData color = getItem(position);
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(RES, parent, false);
                convertView.setLayoutParams(new GridView.LayoutParams(width, width));

                viewHolder = new ViewHolder();
                viewHolder.mText = (TextView) convertView.findViewById(R.id.textView);
                viewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mText.setText(color.toString());


            Bitmap bmp;
            String key = color.toString();
            if (mImageMap.containsKey(key)) {
                bmp = mImageMap.get(key);
            }
            else {
                bmp = Bitmap.createScaledBitmap( color.getBitmap(BITMAP_DIMS,BITMAP_DIMS) , width, width, false);
                mImageMap.put(key,bmp);
            }
            viewHolder.mImage.setImageBitmap(bmp);

            return convertView;
        }


    }

}
