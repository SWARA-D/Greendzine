package com.practice.greendzine;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = this.getIntent();
        String emp_id = intent.getStringExtra("EMP ID");

        setInitialText();

        ImageButton searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchedName = findViewById(R.id.searchBar);
                String empName = searchedName.getText().toString();

                searchEmployee(empName);
            }
        });

        ImageButton homeButton = findViewById(R.id.homeBtn);
        ImageButton userButton = findViewById(R.id.userBtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
                intent.putExtra("EMP ID", emp_id);
                startActivity(intent);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                intent.putExtra("EMP ID", emp_id);
                startActivity(intent);
            }
        });
    }

    private void setInitialText() {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.employee_data);
        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (sc.hasNextLine()) {
            builder.append(sc.nextLine());
        }

        String jsonS = builder.toString();

        try {
            JSONObject obj = new JSONObject(jsonS);
            JSONArray employees = obj.getJSONArray("employee");

            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                TextView textView;

                if(i==0) {
                    textView = findViewById(R.id.employee1);
                }
                else
                {
                    textView = findViewById(R.id.employee2);
                }
                String s1 = "<font color=" + Color.LTGRAY + ">EMP ID : </font><font color="
                        + Color.WHITE + ">" + employee.getString("EMP ID") + "</font><br><br>";
                String s2 = "<font color=" + Color.LTGRAY + ">Name : </font><font color="
                        + Color.WHITE + ">" + employee.getString("Name") + "</font><br><br>";
                String s3 = "<font color=" + Color.LTGRAY + ">DOB : </font><font color= #FFA500>"
                        + employee.getString("DOB") + "</font><br><br>";
                String s4 = "<font color=" + Color.LTGRAY + ">Role : </font><font color= #36A546>"
                        + employee.getString("Role") + "</font><br><br>";

                String result = s1 + "\n" + s2 + "\n" + s3 + "\n" + s4;
                textView.setText(Html.fromHtml(result));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployee(String empName)
    {
        TextView textRemove1 = findViewById(R.id.employee1);
        textRemove1.setText("");

        TextView textRemove2 = findViewById(R.id.employee2);
        textRemove2.setText("");

        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.employee_data);
        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (sc.hasNextLine()) {
            builder.append(sc.nextLine());
        }

        String jsonS = builder.toString();
        boolean flag = false;

        try {
            JSONObject obj = new JSONObject(jsonS);
            JSONArray employees = obj.getJSONArray("employee");

            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                TextView textView = findViewById(R.id.employee1);

                if(employee.getString("Name").equals(empName)) {
                    flag = true;
                    String s1 = "<font color=" + Color.LTGRAY + ">EMP ID : </font><font color="
                            + Color.WHITE + ">" + employee.getString("EMP ID") + "</font><br><br>";
                    String s2 = "<font color=" + Color.LTGRAY + ">Name : </font><font color="
                            + Color.WHITE + ">" + employee.getString("Name") + "</font><br><br>";
                    String s3 = "<font color=" + Color.LTGRAY + ">DOB : </font><font color= #FFA500>"
                            + employee.getString("DOB") + "</font><br><br>";
                    String s4 = "<font color=" + Color.LTGRAY + ">Role : </font><font color= #36A546>"
                            + employee.getString("Role") + "</font><br><br>";

                    String result = s1 + "\n" + s2 + "\n" + s3 + "\n" + s4;
                    textView.setText(Html.fromHtml(result));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!flag)
        {
            Toast.makeText(SearchActivity.this, "No user found.", Toast.LENGTH_SHORT).show();
        }
    }
}
