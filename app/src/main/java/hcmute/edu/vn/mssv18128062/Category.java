package hcmute.edu.vn.mssv18128062;

public class Category {
    int _id;
    String _name;
    Integer _picture;

    public Category(){   }
    public Category(int id, String _name, Integer _picture){
        this._id = id;
        this._name = _name;
        this._picture = _picture;
    }

    public Category(String _name, Integer _picture){
        this._name = _name;
        this._picture = _picture;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public Integer get_picture() {
        return _picture;
    }

    public void set_picture(Integer _picture) {
        this._picture = _picture;
    }



}
