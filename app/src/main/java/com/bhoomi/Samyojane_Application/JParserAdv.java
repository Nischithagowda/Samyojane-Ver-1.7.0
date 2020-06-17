package com.bhoomi.Samyojane_Application;

import android.util.Log;

import com.google.gson.JsonObject;

import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by RS1214250632 on 16-10-2015.
 */
public class JParserAdv {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result = new StringBuilder();
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;
    String data = "";
    JSONArray jsonArray = null;
    InputStream is = null;

    public JSONArray getJSONFromUrl(String strUrl) throws IOException {
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            is = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
            is.close();
            data = sb.toString();

            //br.close();

            jsonArray = new JSONArray(data);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            is.close();
        }

        return jsonArray;
    }
    public String makeServiceCall(String reqUrl) {

        String response = null;
        try {
            Log.d("JSON Parser", reqUrl);
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.d("JSON Parser", "result: " + result.toString());
        } catch (ProtocolException e) {
            Log.d("JSON Parser", "result: " + result.toString());
        } catch (IOException e) {
            Log.d("JSON Parser", "result: " + result.toString());
        } catch (Exception e) {
            Log.d("JSON Parser", "result: " + result.toString());
        }
        return response;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        Log.d("JSON Parser", "SS");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        if(params!=null) {
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(720000000);
                conn.setConnectTimeout(720000000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                Log.d("Exception", " "+ e);
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON_Parser", "result: " + result.toString());

        } catch (FileNotFoundException e){
            Log.d("FileNotFoundException",""+e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("IOException",""+e.getMessage());
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parser Exception", "Error parsing data " + e.getMessage());
        }

        // return JSON Object
        return jObj;
    }


    public JSONObject makeHttpRequest_POST(String url, String method, HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        if(params!=null) {
            for (String key : params.keySet()) {
                try {
                    if (i != 0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=")
                            .append(URLEncoder.encode(params.get(key), charset));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json; utf-8");

                conn.setReadTimeout(720000000);
                conn.setConnectTimeout(720000000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                Log.d("Exception", " "+ e);
            }
        }

        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON_Parser", "result: " + result.toString());

        } catch (FileNotFoundException e){
            Log.d("FileNotFoundException",""+e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("IOException",""+e.getMessage());
        }

        conn.disconnect();

        try {
            XmlToJson xmlToJson = new XmlToJson.Builder(result.toString()).build();
            String formatted = xmlToJson.toFormattedString();

            Log.d("JSON_Parser", "formatted: "+formatted);
            jObj = new JSONObject(formatted.replace("\n", ""));

            jObj  = jObj.getJSONObject("string");
            Log.d("JSON_Parser11",""+jObj);
            jObj  = jObj.getJSONObject("content");

            Log.d("JSON_Parser22", "result: "+jObj);

            //jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parser Exception", "Error parsing data " + e.getMessage());
        }

        return jObj;
    }

}
