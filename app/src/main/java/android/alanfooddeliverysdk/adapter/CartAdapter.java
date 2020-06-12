package android.alanfooddeliverysdk.adapter;

import android.alanfooddeliverysdk.R;
import android.alanfooddeliverysdk.data.CartItem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private List<CartItem> cartItems;
    private int mResource;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<CartItem> items) {
        super(context, resource, items);
        this.mContext = context;
        this.cartItems = items;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View view = inflater.inflate(R.layout.cart_item, null);

        //Set cart item params
        CartItem cartItem = this.cartItems.get(position);

        ImageView itemImage = view.findViewById(R.id.item_image);
        itemImage.setImageDrawable(mContext.getDrawable(R.drawable.drinks_latte));

        TextView itemName = view.findViewById(R.id.item_name);
        itemName.setText(cartItem.getTitle());

        TextView itemCategory = view.findViewById(R.id.item_category);
        itemCategory.setText(cartItem.getType());

        TextView itemPrice = view.findViewById(R.id.item_price);
        itemPrice.setText("" + cartItem.getPrice());

        TextView itemQuantity = view.findViewById(R.id.item_quantity);
        itemQuantity.setText("" + cartItem.getQuantity());

        Button add = view.findViewById(R.id.add_item);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("CartAdapter", "Add Item");
            }
        });

        Button remove = view.findViewById(R.id.remove_item);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("CartAdapter", "Remove Item");
            }
        });

        return view;
    }
}
