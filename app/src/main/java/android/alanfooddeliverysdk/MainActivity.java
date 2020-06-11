package android.alanfooddeliverysdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanState;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.AlanConfig;
import com.alan.alansdk.events.EventCommand;
import com.alan.alansdk.events.EventOptions;
import com.alan.alansdk.events.EventParsed;
import com.alan.alansdk.events.EventRecognised;
import com.alan.alansdk.events.EventText;
import android.alanfooddeliverysdk.alan.Alan;

import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AlanButton alanButton;
    private AlanCallback alanCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        alanButton = findViewById(R.id.alan_button);
        AlanConfig config = AlanConfig.builder()
                .setProjectId("b983d67db70894604d0bf347580da6fd2e956eca572e1d8b807a3e2338fdd0dc/prod")
                .build();
        alanButton.initWithConfig(config);
*/

        alanButton = findViewById(R.id.alan_button);
        Alan.getInstance().setAlanButton(alanButton);
        alanButton.setButtonAlign(AlanButton.BUTTON_RIGHT);
        configAlanVoice();
    }



    /**
     * Method configures the alan voice command UI with application, initializes the Alan command
     * and voice command listener with Alan SDK.
     */
    private void configAlanVoice(){
        if(this.alanButton == null) {
            alanButton = findViewById(R.id.alan_button);
            Alan.getInstance().setAlanButton(alanButton);
            alanButton.setButtonAlign(AlanButton.BUTTON_RIGHT);
        }
    }


}
