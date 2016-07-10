package wsu.csc5991.trustcircle.trustcircleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Inside New Activity Create method");
        setContentView(R.layout.layhelp);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            System.out.println("doubleValue: "+extras.getDouble("doubleValue"));
            System.out.println("intValue: "+ extras.getInt("intValue"));
            System.out.println("stringValue: "+ extras.getString("stringValue"));
            finish();
        }

    }

    public void finish()
    {
        Intent intent = new Intent();
        intent.putExtra("doubleValue", 6.7);
        intent.putExtra("intValue", 99);
        intent.putExtra("stringValue", "Goodbye");
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
