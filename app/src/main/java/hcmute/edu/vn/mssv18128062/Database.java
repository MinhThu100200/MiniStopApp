package hcmute.edu.vn.mssv18128062;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "manageStores";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    //Truy vấn không trả về giá trị: insert, update, create
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Truy vấn trả về giá trị
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    //-------------------------------------------------------------------------------------------------
    //insertUser
    public void insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USERNAME", user.getUsername()); // Username
        values.put("PASSWORD", user.getPassword()); // Password
        values.put("EMAIL", user.getEmail()); //email
        values.put("NAME", user.get_name());
        values.put("PHONE", user.get_phone_number());
        values.put("POINT", user.get_point());
        values.put("PICTURE", user.get_picture());
        // Inserting Row
        db.insert("USERS", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single user
    Cursor getUserByEmail(String email) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = '" + email + "'", null);
        // cursor.close();

        // return count
        return cursor;
    }

    Cursor getUserById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE ID = '" + id + "'", null);
        // cursor.close();

        // return count
        return cursor;
    }

    // code to update the single contact
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USERNAME", user.getUsername()); // Username
        values.put("PASSWORD", user.getPassword()); // Password
        values.put("EMAIL", user.getEmail()); //email
        values.put("NAME", user.get_name());
        values.put("PHONE", user.get_phone_number());
        values.put("PICTURE", user.get_picture());


        // updating row
        return db.update("USERS", values, "ID" + " = ?",
                new String[] { String.valueOf( user.getID()) });
    }
    public boolean updateUserPoint(int id, int point) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("POINT", point);

        // updating row
        db.update("USERS", values, "ID" + " = ?",
                new String[] { String.valueOf( id ) });
        return true;
    }



    // Deleting single user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("USERS", "ID" + " = ?",
                new String[] { String.valueOf(user.getID()) });
        db.close();
    }

    // Getting users Count
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM USERS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    //------------------------------------------------------------------------------------------------
    //insertUser
    public void insertCategory(String name, int imgId){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO CATEGORY VALUES(NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, imgId);

        statement.executeInsert();
    }

    public ArrayList<Category> getCategoryById(int id) {
        ArrayList<Category> categoryArrayList= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor dataFood = db.query("CATEGORY", new String[] { "ID",
                        "NAME", "IMAGE_ID" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        while (dataFood.moveToNext()){
            categoryArrayList.add(new Category(dataFood.getInt(0),
                    dataFood.getString(1), dataFood.getInt(2)
                    ));
        }
        // return user
        return categoryArrayList;
    }
    // Deleting single
    public void deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM CATEGORY WHERE ID=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, id);
        statement.executeUpdateDelete();
    }

    // Getting users Count
    public int getCategoriesCount() {
        String countQuery = "SELECT  * FROM CATEGORY";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    //-----------------------------------------------------------------------------------------------
    //CREATE PRODUCT

    //insertUser
    public void insertProduct(String name, float price, String description, int idCategory, int igmId){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO PRODUCT VALUES(NULL, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, description);
        statement.bindDouble(4, idCategory);
        statement.bindDouble(5, igmId);

        statement.executeInsert();
    }


    // Deleting single user
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM PRODUCT WHERE ID=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, id);
        statement.executeUpdateDelete();
    }

    public ArrayList<Product> getProductByCategory(int id) {
        ArrayList<Product> productArrayList= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor dataFood = db.query("PRODUCT", new String[] { "ID",
                        "NAME", "PRICE", "DESCRIPTION", "ID_CATEGORY", "IMAGE_ID" }, "ID_CATEGORY" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        while (dataFood.moveToNext()){
            productArrayList.add(new Product(dataFood.getInt(0),
                    dataFood.getString(1), dataFood.getInt(2),
                    dataFood.getString(3), dataFood.getInt(4), dataFood.getInt(5)));
        }
        // return user
        return productArrayList;
    }
    Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor dataFood = db.query("PRODUCT", new String[] { "ID",
                        "NAME", "PRICE", "DESCRIPTION", "ID_CATEGORY", "IMAGE_ID" }, "ID_CATEGORY" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);


            Product product = new Product(dataFood.getInt(0),
                    dataFood.getString(1), dataFood.getFloat(2),
                    dataFood.getString(3), dataFood.getInt(4), dataFood.getInt(5));

        // return user
        return product;
    }

    Cursor getProductById(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor dataFood = db.query("PRODUCT", new String[] { "ID",
                        "NAME", "PRICE", "DESCRIPTION", "ID_CATEGORY", "IMAGE_ID" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        return dataFood;
    }

    // Getting users Count
    public int getProductsCount() {
        String countQuery = "SELECT  * FROM PRODUCT";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    //------------------------------------------------------------------------------------------------------
    //Address
    public void insertAddress(String description, int picture){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO ADDRESS VALUES(NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, description);
        statement.bindDouble(2, picture);

        statement.executeInsert();
    }



    //rate
    public void insertRate(Rate rate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_PRODUCT", rate.get_idProduct());
        values.put("NAME",rate.get_name());
        values.put("DATE_RATING", rate.get_dateRating());
        values.put("RATING", rate.get_rating());
        values.put("CMT", rate.get_cmt());
        // Inserting Row
        db.insert("RATE", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public ArrayList<Rate> getRateByIdPro(int id) {
        ArrayList<Rate> rateArrayList= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor dataFood = db.query("RATE", new String[] { "ID",
                        "ID_PRODUCT", "NAME", "DATE_RATING", "RATING", "CMT" }, "ID_PRODUCT" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        while (dataFood.moveToNext()){
            rateArrayList.add(new Rate(dataFood.getInt(0),
                    dataFood.getInt(1), dataFood.getString(2),
                    dataFood.getString(3), dataFood.getFloat(4), dataFood.getString(5)));
        }
        // return user
        return rateArrayList;
    }
    float getRatingByFood(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RATE WHERE ID_PRODUCT = '" + id + "'", null);
        // cursor.close();
        float tong = 0;
        // return count
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                tong = tong + cursor.getFloat(4);
            }
            return tong/cursor.getCount();
        }
        else
        {
            return 0;
        }

    }
    int getCountRating(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RATE WHERE ID_PRODUCT = '" + id + "'", null);
        // cursor.close();

        // return count
        return cursor.getCount();
    }

    //order
    public void insertOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_PRODUCT", order.get_idProduct());
        values.put("PRICE", order.get_price());
        values.put("AMOUNT_PRODUCT", order.get_amount());
        values.put("ID_BOOKED", order.get_idBooked());

        // Inserting Row
        db.insert("ORDERS", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    Cursor getOrderByIdBooked(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ORDERS WHERE ID_BOOKED = '" + id + "'", null);
        // cursor.close();

        // return count
        return cursor;
    }

    //book
    public void insertBooked(Booked booked){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("DATE_ORDER", booked.get_dateOrder());
        values.put("STATUS", booked.get_status());
        values.put("ADDRESS", booked.get_address());
        values.put("TOTAL", booked.get_total());
        // Inserting Row
        db.insert("BOOKED", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    public int updateStatusBooked(int status, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("STATUS", status); // Username

        // updating row
        return db.update("BOOKED", values, "ID" + " = ?",
                new String[] { String.valueOf(id) });
    }
    Cursor getBookedById(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BOOKED WHERE ID = '" + id + "'", null);
        // cursor.close();

        // return count
        return cursor;
    }
    Cursor getBookedByDate(String date) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BOOKED WHERE DATE_ORDER = '" + date + "'", null);
        // cursor.close();

        // return count
        return cursor;
    }

    //notification
    public void insertNotification(String title, String info, int imgId){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO NOTIFICATION VALUES(NULL, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, info);
        statement.bindDouble(3, imgId);

        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
