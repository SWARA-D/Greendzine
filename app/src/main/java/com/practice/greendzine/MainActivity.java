package com.practice.greendzine;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userPassword = findViewById(R.id.Password);
        EditText userEmail = findViewById(R.id.Email);
        Button loginBtn = findViewById(R.id.Login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String userName = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }

                // calling a method to login our user.
                try {
                    loginUser(userName, password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void loginUser(String userName, String password) throws IOException {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.employee_data);
        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(sc.hasNextLine())
        {
            builder.append(sc.nextLine());
        }

        String jsonS = builder.toString();
        boolean flag = false;

        try {
            JSONObject obj = new JSONObject(jsonS);
            JSONArray employees = obj.getJSONArray("employee");

            for(int i = 0; i < employees.length(); i++)
            {
                JSONObject employee = employees.getJSONObject(i);
                String empEmail = employee.getString("email");
                String empPass = employee.getString("password");

                if(empEmail.equals(userName) && empPass.equals(password))
                {
                    flag = true;
                    String empId = employee.getString("EMP ID");
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("EMP ID", empId);
                    startActivity(intent);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(!flag)
        {
            Toast.makeText(MainActivity.this, "Invalid email and/or password", Toast.LENGTH_SHORT).show();
        }
    }

}