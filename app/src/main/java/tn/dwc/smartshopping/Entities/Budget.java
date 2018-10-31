package tn.dwc.smartshopping.Entities;

public class Budget {

	String budget;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    String spent;




    public Budget(String budget, String spent) {



        this.budget=budget;
        this.spent=spent;


	}


}
