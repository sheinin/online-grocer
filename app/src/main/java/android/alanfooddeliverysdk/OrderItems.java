package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class OrderItems {

    private View view;

    private List<CartItem> cartItems;

    OrderItems(View view, List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.view = view;
    }

    void updateOrderItems() {

        LinearLayout container = view.findViewById(R.id.orderItemContainer);
        ImageView pizzaImg = view.findViewById(R.id.orderPizzaImg);
        TextView pizzaTxt = view.findViewById(R.id.orderPizzaTxt);
        ImageView streetImg = view.findViewById(R.id.orderStreetImg);
        TextView streetTxt = view.findViewById(R.id.orderStreetTxt);
        ImageView dessertImg = view.findViewById(R.id.orderDessertImg);
        TextView dessertTxt = view.findViewById(R.id.orderDessertTxt);
        ImageView drinkImg = view.findViewById(R.id.orderDrinksImg);
        TextView drinkTxt = view.findViewById(R.id.orderDrinksTxt);
        Map<String, Integer> map = new HashMap<>();
        String str;
        Integer val;
        boolean show;
        map.put("pizza", 0);
        map.put("street", 0);
        map.put("desserts", 0);
        map.put("drink", 0);

        for (int i = 0, ix = cartItems.size(); i < ix; i++) {

            int qty = cartItems.get(i).getQuantity();

            if (qty > 0) {

                String key = cartItems.get(i).getType();
                val = map.get(key);
                val = val != null ? val : 0;
                map.put(key, val + qty);

            }

        }

        val = map.get("pizza");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        pizzaTxt.setText(str);
        show = val > 0;
        pizzaImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        pizzaTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);

        val = map.get("street");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        streetTxt.setText(str);
        show = show || val > 0;
        streetImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        streetTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);


        val = map.get("desserts");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        dessertTxt.setText(str);
        show = show || val > 0;
        dessertImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        dessertTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);


        container.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

    }

}
