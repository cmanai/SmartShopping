package tn.dwc.smartshopping.Entities;

public class User {

	String User_FullName;
    String User_Email;
    String User_Birthday;
    byte[] User_Picture;

    public String getUser_FullName() {
        return User_FullName;
    }

    public void setUser_FullName(String user_FullName) {
        User_FullName = user_FullName;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    public String getUser_Birthday() {
        return User_Birthday;
    }

    public void setUser_Birthday(String user_Birthday) {
        User_Birthday = user_Birthday;
    }

    public byte[] getUser_Picture() {
        return User_Picture;
    }

    public void setUser_Picture(byte[] user_Picture) {
        User_Picture = user_Picture;
    }

    public User(String User_FullName,String User_Email,String User_Birthday, byte[] User_Picture) {



        this.User_FullName=User_FullName;
        this.User_Email=User_Email;
        this.User_Birthday=User_Birthday;
        this.User_Picture=User_Picture;


	}


}
