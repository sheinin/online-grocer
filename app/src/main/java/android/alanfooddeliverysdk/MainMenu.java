package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.Utils;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.events.EventCommand;
import org.json.JSONObject;

public class MainMenu extends Fragment {

    private AlanButton alanButton;
    private MainActivity MA;

    public MainMenu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MA = ((MainActivity) requireActivity());
        alanButton = MA.alanButton;

        alanButton.registerCallback(new AlanCallback() {

            @Override
            public void onCommandReceived(EventCommand eventCommand) {
                super.onCommandReceived(eventCommand);

                try {

                    JSONObject commandObject = eventCommand.getData();
                    commandObject.getJSONObject("data");
                    JSONObject alanCommand = commandObject.getJSONObject("data");
                    String cmd = alanCommand.getString("command");

                    if ("navigation".equals(cmd))

                        openItemMenu(alanCommand.getString("route"));

                } catch (Exception e) {

                    alanButton.playText("Invalid response, Some thing went wrong.");
                    e.printStackTrace();

                }


            }

        });

        MA.findViewById(R.id.button_action).setVisibility(View.INVISIBLE);
        MA.findViewById(R.id.button_back).setVisibility(View.INVISIBLE);

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        new OrderItems(view, MA.orderedItemsList);

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


        view.findViewById(R.id.pizzaBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openItemMenu("pizza");

            }

        } );

        view.findViewById(R.id.dessertsBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openItemMenu("dessert");

            }

        } );

        view.findViewById(R.id.drinksBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openItemMenu("drink");

            }

        } );

        view.findViewById(R.id.streetFoodBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openItemMenu("street food");

            }

        } );

    }


    private void openItemMenu(String route){

        MA.route = route;
        MA.cartItems = Utils.getInstance().getMenuItems().get(route);
        NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

    }


}
