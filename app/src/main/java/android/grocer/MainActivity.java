package android.grocer;

import androidx.appcompat.app.AppCompatActivity;

import android.grocer.data.CartItem;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String route;
    String store;
    List<CartItem> cartItems = new ArrayList<>();

    LinkedHashMap<String, CartItem> orderedItemsList = new LinkedHashMap<>();
    HashMap<String ,LinkedHashMap<String, List<CartItem>>> items = new HashMap<>();
    LinkedHashMap<String, String> stores = new LinkedHashMap<>();

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
                String header = o.get("header").toString();
                String store = o.get("store").toString();
                String id = o.get("id").toString();
                String img = o.get("img").toString();
                String icon = o.get("icon").toString();
                String name = o.get("name").toString();
                float price = Float.parseFloat(o.get("price").toString());
                String type = o.get("type").toString();
                String typeIcon = o.get("splotch").toString();
                String cat = o.get("cat").toString();

                if (!stores.containsKey(store))

                    stores.put(store, header);

                if (!items.containsKey(store))

                    items.put(store, new LinkedHashMap<String, List<CartItem>>());

                if (!Objects.requireNonNull(items.get(store)).containsKey(type))

                    Objects.requireNonNull(items.get(store)).put(type, new ArrayList<CartItem>());

                Objects.requireNonNull(Objects.requireNonNull(items.get(store)).get(type)).add(new CartItem(name, img, price, id, type, icon, typeIcon, cat, 0));

            }
        } catch (JSONException | IOException ex) {

                ex.printStackTrace();

        }

        setContentView(R.layout.activity_main);

    }

}
