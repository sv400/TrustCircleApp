package wsu.csc5991.trustcircle.trustcircleapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ActMain extends AppCompatActivity {

    TextView tvHello;

    private static final String REST_URL_BASE_DOMAIN = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);
        tvHello = (TextView) findViewById(R.id.tvHello);
        new HttpRequestTask().execute("1000001000");
    }


    private class HttpRequestTask extends AsyncTask<String, Void, Member> {
        @Override
        protected Member doInBackground(String... params) {
            try {
                String url = REST_URL_BASE_DOMAIN+"/member/mobile/"+params[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Member greeting = restTemplate.getForObject(url, Member.class);
                return greeting;
            } catch (Exception e) {
               e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Member greeting) {
            if(greeting != null){
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+greeting.getId());
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+greeting.getFirstName());
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+greeting.getLastName());
                tvHello = (TextView) findViewById(R.id.tvHello);
                tvHello.setText(String.valueOf(greeting.getFirstName()));
            } else {
                System.out.println("Null greetings");
            }
        }


    }

}
