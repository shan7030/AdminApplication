package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailsofComplaints extends AppCompatActivity {

    ImageView img;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    detaileddescofcomplaints d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsof_complaints);
        firebaseStorage = FirebaseStorage.getInstance();
        t1=(TextView)findViewById(R.id.Dateandtime);
        t2=(TextView)findViewById(R.id.Cash_back);
        t3=(TextView)findViewById(R.id.amountofwaste);
        t4=(TextView)findViewById(R.id.Address);
        t5=(TextView)findViewById(R.id.Status_of_collection);
        img=(ImageView)findViewById(R.id.ivProfilePic);
        b1=(Button)findViewById(R.id.appointbutton);
        b2=(Button)findViewById(R.id.givecashback);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String da = dataSnapshot.getKey();
                Log.v("DetailsofComplaints", "Details of Complaints :" + da);
                d = dataSnapshot.getValue(detaileddescofcomplaints.class);

                if (d != null) {
                    detaileddescofcomplaints dop = new detaileddescofcomplaints();
                    dop = dataSnapshot.getValue(detaileddescofcomplaints.class);
                    // Log.v("DetailsofComplaints",""+dop.informeruid);
                    //Log.v("DetailsofComplaints",);
                    t1.setText(Stringpasser.name);
                    t2.setText("Cash-Back:" + dop.Cashback);

                    t3.setText("Amount of Waste:" + dop.subject);
                    t4.setText("Address :" + dop.Address);
                    t5.setText("Status of Collection:" + dop.extra);

                    if (dop.extra.equals("No")) {
                        b1.setVisibility(View.VISIBLE);
                    } else if (dop.extra.equals("Yes")) {
                        b2.setVisibility(View.VISIBLE);
                    }

                    if(dop.Cashback!=0)
                    {
                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.GONE);
                    }
                    StorageReference storageReference = firebaseStorage.getReference();
                    storageReference.child(dop.informeruid).child("Images/" + Stringpasser.name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).fit().centerCrop().into(img);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void appointthetrucker(View view)
    {
        Intent intent = new Intent(this, Appointetrucker.class);
        startActivity(intent);
    }

    public void showonmaps(View view)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                d=dataSnapshot.getValue(detaileddescofcomplaints.class);
                Double lat=d.Latitude;
                Double longp=d.Longitude;

                String label = "Address from where the garbage is to be collected!!";
                String uriBegin = "geo:"+lat+","+longp;
                String query = lat+","+longp+"(" + label + ")";
                String encodedQuery = Uri.encode( query  );
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                startActivity( intent );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void givecashback(View view)
    {
        Intent intent = new Intent(this, GiveCashback.class);
        startActivity(intent);
    }
}
