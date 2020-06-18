package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        final List<CartItem> cartItems = MA.items.get(MA.route);
        final OrderItems orderItems = new OrderItems(view, MA.orderedItemsList);

        LayoutInflater li = (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mainLayout = view.findViewById(R.id.menu_entry);

        assert li != null;
        assert cartItems != null;

        orderItems.updateOrderItems();

        for (int i = 0, ix = cartItems.size(); i < ix; i++) {

            final CartItem val  = cartItems.get(i);
            final CartItem item = MA.orderedItemsList.get(val.getId());

            View vi   = li.inflate(R.layout.cat_item, container, false);
            View wrap = li.inflate(R.layout.item_wrap, container, false);

            final TextView qty = vi.findViewWithTag("cat_qty");
            final ImageView rm = vi.findViewWithTag("cat_rm");

            ImageView add   = vi.findViewWithTag("cat_add");
            ImageView img   = vi.findViewWithTag("cat_img");
            LinearLayout iw = wrap.findViewWithTag("item_wrap");
            TextView  price = vi.findViewWithTag("cat_price");
            TextView  title = vi.findViewWithTag("cat_title");

            if (item != null)

                val.setQuantity(item.getQty());

            if (val.getQty() > 0) {

                qty.setText(String.valueOf(val.getQty()));
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

            price.setText(val.getPrice());
            title.setText(val.getTitle());

            vi.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    qty.setText(val.getQtyAsString());
                    qty.setVisibility(View.VISIBLE);
                    rm.setVisibility(View.VISIBLE);
                    val.setQuantity(val.getQty() + 1);
                    MA.orderedItemsList.put(val.getId(), val);
                    orderItems.updateOrderItems();

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

                qty.setText(val.getQtyAsString());
                val.setQuantity(quantity);
                orderItems.updateOrderItems();

                    }

            } );

            iw.addView(vi);
            mainLayout.addView(wrap);

        }

        view.findViewById(R.id.orderItemContainer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(CategoryMenu.this).navigate(R.id.action_SecondFragment_to_ThirdFragment);

            }

        } );

        MA.findViewById(R.id.button_back).setVisibility(View.VISIBLE);
        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CategoryMenu.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        return view;
    }

}
