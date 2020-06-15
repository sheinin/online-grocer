package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.CartItems;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class OrderItems {

    private View view;

    private List<CartItem> cartItems;

    public OrderItems(View view, List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.view = view;
    }

    public void addItem() {

        for (int i = 0, ix = cartItems.size(); i < ix; i++) {
            Log.d("STATUS",cartItems.get(i).getQuantity().toString());

        }
    }

}
