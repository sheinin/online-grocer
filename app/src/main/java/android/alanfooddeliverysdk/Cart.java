package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.adapter.CartAdapter;
import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.Utils;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart extends Fragment implements CartAdapter.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity MA;

    private View cartView;

    CartAdapter cartAdapter;

    private List<CartItem> orderItems;

    Integer totalItems = 0;

    Float totalAmount = 0f;


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
        MA = ((MainActivity) requireActivity());

        cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        Toolbar toolbar = MA.findViewById(R.id.id_app_toolbar);

        this.orderItems = this.MA.getOrderedItems();
        RecyclerView cartList = cartView.findViewById(R.id.cart_list);
        cartAdapter = new CartAdapter(this.orderItems, R.layout.cart_item);
        cartList.setAdapter(cartAdapter);
        cartAdapter.setOnItemClickListener(this);
        cartList.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        //Setting item divider
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        cartList.addItemDecoration(itemDecoration);
        calcItemsAndTotal();
        toolbar.findViewById(R.id.button_back).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Cart.this).navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });
        toolbar.findViewById(R.id.button_action).setVisibility(View.INVISIBLE);

        cartView.findViewById(R.id.finish_order).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MA.cartItems.clear();
                MA.orderedItemsList.clear();
                NavHostFragment.findNavController(Cart.this).navigate(R.id.action_ThirdFragment_to_FourthFragment);
            }
        });

        return cartView;
    }

    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Pepperoni Pepperoni", "pizza-pepperoni", 14.0f, "prn", "pizza", "pizza", "pizza-pepperoni", 10));
        cartItems.add(new CartItem("Margarita", "pizza-margarita", 10.0f, "mrg", "pizza", "pizza", "pizza-pepperoni", 2));
        cartItems.add(new CartItem("Cheese", "pizza-four-cheese", 10f, "4ch", "pizza", "pizza", "pizza-pepperoni", 3));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 4));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 5));
        cartItems.add(new CartItem("Hawaiian", "pizza-hawaii", 10f, "haw", "pizza", "pizza", "pizza-pepperoni", 8));

        return cartItems;
    }

    @Override
    public void addItem(View itemView, int position, int count) {
        if(this.orderItems.size() > 0) {
            Integer qty = this.orderItems.get(position).getQuantity();
            String id = this.orderItems.get(position).getId();
            qty += count;
            this.orderItems.get(position).setQuantity(qty);
            this.MA.orderedItemsList.put(id, this.orderItems.get(position));
            this.cartAdapter.notifyItemChanged(position);
            calcItemsAndTotal();
        }
    }

    @Override
    public void removeItem(View itemView, int position, int count) {
        if(this.orderItems.size() > 0) {
            Integer qty = this.orderItems.get(position).getQuantity();
            String id = this.orderItems.get(position).getId();
            if (qty > 0) {
                qty -= count;
                this.orderItems.get(position).setQuantity(qty);
                this.cartAdapter.notifyItemChanged(position);
                this.MA.orderedItemsList.put(id, this.orderItems.get(position));
                calcItemsAndTotal();
                if (qty == 0) {
                    this.orderItems.remove(position);
                    this.cartAdapter.deleteItem(position);
                    this.MA.orderedItemsList.remove(id);
                }
            }
        }
    }

    /**
     * This method calculates the number of items and total amount of the Cart items.
     */
    private void calcItemsAndTotal(){
        totalItems = 0;
        totalAmount = 0f;
        for(CartItem item : MA.orderedItemsList.values()){
            totalItems += item.getQuantity() ;
            totalAmount += (item.getPrice() * item.getQuantity());
        }
        setItemsAndTotal();
    }

    //This method updates the title with total items and total amount.
    private void setItemsAndTotal(){
        TextView title = cartView.findViewById(R.id.cart_title);
        title.setText("You have added " + totalItems + " items, $" + totalAmount + " total");
    }
}
