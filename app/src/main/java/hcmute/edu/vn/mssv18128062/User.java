package hcmute.edu.vn.mssv18128062;

public class User {
    int _id;
    String _username;
    String _password;
    String _email;
    String _name;
    String _phone_number;
    Integer _point;
    byte [] _picture;


    public User(){   }
    public User(int id, String _username, String _password, String _email, String _name, String _phone_number, int _point, byte [] _picture){
        this._id = id;
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._name = _name;
        this._phone_number = _phone_number;
        this._point = _point;
        this._picture = _picture;
    }

    public User(String _username, String _password, String _email, String _name, String _phone_number, int _point, byte [] _picture){
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._name = _name;
        this._phone_number = _phone_number;
        this._point = _point;
        this._picture = _picture;
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

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }

    public Integer get_point() {
        return _point;
    }

    public void set_point(Integer _point) {
        this._point = _point;
    }

    public byte[] get_picture() {
        return _picture;
    }

    public void set_picture(byte[] _picture) {
        this._picture = _picture;
    }

}
