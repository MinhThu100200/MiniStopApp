package hcmute.edu.vn.mssv18128062;

public class Address {
    int _id;
    String _description;
    int _picture;

    public void set_picture(int _picture) {
        this._picture = _picture;
    }

    public int get_picture() {
        return _picture;
    }

    public Address(){   }
    public Address(int id, String _description, int _picture){
        this._id = id;
        this._description = _description;
        this._picture = _picture;

    }

    public Address(String _description, int _picture){
        this._picture = _picture;
        this._description = _description;

    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getDescription(){return this._description;}

    public void setDescription(String description){this._description = description;}


}
