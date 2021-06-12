package hcmute.edu.vn.mssv18128062;

import java.util.Date;

public class Rate {
    int _id;
    int _idProduct;
    String _name;
    String _dateRating;
    float _rating;
    String _cmt;

    public Rate() {
    }

    public Rate(int _idProduct, String _name, String _dateRating, float _rating, String _cmt) {
        this._idProduct = _idProduct;
        this._name = _name;
        this._dateRating = _dateRating;
        this._rating = _rating;
        this._cmt = _cmt;
    }

    public Rate(int _id, int _idProduct, String _name, String _dateRating, float _rating, String _cmt) {
        this._id = _id;
        this._idProduct = _idProduct;
        this._name = _name;
        this._dateRating = _dateRating;
        this._rating = _rating;
        this._cmt = _cmt;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_idProduct() {
        return _idProduct;
    }

    public void set_idProduct(int _idProduct) {
        this._idProduct = _idProduct;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_dateRating() {
        return _dateRating;
    }

    public void set_dateRating(String _dateRating) {
        this._dateRating = _dateRating;
    }

    public float get_rating() {
        return _rating;
    }

    public void set_rating(float _rating) {
        this._rating = _rating;
    }

    public String get_cmt() {
        return _cmt;
    }

    public void set_cmt(String _cmt) {
        this._cmt = _cmt;
    }
}
