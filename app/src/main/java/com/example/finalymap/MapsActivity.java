package com.example.finalymap;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.finalymap.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    boolean serviceA;
    boolean serviceB;
    boolean serviceC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        serviceA = intent.getBooleanExtra("a", false);
        serviceB = intent.getBooleanExtra("b", false);
        serviceC = intent.getBooleanExtra("c", false);

        System.out.println("*************************");
        System.out.println(serviceA);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private String JsonDataFromAsset(String fileName) {
        String json = null;
        try {
            InputStream inputStream = getAssets().open(fileName);
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset("pins.json"));

            JSONArray ArrayOfPins = jsonObject.getJSONArray("pins");
            JSONArray ArrayOfServices = jsonObject.getJSONArray("services");
            System.out.println("*****************************");
            for (int i=0;i<ArrayOfPins.length();i++) {
                JSONObject Pins = ArrayOfPins.getJSONObject(i);

                String id = Pins.getString("id");
                String service = Pins.getString("service");

                JSONObject ArrayOfCoordinates = Pins.getJSONObject("coordinates");
                Double lat = ArrayOfCoordinates.getDouble("lat");
                Double lng = ArrayOfCoordinates.getDouble("lng");

                if (service.equals("a") && serviceA || service.equals("b") && serviceB || service.equals("c") && serviceC) {

                    try {
                        LatLng new_coordinate = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(new_coordinate).title(id));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new_coordinate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}