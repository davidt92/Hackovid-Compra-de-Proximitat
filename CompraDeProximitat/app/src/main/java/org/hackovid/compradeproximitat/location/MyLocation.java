package org.hackovid.compradeproximitat.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class MyLocation
{

    private static MyLocation myLocation;

    private FusedLocationProviderClient fusedLocationClient;

    private String lastPostalCode;

    private String lastCity;

    public String getLastPostalCode()
    {
        return lastPostalCode;
    }

    public String getLastCity()
    {
        return lastCity;
    }

    public static MyLocation getSingletonLocation(Activity activity)
    {
        if (myLocation == null)
        {
            myLocation = new MyLocation(activity);
        }

        return myLocation;

    }

    private MyLocation(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            checkPermission(activity);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, location ->
                {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null)
                    {
                        List<Address> addresses = null;
                        final Geocoder gcd = new Geocoder(activity, Locale.getDefault());
                        try
                        {
                            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            if (addresses.size() > 0)
                            {
                                lastPostalCode = addresses.get(0).getPostalCode();
                                lastCity = addresses.get(0).getLocality();
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println(location);
                        // Logic to handle location object
                    }
                });

    }

    private void checkPermission(Activity activity)
    {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {//Can add more as per requirement
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }
}
