package android.alanfooddeliverysdk.adapter;

import android.alanfooddeliverysdk.R;
import android.alanfooddeliverysdk.data.CartItem;
import android.alanfooddeliverysdk.data.Utils;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<CartItem> cartItems;
    private int mResource;


    // Define listener member variable
    private OnItemClickListener listener;

    String CURRENCY = "$";

    private int resourceItem = -1;


    // Define the listener interface
    public interface OnItemClickListener {
        void addItem(View itemView, int position, int count);

        void removeItem(View itemView, int position, int count);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView itemImage;
        public TextView itemName;
        public TextView itemCategory;
        public TextView itemPrice;
        public TextView itemQuantity;
        public ImageButton addItem;
        public ImageButton removeItem;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemCategory = itemView.findViewById(R.id.item_category);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            addItem = itemView.findViewById(R.id.add_item);
            removeItem = itemView.findViewById(R.id.remove_item);
        }
    }

    public CartAdapter(@NonNull List<CartItem> items, int layoutItem) {
        super();
        this.cartItems = items;
        this.resourceItem = layoutItem;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View cartItemView = inflater.inflate(this.resourceItem, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartItemView);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, final int position) {
        CartItem cartItem = this.cartItems.get(position);
        holder.itemName.setText(cartItem.getTitle());
        if(holder.itemCategory != null)
            holder.itemCategory.setText(cartItem.getType());
        holder.itemPrice.setText(CURRENCY + cartItem.getPrice());
        int res = mContext.getResources().getIdentifier(
                cartItems.get(position).getImg(),
                "drawable",
                this.mContext.getPackageName());

        holder.itemImage.setImageDrawable(mContext.getDrawable(res));
        holder.itemQuantity.setText("" + cartItem.getQuantity());
        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view, position, 1);
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(view, position, 1);
            }
        });

    }

    public void removeItem(View view,int position, int count){
        if(this.listener != null){
            this.listener.removeItem(view, position, count);
        }
    }

    private void addItem(View view, int position, int count){
        if(this.listener != null){
            this.listener.addItem(view, position, count);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.cartItems.size();
    }
}
