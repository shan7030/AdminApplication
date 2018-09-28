package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class Appointetrucker extends AppCompatActivity {
String addressofcomplaint;
    TextView t1;
    ListView listView;
    Vector<String> v=new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointetrucker);
      t1=(TextView)findViewById(R.id.io);
        t1.setText(Stringpasser.name);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.name);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    detaileddescofcomplaints dr=new detaileddescofcomplaints();
                    dr=dataSnapshot.getValue(detaileddescofcomplaints.class);
                     addressofcomplaint=dr.Address;

                    DatabaseReference drf=FirebaseDatabase.getInstance().getReference().child("Ward_Information");
                    drf.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {




                            String addp = "";
                            for (int i = 0; i < addressofcomplaint.length(); i++) {
                                if (addressofcomplaint.charAt(i) == ',') {
                                    addp = addp + addressofcomplaint.charAt(i);
                                } else if (addressofcomplaint.charAt(i) == '.') {
                                    addp = addp + addressofcomplaint.charAt(i);
                                } else if (addressofcomplaint.charAt(i) == ' ') {
                                    addp = addp + addressofcomplaint.charAt(i);
                                } else {
                                    char g = Character.toLowerCase(addressofcomplaint.charAt(i));
                                    addp = addp + g;

                                }
                            }
                            addressofcomplaint = addp;
                            String addressofward="";
                            for(DataSnapshot f:dataSnapshot.getChildren())
                            {
                                int flag=0;
                                addressofward=f.getKey();
                               for(int i=0;i<(addressofcomplaint.length()-addressofward.length());i++)
                               {
                                   int j=0;
                                   for(;j<addressofward.length();j++)
                                   {
                                       if(addressofcomplaint.charAt(i+j)!=addressofward.charAt(j))
                                       {
                                           break;
                                       }
                                   }
                                   if(j==addressofward.length())
                                   {
                                       flag=1;
                                       t1.setText("Nearest Ward :"+addressofward);
                                       Stringpasser.wardselected=addressofward;
                                       break;
                                   }
                               }
                               if(flag==1)
                               {
                                   break;
                               }


                            }
                            DatabaseReference drf=FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+addressofward);
                            drf.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshoter) {

                                    for(DataSnapshot ds:dataSnapshoter.getChildren()) {
                                        DriverInfo d = new DriverInfo();

                                        d = ds.getValue(DriverInfo.class);
                                        if (d.busy.equals("No")) {
                                            v.add(d.driverid);
                                        }
                                    }
                                    listView = (ListView) findViewById(R.id.listofetrucker);

                                    ArrayAdapter adapter = new ArrayAdapter<String>(Appointetrucker.this, R.layout.singleitem, v);

                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            String selected=(String)adapterView.getItemAtPosition(i);
                                            Stringpasser.storeid=selected;
                                            Intent intent = new Intent(Appointetrucker.this, ETruckerInfo.class);

                                            startActivity(intent);
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

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}
