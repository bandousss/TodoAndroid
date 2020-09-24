package mycompany.example.todolistsample;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TodoListSampleActivity2 extends ListActivity {
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_sample2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteOpenHelper helper = null;
        SQLiteDatabase database = null;

        try{
            helper = new TodoOpenHelper(getApplicationContext());
            database = helper.getReadableDatabase();

            cursor = database.query("TODO",null,null,null,null,null,null);


            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_row,
                    cursor,
                    new String[]{"title"},
                    new int[]{R.id.list_id});

            setListAdapter(adapter);

        }catch (Exception e){


        }finally {
            database.close();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }
}