package hcmute.edu.vn.mssv18128062;

import android.widget.ImageButton;

public class Product {
    int _id;
    String _name;
    double _price;
    String _description;
    Integer _picture;
    int _idcategory;



    public Product(){   }
    public Product(int id, String _name, double _price, String _description, Integer _picture, int _idcategory){
        this._id = id;
        this._name = _name;
        this._picture = _picture;
        this._description = _description;
        this._idcategory = _idcategory;
        this._price = _price;
    }

    public Product(String _name, double _price, String _description, Integer _picture, int _idcategory){
        this._name = _name;
        this._picture = _picture;
        this._description = _description;
        this._idcategory = _idcategory;
        this._price = _price;
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

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }

    public String getDescription(){return this._description;}

    public void setDescription(String description){this._description = description;}

    public int getIdCategory(){return this._idcategory;}

    public void setIdCategory(int idCategory){this._idcategory = idCategory; }

    public Integer get_picture() {
        return _picture;
    }

    public void set_picture(Integer _picture) {
        this._picture = _picture;
    }
}
