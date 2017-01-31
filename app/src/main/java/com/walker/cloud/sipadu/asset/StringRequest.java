package com.walker.cloud.sipadu.asset;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 19/01/2017.
 */

public class StringRequest extends com.android.volley.toolbox.StringRequest {

    private final Map<String, String> _params;
    private CostumListenerVolley listener;

    /**
     * @param method
     * @param url
     * @param params
     *            A {@link HashMap} to post with the request. Null is allowed
     *            and indicates no parameters will be posted along with request.
     * @param listener
     * @param errorListener
     */
    public StringRequest(int method, String url, Map<String, String> params, CostumListenerVolley listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        _params = params;
        this.listener=listener;
    }

    @Override
    protected Map<String, String> getParams() {
        return _params;
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
        MyApp.get().checkSessionCookie(response.headers);

        return super.parseNetworkResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
            headers.put("Cookie","ci_session_baak=a%3A4%3A%7Bs%3A10%3A%22session_id%22%3Bs%3A32%3A%2269a9d5c925776c6d76e5c73f7102e4c6%22%3Bs%3A10%3A%22ip_address%22%3Bs%3A13%3A%2236.70.212.201%22%3Bs%3A10%3A%22user_agent%22%3Bs%3A50%3A%22Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%3B+rv%3A50.0%29%22%3Bs%3A13%3A%22last_activity%22%3Bs%3A10%3A%221484824687%22%3B%7D904aeaf5a0d40a7420a6189a51824a56; stis_mahasiswa="+listener.getChaceDua().trim()
                    +"; CMSSESSID40d1b2d8="+listener.getChaceSatu());
            Log.d("COOKIENYA","cookinya null");
        }

        MyApp.get().addSessionCookie(headers);

        return headers;
    }


}
