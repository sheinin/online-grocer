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
import android.alanfooddeliverysdk.data.DataProvider;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String route;
    List<CartItem> cartItems = new ArrayList<>();

    Map<String, CartItem> orderedItemsList = new HashMap<>();
    LinkedHashMap<String, List<CartItem>> items = new LinkedHashMap<>();
    //Map<String, CartItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            InputStream is = getAssets().open("menu.json");
            int size;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            JSONArray jsonArray  = new JSONArray(text);

            for (int i = 0, ix = jsonArray.length(); i < ix; i++) {

                JSONObject o = jsonArray.getJSONObject(i);
                String id = o.get("id").toString();
                String img = o.get("img").toString();
                String icon = o.get("icon").toString();
                String name = o.get("name").toString();
                float price = Float.parseFloat(o.get("price").toString());
                String type = o.get("type").toString();
                String typeIcon = o.get("typeIcon").toString();

                if (!items.containsKey(type))

                    items.put(type, new ArrayList<CartItem>());

                items.get(type).add(new CartItem(name, img, price, id, type, icon, typeIcon, 0));


            }
        } catch (JSONException | IOException ex) {

                ex.printStackTrace();

        }


        setContentView(R.layout.activity_main);

        //Toolbar toolbar = findViewById(R.id.id_app_toolbar);
        //setSupportActionBar(toolbar);


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
