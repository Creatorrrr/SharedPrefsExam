package com.example.kosta.sharedprefsexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edit;
    private CheckBox check;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        check = (CheckBox)findViewById(R.id.check);

        prefs = getSharedPreferences("setting", Context.MODE_PRIVATE);

        String str = prefs.getString("edit", "EMPTY");
        boolean bool = prefs.getBoolean("checkbox", false);

        edit.setText(str);
        if(bool) {
            check.setChecked(bool);
            edit.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.putString("edit", edit.getText().toString());
                prefsEdit.putBoolean("checkbox", check.isChecked());
                prefsEdit.apply();  // use thread
                // prefsEdit.commit();  // not use thread
            }
        });

        prefs.registerOnSharedPreferenceChangeListener(new ChangeHandler());
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        SharedPreferences.Editor prefsEdit = prefs.edit();
//        prefsEdit.putString("edit", edit.getText().toString());
//        prefsEdit.putBoolean("checkbox", check.isChecked());
//        prefsEdit.apply();  // use thread
//        // prefsEdit.commit();  // not use thread
//    }

    private class ChangeHandler implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("checkbox")) {
                if(sharedPreferences.getBoolean(key, false)) {
                    check.setChecked(true);
                    edit.setTypeface(null, Typeface.BOLD_ITALIC);
                } else {
                    check.setChecked(false);
                    edit.setTypeface(null, Typeface.NORMAL);
                }
            }
        }

    }
}
