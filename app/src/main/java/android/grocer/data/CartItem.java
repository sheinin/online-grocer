package android.grocer.data;

import java.util.Locale;

public class CartItem {
    private String title;
    private String img;
    private Float price;
    private String id;
    private String type;
    private String typeIcon;
    private String categoryImg;
    private String categoryTitle;
    private Integer quantity;

    public CartItem(){

    }

    public CartItem(String title, String img, Float price, String id, String type, String typeIcon, String categoryImg, String categoryTitle, Integer  quantity){
        this.title = title;
        this.img = img;
        this.price = price;
        this.id = id;
        this.type = type;
        this.typeIcon = typeIcon;
        this.categoryImg = categoryImg;
        this.categoryTitle = categoryTitle;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getPriceAsString() {
        return  "$" + Math.round(price);
    }

    public Integer getPrice() {
        return Math.round(price);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public String getCategory() {
        return categoryTitle;
    }

    public Integer getQty() {
        return quantity;
    }

    public String getQtyAsString() {
        return String.format(Locale.getDefault(), "%d",quantity);
    }

    public void setQuantity(Integer count) {
         quantity = count;
    }


}
