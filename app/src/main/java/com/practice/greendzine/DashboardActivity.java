package com.practice.greendzine;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class DashboardActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = this.getIntent();
        String emp_id = intent.getStringExtra("EMP ID");

        displayProgess(emp_id);

        ImageButton homeButton = findViewById(R.id.homeBtn);
        ImageButton userButton = findViewById(R.id.userBtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                intent.putExtra("EMP ID", emp_id);
                startActivity(intent);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                intent.putExtra("EMP ID", emp_id);
                startActivity(intent);
            }
        });
    }

    private void displayProgess(String emp_id) {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.employee_progress);
        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(sc.hasNextLine())
        {
            builder.append(sc.nextLine());
        }

        String jsonS = builder.toString();

        try {
            JSONObject obj = new JSONObject(jsonS);
            JSONArray progress = obj.getJSONArray("progress");

            for(int i = 0; i < progress.length(); i++)
            {
                JSONObject empProg = progress.getJSONObject(i);
                String empID = empProg.getString("EMP ID");

                if(empID.equals(emp_id))
                {
                    textView = findViewById(R.id.monPer);
                    progressBar = findViewById(R.id.monProg);
                    int empProgress = empProg.getInt("monday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);

                    textView = findViewById(R.id.tuePer);
                    progressBar = findViewById(R.id.tueProg);
                    empProgress = empProg.getInt("tuesday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);

                    textView = findViewById(R.id.wedPer);
                    progressBar = findViewById(R.id.wedProg);
                    empProgress = empProg.getInt("wednesday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);

                    textView = findViewById(R.id.thurPer);
                    progressBar = findViewById(R.id.thurProg);
                    empProgress = empProg.getInt("thursday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);

                    textView = findViewById(R.id.friPer);
                    progressBar = findViewById(R.id.friProg);
                    empProgress = empProg.getInt("friday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);

                    textView = findViewById(R.id.satPer);
                    progressBar = findViewById(R.id.satProg);
                    empProgress = empProg.getInt("saturday");
                    textView.setText("" + empProgress + "%");
                    progressBar.setProgress(empProgress);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
