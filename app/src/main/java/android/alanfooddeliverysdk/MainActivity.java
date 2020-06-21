package android.alanfooddeliverysdk;

import androidx.appcompat.app.AppCompatActivity;
import android.alanfooddeliverysdk.data.CartItem;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    String route;
    List<CartItem> cartItems = new ArrayList<>();

    LinkedHashMap<String, CartItem> orderedItemsList = new LinkedHashMap<>();
    LinkedHashMap<String, List<CartItem>> items = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {

            InputStream is = getAssets().open("menu.json");
            byte[] buffer = new byte[is.available()];
            int size = is.read(buffer);
            is.close();
            String text = new String(buffer, 0, size);
            JSONArray jsonArray  = new JSONArray(text);

            for (int i = 0, ix = jsonArray.length(); i < ix; i++) {

                JSONObject o = jsonArray.getJSONObject(i);
                String id = o.get("id").toString();
                String img = o.get("img").toString();
                String icon = o.get("icon").toString();
                String name = o.get("name").toString();
                float price = Float.parseFloat(o.get("price").toString());
                String type = o.get("type").toString();
                String typeIcon = o.get("splotch").toString();
                String cat = o.get("cat").toString();

                if (!items.containsKey(type))

                    items.put(type, new ArrayList<CartItem>());

                Objects.requireNonNull(items.get(type)).add(new CartItem(name, img, price, id, type, icon, typeIcon, cat, 0));

            }
        } catch (JSONException | IOException ex) {

                ex.printStackTrace();

        }


        setContentView(R.layout.activity_main);

    }

}
