package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.alan.Alan;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;

import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanState;
import com.alan.alansdk.events.EventCommand;
import com.alan.alansdk.events.EventOptions;
import com.alan.alansdk.events.EventParsed;
import com.alan.alansdk.events.EventRecognised;
import com.alan.alansdk.events.EventText;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenu newInstance(String param1, String param2) {
        MainMenu fragment = new MainMenu();
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
        // Inflate the layout for this fragment
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

        initializeAlanListener();

    }



    private void initializeAlanListener(){
        Alan.getInstance().registerCallback(new AlanCallback() {
            @Override
            public void onAlanStateChanged(@NonNull AlanState alanState) {
                super.onAlanStateChanged(alanState);
            }

            @Override
            public void onRecognizedEvent(EventRecognised eventRecognised) {
                super.onRecognizedEvent(eventRecognised);
            }

            @Override
            public void onParsedEvent(EventParsed eventParsed) {
                super.onParsedEvent(eventParsed);
            }

            @Override
            public void onOptionsReceived(EventOptions eventOptions) {
                super.onOptionsReceived(eventOptions);
            }

            @Override
            public void onCommandReceived(EventCommand eventCommand) {
                super.onCommandReceived(eventCommand);
                Log.d("STATE", "EVENT COMMAND");
                Log.d("STATE", String.valueOf(eventCommand));
                JSONObject alanCommand = Alan.getInstance().processAlanEventCommand(eventCommand);
                Log.d("STATE", String.valueOf(alanCommand));

                handleAlanVoiceCommand(alanCommand);
            }

            @Override
            public void onTextEvent(EventText eventText) {
                super.onTextEvent(eventText);
            }

            @Override
            public void onEvent(String event, String payload) {
                super.onEvent(event, payload);
            }

            @Override
            public void onError(String error) {
                super.onError(error);
            }
        });
    }

    private void handleAlanVoiceCommand(JSONObject alanCommand) {
        try {
            if (alanCommand != null) {
                String cmd;
                cmd = alanCommand.getString("command");
                Log.d("STATE", cmd);
                if ("navigation".equals(cmd)) {
                    Log.d("STATE", "SCREEN ");
                    String screen = alanCommand.getString("route");

                    NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                }
            } else {
                Alan.getInstance().playText(com.alan.actsoft.alan.data.Msgs.INVALID_RESPONSE);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }




}
