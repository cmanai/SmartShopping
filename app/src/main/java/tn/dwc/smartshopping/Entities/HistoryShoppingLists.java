package tn.dwc.smartshopping.Entities;

/**
 * Created by Chi on 11/16/2015.
 */
public class HistoryShoppingLists {




    String shopping_list_name;
    String shopping_Product_id;
    String shopping_Product_sheve_id;
    public String shopping_Product_name;
    byte[] shopping_Product_image;
    String shopping_Product_category;

    String shopping_Product_price;
    String shopping_Product_created_at;
    String shopping_product_state;

    public String getShopping_list_name() {
        return shopping_list_name;
    }

    public void setShopping_list_name(String shopping_list_name) {
        this.shopping_list_name = shopping_list_name;
    }

    public String getShopping_Product_id() {
        return shopping_Product_id;
    }

    public void setShopping_Product_id(String shopping_Product_id) {
        this.shopping_Product_id = shopping_Product_id;
    }

    public String getShopping_Product_sheve_id() {
        return shopping_Product_sheve_id;
    }

    public void setShopping_Product_sheve_id(String shopping_Product_sheve_id) {
        this.shopping_Product_sheve_id = shopping_Product_sheve_id;
    }

    public String getShopping_Product_name() {
        return shopping_Product_name;
    }

    public void setShopping_Product_name(String shopping_Product_name) {
        this.shopping_Product_name = shopping_Product_name;
    }

    public byte[] getShopping_Product_image() {
        return shopping_Product_image;
    }

    public void setShopping_Product_image(byte[] shopping_Product_image) {
        this.shopping_Product_image = shopping_Product_image;
    }

    public String getShopping_Product_category() {
        return shopping_Product_category;
    }

    public void setShopping_Product_category(String shopping_Product_category) {
        this.shopping_Product_category = shopping_Product_category;
    }



    public String getShopping_Product_price() {
        return shopping_Product_price;
    }

    public void setShopping_Product_price(String shopping_Product_price) {
        this.shopping_Product_price = shopping_Product_price;
    }

    public String getShopping_Product_created_at() {
        return shopping_Product_created_at;
    }

    public void setShopping_Product_created_at(String shopping_Product_created_at) {
        this.shopping_Product_created_at = shopping_Product_created_at;
    }

    public String getShopping_product_state() {
        return shopping_product_state;
    }

    public void setShopping_product_state(String shopping_product_state) {
        this.shopping_product_state = shopping_product_state;
    }

    public HistoryShoppingLists(String shopping_list_name,String shopping_Product_id, String shopping_Product_sheve_id, String shopping_Product_name, byte[] shopping_Product_image, String shopping_Product_category, String shopping_Product_price, String shopping_Product_created_at, String shopping_product_state) {

        this.shopping_list_name=shopping_list_name;

        this.shopping_Product_id=shopping_Product_id;
        this.shopping_Product_sheve_id=shopping_Product_sheve_id;
        this.shopping_Product_name=shopping_Product_name;
        this.shopping_Product_image=shopping_Product_image;
        this.shopping_Product_category=shopping_Product_category;

        this.shopping_Product_price=shopping_Product_price;
        this.shopping_Product_created_at=shopping_Product_created_at;
        this.shopping_product_state=shopping_product_state;

    }


}
