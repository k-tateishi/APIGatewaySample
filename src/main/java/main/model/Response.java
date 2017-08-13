package main.model;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Response Wrapper Class
 *
 * Created by k-tateishi.
 */
public class Response {

    private static JSONObject response;

    private Response() {}

    /**
     * Call this method first.
     *
     * @return Response instance.
     */
    public static Response init() {
        if (response == null) {
            response = new JSONObject();
        }
        return new Response();
    }

    /**
     * Set HTTP Status Code by Integer value.
     *
     * @param statusCode HTTP Status Code
     */
    public void setStatusCode(int statusCode) {
        this.response.put("statusCode", String.valueOf(statusCode));
    }

    /**
     * Set HTTP Status Code by String value.
     *
     * @param statusCode HTTP Status Code
     */
    public void setStatusCode(String statusCode) {
        this.response.put("statusCode", statusCode);
    }

    /**
     * Set HTTP Headers by Map.
     *
     * @param headers HTTP Header
     */
    public void setHeaders(Map headers) {
        this.response.put("headers", headers);
    }

    /**
     * Set Response Body.
     *
     * @param body Response Body
     */
    public void setBody(String body) {
        this.response.put("body", body);
    }

    /**
     * Convert this class to a JSON string.
     *
     * @return JSON string
     */
    public String toJSONString() {
        return this.response.toJSONString();
    }
}
