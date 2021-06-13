package hcmute.edu.vn.mssv18128062;

import java.util.Date;

public class Order {
    int _id;
    int _idProduct;
    float _price;
    int _amount;
    int _idBooked;


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

    public float get_price() {
        return _price;
    }

    public void set_price(float _price) {
        this._price = _price;
    }

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public int get_idBooked() {
        return _idBooked;
    }

    public void set_idBooked(int _idBooked) {
        this._idBooked = _idBooked;
    }


    public Order() {
    }

    public Order(int _idProduct, float _price, int _amount, int _idBooked) {
        this._idProduct = _idProduct;
        this._price = _price;
        this._amount = _amount;
        this._idBooked = _idBooked;
    }

    public Order(int _id, int _idProduct, float _price, int _amount, int _idBooked) {
        this._id = _id;
        this._idProduct = _idProduct;
        this._price = _price;
        this._amount = _amount;
        this._idBooked = _idBooked;
    }
}
