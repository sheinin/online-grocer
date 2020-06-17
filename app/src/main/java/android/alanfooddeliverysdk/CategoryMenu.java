package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;


public class CategoryMenu extends Fragment {

    public CategoryMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity MA1 = ((MainActivity) requireActivity());
        MA1.findViewById(R.id.button_back).setVisibility(View.INVISIBLE);
        final MainActivity MA = ((MainActivity) requireActivity());
        View view = inflater.inflate(R.layout.fragment_menu_category, container, false);
        final List<CartItem> cartItems = MA.items.get(MA.route);
        final OrderItems orderItems =  new OrderItems(view, MA.orderedItemsList);
        LinearLayout mainLayout = view.findViewById(R.id.menu_entry);
        LayoutInflater li =  (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View wrapperView;

        assert li != null;

        orderItems.updateOrderItems();

        assert cartItems != null;
        for (int i = 0, ix = cartItems.size(); i < ix; i++) {

                wrapperView = li.inflate(R.layout.menu_entry_wrapper, container, false);
                mainLayout.addView(wrapperView);

            String id = cartItems.get(i).getId();
            CartItem orderItem = MA.orderedItemsList.get(id);

            if(orderItem != null)

                cartItems.get(i).setQuantity(orderItem.getQuantity());

            LinearLayout wrapperLayout = wrapperView.findViewWithTag("itemMenuWrapper");
            View itemView = li.inflate(R.layout.category_menu_entry, container, false);
            ImageView itemImg = itemView.findViewWithTag("entry_img");
            TextView itemTitle = itemView.findViewWithTag("itemTitle");
            TextView itemPrice = itemView.findViewWithTag("itemPrice");
            ImageView itemAdd = itemView.findViewWithTag("itemAdd");
            //LinearLayout imgContainer = itemView.findViewWithTag("entry_img_container");
            final ImageView itemRemove = itemView.findViewWithTag("itemRemove");
            final TextView itemQuantity = itemView.findViewWithTag("itemQuantity");
            final int finalI = i;
            int quantity = cartItems.get(i).getQuantity();

            int res = getResources().getIdentifier(
                cartItems.get(i).getImg(),
                "drawable",
                this.requireContext().getPackageName());

            if (res != 0)

                itemImg.setImageResource(res);

            itemTitle.setText(cartItems.get(i).getTitle());
            String text = "$" + Math.round(cartItems.get(i).getPrice());
            itemPrice.setText(text);

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
                    itemQuantity.setText(String.format(Locale.getDefault(), "%d",cartItems.get(finalI).getQuantity()));
                    CartItem item = cartItems.get(finalI);
                    MA.orderedItemsList.put(item.getId(), item);
                    orderItems.updateOrderItems();

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
                    itemQuantity.setText(String.format(Locale.getDefault(), "%d",cartItems.get(finalI).getQuantity()));
                    CartItem item = cartItems.get(finalI);
                    MA.orderedItemsList.put(item.getId(), item);
                    if (quantity == 0)
                        MA.orderedItemsList.remove(item.getId());
                    orderItems.updateOrderItems();

                    }

            } );

            wrapperLayout.addView(itemView);


            //}

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
