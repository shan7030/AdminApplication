package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    HashMap<String,String> h=new HashMap<>();
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        h.put("Admin","Admin123");
        h.put("Shantanu","Shantanu123");
        e1=(EditText)findViewById(R.id.adminname);
        e2=(EditText)findViewById(R.id.adminpass);


    }
    public void openmaps(View view)
    {
        Double latitude=20.003805;
        Double longitude=73.760559;
       /*
       This is for showing the tracing from source to destination

       Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude));
        startActivity(intent);
     */
     /*
      This is for showing the resident address
      String label = "Cinnamon & Toast";
        String uriBegin = "geo:"+latitude+","+longitude;
        String query = latitude+","+longitude+"(" + label + ")";
        String encodedQuery = Uri.encode( query  );
        String uriString = uriBegin + "?q=" + encodedQuery;
        Uri uri = Uri.parse( uriString );
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
        startActivity( intent );*/
    }

    public void openthenext(View view)
    {
        nameandpassword.name=e1.getText().toString().trim();
        nameandpassword.pass=e2.getText().toString().trim();

        if(h.get(nameandpassword.name)!=null && h.get(nameandpassword.name).equals(nameandpassword.pass))
        {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Credentials!!", Toast.LENGTH_SHORT).show();
        }

    }
}
