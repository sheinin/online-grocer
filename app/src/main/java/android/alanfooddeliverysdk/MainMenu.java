package android.alanfooddeliverysdk;

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

    public MainMenu() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alanButton = ((MainActivity) requireActivity()).alanButton;

        alanButton.registerCallback(new AlanCallback() {

            @Override
            public void onCommandReceived(EventCommand eventCommand) {
                super.onCommandReceived(eventCommand);

                try {

                    JSONObject commandObject = eventCommand.getData();
                    commandObject.getJSONObject("data");
                    JSONObject alanCommand = commandObject.getJSONObject("data");
                    String cmd = alanCommand.getString("command");

                    if ("navigation".equals(cmd)) {

                       // String screen = alanCommand.getString("route");

                        NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                    }

                } catch (Exception e) {
                    alanButton.playText("Invalid response, Some thing went wrong.");
                    e.printStackTrace();
                }


            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_menu, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.pizzaBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

            }

        } );

    }



}
