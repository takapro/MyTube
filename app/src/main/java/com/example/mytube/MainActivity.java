package com.example.mytube;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEdit;
    private Button searchButton;
    private ListView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdit = findViewById(R.id.searchEdit);
        searchButton = findViewById(R.id.searchButton);
        searchList = findViewById(R.id.searchList);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEdit.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    new SearchTask(MainActivity.this).execute(query);
                }
            }
        });
    }

    public void setResult(JSONObject result) {
        try {
            JSONArray items = result.getJSONArray("items");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject snippet = item.getJSONObject("snippet");
                String title = snippet.getString("title");
                list.add(title);
            }
            String[] array = list.toArray(new String[list.size()]);
            searchList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
        } catch (Exception e) {
            // ignore
        }
    }
}
