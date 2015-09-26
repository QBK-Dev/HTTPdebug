package by.ivanm.httpdebug;

import android.os.AsyncTask;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ivanm on 9/24/2015.
 */
public class ServCon extends AsyncTask<String, Void, String> {
    public String method="GET";
    public String requestBody="";
    public int totalSize = 0;
    public int downloadedSize = 0;
    public String destination;
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
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
        String resCode = "empty_form_Serv_Con_getOutputFromUrl";
        try {
            resCode = getHttpConnection(url);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return resCode;
    }

    private String getHttpConnection(String urlString) throws IOException {
        String responseCode = "empty_form_Serv_Con_getHttpConnection";
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
                writer.close();
                responseCode = String.valueOf(httpConnection.getResponseCode());
                Log.v("CatalogClient", "Response code:" + responseCode);
                httpConnection.disconnect();
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
                InputStream inputStream = httpConnection.getInputStream();
                totalSize = httpConnection.getContentLength();
                byte[] buffer = new byte[1024];
                int bufferLength;
                totalSize = httpConnection.getContentLength();
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    //publishProgress(downloadSize, totalSize, i + 1, filesCount);
                }
                fileOutput.close();
                responseCode = String.valueOf(httpConnection.getResponseCode());
                Log.v("CatalogClient", "Response code:" + responseCode);
                httpConnection.disconnect();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //AsyncTask END
    }
}

