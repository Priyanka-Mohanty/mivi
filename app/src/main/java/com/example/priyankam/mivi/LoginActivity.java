package com.example.priyankam.mivi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * A login screen that offers login via Msn.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mMsnView;
    private View mProgressView;
    private View mLoginFormView;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        // Set up the login form.
        mMsnView = (EditText) findViewById(R.id.edit_msn);


        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid msn, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mMsnView.setError(null);

        // Store values at the time of the login attempt.
        String msn = mMsnView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid msn.
        if (TextUtils.isEmpty(msn)) {
            mMsnView.setError(getString(R.string.error_field_required));
            focusView = mMsnView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(msn);
            mAuthTask.execute((Void) null);
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mMSN;

        UserLoginTask(String msn) {
            mMSN = msn;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = false;
            try {
                result = getMSNValue(context, mMSN);
            } catch (Exception e) {
                return false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try {
                mAuthTask = null;
                showProgress(false);

                if (success){
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mMsnView.setError(getString(R.string.error_invalid_msn));
                    mMsnView.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private boolean getMSNValue(Context context, String mMSN) {
        try {

            String jsonString = Utils.loadJSONFromAsset(context);
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObj.getJSONArray(Utils.JSON_INCLUDED);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                JSONObject attributes = c.getJSONObject(Utils.JSON_ATTRIBUTES);
                System.out.println("Json Object key Attributes = " + attributes);

                Iterator<String> keys = attributes.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = attributes.getString(key);
                    Log.i("Json Object key value", key + " : " + value);
                    if (key.equals(Utils.MSN)) {
                        Log.i("Msn Value = ", "Msn =" + value);
                        String MsnValue = value.trim();
                        if (mMSN.equals(MsnValue)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

