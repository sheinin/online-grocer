package android.grocer;

import android.grocer.data.CartItem;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class Cart extends Fragment {

    public Cart() {
        // Required empty public constructor
    }


    CartItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        final MainActivity MA = ((MainActivity) requireActivity());

        RecyclerView recyclerView = view.findViewById(R.id.order_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CartItemAdapter(getActivity(), MA.orderedItemsList, Cart.this);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);




        LinkedHashMap<String, CartItem> items = MA.orderedItemsList;


        LayoutInflater li = (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mainLayout = view.findViewById(R.id.menu_entry);
        String cat = null;

        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Cart.this).navigate(R.id.action_CartFragment_to_MenuFragment);
            }
        });

        return view;
    }

}