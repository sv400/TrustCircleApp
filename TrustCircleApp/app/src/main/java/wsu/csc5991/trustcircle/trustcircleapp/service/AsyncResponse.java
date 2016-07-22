package wsu.csc5991.trustcircle.trustcircleapp.service;


import wsu.csc5991.trustcircle.trustcircleapp.data.Member;

/**
 * Created by sasidhav on 7/21/16.
 */
public interface AsyncResponse {
    void processMemberFerchResult(Member member);
}
