package android.grocer;

import android.content.res.Resources;
import android.grocer.data.CartItem;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainMenu extends Fragment {

    private MainActivity MA;

    public MainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MA = ((MainActivity) requireActivity());

        View view         = inflater.inflate(R.layout.menu_fragment, container, false);
        LayoutInflater li = (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout main = view.findViewById(R.id.menu_entry);
        InfoBar items  = new InfoBar(view, MA.orderedItemsList, getActivity());

        assert li != null;

        items.updateOrderItems();

        for (final Map.Entry<String, List<CartItem>> item : Objects.requireNonNull(MA.items.get(MA.store)).entrySet()) {

            final CartItem val = item.getValue().get(0);

            View vi         = li.inflate(R.layout.menu_item, container, false);
            View wrap       = li.inflate(R.layout.menu_item_container, container, false);
            ImageView img   = vi.findViewWithTag("menu_img");
            LinearLayout wl = wrap.findViewWithTag("menu_item_container");
            TextView txt    = vi.findViewWithTag("menu_txt");

            try {

                InputStream is = requireContext().getAssets().open(val.getCategoryImg());
                img.setImageBitmap(BitmapFactory.decodeStream(is));
                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            img.setClipToOutline(true);
            txt.setText(val.getCategory());

            vi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    openItemMenu(item.getKey());

                }

            } );

            wl.addView(vi);
            main.addView(wrap);

        }

        view.findViewById(R.id.info_bar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (MA.orderedItemsList.size() == 0)

                    Toast.makeText(MA.getApplicationContext(), "Your cart is empty.", Toast.LENGTH_SHORT).show();

                else

                    NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_MenuFragment_to_CartFragment);

            }

        } );

        MA.findViewById(R.id.header_text).setVisibility(View.INVISIBLE);
        MA.findViewById(R.id.button_back).setVisibility(View.VISIBLE);
        MA.findViewById(R.id.header_logo).setVisibility(View.VISIBLE);
        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_MenuFragment_to_StoreFragment);
            }
        });

        ((TextView) view.findViewById(R.id.page_title)).setText(MA.store);

        Resources resources = MA.getResources();
        final int resourceId = resources.getIdentifier(MA.stores.get(MA.store), "drawable",
                MA.getPackageName());
        ((ImageView)MA.findViewById(R.id.header_logo)).setImageResource(resourceId);

        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }


    private void openItemMenu(String route){

        MA.route = route;
        MA.cartItems = Objects.requireNonNull(MA.items.get(MA.store)).get(route);
        NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_MenuFragment_to_CatFragment);

    }


}
