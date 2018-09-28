package com.example.shantanu.adminapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.newward:
                {
                    startActivity(new Intent(SecondActivity.this, Addnewward.class));

                    break;

                }
            case R.id.etrucker:{
                startActivity(new Intent(SecondActivity.this, Addnewtrucker.class));

                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void dailyactivity(View view)
    {
        Intent intent = new Intent(this, DailyActivity.class);
        startActivity(intent);
    }
    public void gotocollectgarbageondemand(View view)
    {


        Intent intent = new Intent(this, CollectgarbageonDemand.class);
        startActivity(intent);

    }
}
