package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.CartItems;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemMenu extends Fragment {

    private MainActivity MA;

    public ItemMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MA = ((MainActivity) requireActivity());

        View view = inflater.inflate(R.layout.fragment_item_menu, container, false);
//        final List<CartItem> cartItems = CartItems.getCartItems();
        final List<CartItem> cartItems = MA.cartItems;
        LinearLayout mainLayout = view.findViewById(R.id.itemMenuCart);
        LayoutInflater li =  (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View wrapperView = null;
        int count = 0;

        assert li != null;

        for (int i = 0, ix = cartItems.size(); i < ix;  i++) {

            if (MA.route.equals(cartItems.get(i).getType())) {

                if (count % 2 == 0) {

                    wrapperView = li.inflate(R.layout.menu_item_view_wrapper, null);
                    mainLayout.addView(wrapperView);

                }

                LinearLayout wrapperLayout = wrapperView.findViewWithTag("itemMenuWrapper");
                View itemView = li.inflate(R.layout.menu_item_view, null);
                ImageView itemImg = itemView.findViewWithTag("itemImg");
                TextView itemTitle = itemView.findViewWithTag("itemTitle");
                TextView itemPrice = itemView.findViewWithTag("itemPrice");
                ImageView itemAdd = itemView.findViewWithTag("itemAdd");
                final ImageView itemRemove = itemView.findViewWithTag("itemRemove");
                final TextView itemQuantity = itemView.findViewWithTag("itemQuantity");
                final int finalI = i;
                int quantity = cartItems.get(i).getQuantity();
                LinearLayout itemContainer = itemView.findViewWithTag("itemContainer");

                if (count % 2 == 0)
                    itemContainer.setPadding(10,10,5,10);
                else
                    itemContainer.setPadding(5,10,10,10);

                int res = getResources().getIdentifier(
                        cartItems.get(i).getImg(),
                        "drawable",
                        this.getContext().getPackageName());

                if (res != 0)

                    itemImg.setImageResource(res);

                itemTitle.setText(cartItems.get(i).getTitle());
                itemPrice.setText("$" + Math.round(cartItems.get(i).getPrice()));


                if (quantity > 0) {
                    itemQuantity.setText(String.valueOf(quantity));
                    itemRemove.setVisibility(View.VISIBLE);
                    itemQuantity.setVisibility(View.VISIBLE);
                }
                itemView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f));


                itemAdd.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        itemRemove.setVisibility(View.VISIBLE);
                        itemQuantity.setVisibility(View.VISIBLE);
                        cartItems.get(finalI).setQuantity(cartItems.get(finalI).getQuantity() + 1);
                        itemQuantity.setText(cartItems.get(finalI).getQuantity().toString());

                    }

                } );

                itemRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        int quantity = cartItems.get(finalI).getQuantity() - 1;

                        if (quantity == 0) {

                            itemRemove.setVisibility(View.INVISIBLE);
                            itemQuantity.setVisibility(View.INVISIBLE);

                        }

                        cartItems.get(finalI).setQuantity(quantity);
                        itemQuantity.setText(cartItems.get(finalI).getQuantity().toString());

                    }

                } );

                wrapperLayout.addView(itemView);

                count++;

            }

        }

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(ItemMenu.this).navigate(R.id.action_SecondFragment_to_FirstFragment);

            }

        } );
        return view;
    }

}
