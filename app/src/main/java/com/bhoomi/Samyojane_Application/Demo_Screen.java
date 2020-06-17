package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;

@SuppressLint("Registered")
public class Demo_Screen extends AppCompatActivity {

    ProgressDialog dialog;

    String SOAP_ACTION = "http://tempuri.org/KannadatoEnglish";
    public final String OPERATION_NAME = "KannadatoEnglish";  //Method_name

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ADDRESS = "http://164.100.133.47/nk_namesearch/SearchRecordsByName_RCOTC.asmx";

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_screen);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        char[] str = "geeksforgeeks".toCharArray();
        Log.d("given_string_Valu",""+ Arrays.toString(str));
        int n = str.length;
        Log.d("given_string_length",""+n);
        System.out.println(removeDuplicate(str, n));

        //UploadInactiveMember();
//        if (isNetworkAvailable()) {
//            new GetEngName().execute();
//        }else {
//            buildAlert_Internet();
//            //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
//        }
    }

    static String removeDuplicate(char[] str, int n)
    {
        // Used as index in the modified string
        int index = 0;

        // Traverse through all characters
        for (int i = 0; i < n; i++)
        {

            // Check if str[i] is present before it
            int j;
            for (j = 0; j < i; j++)
            {
                Log.d("given_string", str[i]+" == "+str[j]);
                if (str[i] == str[j])
                {
                    Log.d("given_string", "i="+i+" & j="+j+", NO "+str[i]+" and "+str[j]+" Breaks");
                    break;
                }
            }

            // If not present, then add it to
            // result.
            if (j == i)
            {
                Log.d("given_string", "i="+i+" & j="+j+", YES "+str[i]+" and "+str[j]+" Print");
                str[index++] = str[i];
                Log.d("geeksforgeeks",""+String.valueOf(Arrays.copyOf(str, index)));
            }
        }
        Log.d("geeksforgeeks",""+String.valueOf(Arrays.copyOf(str, index)));
        return String.valueOf(Arrays.copyOf(str, index));
    }

    @SuppressLint("StaticFieldLeak")
    class GetEngName extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {

            try {


                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

                request.addProperty("kanstr", "ಸದಾನಂದ");

                Log.d("Request","" + request);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                resultFromServer = String.valueOf(resultString);

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Error1", e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                    Log.d("Error1", "Server Exception Occurred");
                    dialog.dismiss();
                });
            }

            return resultFromServer;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        protected void onProgressUpdate(Integer... a) {

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private  void buildAlert_Internet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.internet_not_avail))
                .setMessage(getString(R.string.switch_on_internet))
                .setIcon(R.drawable.ic_error_black_24dp)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }
}
