package com.example.finalymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Chip chipA;
    Chip chipB;
    Chip chipC;
    ChipGroup chipGroup;
    final Boolean[] ArrayOfBoolean = {false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button buttonMain = (Button) findViewById(R.id.buttonMain);

        chipGroup = (ChipGroup) findViewById(R.id.services);
        chipA = (Chip) findViewById(R.id.serviceA);
        chipB = (Chip) findViewById(R.id.serviceB);
        chipC = (Chip) findViewById(R.id.serviceC);

        chipA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ArrayOfBoolean[0] = b;
                System.out.println(ArrayOfBoolean[0]);
            }
        });

        chipB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ArrayOfBoolean[1] = b;
                System.out.println(ArrayOfBoolean[1]);
            }
        });

        chipC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ArrayOfBoolean[2] = b;
                System.out.println(ArrayOfBoolean[2]);
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("a", ArrayOfBoolean[0]);
                intent.putExtra("b", ArrayOfBoolean[1]);
                intent.putExtra("c", ArrayOfBoolean[2]);
                startActivity(intent);
            }
        };

        buttonMain.setOnClickListener(clickListener);

    }

}
