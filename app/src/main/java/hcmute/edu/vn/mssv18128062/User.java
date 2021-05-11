package hcmute.edu.vn.mssv18128062;

public class User {
    int _id;
    String _username;
    String _password;
    String _email;
    int _role;

    public User(){   }
    public User(int id, String _username, String _password, String _email, int _role){
        this._id = id;
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._role = _role;
    }

    public User(String _username, String _password, String _email, int _role){
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._role = _role;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getUsername(){
        return this._username;
    }

    public void setUsername(String username){
        this._username = username;
    }

    public String getPassword(){
        return this._password;
    }

    public void setPassword(String password){
        this._password = password;
    }

    public String getEmail(){return this._email;}

    public void setEmail(String email){this._email = email;}

    public int getRole(){return this._role;}

    public void setRole(int role){this._role = role;}
}
