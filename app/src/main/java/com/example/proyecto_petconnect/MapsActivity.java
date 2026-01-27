package com.example.proyecto_petconnect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper db;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        centrarEnMiUbicacion();
        cargarMarcadoresDesdeBD();
    }

    private void centrarEnMiUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng miPos = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miPos, 14f));
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    private void cargarMarcadoresDesdeBD() {
        Cursor cursor = db.obtenerTodasLasMascotas();
        // Simulamos un desplazamiento pequeño para que no se amontonen todos en el mismo punto exacto
        double latBase = 37.3891;
        double lonBase = -5.9845;
        int i = 0;

        while (cursor.moveToNext()) {
            String nombre = cursor.getString(1);
            String estado = cursor.getString(4);

            int colorAro;
            if (estado.equalsIgnoreCase("Perdido")) colorAro = Color.RED;
            else if (estado.equalsIgnoreCase("Localizado")) colorAro = Color.parseColor("#2E7D32");
            else colorAro = Color.BLUE;

            // Coordenada con pequeña variación para la demo
            LatLng pos = new LatLng(latBase + (i * 0.002), lonBase + (i * 0.002));

            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(nombre)
                    .snippet("Estado: " + estado)
                    .icon(BitmapDescriptorFactory.fromBitmap(crearMarcadorCircular(colorAro))));
            i++;
        }
    }

    private Bitmap crearMarcadorCircular(int colorBorde) {
        int size = 120;
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(colorBorde);
        canvas.drawCircle(size/2f, size/2f, size/2f, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(size/2f, size/2f, size/2f - 10, paint);

        return output;
    }
}