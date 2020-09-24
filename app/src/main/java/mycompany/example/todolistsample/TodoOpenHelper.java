package mycompany.example.todolistsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;



//以下、データベースを扱うためのクラス



public class TodoOpenHelper extends SQLiteOpenHelper {

    //データベースの作成。今回はタイトルとコンテントの作成。
    private static final String FILE_NAME = "data";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE `TODO` (" +
            "`_id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`title` TEXT, `content` TEXT" +
            ");";

    public TodoOpenHelper(Context context){
        super(context,FILE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);//テーブルの作成

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
