package by.ivanm.httpdebug;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by ivanm on 9/26/2015.
 */
public class MyFilesStr {

    private static final String LOG_TAG = "my_file_tag";

    public String readFile(Context context, String destination) {

        Log.d(LOG_TAG, "readFile");

        FileInputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        String line;
        String inputString = "empty_form_MyFilesStr_readFle";

        try {
            inputStream = context.openFileInput(destination);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                inputStream.close();
            }
            inputString = sb.toString();
            Log.d(LOG_TAG, "Data from file: " + inputString);

        } catch (Exception e) {
            Log.d(LOG_TAG, "No file or en error");
        }
        return inputString;
    }

    public void writeToFile(Context context, String destination, String stringToWrite) {

        Log.d(LOG_TAG, "writeToFile");
        try {
            FileOutputStream outputStream = context.openFileOutput(destination, Context.MODE_PRIVATE );
            outputStream.write(stringToWrite.getBytes());
            outputStream.close();

        } catch (Exception e) {
            Log.d(LOG_TAG, "Writing file error");
        }
    }
}
