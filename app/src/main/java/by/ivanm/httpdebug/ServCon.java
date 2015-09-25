package by.ivanm.httpdebug;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ivanm on 9/24/2015.
 */
public class ServCon extends AsyncTask<String, Void, String> {
    public String method="POST";
    public String requestBody="maznik=pisecka";
    public int totalSize = 0;
    private int downloadSize = 0;
    public String destination;
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        //AsyncTask BEGIN;
    }
    @Override
    protected String doInBackground(String... urls) {
        String output = null;
        for (String url : urls) {
             output = getOutputFromUrl(url);
        }
        return output;
    }

    private String getOutputFromUrl(String url) {
        //StringBuffer output = new StringBuffer("");
        //String output = " ";
        String resCode = "empty";
        try {
            //InputStream stream = getHttpConnection(url);
            resCode = getHttpConnection(url);
            /*
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(stream));
            */
            /*
            String s;
            while ((s = buffer.readLine()) != null)
                output.append(s);
                //output += s;
            */
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return resCode;
        //return output.toString();
        //return output;
    }
    //private InputStream getHttpConnection(String urlString) throws IOException {
    private String getHttpConnection(String urlString) throws IOException {
        //InputStream stream = null;
        String responseCode = "empty";
        try {
            if ( method.equals("POST") ) {
                URL url = new URL(urlString);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
                httpConnection.setRequestProperty("Accept", "*/*");
                httpConnection.setDoOutput(true);
                httpConnection.connect();
                OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());
                String urlParameters = requestBody;
                writer.write(urlParameters);
                writer.flush();
                /*
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
                */
                writer.close();
                responseCode = String.valueOf(httpConnection.getResponseCode());
                Log.v("CatalogClient", "Response code:" + responseCode);
                //httpConnection.disconnect();
            }
            if (method.equals( "GET") ) {
                URL url = new URL(urlString + requestBody);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
                httpConnection.setRequestProperty("Accept", "*/*");
                httpConnection.setDoOutput(true);
                httpConnection.connect();

                FileOutputStream fileOutput = new FileOutputStream(destination);
                //Stream used for reading the data from the internet
                InputStream inputStream = httpConnection.getInputStream();
                //this is the total size of the file which we are downloading
                totalSize = httpConnection.getContentLength();
                /*
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
                */
                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength;
                totalSize = httpConnection.getContentLength();
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadSize += bufferLength;
                    //publishProgress(downloadSize, totalSize, i + 1, filesCount);
                }
                fileOutput.close();
                responseCode = String.valueOf(httpConnection.getResponseCode());
                Log.v("CatalogClient", "Response code:" + responseCode);
                //httpConnection.disconnect();
            }


        } catch (final Exception e) {
            e.printStackTrace();
        }
        //return stream;
        return responseCode;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //AsyncTask END
    }
}

