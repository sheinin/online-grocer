package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.adapter.CartAdapter;
import android.alanfooddeliverysdk.data.CartItem;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View cartView;


    public Cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        ListView cartList = cartView.findViewById(R.id.cart_list);
        CartAdapter cartAdapter = new CartAdapter(getContext(), R.layout.cart_item, getCartItems());
        cartList.setAdapter(cartAdapter);

        return cartView;
    }

    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Pepperoni", "pizza-pepperoni", 14.0f, "prn", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Margarita", "pizza-margarita", 10.0f, "mrg", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Cheese", "pizza-four-cheese", 10f, "4ch", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 0));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 0));


        return cartItems;


    }
}
