package com.example.shantanu.adminapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


class DriverInfo
{
    public String Wardname;
    public String Firstname;
    public String Lastname;
    public String age;
    public String driverid;
    public String address;
    public String contactno;
    public String busy;
}
class ispresent
{
    public static int pre=0;
}
public class Addnewtrucker extends AppCompatActivity {
    private String Wardname;
    private String Firstname;
    private String Lastname;
    private String age;
    private String id;
    private String address;
    private String contactno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewtrucker);
    }

    public void addtrucker(View view)
    {

        Wardname=((EditText)findViewById(R.id.wardname)).getText().toString().trim();
        String add="";
        for(int i=0;i<Wardname.length();i++)
        {
            if(Wardname.charAt(i)==',')
            {
                add=add+Wardname.charAt(i);
            }
            else if(Wardname.charAt(i)=='.')
            {
                add=add+Wardname.charAt(i);
            }
            else
            {
                char g=Character.toLowerCase(Wardname.charAt(i));
                add=add+g;

            }
        }
        Wardname=add;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Ward_Information");
      databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int flag=0;
                for(DataSnapshot dsf:dataSnapshot.getChildren())
                {
                    String wardif=dsf.getKey();
                    if(wardif.equals(Wardname))
                    {
                        flag=1;
                        ispresent.pre=1;
                        break;
                    }

                }
                if(flag==0)
                {
                    ispresent.pre=0;
                    Toast.makeText(Addnewtrucker.this,"Please enter valid Ward Name present in the database!!",Toast.LENGTH_SHORT).show();
                     return;
                }
            }


        @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(ispresent.pre==0)
        {
            return;
        }

        Firstname=((EditText)findViewById(R.id.fname1)).getText().toString().trim();
        Lastname=((EditText)findViewById(R.id.lname1)).getText().toString().trim();

        age = ((EditText) findViewById(R.id.age1)).getText().toString().trim();

        for(int i=0;i<age.length();i++)
        {
            if(age.charAt(i)<48 || age.charAt(i)>57)
            {
                Toast.makeText(this,"Please enter valid age!",Toast.LENGTH_SHORT).show();
                return;
            }

        }

        id=((EditText)findViewById(R.id.idofdriver)).getText().toString().trim();
        address=((EditText)findViewById(R.id.address1)).getText().toString().trim();
        contactno=((EditText)findViewById(R.id.contact1)).getText().toString().trim();

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
        DriverInfo di=new DriverInfo();
        di.Wardname=Wardname;
        di.Firstname=Firstname;
        di.Lastname=Lastname;
        di.contactno=contactno;
        di.address=address;
        di.age=age;
        di.driverid=id;
        di.busy="No";

        DatabaseReference drt=FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+Wardname+"/"+id);
        drt.setValue(di);
        Toast.makeText(this,"Driver Inserted !!",Toast.LENGTH_SHORT).show();



    }
}