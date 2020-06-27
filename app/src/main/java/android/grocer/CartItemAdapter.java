package android.grocer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.grocer.data.CartItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private LinkedHashMap<String, CartItem> mData;
    private LayoutInflater mInflater;
    private List<String> mIndex;
    private Fragment fragment;
    private TextView priceText;
    private Integer priceNum = 0;

    // data is passed into the constructor
    CartItemAdapter(Context context, LinkedHashMap<String, CartItem> data, Fragment fragment, View view) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mIndex = new ArrayList<>(this.mData.keySet());
        this.fragment = fragment;
        this.priceText = view.findViewById(R.id.cart_total_price);

        for (LinkedHashMap.Entry<String, CartItem> item : data.entrySet())

            priceNum += item.getValue().getQty() * item.getValue().getPrice();
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder){

        setTotalPriceText();
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CartItem item = mData.get(mIndex.get(position));

        assert item != null;

        try {

            InputStream is = mInflater.getContext().getAssets().open(item.getImg());
            holder.itemImage.setImageBitmap(BitmapFactory.decodeStream(is));
            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
        holder.itemQty.setText(item.getQtyAsString());
        holder.itemTitle.setText(item.getTitle());
        holder.itemCategory.setText(item.getCategory());

        holder.itemAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                item.setQuantity(item.getQty() + 1);
                priceNum += item.getPrice();
                setTotalPriceText();
                holder.itemQty.setText(item.getQtyAsString());

            }

        } );

        holder.itemRm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int quantity = item.getQty() - 1;
                item.setQuantity(quantity);
                priceNum -= item.getPrice();
                setTotalPriceText();

                if (quantity == 0) {

                    mData.remove(item.getId());
                    mIndex.remove(position);

                    notifyDataSetChanged();

                    if (mData.size() == 0)

                        NavHostFragment.findNavController(fragment).navigate(R.id.action_CartFragment_to_MenuFragment);

                }

                holder.itemQty.setText(item.getQtyAsString());


            }

        } );
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemAdd;
        ImageView itemRm;
        ImageView itemImage;
        TextView itemCategory;
        TextView itemQty;
        TextView itemTitle;

        ViewHolder(View itemView) {
            super(itemView);
            itemAdd = itemView.findViewById(R.id.item_add);
            itemRm = itemView.findViewById(R.id.item_rm);
            itemImage = itemView.findViewById(R.id.item_image);
            itemCategory = itemView.findViewById(R.id.item_category);
            itemQty = itemView.findViewById(R.id.item_qty);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }

    public void setTotalPriceText(){
        priceText.setText(String.valueOf(priceNum));
    }

}
