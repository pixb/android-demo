package com.pix.http;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Defile send Request wrapper class usage volley!
 *
 */
public class VolleyNetwork implements INetwork {

    /**
     * Defile send's methord
     * usage HttpRequester and responseListener
     * @param httpRequester
     * @param responseListener
     */
    @Override
    public void performRequest(final HttpRequester httpRequester,final INetwork.OnResponseListener responseListener) {
        final HttpRequester requester = httpRequester;
        final HttpResponse<Object> httpResponse=new HttpResponse<>();
        final Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                httpResponse.status = HttpResponse.STATUS_OK;
                httpResponse.setResult(response);
                responseListener.onResponseSuccess(httpRequester,httpResponse,response);
            }
        };

        final Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                httpResponse.throwable = error.fillInStackTrace();
                httpResponse.status = error.networkResponse == null ? HttpResponse.STATUS_ERROR_NET : HttpResponse.STATUS_ERROR_SERVRE;
                if(error.networkResponse!=null){
                    httpResponse.httpStatusCode = error.networkResponse.statusCode;
                    httpResponse.headers = error.networkResponse.headers;
                    httpResponse.networkMS = error.networkResponse.networkTimeMs;
                }
                responseListener.onResponseError(httpRequester,httpResponse);
            }
        };
        //new StringRequest obj
        StringRequestCompent stringRequest=StringRequestCompent.newStringRequest(httpRequester.mMethod,httpRequester.mUrl,httpRequester.params,listener,errorListener);
        // add header
        stringRequest.addHeaders(httpRequester.getHeaders());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(httpRequester.getTimeOut(),httpRequester.getRetryNumber(),DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyManager.getInstance().request(stringRequest);

    }


}
