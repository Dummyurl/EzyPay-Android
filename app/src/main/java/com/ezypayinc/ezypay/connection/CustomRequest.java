package com.ezypayinc.ezypay.connection;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Map;


class CustomRequest extends JsonRequest<JsonElement>{
    private final Map<String, String> headers;

    CustomRequest(int requestMethod, String url, JSONObject jsonRequest,
                         Map<String, String> headers, Response.Listener<JsonElement> responseListener, Response.ErrorListener errorListener) {

        super(requestMethod, url, (jsonRequest == null) ? null : jsonRequest.toString(), responseListener,
                errorListener);
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null? super.getHeaders() : headers;
    }

    @Override
    protected Response<JsonElement> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            return Response.success(element,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

}
