package android.grocer;

import android.content.Intent;
import android.grocer.data.CartItem;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Store extends Fragment {

    private MainActivity MA;

    GridView grid;

    public Store() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MA = ((MainActivity) requireActivity());
        MA.orderedItemsList = new LinkedHashMap<>();

        View view = inflater.inflate(R.layout.store_fragment, container, false);

        int count = 0;
        int qty = MA.stores.size();
        final String[] name = new String[qty];
        String[] image = new String[qty];

        for (final Map.Entry<String, String> store : MA.stores.entrySet()) {
            name[count] = store.getKey();
            image[count] = store.getValue();
            count++;
        }

        StoreGrid adapter = new StoreGrid(view.getContext(), name, image);
        grid= view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                MA.store = name[+ position];
                NavHostFragment.findNavController(Store.this).navigate(R.id.action_StoreFragment_to_MenuFragment);

            }
        });

        ((ImageView)MA.findViewById(R.id.header_logo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.snack_title), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snack_action), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://js2dx.com"));
                                startActivity(browserIntent);
                            }
                        }).show();

            }
        });

        MA.findViewById(R.id.button_back).setVisibility(View.INVISIBLE);
        ((ImageView)MA.findViewById(R.id.header_logo)).setImageResource(R.drawable.info);

        return view;
    }

}