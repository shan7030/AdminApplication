package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResolveDaily extends AppCompatActivity {

    private String emailid;
    private Double lat;
    private Double longp;
    TextView t1,t2,t3;
    String addressofcitizen;
    String wardname;
    String date12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_daily);
        date12=stringerpasser2.key2;
        TextView ty=(TextView)findViewById(R.id.namer);
        ty.setText(date12);
        t1=(TextView)findViewById(R.id.Address);
        t2=(TextView)findViewById(R.id.nearestward);
        t3=(TextView)findViewById(R.id.Contactnumberward);
        emailid="";
        Log.v("ResolveDaily",""+stringerpasser2.key2);
        for(int i=0;i<stringerpasser2.key2.length();i++)
        {
            if(stringerpasser2.key2.charAt(i)=='/')
            {
                break;
            }
            else
            {
                emailid=emailid+stringerpasser2.key2.charAt(i);
            }
        }
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Citizens_info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Citizens_info c=ds.getValue(Citizens_info.class);
                    if(c.userEmail.equals(emailid))
                    {
                        stringerpasser2.key2=ds.getKey();
                        Log.v("ResolveDaily","Inside :"+stringerpasser2.key2);
                        DatabaseReference dsf=FirebaseDatabase.getInstance().getReference().child("Address_of_citizens/"+stringerpasser2.key2);
                        dsf.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {

                                        LatlongAdd.Address=dataSnapshot.child("Address").getValue(String.class);
                                       Log.v("ResolveDaily",""+LatlongAdd.Address);
                                       LatlongAdd.Latitude=dataSnapshot.child("Latitude").getValue(Double.class);
                                       LatlongAdd.Longitude=dataSnapshot.child("Longitude").getValue(Double.class);
                                       t1.setText("Address of Complaint :"+LatlongAdd.Address);
                                       addressofcitizen=LatlongAdd.Address;
                                       lat=LatlongAdd.Latitude;
                                        longp=LatlongAdd.Longitude;
                                       Button button=(Button)findViewById(R.id.viewaddress1);
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {

                                        String label = "Address of Complainer";
                                        String uriBegin = "geo:"+lat+","+longp;
                                        String query = lat+","+longp+"(" + label + ")";
                                        String encodedQuery = Uri.encode( query  );
                                        String uriString = uriBegin + "?q=" + encodedQuery;
                                        Uri uri = Uri.parse( uriString );
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                                        startActivity( intent );
                                    }
                                });


                                DatabaseReference databaseReference12=FirebaseDatabase.getInstance().getReference().child("Ward_Information");
                                databaseReference12.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot34) {


                                        for(DataSnapshot dsre:dataSnapshot34.getChildren()) {

                                            //   h=dataSnapshot34.getValue(HashMap.class);
                                            //for (DataSnapshot dfi : dsre.getChildren()) {
                                               // String keyeye=dfi.getKey();
                                                    String add=dsre.child("Wardname").getValue(String.class);
                                                    String contactus=dsre.child("phoneno").getValue(String.class);

                                                Log.v("ResolveDaily","keyeye"+add);

                                                String addp = "";
                                                for (int i = 0; i < addressofcitizen.length(); i++) {
                                                    if (addressofcitizen.charAt(i) == ',') {
                                                        addp = addp + addressofcitizen.charAt(i);
                                                    } else if (addressofcitizen.charAt(i) == '.') {
                                                        addp = addp + addressofcitizen.charAt(i);
                                                    } else if (addressofcitizen.charAt(i) == ' ') {
                                                        addp = addp + addressofcitizen.charAt(i);
                                                    } else {
                                                        char g = Character.toLowerCase(addressofcitizen.charAt(i));
                                                        addp = addp + g;

                                                    }
                                                }
                                                addressofcitizen = addp;
                                                Log.v("ResolveDaily", "errt" + add);
                                                Log.v("ResolveDaily", "errt" + addressofcitizen);
                                                for (int i = 0; i < (addressofcitizen.length()-add.length()); i++) {
                                                    int j = 0;
                                                    for (; j < add.length(); j++) {

                                                        if (addressofcitizen.charAt(i + j) == (add.charAt(j))) {

                                                        }
                                                        else
                                                        {
                                                            break;
                                                        }
                                                    }

                                                    if (j == add.length()) {
                                                        t2.setText("Nearest Ward from citizens Location :" + add);
                                                        t3.setText("Contact Nearest Ward using :"+contactus);
                                                        break;
                                                    }
                                                }

                                           // }
                                        }
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
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.v("ResolveDaily",""+addressofcitizen);
       /* String add="";
        for(int i=0;i<addressofcitizen.length();i++)
        {
            if(addressofcitizen.charAt(i)==',')
            {
                add=add+addressofcitizen.charAt(i);
            }
            else if(addressofcitizen.charAt(i)=='.')
            {
                add=add+addressofcitizen.charAt(i);
            }
            else if(addressofcitizen.charAt(i)==' ')
            {
                add=add+addressofcitizen.charAt(i);
            }
            else
            {
                char g=Character.toLowerCase(addressofcitizen.charAt(i));
                add=add+g;

            }
        }
        addressofcitizen=add;
        DatabaseReference databaseReference12=FirebaseDatabase.getInstance().getReference().child("Ward_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot dsre:dataSnapshot.getChildren())
            {
                String add=dataSnapshot.getKey();
                for(int i=0;i<addressofcitizen.length();i++)
                {
                    int j=0;
                    for(;j<add.length();i++)
                    {
                        if(addressofcitizen.charAt(i+j)!=add.charAt(j))
                        {
                            break;
                        }
                    }

                    if(j==add.length())
                    {
                        t2.setText("Nearest Ward from citizens Location :"+add);
                        wardname=add;
                        break;
                    }
                }

            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
    }

    public void gotophone(View view)
    {
       Intent intent = new Intent(Intent.ACTION_DIAL);
        String jk=((TextView)findViewById(R.id.Contactnumberward)).getText().toString().trim();
        String h="";
        int i=0;
        for(;i<jk.length();i++)
        {
            if(jk.charAt(i)==':')
            {
                i++;
                break;
            }
        }

        while(i<jk.length())
        {
            h=h+jk.charAt(i);
            i++;
        }
        /*
        intent.setData(Uri.parse("tel:"+h));
        startActivity(intent);*/
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"+h));
        String ft=((TextView)findViewById(R.id.Address)).getText().toString().trim();
        sendIntent.putExtra("sms_body",ft+"RESOLVE THE DAILY ACTIVITY SOON !!");
        startActivity(sendIntent);



    }
}
