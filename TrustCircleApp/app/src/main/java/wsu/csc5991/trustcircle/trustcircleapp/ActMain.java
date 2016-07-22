package wsu.csc5991.trustcircle.trustcircleapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import wsu.csc5991.trustcircle.trustcircleapp.data.Member;
import wsu.csc5991.trustcircle.trustcircleapp.service.AsyncResponse;
import wsu.csc5991.trustcircle.trustcircleapp.service.GetMemberAsyncTask;


public class ActMain extends AppCompatActivity {

    private static final String REST_URL_BASE_DOMAIN = "http://10.0.2.2:8080";

    EditText etFirstName;
    EditText etLastName;
    EditText etPin;
    EditText etMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etPin = (EditText) findViewById(R.id.etPin);
    }


    public void getMemberInfo(View view) {
        new GetMemberAsyncTask(new AsyncResponse() {
            @Override
            public void processMemberFerchResult(Member output) {
                etFirstName.setText(String.valueOf(output.getFirstName()));
                etFirstName.setText(String.valueOf(output.getLastName()));
            }
        }).execute(etMobileNumber.getText().toString());

    }

    public void registerMember(View view) {
        new HttpRequestTask().execute();
    }


    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url = REST_URL_BASE_DOMAIN + "/member";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Member member = new Member();
                member.setFirstName("Test");
                member.setLastName("Test2");
                member.setPin(12334);
                member.setMobileNumber(999);
                String result2 = restTemplate.postForObject(new URI(url), member, String.class);
                return result2;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String member) {
            if (member != null) {

            } else {
                System.out.println("Null greetings");
            }
        }


    }

}