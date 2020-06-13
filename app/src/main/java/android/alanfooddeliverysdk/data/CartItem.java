package android.alanfooddeliverysdk.data;

public class CartItem {
    private String title;
    private String img;
    private Float price;
    private String id;
    private String type;
    private String typeIcon;
    private String categoryImg;
    private Integer quantity;

    public CartItem(){

    }

    public CartItem(String title, String img, Float price, String id, String type, String typeIcon, String categoryImg, Integer  quantity){
        this.title = title;
        this.img = img;
        this.price = price;
        this.id = id;
        this.type = type;
        this.typeIcon = typeIcon;
        this.categoryImg = categoryImg;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public Float getPrice() {
        return price;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer count) {
         quantity = count;
    }


}
