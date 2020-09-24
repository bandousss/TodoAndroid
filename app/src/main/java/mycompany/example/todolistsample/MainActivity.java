package mycompany.example.todolistsample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//ボタンが押された時に登録をするクラス.演習テキストp100


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {//登録ボタンが押された時の処理
        super.onResume();

        View btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//画面遷移
                Intent intent = new Intent(MainActivity.this,TodoListSampleActivity2.class);
                startActivity(intent);
            }
        });


        View btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override//入力された内容の取得
            public void onClick(View view) {
                EditText editTitle = findViewById(R.id.edit_title);
                EditText editContent = findViewById(R.id.edit_content);

                //入力された内容の登録
                SQLiteOpenHelper helper = null;
                SQLiteDatabase database = null;

                try{
                    helper = new TodoOpenHelper(getApplicationContext());
                    database = helper.getWritableDatabase();

                    //データベースに登録
                    ContentValues cv = new ContentValues();
                    cv.put("title",editTitle.getText().toString());
                    cv.put("content",editContent.getText().toString());

                    database.insert("TODO",null,cv);

                }catch (Exception e){
                    //最新のクラス名の取得。今回はMainActivity.java
                    Log.e(this.getClass().getSimpleName(),"DB エラー", e);
                }finally {
                    database.close();
                }

                Toast.makeText(MainActivity.this,
                        "登録しました",Toast.LENGTH_LONG);

            }
        });

    }
}