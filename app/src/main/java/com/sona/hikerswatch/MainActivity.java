package com.sona.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView resulttext;
    LocationListener locationListener;
    LocationManager locationManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resulttext=(TextView) findViewById(R.id.resultText);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());
                try {
                    List<Address>useraddress1=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if (useraddress1!=null && useraddress1.size()>0){
                        String ab="";
                        ab="Latitude:"+useraddress1.get(0).getLatitude()+"\n\nLongitude:"+useraddress1.get(0).getLongitude()+"\n\nCountry:"+useraddress1.get(0).getCountryName()+"\n\nAddress:"+useraddress1.get(0).getThoroughfare()+"\n"+useraddress1.get(0).getLocality()+"\n"+useraddress1.get(0).getPostalCode();
                        Log.i("Hello",ab);
                        resulttext.setText(ab);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastloc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());
            try {
                List<Address>useraddress=geocoder.getFromLocation(lastloc.getLatitude(),lastloc.getLongitude(),1);
                if (useraddress!=null && useraddress.size()>0){
                    String abc="";
                    abc="Latitude:"+useraddress.get(0).getLatitude()+"\n\nLongitude:"+useraddress.get(0).getLongitude()+"\n\nCountry:"+useraddress.get(0).getCountryName()+"\n\nAddress:"+useraddress.get(0).getThoroughfare()+"\n"+useraddress.get(0).getSubThoroughfare()+"\n"+useraddress.get(0).getPostalCode();
                    Log.i("Hello",abc);
                    resulttext.setText(abc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }





}
