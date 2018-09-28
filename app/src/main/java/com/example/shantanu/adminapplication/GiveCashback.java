package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiveCashback extends AppCompatActivity {

    TextView t1,t2;
    Button b1;
    int coster=0;
    detaileddescofcomplaints d=new detaileddescofcomplaints();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_cashback);
        t1=(TextView)findViewById(R.id.actualweight);
        t2=(TextView)findViewById(R.id.amount);
        b1=(Button)findViewById(R.id.completeprocess);
        Log.v("GiveCashback",""+Stringpasser.storeid);
        Log.v("GiveCashback",""+Stringpasser.wardselected);
        Log.v("GiveCashback",""+Stringpasser.storeid);
        Log.v("GiveCashback",""+stringerpasser2.key);
        Log.v("GiveCashback",""+stringerpasser2.key2);
        Log.v("GiveCashback",""+Stringpasser3.date);
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser3.date);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                d=dataSnapshot.getValue(detaileddescofcomplaints.class);
                t1.setText("Actual amount of Weight Collected :"+d.subject);
                String cost=d.subject;
                Stringpasser3.key=d.informeruid;

                for(int i=0;i<cost.length();i++)
                {
                    if(cost.charAt(i)==' ')
                    {
                        break;
                    }
                    coster=coster*10+(cost.charAt(i)-48);
                }
                t2.setText("Gift for him is :"+(coster*3));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void completeprocess(View view)
    {

        Log.v("GiveCashback",""+Stringpasser3.key);
        DatabaseReference drf=FirebaseDatabase.getInstance().getReference().child("Citizens_info/"+Stringpasser3.key+"/userAge");

        drf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Stringpasser3.no=dataSnapshot.getValue(String.class);
                Log.v("GiveCashback",""+Stringpasser3.no);
                Log.v("GiveCashback",""+Stringpasser3.key);
                Log.v("GiveCashback",""+Stringpasser3.date);
                int finalcost=coster*3;
                String weight=""+coster;
               FirebaseDatabase.getInstance().getReference().child("Description_of_Complaints/"+Stringpasser3.key+"/"+Stringpasser3.date+"/Cashback").setValue(finalcost);
                FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser3.date+"/Cashback").setValue(1);
                FirebaseDatabase.getInstance().getReference().child("Description_of_Complaints/"+Stringpasser3.key+"/"+Stringpasser3.date+"/extra").setValue("Yes");
                FirebaseDatabase.getInstance().getReference().child("Description_of_Complaints/"+Stringpasser3.key+"/"+Stringpasser3.date+"/subject").setValue(weight);
               // FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser3.date).removeValue();

                Toast.makeText(getApplicationContext(), "Handled this Complaint Succesfully!!", Toast.LENGTH_SHORT).show();

                String hk=""+finalcost;
                String ft="CONGO !! You have recieved an Coupen worth Rs. : "+hk+" from E-Truckerservice !!";


                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+Stringpasser3.no));

                sendIntent.putExtra("sms_body",ft);
                startActivity(sendIntent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void gotomainer(View view)
    {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
