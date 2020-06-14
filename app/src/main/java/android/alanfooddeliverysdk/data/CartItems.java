package android.alanfooddeliverysdk.data;

import android.alanfooddeliverysdk.data.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItems {

    public static List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Pepperoni", "pizza_pepperoni", 14.0f, "prn", "pizza", "pizza", "pizza_pepperoni", 0));
        cartItems.add(new CartItem("Margarita", "pizza_margarita", 10.0f, "mrg", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Cheese", "pizza_four_cheese", 10f, "4ch", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Hawaiian", "pizza_hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 0));


        return cartItems;

    }
}
