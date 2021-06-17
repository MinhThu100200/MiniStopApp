package hcmute.edu.vn.mssv18128062;

public class Notification {
    int _id;
    String _title;
    String _info;
    int _Imgae_id;

    public Notification() {
    }

    public Notification(String _title, String _info, int _Imgae_id) {
        this._title = _title;
        this._info = _info;
        this._Imgae_id = _Imgae_id;
    }

    public Notification(int _id, String _title, String _info, int _Imgae_id) {
        this._id = _id;
        this._title = _title;
        this._info = _info;
        this._Imgae_id = _Imgae_id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_info() {
        return _info;
    }

    public void set_info(String _info) {
        this._info = _info;
    }

    public int get_Imgae_id() {
        return _Imgae_id;
    }

    public void set_Imgae_id(int _Imgae_id) {
        this._Imgae_id = _Imgae_id;
    }
}
