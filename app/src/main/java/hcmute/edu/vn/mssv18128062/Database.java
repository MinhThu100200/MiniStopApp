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
        values.put("ROLE", user.getRole()); //role

        // Inserting Row
        db.insert("USERS", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single user
    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("USERS", new String[] { "ID",
                        "USERNAME", "PASSWORD", "EMAIL", "ROLE" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));
        // return user
        return user;
    }
    // code to get all users in a list view
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM USERS";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setRole(Integer.parseInt(cursor.getString(4)));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }
    // code to update the single contact
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("USERNAME", user.getUsername());
        values.put("PASSWORD", user.getPassword());
        values.put("EMAIL", user.getEmail());
        values.put("ROLE", user.getRole());

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
    public void insertCategory(String name, byte[] picture){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO CATEGORY VALUES(NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindBlob(2, picture);

        statement.executeInsert();
    }

    // code to update the single contact
    public void updateCategory(int id, String name, byte[] picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE CATEGORY SET NAME=?, PICTURE=? WHERE ID=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindBlob(2, picture);
        statement.bindDouble(3, id);

        statement.executeUpdateDelete();
    }

    // Deleting single user
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
    public void createProduct(SQLiteDatabase db)
    {
        //CREATE USERTABLE
        String CREATE_USERS_TABLE = "CREATE TABLE PRODUCT( ID INTEGER PRIMARY KEY, NAME TEXT, PRICE FLOAT, DESCRIPTION TEXT, PICTURE BLOB, ID_CATEGORY INTEGER)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    //insertUser
    public void insertProduct(String name, float price, String description, byte[] picture, int idCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO PRODUCT VALUES(NULL, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, description);
        statement.bindBlob(4, picture);
        statement.bindDouble(5, idCategory);

        statement.executeInsert();
    }

    public void updateProduct(int id, String name, float price, String description, byte[] picture, int idCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE PRODUCT SET NAME=?, PRICE=?, DESCRIPTION=?, PICTURE=?, ID_CATEGORY=? WHERE ID=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, description);
        statement.bindBlob(4, picture);
        statement.bindDouble(5, idCategory);
        statement.bindDouble(6, id);

        statement.executeUpdateDelete();
    }

    // Deleting single user
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM PRODUCT WHERE ID=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, id);
        statement.executeUpdateDelete();
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
    public void insertAddress(String description, byte[] picture){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO ADDRESS VALUES(NULL, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, description);
        statement.bindBlob(2, picture);

        statement.executeInsert();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
