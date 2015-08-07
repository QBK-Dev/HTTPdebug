package by.ivanm.httpdebug;

import android.app.Activity;
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

    SharedPreferences mPreferences;
    SharedPreferences.Editor mPreferencesEditor;
    String method = "GET";
    String requestUri = "";
    String requestBody = "";
    String responseBody = "";
    public enum RequestMethod {
        GET,
        POST
    }
   /* mPreferences = this.getSharedPreferences("ru.mipt.botay", Context.MODE_PRIVATE);
    mPreferencesEditor = mPreferences.edit();
    mPreferencesEditor.remove("string");
    mPreferencesEditor.putString("method","GET");
    mPreferencesEditor.apply();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextServer =  (EditText)findViewById(R.id.editTextServer);
        final EditText editTextRequestBody =  (EditText)findViewById(R.id.editTextRequestBody);

        mPreferences = this.getSharedPreferences("by.ivanm.httpdebug", Context.MODE_PRIVATE);
        //mPreferencesEditor = mPreferences.edit();

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
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

        Button buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultViewer.class);
                intent.putExtra("respBody",responseBody);
                startActivity(intent);
            }
        });

        // When user clicks button, calls AsyncTask.
        // Before attempting to fetch the URL, makes sure that there is a network connection.
        Button buttonDo = (Button) findViewById(R.id.buttonDownload);
        buttonDo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestUri = "http://" + editTextServer.getText().toString();
                requestBody = editTextRequestBody.getText().toString();
                //TODO then the fields are empty
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    DownloadWebpageTask DWT =  new DownloadWebpageTask();
                    DWT.execute(requestUri,method);
                    //DWT.onPostExecute();
                } else {
                    //TODO Toast
                    Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url and params[1] is the method
            try {
                Requester req = new Requester(params[0] ,params[1]);

                return req.request();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            responseBody = result;
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
