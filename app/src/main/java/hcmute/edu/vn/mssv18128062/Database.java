package hcmute.edu.vn.mssv18128062;

import android.content.ContentValues;
import android.content.Context;
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


    // code to update the single contact
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USERNAME", user.getUsername()); // Username
        values.put("PASSWORD", user.getPassword()); // Password
        values.put("EMAIL", user.getEmail()); //email
        values.put("NAME", user.get_name());
        values.put("PHONE", user.get_phone_number());
        values.put("POINT", user.get_point());
        values.put("PICTURE", user.get_picture());


        // updating row
        return db.update("USERS", values, "ID" + " = ?",
                new String[] { String.valueOf(user.getID()) });
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
    //insertUser
    public void insertAddress(String description, int picture){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO ADDRESS VALUES(NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, description);
        statement.bindDouble(2, picture);

        statement.executeInsert();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
