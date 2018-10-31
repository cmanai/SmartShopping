package tn.dwc.smartshopping.Entities;

public class Maps_Shelve {


    String Shelve_Id;
    byte[] Maps_Sh;
    String Qr_ID;
    String H_B;

    public String getShelve_Id() {
        return Shelve_Id;
    }

    public void setShelve_Id(String shelve_Id) {
        Shelve_Id = shelve_Id;
    }

    public byte[] getMaps_Sh() {
        return Maps_Sh;
    }

    public void setMaps_Sh(byte[] maps_Sh) {
        Maps_Sh = maps_Sh;
    }

    public String getQr_ID() {
        return Qr_ID;
    }

    public void setQr_ID(String qr_ID) {
        Qr_ID = qr_ID;
    }

    public String getH_B() {
        return H_B;
    }

    public void setH_B(String h_B) {
        H_B = h_B;
    }

    public Maps_Shelve(String Shelve_Id, byte[] Maps_Sh,String Qr_ID,String H_B) {



        this.Shelve_Id=Shelve_Id;
        this.Maps_Sh=Maps_Sh;
        this.Qr_ID=Qr_ID;
        this.H_B=H_B;

	}


}
