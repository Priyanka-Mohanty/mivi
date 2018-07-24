package com.example.priyankam.mivi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    static Context context;
    static TextView tvRemDataBal, tvExpDate, tvProductName, tvProductPrice;
    static TextView tvFName, tvLName, tvDOB, tvContact, tvEmail, tvPaymentType;
    static String dataBalance, productName, productPrice, expDate;
    static String fName, lName, DOB, contact, email, paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context = MainActivity.this;

        tvRemDataBal = findViewById(R.id.text_data_balance_value);
        tvProductName = findViewById(R.id.text_product_name);
        tvProductPrice = findViewById(R.id.text_price_product_price);
        tvExpDate = findViewById(R.id.text_exp_date);

        tvFName = findViewById(R.id.text_first_name);
        tvLName = findViewById(R.id.text_last_name);
        tvDOB = findViewById(R.id.text_date_of_birth);
        tvContact = findViewById(R.id.text_contact);
        tvEmail = findViewById(R.id.text_email);
        tvPaymentType = findViewById(R.id.text_payment_type);

        getAllValue(context);


    }

    private void getAllValue(Context context) {
        try {

            String jsonString = Utils.loadJSONFromAsset(context);
            JSONObject jsonObj = new JSONObject(jsonString);

            JSONObject jsonObjData = jsonObj.getJSONObject(Utils.JSON_DATA);

            JSONObject dataAttributes = jsonObjData.getJSONObject(Utils.JSON_ATTRIBUTES);
            System.out.println("Json Object key Data = " + dataAttributes);

            Iterator<String> keysData = dataAttributes.keys();
            while (keysData.hasNext()) {
                String key = keysData.next();
                String value = dataAttributes.getString(key);
                Log.i("Json Object key value", key + " : " + value);
                //-----------For payment-type
                if (key.equals(Utils.PAYMENT_TYPE)) {
                    Log.i("payment-type Value = ", "payment-type =" + value);
                    paymentType = value.trim();
                }
                //-----------For first-name
                if (key.equals(Utils.FIRST_NAME)) {
                    Log.i("first-name = ", "first-name =" + value);
                    fName = value.trim();
                }
                //-----------For Last Name
                if (key.equals(Utils.LAST_NAME)) {
                    Log.i("Last Name = ", "Last Name =" + value);
                    lName = value.trim();
                }

                //-----------For DateOfBirth
                if (key.equals(Utils.DATE_OF_BIRTH)) {
                    Log.i("DateOfBirth = ", "DateOfBirth =" + value);
                    DOB = value.trim();
                }

                //-----------For Contact Number
                if (key.equals(Utils.CONTACT_NUMBER)) {
                    Log.i("contact-number = ", "contact-number =" + value);
                    contact = value.trim();
                }

                //-----------For email
                if (key.equals(Utils.EMAIL_ADDRESS)) {
                    Log.i("email-address = ", "email-address =" + value);
                    email = value.trim();
                }
            }


            tvFName.setText(fName);
            tvLName.setText(lName);
            tvContact.setText(contact);
            tvDOB.setText(DOB);
            tvEmail.setText(email);
            tvEmail.setText(email);
            tvPaymentType.setText(paymentType);

            //---------------for included json array
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
                    //-----------For Remaining DataBalance
                    if (key.equals(Utils.DATA_BALANCE)) {
                        Log.i("DataBalance Value = ", "DataBalance =" + value);
                        dataBalance = value.trim();
                    }
                    //-----------For Expiry Date
                    if (key.equals(Utils.EXPIRY_DATE)) {
                        Log.i("Expiry Date = ", "Expiry Date  =" + value);
                        expDate = value.trim();
                    }
                    //-----------For Product Name
                    if (key.equals(Utils.PRODUCT_NAME)) {
                        Log.i("Product Name = ", "Product Name =" + value);
                        productName = value.trim();
                    }

                    //-----------For Product Price
                    if (key.equals(Utils.PRODUCT_PRICE)) {
                        Log.i("Product Price = ", "Product Price =" + value);
                        productPrice = value.trim();
                    }
                }
            }
            tvRemDataBal.setText(dataBalance);
            tvExpDate.setText(expDate);
            tvProductName.setText(productName);
            tvProductPrice.setText(productPrice);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
