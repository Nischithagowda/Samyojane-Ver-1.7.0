package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Language_selection extends AppCompatActivity {

    ListView listLanguage;
    ArrayAdapter<String> adapter_language;
    String[] lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_selection);

        listLanguage = findViewById(R.id.listLanguage);

        lang = getResources().getStringArray(R.array.language);
        adapter_language = new ArrayAdapter<>(this, R.layout.language_selection_item, R.id.tvlanguageSelection, lang);
        listLanguage.setAdapter(adapter_language);
    }
}
