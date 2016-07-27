package wsu.csc5991.trustcircle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.DecimalFormat;

import wsu.csc5991.trustcircle.task.RecordEventTask;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class to handle all the buttons for configuring a circle
 */
public class ActCircleConfig extends ActBase {
    Button buttonJoinCircle;
    Button buttonCircleSignUp;
    Button buttonEditCircle;
    Button buttonDeleteCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laycircleconfig);
        ((LinearLayout)findViewById(R.id.LayCircleConfig)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        /*buttonSignIn = (Button) findViewById(R.id.buttonSignIn);*/
        buttonJoinCircle = (Button) findViewById(R.id.buttonJoinCircle);
        buttonCircleSignUp = (Button) findViewById(R.id.buttonCircleSignUp);
        buttonEditCircle = (Button) findViewById(R.id.buttonEditCircle);
        buttonDeleteCircle = (Button) findViewById(R.id.buttonDeleteCircle);

        buttonJoinCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActJoinCircle.class);
                startActivity(createIntent(i));
            }
        });
        buttonCircleSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActCircleSignUp.class);
                startActivity(createIntent(i));
            }
        });
        buttonEditCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActEditCircle.class);
                startActivity(createIntent(i));
            }
        });
        buttonDeleteCircle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActDeleteCircle.class);
                startActivity(createIntent(i));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("### DEBUG ### onStart");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }


    private Intent createIntent(Intent i){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            i.putExtra("member_first_name", extras.getString("member_first_name"));
            i.putExtra("member_last_name", extras.getString("member_last_name"));
            i.putExtra("member_mobile_number", extras.getString("member_mobile_number"));
            i.putExtra("member_pin", extras.getInt("member_pin"));
            i.putExtra("circle_name", extras.getString("circle_name"));
        }
        return i;
    }

}
