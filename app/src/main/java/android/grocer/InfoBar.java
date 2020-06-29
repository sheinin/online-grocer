package android.grocer;

import android.grocer.data.CartItem;

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
import java.util.LinkedHashMap;
import java.util.Objects;


class InfoBar {

    private View view;
    private LinkedHashMap<String, CartItem> orderedItemList;
    private Context context;

    InfoBar(View view, LinkedHashMap<String, CartItem> orderedItemList, Context context) {
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

                Integer val = items.get(item.getTypeIcon());
                val = val != null ? val : 0;
                items.put(item.getTypeIcon(), item.getQty() + val);

            }

        }

        ((ImageView) view.findViewById(R.id.shop_indicator)).setImageResource(items.entrySet().size() == 0 ? R.drawable.shop_off : R.drawable.shop_on);
        ((ImageView) view.findViewById(R.id.shop_indicator)).setBackground(ContextCompat.getDrawable(context, items.entrySet().size() == 0 ? R.drawable.shop_bg_off : R.drawable.shop_bg_on));
        view.findViewById(R.id.info_bar_container).setBackground(ContextCompat.getDrawable(context, items.entrySet().size() == 0 ? R.drawable.shop_bg_cart_off : R.drawable.shop_bg_cart_on));

        int count = 0;

        for (LinkedHashMap.Entry<String, Integer> entry : items.entrySet()) {

            ImageView img = new ImageView(view.getContext());
            Integer val = entry.getValue();
            RelativeLayout rl = new RelativeLayout(view.getContext());
            String key = entry.getKey();
            TextView txt = new TextView(view.getContext());
            ViewGroup.LayoutParams lp;

            try {

                Log.d("STATE", key);
                InputStream is = context.getAssets().open(key);
                img.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            txt.setText(String.valueOf(val));

            lp = new ViewGroup.LayoutParams(dpToPx(36), dpToPx(36));
            img.setLayoutParams(lp);
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            txt.setLayoutParams(lp);
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rl.setLayoutParams(lp);
            txt.setTextColor(Color.rgb(255,255,255));
            txt.setTextSize(12);
            txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
            int pad = dpToPx(3);
            txt.setPadding(pad, pad, pad, pad);
            txt.setBackground(ContextCompat.getDrawable(context, R.drawable.info_bar_qty_bg));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            txt.setLayoutParams(params);

            rl.addView(img);
            rl.addView(txt);

            pad = count % 2 == 0 ? dpToPx(3) : dpToPx(16);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(40), ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(dpToPx(2), pad, dpToPx(4),0);
            rl.setLayoutParams(param);
            container.addView(rl);

            count++;

        }

    }

    int dpToPx(int dp) {
        float density = view.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}
