package wsu.csc5991.trustcircle.trustcircleapp.service;

import android.os.AsyncTask;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import wsu.csc5991.trustcircle.trustcircleapp.data.Member;


/**
 * Created by sasidhav on 7/21/16.
 */
public class GetMemberAsyncTask extends AsyncTask<String, Void, Member> {

    private static final String REST_URL_BASE_DOMAIN = "http://10.0.2.2:8080";

    public AsyncResponse delegate = null;

    //----------------------------------------------------------------
    // AutoTask constructor
    //----------------------------------------------------------------
    public GetMemberAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }


    @Override
    protected Member doInBackground(String... params) {
        try {
            String url = REST_URL_BASE_DOMAIN+"/member/mobile/"+params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Member greeting = restTemplate.getForObject(url, Member.class);
            return greeting;
        } catch (Exception e) {
            System.out.println("Failed to get member Data");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Member member) {
        if(member != null){
            System.out.println("Got member Data "+ member.getFirstName()+" "+member.getLastName());
            delegate.processMemberFerchResult(member);
        } else {
            System.out.println("Failed to get member Data");
        }
    }
}