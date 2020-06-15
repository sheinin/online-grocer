package android.alanfooddeliverysdk.data;

import android.alanfooddeliverysdk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static Utils instance;

    static {
        instance = new Utils();
    }

    public static Utils getInstance(){
        return instance;
    }

    public Map<String, String> getCategories() {
        Map<String, String> categories = new HashMap<String, String>() {{
            put("pizza", "pizza");
            put("desserts", "dessert");
            put("streetFood", "street food");
            put("drinks", "drink");
        }};
        return categories;
    }

    public Map<String, String> getCategoriesTitles() {
        Map<String, String> categoriesTitles = new HashMap<String, String>() {{
            put("pizza", "Pizza");
            put("dessert", "Desserts");
            put("street food", "Street food");
            put("drink", "Drinks");
        }};

        return categoriesTitles;
    }


    public Map<String, List<CartItem>> getMenuItems(){
        Map<String, List<CartItem>> menuItems = new HashMap<>();
        List<CartItem> pizzas = new ArrayList<CartItem>();
        pizzas.add(new CartItem("Pepperoni","pizza-pepperoni", 14f,"prn",getCategories().get("pizza"), "pizza","pizza-pepperoni", 0));
        pizzas.add(new CartItem("Margarita","pizza-margarita", 10f,"mrg",getCategories().get("pizza"), "pizza","pizza-pepperoni", 0));
        pizzas.add(new CartItem("Cheese","pizza-four-cheese", 10f,"4ch",getCategories().get("pizza"), "pizza","pizza-pepperoni", 0));
        pizzas.add(new CartItem("Hawaiian","pizza-hawaii", 10f,"haw",getCategories().get("pizza"), "pizza","pizza-pepperoni", 0));
        menuItems.put("pizza", pizzas);

        List<CartItem> streetFoods = new ArrayList<CartItem>();
        streetFoods.add(new CartItem("Burrito","street-food-burrito", 12f,"brt", getCategories().get("streetFood"), "restaurant","street-food-burrito", 0));
        streetFoods.add(new CartItem("Burger","street-food-burger", 23f,"brg", getCategories().get("streetFood"), "restaurant","street-food-burrito", 0));
        streetFoods.add(new CartItem("Taco","street-food-taco", 10f,"tco", getCategories().get("streetFood"), "restaurant","street-food-burrito", 0));
        streetFoods.add(new CartItem("Hawaiian","street-food-sandwich", 10f,"snd", getCategories().get("streetFood"), "restaurant","street-food-burrito", 0));
        menuItems.put("streetFood", streetFoods);


        List<CartItem> desserts = new ArrayList<CartItem>();
        desserts.add(new CartItem("Apple Pie","dessert-apple-pie", 5f,"apl", getCategories().get("desserts"), "ice-cream","dessert-apple-pie", 0));
        desserts.add(new CartItem("Cheesecake","dessert-cheesecake", 15f,"chc", getCategories().get("desserts"), "ice-cream","dessert-apple-pie", 0));
        menuItems.put("desserts", desserts);

        List<CartItem> drinks = new ArrayList<CartItem>();
        drinks.add(new CartItem("Coca-Cola","drinks-cola", 2f,"sod", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        drinks.add(new CartItem("Americano","drinks-americano", 1f,"amr", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        drinks.add(new CartItem("Latte","drinks-latte", 3f,"lat", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        drinks.add(new CartItem("Cappuccino","drinks-cappuccino", 3f,"cap", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        drinks.add(new CartItem("Orange Juice","drinks-orange-juice", 3f,"orj", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        drinks.add(new CartItem("Tea","drinks-tea", 3f,"tea", getCategories().get("drinks"), "cafe","drinks-latte", 0));
        menuItems.put("drinks", drinks);

        return menuItems;
    }


    public int getMenuResourceId(String type){
        switch (type){
            case "cafe":
                return R.drawable.drinks_latte;
            case "desserts":
                return R.drawable.dessert_apple_pie;
            case "streetFood":
                return R.drawable.street_food_burrito;
            case "pizza":
                return R.drawable.pizza_pepperoni;
        }
        return -1;
    }

    /**
     * Method used to map the resource id with cart item image.
     * @param imageType
     * @return
     */
    public int getCartResourceId(String imageType){
        switch (imageType){
            case "pizza-pepperoni":
                return R.drawable.pizza_pepperoni;
            case "pizza-margarita":
                return R.drawable.pizza_margarita;
            case "pizza-four-cheese":
                return R.drawable.pizza_four_cheese;
            case "pizza-hawaii":
                return R.drawable.pizza_hawaii;
            case "street-food-burrito":
                return R.drawable.street_food_burrito;
            case "street-food-burger":
                return R.drawable.street_food_burger;
            case "street-food-taco":
                return R.drawable.street_food_taco;
            case "street-food-sandwich":
                return R.drawable.street_food_sandwich;
            case "dessert-apple-pie":
                return R.drawable.dessert_apple_pie;
            case "dessert-cheesecake":
                return R.drawable.dessert_cheesecake;
            case "drinks-cola":
                return R.drawable.drinks_cola;
            case "drinks-americano":
                return R.drawable.drinks_americano;
            case "drinks-latte":
                return R.drawable.drinks_latte;
            case "drinks-cappuccino":
                return R.drawable.drinks_cappuccino;
            case "drinks-orange-juice":
                return R.drawable.drinks_orange_juice;
            case "drinks-tea":
                return R.drawable.drinks_tea;
        }
        return -1;
    }
}
