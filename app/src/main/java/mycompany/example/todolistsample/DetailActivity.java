package mycompany.example.todolistsample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }


    @Override
    protected void onResume() {
        super.onResume();
        long id = getIntent().getLongExtra("id",0);

        SQLiteOpenHelper helper = null;
        SQLiteDatabase detabase = null;
        Cursor cursor = null;


        try{
            helper = new TodoOpenHelper(getApplicationContext());
            detabase = helper.getReadableDatabase();

            cursor = detabase.query("TODO",
                    null,"_id=?",new String[]{String.valueOf(id)},
                    null,null,null);
            if(cursor.moveToFirst()){
                String strTitle =
                        cursor.getString(cursor.getColumnIndex("title"));
                String strContent =
                        cursor.getString(cursor.getColumnIndex("content"));

                EditText titleView = findViewById(R.id.text_title);
                EditText contentView = findViewById(R.id.text_content);

                titleView.setText(strTitle);
                contentView.setText(strContent);
            }
        }catch (Exception e){
            Log.e(this.getClass().getSimpleName(),"DBエラー",e);
        }finally {
            cursor.close();
            detabase.close();
        }

        //削除する場合の処理
        findViewById(R.id.btn_delete).setOnClickListener((view) -> {
            SQLiteOpenHelper deleteHelper = null;
            SQLiteDatabase deleteDB = null;

            try{
                deleteHelper = new TodoOpenHelper(this);
                deleteDB = deleteHelper.getWritableDatabase();

                int deleteCount = deleteDB.delete("TODO","_id=?",
                        new String[]{String.valueOf(
                                getIntent().getLongExtra("id",0))});

                if (deleteCount == 1){
                    Toast.makeText(DetailActivity.this,
                            R.string.lbl_delete_success, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,
                            R.string.lbl_delete_failed,Toast.LENGTH_SHORT).show();
                }
                finish();
            }catch (Exception e){
                Log.e(getLocalClassName(),"DBエラー",e);
            }finally {
                if (deleteDB != null){
                    deleteDB.close();
                }
            }
        });

        //更新した場合
        findViewById(R.id.btn_update).setOnClickListener((view) -> {
          SQLiteOpenHelper updateHelper = null;
          SQLiteDatabase updateDB = null;

          String title =
                  ((EditText) findViewById(R.id.text_title)).getText().toString();
          String content =
                  ((EditText) findViewById(R.id.text_content)).getText().toString();

          try{
              updateHelper = new TodoOpenHelper(this);
              updateDB = updateHelper.getWritableDatabase();

              ContentValues cv = new ContentValues();
              cv.put("title",title);
              cv.put("content",content);

              int updateCount = updateDB.update("TODO",cv,"_id=?",
                      new String[]{String.valueOf(
                              getIntent().getLongExtra("id",0))});

              if (updateCount == 1){
                  Toast.makeText(this,
                          R.string.lbl_update_failed, Toast.LENGTH_SHORT).show();
              }
              finish();
          }catch (Exception e){
              Log.e(getLocalClassName(),"DBエラー",e);
          }finally {
              if (updateDB != null){
                  updateDB.close();
              }
          }

        });


    }
}