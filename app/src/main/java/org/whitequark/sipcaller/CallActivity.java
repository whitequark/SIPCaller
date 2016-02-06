package org.whitequark.sipcaller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        EditText sipDomainEdit = (EditText) findViewById(R.id.sipDomain);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sipDomainEdit.setText(sharedPref.getString(getString(R.string.saved_domain), ""));
    }

    public void call(View view) {
        EditText phoneNumberEdit = (EditText) findViewById(R.id.phoneNumber);
        EditText sipDomainEdit = (EditText) findViewById(R.id.sipDomain);

        String phoneNumber = phoneNumberEdit.getText().toString();
        String sipDomain = sipDomainEdit.getText().toString();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_domain), sipDomain);
        editor.commit();

        Uri phoneCall = Uri.fromParts("sip", phoneNumber + "@" + sipDomain, "");
        Intent caller = new Intent(Intent.ACTION_CALL, phoneCall);
        startActivity(caller);
    }
}
