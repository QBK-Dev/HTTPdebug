package by.ivanm.httpdebug;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

//test git org
public class MainActivity extends Activity {

    //SharedPreferences mPreferences;
    //SharedPreferences.Editor mPreferencesEditor;
    String method = "GET";
    String requestUri = "http://";
    String requestBody = "";
    String responseBody = "";
    /*mPreferences = this.getSharedPreferences("ru.mipt.botay", Context.MODE_PRIVATE);
    mPreferencesEditor = mPreferences.edit();
    mPreferencesEditor.remove("string");
    mPreferencesEditor.putString("method","GET");
    mPreferencesEditor.apply();
    */
    DialogFragment dialog1;


    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(), "Po345q34tq34yq5yaeryaeryst", Toast.LENGTH_SHORT).show();
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog1 = new Dialog1();

        final EditText editTextServer =  (EditText)findViewById(R.id.editTextServer);
        final EditText editTextRequestBody =  (EditText)findViewById(R.id.editTextRequestBody);

        //mPreferences = this.getSharedPreferences("by.ivanm.httpdebug", Context.MODE_PRIVATE);
        //mPreferencesEditor = mPreferences.edit();

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        Toast.makeText(getApplicationContext(), "No choice", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonGet:
                        method = "GET";
                        Toast.makeText(getApplicationContext(), "Get", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonPost:
                        method = "POST";
                        Toast.makeText(getApplicationContext(), "Post", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });




        //When user click button, new activity starts
        //Extra contains url parameters
        Button buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ResultViewer.class);
                intent.putExtra("respBody",responseBody);
                startActivity(intent);
            }
        });

        //When user click button, new stupid dialog1 stars
        Button buttonDialog = (Button) findViewById(R.id.buttonDialog);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog1.show(getFragmentManager(), "dialog 1");
            }
        });

        // When user clicks button url and url parameters takes from editText fields,after that calls AsyncTask.
        // Before attempting to fetch the URL, makes sure that there is a network connection.
        Button buttonDo = (Button) findViewById(R.id.buttonDownload);
        buttonDo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestUri = requestUri + editTextServer.getText().toString();
                requestBody = editTextRequestBody.getText().toString();
                //TODO then the fields are empty
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                //TODO use only wifi?
                if (networkInfo != null && networkInfo.isConnected()) {
                    DownloadWebPageTask DWT =  new DownloadWebPageTask();
                    DWT.execute( requestUri, method, "defaultFileForResponse.txt");
                    
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "AsyncTask BEGIN", Toast.LENGTH_SHORT).show();

        }
        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url, params[1] is the method param[2] is the file name for result
            try {
                Requester req = new Requester(params[0], params[1], params[2]);





                return req.request();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            responseBody = result;
            Toast.makeText(getApplicationContext(), "AsyncTask END", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
