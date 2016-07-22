package ir.adicom.app.dictionaryapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("DicDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS dic(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "enw TEXT, " +
                "faw TEXT );");
//        db.execSQL("INSERT INTO dic(enw,faw) VALUES('book', 'کتاب');");
//        db.execSQL("INSERT INTO dic(enw,faw) VALUES('black', 'سیاه');");
//        db.execSQL("INSERT INTO dic(enw,faw) VALUES('body', 'بدن');");
        c = db.rawQuery("SELECT * FROM dic", null);
        Log.e("TAG", "" + c.getCount());

        final TextView textView1 = (TextView) findViewById(R.id.txtEng);
        final TextView textView2 = (TextView) findViewById(R.id.txtFa);
        final EditText edt = (EditText) findViewById(R.id.editText);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String temp = editable.toString();
                StringBuffer b1 = new StringBuffer();
                StringBuffer b2 = new StringBuffer();
                if(temp.trim().length() != 0) {
                    db = openOrCreateDatabase("DicDB", Context.MODE_PRIVATE, null);
                    c = db.rawQuery("SELECT * FROM dic WHERE enw LIKE '" + editable.toString() + "%'", null);
                    if (c.getCount() == 0) {
                        Log.e("TAG", "No records found");
                        return;
                    }
                    while (c.moveToNext()) {
                        b1.append(c.getString(1) + "\n");
                        b2.append(c.getString(2) + "\n");
                    }
                    //Log.e("TAG", buffer.toString());
                }
                textView1.setText(b1);
                textView2.setText(b2);
            }
        });
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_word)
            startActivity(new Intent(this, ActivityAdd.class));
        return super.onOptionsItemSelected(item);
    }
}
