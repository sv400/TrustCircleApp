package wsu.csc5991.trustcircle.trustcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import wsu.csc5991.trustcircle.trustcircleapp.R;

public class ActDisplayCircle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydisplaycircle);
        ((LinearLayout)findViewById(R.id.LayDisplayCircle)).setBackgroundColor(Setting.Shared.Data.backgroundColor);
    }
}
