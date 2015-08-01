package by.ivanm.httpdebug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

//test git org
public class MainActivity extends Activity {

    SharedPreferences mPreferences;
    SharedPreferences.Editor mPreferencesEditor;
    String method = "GET";
    String requestUri = "";
    String requestBody = "";
   /* mPreferences = this.getSharedPreferences("ru.mipt.botay", Context.MODE_PRIVATE);
    mPreferencesEditor = mPreferences.edit();
    mPreferencesEditor.remove("string");
    mPreferencesEditor.putString("method","GET");
    mPreferencesEditor.apply();*/
//hjh
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
                startActivity(intent);
            }
        });

        Button buttonDo = (Button) findViewById(R.id.buttonDownload);
        buttonDo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestUri = "http://" + editTextServer.getText().toString();
                requestBody = editTextRequestBody.getText().toString();
                switch(method){
                    case "GET":

                        break;
                    case "POST":

                        break;
                    default:
                        break;
                }
            }
        });
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
