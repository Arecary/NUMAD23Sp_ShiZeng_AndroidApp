package edu.northeastern.numad23sp_shizeng;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationRequest.Builder;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

public class Location extends AppCompatActivity implements View.OnClickListener {

  TextView tvLatitude, tvLongitude, tvDistance;
  Double latitude, longitude;
  Float distance = 0.0f;
  String lat, lon, dis = "0.0";
  Boolean flag = true;
  Button button;

  FusedLocationProviderClient fusedLocationProviderClient;
  LocationRequest locationRequest;
  LocationCallback locationCallback;
  android.location.Location location1, location2, locationTemp;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location);
    if (savedInstanceState != null) {
      distance = savedInstanceState.getFloat("distance");
      useGps();
    }
    tvLatitude = (TextView) findViewById(R.id.tv_Location_Latitude);
    tvLongitude = (TextView) findViewById(R.id.tv_Location_Longitude);
    tvDistance = (TextView) findViewById(R.id.tv_Location_Distance);
    button = (Button) findViewById(R.id.btn_distanceReset);

    button.setOnClickListener(this);

    locationRequest = new Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
        .setWaitForAccurateLocation(false)
        .setMinUpdateIntervalMillis(3000)
        .setMaxUpdateDelayMillis(4000)
        .build();

    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(@NonNull LocationResult locationResult) {
        super.onLocationResult(locationResult);
        android.location.Location location = locationResult.getLastLocation();
        locationTemp = location1;
        if (location2 != null) {
          if (locationTemp.distanceTo(location) >= 4.5f) {
            location1 = location2;
          } else {
            locationTemp = location;
          }
        } else {
          if (locationTemp.distanceTo(location) <= 4.5f) {
            locationTemp = location;
          } else {
            location2 = location;
          }
        }


        updateUi(location);

      }
    };

    // This constant is deprecated.
/*    locationRequest = new LocationRequest();
    locationRequest.setInterval(2000);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/

  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btn_distanceReset) {

      distance = 0.0f;
      location2 = null;
      useGps();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case 99:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          useGps();
        } else {
          Toast.makeText(Location.this, "No permission on GPS service!", Toast.LENGTH_LONG).show();
          finish();
        }
        break;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    useGps();

    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

    useGps();
  }

  @Override
  public void onBackPressed() {
    new AlertDialog.Builder(this)
        .setTitle("Closing Activity")
        .setMessage("Are you sure you want to close this activity?")
        .setPositiveButton("Yes", (dialog, which) -> finish())
        .setNegativeButton("No", null)
        .show();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

    super.onSaveInstanceState(savedInstanceState);
    savedInstanceState.putFloat("distance", distance);
  }


  public void useGps() {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Location.this);
    if (ActivityCompat.checkSelfPermission(Location.this, permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      fusedLocationProviderClient.getLastLocation().addOnSuccessListener(Location.this,
          new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
              location1 = location;
              updateUi(location);

            }
          });

    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(new String[]{permission.ACCESS_FINE_LOCATION}, 99);
      }

    }
  }

  public void updateUi(android.location.Location location) {
    latitude = location.getLatitude();
    longitude = location.getLongitude();
    lat = String.valueOf(latitude);
    lat = " latitude: " + lat;
    lon = String.valueOf(longitude);
    lon = " longitude: " + lon;

    if (location1 == null || location2 == null) {
      dis = " Distance is : 0.0";
    } else {
      distance = distance + location2.distanceTo(location1);
      dis = String.valueOf(distance);
      dis = " Distance is : " + dis;
    }
    tvDistance.setText(dis);
    tvLatitude.setText(lat);
    tvLongitude.setText(lon);
  }



  /*class MyThread extends Thread {
    @Override
    public void run() {
      super.run();

      fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Location.this);
      if (ActivityCompat.checkSelfPermission(Location.this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(Location.this,
            new OnSuccessListener<android.location.Location>() {
              @Override
              public void onSuccess(android.location.Location location) {
                updateUi(location);

                locationCallback = new LocationCallback() {
                  @Override
                  public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    android.location.Location location = locationResult.getLastLocation();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                  }
                };


              }
            });

      } else {
*//*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          requestPermissions(new String[] {permission.ACCESS_FINE_LOCATION}, 90);
        }*//*
        Toast.makeText(Location.this, "No permission on GPS service!", Toast.LENGTH_LONG).show();
      }


    }
  }*/


}