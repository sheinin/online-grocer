package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;

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
import androidx.navigation.fragment.NavHostFragment;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class MainMenu extends Fragment {

    private MainActivity MA;

    public MainMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MA = ((MainActivity) requireActivity());

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        LayoutInflater li = (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout main = view.findViewById(R.id.menu_entry);
        OrderItems items  = new OrderItems(view, MA.orderedItemsList);

        assert li != null;

        items.updateOrderItems();

        for (final Map.Entry<String, List<CartItem>> item : MA.items.entrySet()) {

            final CartItem val = item.getValue().get(0);

            View vi         = li.inflate(R.layout.menu_item, container, false);
            View wrap       = li.inflate(R.layout.item_wrap, container, false);
            ImageView img   = vi.findViewWithTag("menu_img");
            LinearLayout wl = wrap.findViewWithTag("item_wrap");
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

        view.findViewById(R.id.orderItemContainer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);

            }

        } );

        MA.findViewById(R.id.button_back).setVisibility(View.INVISIBLE);

        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


    }


    private void openItemMenu(String route){

        MA.route = route;
        MA.cartItems = MA.items.get(route);//Utils.getInstance().getMenuItems().get(route);
        NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

    }


}
