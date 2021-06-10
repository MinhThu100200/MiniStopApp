package hcmute.edu.vn.mssv18128062;

import java.util.Date;

public class Booked {
    int _id;
    Date dateOrder;

    public Booked() {
    }

    public Booked(int _id, Date dateOrder) {
        this._id = _id;
        this.dateOrder = dateOrder;
    }

    public Booked(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }
}
