package tn.dwc.smartshopping.Entities;

public class Maps_Product {


    String Shelve_Id_p;
    byte[] Maps_p;

    public String getShelve_Id_p() {
        return Shelve_Id_p;
    }

    public void setShelve_Id_p(String shelve_Id_p) {
        Shelve_Id_p = shelve_Id_p;
    }

    public byte[] getMaps_p() {
        return Maps_p;
    }

    public void setMaps_p(byte[] maps_p) {
        Maps_p = maps_p;
    }

    public String getProduct_Id_p() {
        return Product_Id_p;
    }

    public void setProduct_Id_p(String product_Id_p) {
        Product_Id_p = product_Id_p;
    }

    public String getH_B_p() {
        return H_B_p;
    }

    public void setH_B_p(String h_B_p) {
        H_B_p = h_B_p;
    }

    String Product_Id_p;
    String H_B_p;



    public Maps_Product(String Shelve_Id_p, byte[] Maps_p, String Product_Id_p, String H_B_p) {



        this.Shelve_Id_p=Shelve_Id_p;
        this.Maps_p=Maps_p;
        this.Product_Id_p=Product_Id_p;
        this.H_B_p=H_B_p;

	}


}
