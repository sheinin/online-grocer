package android.grocer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class
StoreGrid extends BaseAdapter{
    private Context mContext;
    private final String[] name;
    private final String[] image;

    public StoreGrid(Context c, String[] name, String[] image ) {
        mContext = c;
        this.image = image;
        this.name = name;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            assert inflater != null;

            grid = inflater.inflate(R.layout.store_grid, parent, false);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(name[position]);

            try {

                InputStream is = mContext.getAssets().open(image[position]);
                imageView.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}