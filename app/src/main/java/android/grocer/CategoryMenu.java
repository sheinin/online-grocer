package android.grocer;

import android.grocer.data.CartItem;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class CategoryMenu extends Fragment {

    public CategoryMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cat_fragment, container, false);

        final MainActivity MA = ((MainActivity) requireActivity());
        final List<CartItem> cartItems = MA.items.get(MA.store).get(MA.route);
        final InfoBar infoBar = new InfoBar(view, MA.orderedItemsList, getActivity());
        
        LayoutInflater li = (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mainLayout = view.findViewById(R.id.menu_entry);
        String cat = null;

        assert li != null;
        assert cartItems != null;

        infoBar.updateOrderItems();

        for (int i = 0, ix = cartItems.size(); i < ix; i++) {

            final CartItem val  = cartItems.get(i);
            final CartItem item = MA.orderedItemsList.get(val.getId());

            cat = val.getCategory();
            
            View vi   = li.inflate(R.layout.cat_item, container, false);
            View wrap = li.inflate(R.layout.item_wrap, container, false);

            final LinearLayout qty = vi.findViewWithTag("cat_qty");
            final TextView qtx = vi.findViewWithTag("cat_qty_txt");
            final RelativeLayout rm = vi.findViewWithTag("cat_rm");

            ImageView add   = vi.findViewWithTag("cat_add");
            ImageView img   = vi.findViewWithTag("cat_img");
            LinearLayout iw = wrap.findViewWithTag("item_wrap");
            TextView price  = vi.findViewWithTag("cat_price");
            TextView title  = vi.findViewWithTag("cat_title");

            if (item != null)

                val.setQuantity(item.getQty());

            if (val.getQty() > 0) {

                qtx.setText(String.valueOf(val.getQty()));
                rm.setVisibility(View.VISIBLE);
                qty.setVisibility(View.VISIBLE);

            }

            try {

                InputStream is = requireContext().getAssets().open(val.getImg());
                img.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            price.setText(val.getPriceAsString());
            title.setText(val.getTitle());

            vi.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            int[][] colors = {

                { 0xff666600, 0xffbbbb00 },
                { 0xff777700, 0xffcccc00 },
                { 0xff888800, 0xffeeee00 },
                { 0xff999900, 0xffeeee00 },
                { 0xffaaaa00, 0xffffff00 }

            };

            int idx = val.getPrice() / 2;

            if (idx > 4)

                idx = 4;

            int[] color = colors[idx];

            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BL_TR, color);
            gd.setShape(GradientDrawable.OVAL);
            gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            gd.setCornerRadii(new float[] { 5, 5, 5, 5, 0, 0, 0, 0 });
            price.setBackground(gd);

            add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    val.setQuantity(val.getQty() + 1);

                    if (!MA.orderedItemsList.containsKey(val.getId()))

                        MA.orderedItemsList.put(val.getId(), val);

                    qtx.setText(val.getQtyAsString());
                    qty.setVisibility(View.VISIBLE);
                    rm.setVisibility(View.VISIBLE);
                    infoBar.updateOrderItems();

                }

            } );

            rm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    assert item != null;

                    int quantity = val.getQty() - 1;

                    if (quantity == 0) {

                        qty.setVisibility(View.INVISIBLE);
                        rm.setVisibility(View.INVISIBLE);
                        MA.orderedItemsList.remove(val.getId());

                    }

                    val.setQuantity(quantity);
                    qtx.setText(val.getQtyAsString());
                    infoBar.updateOrderItems();

                }

            } );

            iw.addView(vi);
            mainLayout.addView(wrap);

        }

        view.findViewById(R.id.info_bar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(CategoryMenu.this).navigate(R.id.action_CatFragment_to_CartFragment);

            }

        } );

        MA.findViewById(R.id.button_back).setVisibility(View.VISIBLE);
        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CategoryMenu.this).navigate(R.id.action_CatFragment_to_MenuFragment);
            }
        });
        ((TextView) view.findViewById(R.id.page_title)).setText(cat);

        return view;
    }

}
