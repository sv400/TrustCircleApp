package wsu.csc5991.trustcircle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import wsu.csc5991.trustcircle.task.RecordEventTask;
import wsu.csc5991.trustcircle.vo.Event;
import wsu.csc5991.trustcircle.vo.Member;

/**
 * Class to display signed in member's circle name and circle member's latitude and longitude
 */
public class ActDisplayCircle extends ActBase {

    TableLayout memberTable;
    TextView circleName;
    Member member;

    // Declare GPS constants
    private final float MIN_DISTANCE_BETWEEN_UPDATES = 1f;  // Meters
    private final long MIN_TIME_BETWEEN_UPDATES = 3600000;  // 1hr Milliseconds
    private final DecimalFormat FORMAT_COORDINATE = new DecimalFormat("0.000000");

    // Declare location variables
    private Location currentLocation;  // Set from GPS sensor
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydisplaycircle);
        ((LinearLayout)findViewById(R.id.LayDisplayCircle)).setBackgroundColor(Util.Shared.Data.backgroundColor);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();

        memberTable = (TableLayout) findViewById(R.id.MemberTable);
        circleName = (TextView) findViewById(R.id.circleName);
        circleName.setText(extras.getString("circle_name"));

        init();

        member = new Member();
        member.setPin(extras.getInt("member_pin"));
        member.setMobileNumber(extras.getString("member_mobile_number"));

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Start location manager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission not granted");
            return;
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_BETWEEN_UPDATES,
            locationListener);

    }

    //----------------------------------------------------------------
    // Creates the table to display circle member's latitude and longitude
    //----------------------------------------------------------------
    public void init() {
        memberTable.setStretchAllColumns(true);
        memberTable.bringToFront();

        addHeader();

        Bundle args = getIntent().getBundleExtra("memberBundle");
        ArrayList<Member> memberList = (ArrayList<Member>) args.getSerializable("memberList");

        Integer count = 0;
        String name = null;
        String latitude = null;
        String longitude = null;

        for (Member member : memberList) {
            name = member.getFirstName() + " " + member.getLastName();
            List<Event> eventList = member.getEvents();
            if(eventList != null) {
                for (Event event : eventList) {
                    String[] parts = event.getValue().split(",");
                    latitude = parts[0];
                    longitude = parts[1];
                    addRow(count, name, latitude, longitude);
                    count++;
                }
            }
        }
    }

    //----------------------------------------------------------------
    // Adds table header
    //----------------------------------------------------------------
    private void addHeader() {
        TableRow tr_head = new TableRow(this);
        tr_head.setBackgroundColor(getResources().getColor(R.color.blue));
        tr_head.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        TextView label_name = new TextView(this);
        label_name.setText("NAME");
        label_name.setTextColor(Color.WHITE);
        label_name.setPadding(5, 5, 5, 5);
        label_name.setTextSize(18);
        tr_head.addView(label_name);

        TextView label_latitude = new TextView(this);
        label_latitude.setText("LATITUDE");
        label_latitude.setTextColor(Color.WHITE);
        label_latitude.setPadding(5, 5, 5, 5);
        label_latitude.setTextSize(18);
        tr_head.addView(label_latitude);

        TextView label_longitude = new TextView(this);
        label_longitude.setText("LONGITUDE");
        label_longitude.setTextColor(Color.WHITE);
        label_longitude.setPadding(5, 5, 5, 5);
        label_longitude.setTextSize(18);
        tr_head.addView(label_longitude);

        memberTable.addView(tr_head, new TableLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
    }

    //----------------------------------------------------------------
    // Adds table row
    //----------------------------------------------------------------
    private void addRow(int count, String name, String latitude, String longitude) {
        TableRow tr = new TableRow(this);
        if(count%2!=0) {
            tr.setBackgroundColor(getResources().getColor(R.color.light_blue));
        } else {
            tr.setBackgroundColor(getResources().getColor(R.color.white));
        }
        tr.setId(100+count);
        tr.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        TextView labelName = new TextView(this);
        labelName.setText(name);
        labelName.setPadding(2, 0, 5, 0);
        labelName.setTextColor(Color.BLACK);
        labelName.setTextSize(16);
        tr.addView(labelName);

        TextView labelLatitude = new TextView(this);
        labelLatitude.setText(latitude);
        labelLatitude.setTextColor(Color.BLACK);
        labelLatitude.setTextSize(16);
        tr.addView(labelLatitude);

        TextView labelLongitude = new TextView(this);
        labelLongitude.setText(longitude);
        labelLongitude.setTextColor(Color.BLACK);
        labelLongitude.setTextSize(16);
        tr.addView(labelLongitude);

        memberTable.addView(tr, new TableLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
    }


    //----------------------------------------------------------------
    // locationListener
    //----------------------------------------------------------------
    public LocationListener locationListener = new LocationListener() {

        //------------------------------------------------------------
        // onLocationChanged
        //------------------------------------------------------------
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            System.out.println("On Location Changed Method ");
            if(member != null && member.getMobileNumber() !=null && !member.getMobileNumber().isEmpty()){
                String locationStr = FORMAT_COORDINATE.format(currentLocation.getLatitude()) + "," + FORMAT_COORDINATE.format(currentLocation.getLongitude());
                String[] arr = {String.valueOf(member.getMobileNumber()), String.valueOf(member.getPin()) , locationStr, getResources().getString(R.string.rest_service_url).toString()};
                new RecordEventTask().execute(arr);
            }
        }

        //------------------------------------------------------------
        // onStatusChanged
        //------------------------------------------------------------
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }

        //------------------------------------------------------------
        // onProviderEnabled
        //------------------------------------------------------------
        @Override
        public void onProviderEnabled(String provider) {
        }

        //------------------------------------------------------------
        // onProviderDisabled
        //------------------------------------------------------------
        @Override
        public void onProviderDisabled(String provider) {
        }

    };
}
