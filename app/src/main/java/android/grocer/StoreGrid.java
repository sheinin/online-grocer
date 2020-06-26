package android.grocer;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreGrid extends BaseAdapter{
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

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.store_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(name[position]);
            Resources resources = mContext.getResources();
            final int resourceId = resources.getIdentifier(image[position], "drawable",
                    mContext.getPackageName());
            //return resources.getDrawable(resourceId);
            imageView.setImageResource(resourceId);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}