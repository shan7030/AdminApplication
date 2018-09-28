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

class Citizens_info
{
    public String userName;
    public String userAge;
    public String userEmail;
    Citizens_info()
    {

    }
}
public class DailyActivity extends AppCompatActivity {

    ListView listView;
    Vector<String> v=new Vector<>();
    String ket;
    Vector<String> v2=new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Daily_activity");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        String key=ds.getKey();
                        Log.v("DailyActivity",""+key);
                        for(DataSnapshot ds2:ds.getChildren())
                        {
                            stringerpasser2.key=ds2.getKey();
                            String visited=ds2.getValue(String.class);
                            if(visited.equals("Not Visited"))
                            {
                                DatabaseReference drt=FirebaseDatabase.getInstance().getReference().child("Citizens_info/"+key);
                                drt.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot21) {
                                        ket=dataSnapshot21.getKey();
                                    Citizens_info c=dataSnapshot21.getValue(Citizens_info.class);
                                    v.add(c.userEmail+"//Date :"+stringerpasser2.key);

                                        listView = (ListView) findViewById(R.id.listofdailyactivities);

                                        ArrayAdapter adapter = new ArrayAdapter<String>(DailyActivity.this, R.layout.singleitem, v);

                                        listView.setAdapter(adapter);
                                      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                              String selected=(String)adapterView.getItemAtPosition(i);
                                              Log.v("DoctorInside1",""+selected);
                                              stringerpasser2.key2=selected;
                                             // DatabaseReference dret=FirebaseDatabase.getInstance().getReference().child("");
                                              Log.v("DoctorInside1","Inside"+ket);
                                              String tobed="";
                                              int ik=0;
                                              while(selected.charAt(ik)!=':')
                                              {
                                                  ik++;
                                              }
                                              ik++;
                                              while(ik<selected.length())
                                              {
                                                  tobed=tobed+selected.charAt(ik);
                                                  ik++;
                                              }
                                              FirebaseDatabase.getInstance().getReference().child("Daily_activity/"+ket+"/"+tobed).setValue("Resolved");

                                              Intent appInfo = new Intent(DailyActivity.this, ResolveDaily.class);
                                              startActivity(appInfo);
                                          }
                                      });
                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
