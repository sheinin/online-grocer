package android.alanfooddeliverysdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanConfig;
import com.alan.alansdk.AlanState;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.events.EventOptions;
import com.alan.alansdk.events.EventParsed;
import com.alan.alansdk.events.EventRecognised;
import com.alan.alansdk.events.EventText;

import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.CartItems;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AlanButton alanButton;
    String route;
    List<CartItem> cartItems = new ArrayList<>();

    Map<String, CartItem> orderedItemsList = new HashMap<String, CartItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.id_app_toolbar);
        setSupportActionBar(toolbar);

        alanButton = findViewById(R.id.alan_button);
        AlanConfig config = AlanConfig.builder()
                .setProjectId("b983d67db70894604d0bf347580da6fd2e956eca572e1d8b807a3e2338fdd0dc/prod")
                .build();
        alanButton.initWithConfig(config);

        alanButton.registerCallback(new AlanCallback() {
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


    /**
     * This method gets the ordered items
     * @return
     */
    public List<CartItem> getOrderedItems(){
        List<CartItem> orderedItems = new ArrayList<CartItem>();
        if(orderedItemsList.size() > 0) {
                orderedItems.addAll(orderedItemsList.values());
        }
        return orderedItems;
    }
}
