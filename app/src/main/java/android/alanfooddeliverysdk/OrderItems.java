package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

class OrderItems {

    private View view;
    private LinkedHashMap<String, CartItem> orderedItemList;
    private Context context;

    OrderItems(View view, LinkedHashMap<String, CartItem> orderedItemList, Context context) {
        this.orderedItemList = orderedItemList;
        this.view = view;
        this.context = context;
        updateOrderItems();

    }

    void updateOrderItems() {

        LinearLayout container = view.findViewById(R.id.info_bar_container);
        LinkedHashMap<String, Integer> items = new LinkedHashMap<>();

        container.removeAllViews();
        for (LinkedHashMap.Entry<String, CartItem> entry : orderedItemList.entrySet()) {

            CartItem item = entry.getValue();

            int qty = item.getQty();

            if (qty > 0) {

                Integer val = items.get(item.getType());
                val = val != null ? val : 0;
                items.put(item.getType(), item.getQty() + val);

            }

        }

        ((ImageView) view.findViewById(R.id.shop_indicator)).setImageResource(items.entrySet().size() == 0 ? R.drawable.shop_off : R.drawable.shop_on);

        int count = 0;

        for (LinkedHashMap.Entry<String, Integer> entry : items.entrySet()) {

            ImageView img = new ImageView(view.getContext());
            Integer val = entry.getValue();
            RelativeLayout rl = new RelativeLayout(view.getContext());
            String key = entry.getKey();
            TextView txt = new TextView(view.getContext());
            ViewGroup.LayoutParams lp;

            try {

                InputStream is = context.getAssets().open(key + ".png");
                img.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            int pad = count % 2 == 0 ? 25 : 90;

            txt.setText(String.valueOf(val));
            lp = new ViewGroup.LayoutParams(100, 100);
            img.setLayoutParams(lp);
            lp = new ViewGroup.LayoutParams(40, 40);
            txt.setLayoutParams(lp);
            lp = new ViewGroup.LayoutParams(110, ViewGroup.LayoutParams.WRAP_CONTENT);
            rl.setLayoutParams(lp);
            txt.setTextColor(Color.rgb(255,255,255));
            txt.setTextSize(12);
            txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
            txt.setPadding(7,7,5,0);
            txt.setBackground(ContextCompat.getDrawable(context, R.drawable.info_bar_qty_bg));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            txt.setLayoutParams(params);

            count++;

            rl.addView(img);
            rl.addView(txt);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(110, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(25, pad,24,0);
            rl.setLayoutParams(param);
            container.addView(rl);

        }

    }

}
