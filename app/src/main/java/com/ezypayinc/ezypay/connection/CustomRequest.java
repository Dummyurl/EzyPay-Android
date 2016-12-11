package com.ezypayinc.ezypay.connection;

import android.app.DownloadManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Gustavo Quesada S on 10/12/2016.
 */

public class CustomRequest extends Request {
    Map<String, String> params;
    Map<String, String> headers;
    Class requestType;
    private Response.Listener listener;

    public CustomRequest(int requestMethod, String url, Map<String, String> params, Map<String, String> headers,
                             Class requestType, Response.Listener responseListener, Response.ErrorListener errorListener) {

        super(requestMethod, url, errorListener);
        this.params = params;
        this.headers = headers;
        this.requestType = requestType;
        this.listener = responseListener;
    }

    @Override
    protected void deliverResponse(Object response) {
        listener.onResponse(response);

    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null? super.getHeaders() : headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (this.requestType == JsonArrayRequest.class) {
                return Response.success(new JSONArray(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
