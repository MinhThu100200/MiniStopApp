package hcmute.edu.vn.mssv18128062;

public class Rate {
    int _id;
    int _idProduct;
    float _rating;

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

    public float get_rating() {
        return _rating;
    }

    public void set_rating(float _rating) {
        this._rating = _rating;
    }

    public Rate() {
    }

    public Rate(int _idProduct, float _rating) {
        this._idProduct = _idProduct;
        this._rating = _rating;
    }

    public Rate(int _id, int _idProduct, float _rating) {
        this._id = _id;
        this._idProduct = _idProduct;
        this._rating = _rating;
    }
}
