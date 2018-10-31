package tn.dwc.smartshopping.Entities;

public class Products {

	String Product_id;
    String Product_sheve_id;
    String Product_name;
    byte[] Product_image;

    public String getProduct_category() {
        return Product_category;
    }

    public void setProduct_category(String product_category) {
        Product_category = product_category;
    }

    String Product_category;
    String Product_sub_category;
    String Product_price;

    public String getProduct_desc() {
        return Product_desc;
    }

    public void setProduct_desc(String product_desc) {
        Product_desc = product_desc;
    }

    String Product_desc;
    String Product_created_at;

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getProduct_sheve_id() {
        return Product_sheve_id;
    }

    public void setProduct_sheve_id(String product_sheve_id) {
        Product_sheve_id = product_sheve_id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public byte[] getProduct_image() {
        return Product_image;
    }

    public void setProduct_image(byte[] product_image) {
        Product_image = product_image;
    }

    public String getProduct_sub_category() {
        return Product_sub_category;
    }

    public void setProduct_sub_category(String product_sub_category) {
        Product_sub_category = product_sub_category;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }

    public String getProduct_created_at() {
        return Product_created_at;
    }

    public void setProduct_created_at(String product_created_at) {
        Product_created_at = product_created_at;
    }

    public Products(String Product_id, String Product_sheve_id, String Product_name,String Product_desc ,byte[] Product_image,String Product_category, String Product_sub_category, String Product_price, String Product_created_at) {



        this.Product_id=Product_id;
        this.Product_sheve_id=Product_sheve_id;
        this.Product_name=Product_name;
        this.Product_image=Product_image;
        this.Product_category=Product_category;
        this.Product_sub_category=Product_sub_category;
        this.Product_price=Product_price;
        this.Product_created_at=Product_created_at;
        this.Product_desc=Product_desc;

	}


}
