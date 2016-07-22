package ir.adicom.app.dictionaryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityAdd extends AppCompatActivity {
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText edtEnWord = (EditText) findViewById(R.id.editText2);
        final EditText edtFaWord = (EditText) findViewById(R.id.editText3);
        Button btn = (Button) findViewById(R.id.button);

        db = openOrCreateDatabase("DicDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS dic(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "enw TEXT, " +
                "faw TEXT );");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = edtEnWord.getText().toString();
                String t2 = edtFaWord.getText().toString();
                db = openOrCreateDatabase("DicDB", Context.MODE_PRIVATE, null);
                db.execSQL("INSERT INTO dic(enw,faw) VALUES('"+t1+"', '"+t2+"');");
                edtEnWord.setText("");
                edtFaWord.setText("");
            }
        });
        db.close();

    }
}
