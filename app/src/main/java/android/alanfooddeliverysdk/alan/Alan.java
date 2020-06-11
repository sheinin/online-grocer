package android.alanfooddeliverysdk.alan;

import com.alan.actsoft.alan.data.AlanCommand;
import com.alan.actsoft.alan.data.Msgs;
import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanConfig;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.events.EventCommand;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Alan {


    static final String PROJECT_ID = "b983d67db70894604d0bf347580da6fd2e956eca572e1d8b807a3e2338fdd0dc/prod";

    private AlanButton alanButton;

    private static Alan INSTANCE;

    public static Alan getInstance()
    {
        if(INSTANCE == null){
            INSTANCE = new Alan();
        }
        return INSTANCE;
    }

    public static void clearInstance(){
        INSTANCE = null;
    }


    public AlanButton getAlanButton() {
        return alanButton;
    }

    public void setAlanButton(AlanButton alanButton) {
        if(this.alanButton != null) {
            this.getAlanButton().deactivate();
            this.getAlanButton().clearCallbacks();
            this.alanButton = null;
        }
        this.alanButton = alanButton;
        initConfig();
    }

    public void initConfig(){
        AlanConfig config = AlanConfig.builder()
                .setProjectId(PROJECT_ID)
                .build();
        this.getAlanButton().initWithConfig(config);
    }

    public void registerCallback(AlanCallback alanCallback){
        this.getAlanButton().registerCallback(alanCallback);
    }

    public void clearCallbacks(){
        if(this.getAlanButton() != null)
            this.getAlanButton().clearCallbacks();
    }


    public void clearAlanInstance(){
        if(this.getAlanButton() != null) {
            this.getAlanButton().clearCallbacks();
            clearInstance();
        }
    }

    public void playText(String inpuText){
        this.getAlanButton().playText(inpuText);
    }

    public void stopPlaying(){
        this.getAlanButton().performClick();
        this.getAlanButton().performClick();
    }

    public void setVisualState(String screenName){
        try{
            Map<String, String> screenState = new HashMap<>();
            screenState.put("screen", screenName);
            JSONObject visualState = new JSONObject(screenState);
            this.getAlanButton().setVisualState(visualState.toString());
        } catch (Exception je){
            this.getAlanButton().playText("Something went wrong while setting visual state");
        }
    }


    public void callProjectApi(String apiFunction, String data){
        this.getAlanButton().callProjectApi(apiFunction, data);
    }

    /**
     * This method process the alan even command object and returns the data of the event, command and value.
     * @param eventCommand
     * @return
     */

    public JSONObject processAlanEventCommand(EventCommand eventCommand){
        try {
            JSONObject commandObject = eventCommand.getData();
            if( commandObject != null && commandObject.getJSONObject("data") != null) {
                return commandObject.getJSONObject("data");
            }
        } catch (Exception e) {
            Alan.getInstance().getAlanButton().playText(Msgs.INVALID_RESPONSE);
//            alanCommand = new AlanCommand(Msgs.INVALID_RESPONSE);
            e.printStackTrace();
        }

        return null;
    }

}