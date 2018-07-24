package com.example.priyankam.mivi;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static final String JSON_DATA = "data";
    public static final String  JSON_INCLUDED = "included";
    public static final String JSON_ATTRIBUTES = "attributes";
    public static final String MSN ="msn";
    public static final String DATA_BALANCE ="included-data-balance";
    public static final String EXPIRY_DATE ="expiry-date";
    public static final String PRODUCT_NAME ="name";
    public static final String PRODUCT_PRICE ="price";
    public static final String PAYMENT_TYPE = "payment-type";
    public static final String FIRST_NAME = "first-name";
    public static final String LAST_NAME ="last-name" ;
    public static final String DATE_OF_BIRTH ="date-of-birth" ;
    public static final String CONTACT_NUMBER ="contact-number" ;
    public static final String EMAIL_ADDRESS = "email-address";

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("collection.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
