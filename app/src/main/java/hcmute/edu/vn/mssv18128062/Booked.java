package hcmute.edu.vn.mssv18128062;

import java.util.Date;

public class Booked {
    int _id;
    String _dateOrder;
    int _status;
    String _address;

    public Booked() {
    }

    public Booked(String _dateOrder, int _status, String _address) {
        this._dateOrder = _dateOrder;
        this._status = _status;
        this._address = _address;
    }

    public Booked(int _id, String _dateOrder, int _status, String _address) {
        this._id = _id;
        this._dateOrder = _dateOrder;
        this._status = _status;
        this._address = _address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_dateOrder() {
        return _dateOrder;
    }

    public void set_dateOrder(String _dateOrder) {
        this._dateOrder = _dateOrder;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }
}
