package android.alanfooddeliverysdk.data;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class DataProvider {

    //public dat

    public DataProvider(Context context) throws IOException {

        String text = null;

        try {

            InputStream is = context.getAssets().open("menu.json");
            int size;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            Log.d("STATE", text);

            JSONArray jsonArray  = new JSONArray(text);
//Java Object
            for (int i = 0, ix = jsonArray.length(); i < ix; i++) {

                HashMap<String, String> map = new HashMap<String, String>();
               // JSONObject e = jsonArray.getJSONObject(i);

                        Log.d("STATE", jsonArray.getJSONObject(i).get("name").toString());

            }



        } catch (IOException | JSONException e) {

            e.printStackTrace();

        }




    }

}

