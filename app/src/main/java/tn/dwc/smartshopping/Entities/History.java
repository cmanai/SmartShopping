package tn.dwc.smartshopping.Entities;

/**
 * Created by Chi on 11/16/2015.
 */
public class History {


   public String History_Name;
    public  String Budget;
    public   String Products_Number;

    public String getCreation_Date() {
        return Creation_Date;
    }

    public void setCreation_Date(String creation_Date) {
        Creation_Date = creation_Date;
    }

    public String getHistory_Name() {
        return History_Name;
    }

    public void setHistory_Name(String history_Name) {
        History_Name = history_Name;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getProducts_Number() {
        return Products_Number;
    }

    public void setProducts_Number(String products_Number) {
        Products_Number = products_Number;
    }

    String Creation_Date;



    public History(String history_Name,String budget,String products_Number,String creation_Date){

this.History_Name=history_Name;
        this.Budget=budget;
        this.Products_Number= products_Number;
        this.Creation_Date=creation_Date;



    }
}
