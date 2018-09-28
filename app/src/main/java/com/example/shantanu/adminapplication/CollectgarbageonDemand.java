package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

class detaileddescofcomplaints
{
    public String subject;
    public String Address;
    public String informeruid;
    public Double Latitude;
    public Double Longitude;
    public String datetime;
    public String extra;
    public int Cashback;

    detaileddescofcomplaints()
    {

    }
}

public class CollectgarbageonDemand extends AppCompatActivity {

    Vector<String> v=new Vector<>();
    ListView listView;
    detaileddescofcomplaints d=new detaileddescofcomplaints();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectgarbageon_demand);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // String key=dataSnapshot.getKey();
            for(DataSnapshot dsr:dataSnapshot.getChildren()) {

                d=dsr.getValue(detaileddescofcomplaints.class);
                Log.v("CollectgarbageonDemand",""+d.Address);
                    if(d.Cashback==0) {
                        v.add(d.datetime);
                    }
            }


                listView = (ListView) findViewById(R.id.listofcomplaints);

                ArrayAdapter adapter = new ArrayAdapter<String>(CollectgarbageonDemand.this, R.layout.singleitem, v);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                        String selected=(String)adapterView.getItemAtPosition(i);

                                                        Stringpasser.name=selected;
                                                        Stringpasser3.date=selected;
                                                        Intent appInfo = new Intent(CollectgarbageonDemand.this, DetailsofComplaints.class);
                                                        startActivity(appInfo);

                                                    }
                                                }
                );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
