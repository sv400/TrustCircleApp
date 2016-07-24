package wsu.csc5991.trustcircle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import wsu.csc5991.trustcircle.vo.Member;

public class ActSignIn extends AppCompatActivity {

    EditText etPhoneNumber;
    EditText etPin;
    Button btnSignIn;
    Button btnRegisterUser;
    int pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define and show application icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);


        setContentView(R.layout.laysignin);
        ((LinearLayout)findViewById(R.id.LaySignIn)).setBackgroundColor(Setting.Shared.Data.backgroundColor);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPin = (EditText) findViewById(R.id.etPin);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);
    }

    public void login(View view){
        String mobileNumber = etPhoneNumber.getText().toString();
        String password = etPin.getText().toString();
        boolean isValidInput = true;
        if (mobileNumber.length() != 10) {
            etPhoneNumber.setError("Mobile number should be 10 digits long!");
            isValidInput = false;
        }
        if (password.length() == 0) {
            etPin.setError("Password is required to sign in!");
            isValidInput = false;
        }
        if (isValidInput) {
            new HttpRequestTask().execute(mobileNumber, password);
        }
    }

    public void registerNewMember(View view){
        Intent i = new Intent(getApplicationContext(), ActMemberSignUp.class);
        startActivity(i);
    }


    private class HttpRequestTask extends AsyncTask<String, Void, Member> {
        @Override
        protected Member doInBackground(String... params) {
            try {
                String url = getResources().getString(R.string.rest_service_url) + "/member/mobile/" + params[0];

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Member member = restTemplate.getForObject(url, Member.class);
                pin = Integer.parseInt(params[1]);

                return member;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Member member) {
            if(member != null) {
                if (pin == member.getPin()) {
                    Intent i = new Intent(getApplicationContext(), ActMain.class);
                    i.putExtra("m_Id", member.getId());
                    i.putExtra("m_Phone", member.getMobileNumber());
                    i.putExtra("m_first_name", member.getFirstName());
                    i.putExtra("m_last_name", member.getLastName());
                    i.putExtra("m_pin", member.getPin());
                    startActivity(i);
                } else {
                    Setting.showDialogBox(ActSignIn.this, "Trust Circle SignIn", "Invalid login!");
                }
            } else {
                Setting.showDialogBox(ActSignIn.this, "Trust Circle SignIn", "Invalid login!");
            }
        }
    }
}
