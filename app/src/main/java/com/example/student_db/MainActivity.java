package com.example.student_db;



import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editUSN, editName, editMarks;
    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editUSN = findViewById(R.id.editUSN);
        editName = findViewById(R.id.editName);
        editMarks = findViewById(R.id.editMarks);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        addData();
        viewAllData();
    }

    public void addData() {
        btnAdd.setOnClickListener(v -> {
            boolean isInserted = myDb.insertData(
                    editUSN.getText().toString(),
                    editName.getText().toString(),
                    editMarks.getText().toString()
            );

            if (isInserted)
                Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
        });
    }

    public void viewAllData() {
        btnView.setOnClickListener(v -> {
            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                showMessage("Error", "No data found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append("USN : ").append(res.getString(0)).append("\n");
                buffer.append("Name : ").append(res.getString(1)).append("\n");
                buffer.append("Marks : ").append(res.getString(2)).append("\n\n");
            }

            showMessage("Student Records", buffer.toString());
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}