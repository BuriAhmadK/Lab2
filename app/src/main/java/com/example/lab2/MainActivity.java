package com.example.lab2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView cityListView;
    private Button addCityBtn;
    private Button deleteCityBtn;

    private ArrayList<String> cities;
    private ArrayAdapter<String> adapter;

    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityListView = findViewById(R.id.cityListView);
        addCityBtn = findViewById(R.id.addCityBtn);
        deleteCityBtn = findViewById(R.id.deleteCityBtn);

        cities = new ArrayList<>();
        cities.add("Lahore");
        cities.add("Karachi");
        cities.add("Islamabad");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, cities);
        cityListView.setAdapter(adapter);
        cityListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityListView.setItemChecked(position, true);
        });

        addCityBtn.setOnClickListener(v -> showAddCityDialog());

        deleteCityBtn.setOnClickListener(v -> {
            if (selectedIndex >= 0 && selectedIndex < cities.size()) {
                cities.remove(selectedIndex);
                adapter.notifyDataSetChanged();
                cityListView.clearChoices();
                selectedIndex = -1;
            }
        });
    }

    private void showAddCityDialog() {
        EditText input = new EditText(this);
        input.setHint("Enter city name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String cityName = input.getText().toString().trim();
                    if (!cityName.isEmpty()) {
                        cities.add(cityName);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }
}
