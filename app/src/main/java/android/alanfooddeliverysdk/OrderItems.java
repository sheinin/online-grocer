package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.Utils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class OrderItems {

    private View view;

    private List<CartItem> cartItems;
    private Map<String, CartItem> orderedItemList;

    OrderItems(View view, List<CartItem> cartItems, Map<String, CartItem> orderedItemList) {
        this.orderedItemList = orderedItemList;
        this.cartItems = cartItems;
        this.view = view;
        updateOrderItems();
    }

    void updateOrderItems() {

        RelativeLayout container = view.findViewById(R.id.orderItemContainer);
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
        map.put("street food", 0);
        map.put("dessert", 0);
        map.put("drink", 0);

        for (Map.Entry<String, CartItem> entry : orderedItemList.entrySet()) {

            CartItem item = entry.getValue();

            int qty = item.getQuantity();

            if (qty > 0) {

                String key = item.getType();
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

        val = map.get("street food");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        streetTxt.setText(str);
        show = show || val > 0;
        streetImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        streetTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);

        val = map.get("dessert");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        dessertTxt.setText(str);
        show = show || val > 0;
        dessertImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        dessertTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);

        val = map.get("drink");
        val = val != null ? val : 0;
        str = String.format(Locale.getDefault(), "%d", val);
        drinkTxt.setText(str);
        show = show || val > 0;
        drinkImg.setVisibility(val == 0 ? View.GONE : View.VISIBLE);
        drinkTxt.setVisibility(val == 0 ? View.GONE : View.VISIBLE);

        container.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

    }

}
