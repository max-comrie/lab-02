package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> datalist;

    Button addCityButton;
    Button deleteCityButton;

    EditText inputCity;
    Button confirmButton;

    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        // Buttons already in your layout
        addCityButton = findViewById(R.id.button2);
        deleteCityButton = findViewById(R.id.button3);

        // Input row in your layout (for adding)
        inputCity = findViewById(R.id.input_city);
        confirmButton = findViewById(R.id.confirm_button);

        // Initial data
        String[] cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};
        datalist = new ArrayList<>();
        datalist.addAll(Arrays.asList(cities));

        // Adapter + ListView
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, datalist);
        cityList.setAdapter(cityAdapter);

        // Hide add UI by default
        inputCity.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        // Tap a city to select it (needed for delete)
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
        });

        // ADD CITY: show input + confirm
        addCityButton.setOnClickListener(v -> {
            inputCity.setText("");
            inputCity.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            inputCity.requestFocus();
        });

        // CONFIRM: add city, hide input
        confirmButton.setOnClickListener(v -> {
            String name = inputCity.getText().toString().trim();
            if (!name.isEmpty()) {
                datalist.add(name);
                cityAdapter.notifyDataSetChanged();
            }
            inputCity.setText("");
            inputCity.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        });

        // DELETE CITY: delete selected item
        deleteCityButton.setOnClickListener(v -> {
            if (selectedIndex >= 0 && selectedIndex < datalist.size()) {
                datalist.remove(selectedIndex);
                selectedIndex = -1;
                cityAdapter.notifyDataSetChanged();
            }
        });
    }
}
