package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.Utils;
import android.content.Context;
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
        MA.findViewById(R.id.button_back).setVisibility(View.INVISIBLE);
        View view = inflater.inflate(R.layout.fragment_menu_main, container, false);
        OrderItems orderItems = new OrderItems(view, MA.orderedItemsList);
        LinearLayout mainLayout = view.findViewById(R.id.menu_entry);
        LayoutInflater li =  (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View wrapperView = null;
        assert li != null;

        orderItems.updateOrderItems();

        for (Map.Entry entry : MA.items.entrySet()) {

            final String key = entry.getKey().toString();
            CartItem val = ((List<CartItem>) entry.getValue()).get(0);
            wrapperView = li.inflate(R.layout.menu_entry_wrapper, container, false);
            mainLayout.addView(wrapperView);
            LinearLayout wrapperLayout = wrapperView.findViewWithTag("itemMenuWrapper");
            View itemView = li.inflate(R.layout.main_menu_entry, container, false);
            ImageView itemImg = itemView.findViewWithTag("itemImg");
            TextView itemTxt = itemView.findViewWithTag("itemTxt");

            int res = getResources().getIdentifier(
                    val.getCategoryImg(),
                    "drawable",
                    this.requireContext().getPackageName());

            if (res != 0)

                itemImg.setImageResource(res);

            itemImg.setClipToOutline(true);
            itemTxt.setText(val.getTitle());

            itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    openItemMenu(key);

                }

            } );

            wrapperLayout.addView(itemView);

        }



        view.findViewById(R.id.orderItemContainer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);

            }

        } );





        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


    }


    private void openItemMenu(String route){

        MA.route = route;
        MA.cartItems = Utils.getInstance().getMenuItems().get(route);
        NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

    }


}
