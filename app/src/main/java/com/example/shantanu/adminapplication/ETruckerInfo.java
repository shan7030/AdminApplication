package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


class basicdesc
{
    public String Address;
    public int Cashback;
    public String datetime;
    public String extra;
    public String subject;
    basicdesc()
    {

    }
}
public class ETruckerInfo extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    String duid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etrucker_info);
        Log.v("ETruckerInfo",""+Stringpasser.wardselected);
        Log.v("ETruckerInfo",""+Stringpasser.storeid);

        t1=(TextView)findViewById(R.id.Empfname);
        t2=(TextView)findViewById(R.id.Empflname);
        t3=(TextView)findViewById(R.id.Empage);
        t4=(TextView)findViewById(R.id.Empcontactno);
        t5=(TextView)findViewById(R.id.Empfaddress);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+Stringpasser.wardselected+"/"+Stringpasser.storeid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DriverInfo di=new DriverInfo();
                di=dataSnapshot.getValue(DriverInfo.class);
                t1.setText("First Name :"+di.Firstname);
                t2.setText("Last Name :"+di.Lastname);
                t3.setText("Age :"+di.age);
                t4.setText("Contact Number :"+di.contactno);
                t5.setText("Address :"+di.address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void appointthis(View view)
    {
        DatabaseReference datar=FirebaseDatabase.getInstance().getReference().child("Current_Etrucker_Visits/"+Stringpasser.storeid);
        datar.setValue(Stringpasser.name);
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.name);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                detaileddescofcomplaints d=new detaileddescofcomplaints();

                d=dataSnapshot.getValue(detaileddescofcomplaints.class);
                 d.extra="Processing";
                DatabaseReference drpm=FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.name);
                drpm.setValue(d);
                duid=d.informeruid;
                DatabaseReference drt=FirebaseDatabase.getInstance().getReference().child("Description_of_Complaints/"+d.informeruid+"/"+Stringpasser.name);
                drt.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot45) {
                        basicdesc bl=new basicdesc();
                        bl=dataSnapshot45.getValue(basicdesc.class);
                        bl.extra="Proceeing";
                        FirebaseDatabase.getInstance().getReference().child("Description_of_Complaints/"+duid+"/"+Stringpasser.name).setValue(bl);
                        DatabaseReference fd=FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+Stringpasser.wardselected+"/"+Stringpasser.storeid);
                        fd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot56) {
                                 DriverInfo dpt=new DriverInfo();
                                 dpt=dataSnapshot56.getValue(DriverInfo.class);
                                 dpt.busy="Yes";
                                FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+Stringpasser.wardselected+"/"+Stringpasser.storeid).setValue(dpt);

                                Intent intent = new Intent(ETruckerInfo.this, CollectgarbageonDemand.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
