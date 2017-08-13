package main;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import main.model.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * APIGateway to Lmabda Java Implementation Sample Class
 * (Using Integrated lambda proxy by API Gateway)
 *
 * Created by k-tateishi.
 */
public class APIGatewaySample implements RequestStreamHandler {

    private JSONParser parser = new JSONParser();

    /**
     * Handle Request.
     *
     * @param inputStream HTTP Request Stream through API Gateway.
     * @param outputStream HTTP Response Stream From Lambda Function.
     * @param context Lambda Context.
     * @throws IOException Stream Process Error.
     */
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler");


        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Response res = Response.init();
        String responseCode = "200";

        try {

            // Event from API Gateway
            JSONObject event = (JSONObject) parser.parse(reader);

            // Create Response Body
            JSONObject responseBody = new JSONObject();
            responseBody.put("input", event.toJSONString());

            // Create HTTP Header
            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-response-header", "my custom response header value");

            // Create Response
            res.setStatusCode(responseCode);
            res.setHeaders(headerJson);
            res.setBody(responseBody.toString());

            logger.log("Java Lambda handler Success");

        } catch (ParseException pex) {

            // Error Response
            res.setStatusCode(400);
            res.setBody(pex.getMessage());

            logger.log("Java Lambda handler Error");

        }

        logger.log(res.toJSONString());

        // Lambda to APIGateway
        sendResponse(outputStream, res.toJSONString());

    }

    /**
     * Response to APIGateway.
     *
     * @param outputStream HTTP Response Stream From Lambda Function.
     * @param body Response Body
     * @throws IOException Stream Process Error.
     */
    private void sendResponse(OutputStream outputStream, String body) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(body);
        writer.close();
    }
}
