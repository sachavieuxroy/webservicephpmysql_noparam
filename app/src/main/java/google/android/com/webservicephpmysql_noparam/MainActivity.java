package google.android.com.webservicephpmysql_noparam;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {
    String result="";
    String resultat="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public class Asynchrone extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection httpUrlConnection = null;

            try {
                URL url = new URL("http://10.0.2.2/formation2/book.php");
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = new  BufferedInputStream(httpUrlConnection.getInputStream());
                int inChar;
                final StringBuilder readStr = new StringBuilder();
                while ((inChar = inStream.read()) != -1) {
                    readStr.append((char) inChar);
                }
                result=readStr.toString();
                parseJsonFile(result);
                httpUrlConnection.disconnect();
            } catch (MalformedURLException me) {
                me.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return resultat;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }


    }
    public void reception(View v){
        resultat="";
        Asynchrone as=new Asynchrone();
        as.execute("ok");
    }
    private void parseJsonFile (String jString) throws Exception {
        JSONObject jsonObj = new JSONObject(jString);


        JSONArray book  = jsonObj.getJSONArray("books");
        for (int i = 0; i < book.length(); i++) {
            String attributeId = book.getJSONObject(i).getString("id");
            resultat+="id: "+attributeId+"\n";

            String attributeName = book.getJSONObject(i).getString(
                    "name");
            resultat+="name: "+attributeName+"\n";
            String attributeAuthor = book.getJSONObject(i).getString(
                    "name");
            resultat+="author: "+attributeAuthor+"\n";
            String attributeIsbn = book.getJSONObject(i).getString(
                    "isbn");
            resultat+="isbn: "+attributeIsbn+"\n";


        }
    }


}
