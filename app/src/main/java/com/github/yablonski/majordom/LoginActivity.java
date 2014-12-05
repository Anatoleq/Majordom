package com.github.yablonski.majordom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.yablonski.majordom.auth.OAuthHelper;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity implements OAuthHelper.Callbacks {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private InputStream is;
    MainActivity url;
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        dismissProgress();
    }

    public void onSubmitButtonClick(View v){
        new LoginTask().execute("http://melville.webatu.com/lib/userAndroid.php");
    }

    public void onExitButtonClick(View v){
        finish();
    }

    class LoginTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                HttpPost postMethod = new HttpPost(params[0]);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", emailEditText.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", passwordEditText.getText().toString()));
                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                String response = httpClient.execute(postMethod, responseHandler);
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //intent.putExtra(MainActivity.JsonURL, response.toString());
                //startActivity(intent);
            } catch (Exception e) {
                System.out.println("Exp=" + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissProgress();
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //intent.putExtra(MainActivity.JsonURL, response.toString());
            //startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }
    }

    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError(Exception e) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(e.getMessage())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void dismissProgress() {
        findViewById(android.R.id.progress).setVisibility(View.GONE);
    }

    private void showProgress() {
        findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
    }

}
