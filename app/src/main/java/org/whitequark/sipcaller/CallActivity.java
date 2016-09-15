package org.whitequark.sipcaller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {

    private boolean allowAlphanumeric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        EditText sipDomainEdit = (EditText) findViewById(R.id.sipDomain);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        allowAlphanumeric = sharedPref.getBoolean("allowAlphanumeric", false);
        sipDomainEdit.setText(sharedPref.getString(getString(R.string.saved_domain), ""));

        updateOptions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.allow_alphanumeric);
        checkable.setChecked(allowAlphanumeric);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allow_alphanumeric:
                allowAlphanumeric = !item.isChecked();
                updateOptions();
                return true;
            default:
                return false;
        }
    }

    private void updateOptions() {
        EditText phoneNumberEdit = (EditText) findViewById(R.id.phoneNumber);
        phoneNumberEdit.setInputType(allowAlphanumeric ?
                InputType.TYPE_CLASS_TEXT :
                InputType.TYPE_CLASS_PHONE);
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
