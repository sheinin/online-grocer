package android.grocer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.grocer.data.CartItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private LinkedHashMap<String, CartItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<String> mIndex;

    // data is passed into the constructor
    CartItemAdapter(Context context, LinkedHashMap<String, CartItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mIndex = new ArrayList<>(this.mData.keySet());
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_item_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem item = mData.get(mIndex.get(position));
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
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemImage;
        TextView itemCategory;
        TextView itemQty;
        TextView itemTitle;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemCategory = itemView.findViewById(R.id.item_category);
            itemQty = itemView.findViewById(R.id.item_qty);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    CartItem getItem(int id) {
        return mData.get(mIndex.get(id));
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
