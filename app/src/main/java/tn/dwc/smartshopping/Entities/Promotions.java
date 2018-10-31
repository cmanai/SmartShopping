package tn.dwc.smartshopping.Entities;

import java.sql.Blob;

public class Promotions {

	String Promotion_id;
    String Promotion_sheve_id;
    String Promotion_name;
    byte[] Promotion_image;
    String Promotion_category;
    String Promotion_sub_category;
    String Promotion_old_price;
    String Promotion_new_price;

    public String getPromotion_Product_desc() {
        return Promotion_Product_desc;
    }

    public void setPromotion_Product_desc(String promotion_Product_desc) {
        Promotion_Product_desc = promotion_Product_desc;
    }

    String Promotion_Product_desc;

    public String getPromotion_category() {
        return Promotion_category;
    }

    public void setPromotion_category(String promotion_category) {
        Promotion_category = promotion_category;
    }



    public String getPromotion_id() {
        return Promotion_id;
    }

    public void setPromotion_id(String promotion_id) {
        Promotion_id = promotion_id;
    }

    public String getPromotion_sheve_id() {
        return Promotion_sheve_id;
    }

    public void setPromotion_sheve_id(String promotion_sheve_id) {
        Promotion_sheve_id = promotion_sheve_id;
    }

    public String getPromotion_name() {
        return Promotion_name;
    }

    public void setPromotion_name(String promotion_name) {
        Promotion_name = promotion_name;
    }

    public byte[] getPromotion_image() {
        return Promotion_image;
    }

    public void setPromotion_image(byte[] promotion_image) {
        Promotion_image = promotion_image;
    }

    public String getPromotion_sub_category() {
        return Promotion_sub_category;
    }

    public void setPromotion_sub_category(String promotion_sub_category) {
        Promotion_sub_category = promotion_sub_category;
    }

    public String getPromotion_old_price() {
        return Promotion_old_price;
    }

    public void setPromotion_old_price(String promotion_old_price) {
        Promotion_old_price = promotion_old_price;
    }

    public String getPromotion_new_price() {
        return Promotion_new_price;
    }

    public void setPromotion_new_price(String promotion_new_price) {
        Promotion_new_price = promotion_new_price;
    }

    public String getPromotion_pourcentage() {
        return Promotion_pourcentage;
    }

    public void setPromotion_pourcentage(String promotion_pourcentage) {
        Promotion_pourcentage = promotion_pourcentage;
    }

    String Promotion_pourcentage;


	public Promotions(String promotion_id, String promotion_sheve_id, String promotion_name,String Promotion_Product_desc, byte[] promotion_image, String promotion_category, String promotion_sub_category, String promotion_old_price, String promotion_new_price, String promotion_pourcentage) {



        this.Promotion_id=promotion_id;
        this.Promotion_sheve_id=promotion_sheve_id;
        this.Promotion_name=promotion_name;
        this.Promotion_Product_desc=Promotion_Product_desc;
        this.Promotion_image=promotion_image;
        this.Promotion_category=promotion_category;
        this.Promotion_sub_category=promotion_sub_category;
        this.Promotion_old_price=promotion_old_price;
        this.Promotion_new_price=promotion_new_price;
        this.Promotion_pourcentage=promotion_pourcentage;

	}


}
