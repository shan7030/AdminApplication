package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

class wardinfo
{
    public String Address;
    public Double Latitude;
    public Double Longitude;
    public String Wardname;
    public String phoneno;
    wardinfo()
    {

    }
}
public class Addnewward extends AppCompatActivity {

    private TextView t1;
    private EditText e1,e2;
    int PLACE_PICKER_REQUEST = 1;
    LatLng latlong;
    wardinfo wi=new wardinfo();
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewward);
        e1=(EditText) findViewById(R.id.wardname);
        t1=(TextView) findViewById(R.id.addressofward);
        e2=(EditText)findViewById(R.id.contactno);
        t1.setVisibility(View.GONE);
    }

    public void openmapper(View view)
    {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try
        {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        }
        catch(GooglePlayServicesRepairableException e)
        {
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {





        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                latlong= place.getLatLng();
                address= (String) place.getAddress();
                t1.setText("Address Selected :"  +place.getAddress());
                t1.setVisibility(View.VISIBLE);
            }

        }









    }
    public void addwardonclick(View view)
    {

        wi.Address=address;
        wi.Latitude=latlong.latitude;
        wi.Longitude=latlong.longitude;
        wi.Wardname=e1.getText().toString().trim();
        wi.phoneno=e2.getText().toString().trim();
        String contactno=wi.phoneno;
        if(wi.Wardname.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Wardname!!", Toast.LENGTH_SHORT).show();

            return;
        }

        if(contactno.length()!=10)
        {
            Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<contactno.length();i++)
        {
            if(contactno.charAt(i)<48 || contactno.charAt(i)>57)
            {
                Toast.makeText(this,"Please enter valid Contact number !",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if(wi.Address.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Address!!", Toast.LENGTH_SHORT).show();

            return;
        }
        String add="";
        for(int i=0;i<wi.Wardname.length();i++)
        {
            if(wi.Wardname.charAt(i)==',')
            {
                add=add+wi.Wardname.charAt(i);
            }
            else if(wi.Wardname.charAt(i)=='.')
            {
                add=add+wi.Wardname.charAt(i);
            }
            else
            {
                char g=Character.toLowerCase( wi.Wardname.charAt(i));
                add=add+g;

            }
        }
        wi.Wardname=add;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Ward_Information/"+wi.Wardname);
        databaseReference.setValue(wi);

        Toast.makeText(getApplicationContext(), "Ward added successfully!!", Toast.LENGTH_SHORT).show();



    }
}
