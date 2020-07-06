package android.grocer;

import android.grocer.data.CartItem;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;


class InfoBar {

    private View view;
    private LinkedHashMap<String, CartItem> orderedItemList;
    private Context context;
    private MainActivity MA;
    private Fragment fragment;

    InfoBar(View view, MainActivity MA, Context context, Fragment fragment) {

        this.MA = MA;
        this.orderedItemList = this.MA.orderedItemsList;
        this.view = view;
        this.context = context;
        this.fragment = fragment;
        updateOrderItems();

    }

    void updateOrderItems() {

        final HorizontalScrollView container = view.findViewById(R.id.info_bar_container);
        LinearLayout wrap = view.findViewById(R.id.info_bar_wrap);
        ImageView indicator = view.findViewById(R.id.shop_indicator);
        RelativeLayout bar = view.findViewById(R.id.info_bar_box);
        LinkedHashMap<String, Integer> items = new LinkedHashMap<>();
        final LinkedHashMap<String, String> routes = new LinkedHashMap<>();

        bar.removeAllViews();

        for (LinkedHashMap.Entry<String, CartItem> entry : orderedItemList.entrySet()) {

            CartItem item = entry.getValue();

            int qty = item.getQty();

            if (qty > 0) {

                Integer val = items.get(item.getTypeIcon());
                val = val != null ? val : 0;
                items.put(item.getTypeIcon(), item.getQty() + val);
                routes.put(item.getTypeIcon(), item.getType());

            }

        }

        indicator.setImageResource(items.entrySet().size() == 0 ? R.drawable.shop_off : R.drawable.shop_on);
        indicator.setBackground(ContextCompat.getDrawable(context, items.entrySet().size() == 0 ? R.drawable.shop_bg_off : R.drawable.shop_bg_on));
        container.setBackground(ContextCompat.getDrawable(context, items.entrySet().size() == 0 ? R.drawable.shop_bg_cart_off : R.drawable.shop_bg_cart_on));
        wrap.setBackground(ContextCompat.getDrawable(context, items.entrySet().size() == 0 ? R.drawable.info_bar_bg : R.drawable.info_bar_bg_on));

        int count = 0;
        int id = 0;

        for (LinkedHashMap.Entry<String, Integer> entry : items.entrySet()) {

            ImageView img = new ImageView(view.getContext());
            Integer val = entry.getValue();
            RelativeLayout rl = new RelativeLayout(view.getContext());
            final String key = entry.getKey();
            TextView txt = new TextView(view.getContext());

            try {

                InputStream is = context.getAssets().open(key);
                img.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            txt.setText(String.valueOf(val));

            img.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(36), dpToPx(36)));
            txt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(dpToPx(40), ViewGroup.LayoutParams.WRAP_CONTENT);
            param.addRule(count % 2 == 0 ? RelativeLayout.ALIGN_PARENT_TOP : RelativeLayout.ALIGN_PARENT_BOTTOM);

            if (id != 0)

                param.addRule(RelativeLayout.RIGHT_OF, id);

            param.setMargins(dpToPx(4), dpToPx(4), dpToPx(4),dpToPx(4));

            rl.setLayoutParams(param);
            id = View.generateViewId();
            rl.setId(id);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MA.route = routes.get(key);
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_Fragment_to_CatFragment);
                }
            });

            bar.addView(rl);

            count++;

        }
/*
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                container.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        },100);

*/

    }

    int dpToPx(int dp) {
        float density = view.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}
