package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        db = new DatabaseHelper(this);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(marker -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:600000000")); // Teléfono simulado
            startActivity(i);
        });

        Cursor c = db.obtenerMascotasFiltradas("Todos");
        double lat = 37.3891, lon = -5.9845;
        while (c.moveToNext()) {
            int color = c.getString(4).equals("Perdido") ? Color.RED :
                    c.getString(4).equals("Localizado") ? Color.GREEN : Color.BLUE;
            Bitmap foto = BitmapFactory.decodeFile(c.getString(5));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat += 0.003, lon += 0.003))
                    .title(c.getString(1))
                    .snippet("Estado: " + c.getString(4) + "\nPulsa para contactar")
                    .icon(BitmapDescriptorFactory.fromBitmap(crearIcono(foto, color))));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3891, -5.9845), 13f));
    }

    private Bitmap crearIcono(Bitmap f, int col) {
        int s = 140;
        Bitmap b = Bitmap.createBitmap(s, s, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(col);
        canvas.drawCircle(s/2f, s/2f, s/2f, p);
        Path path = new Path();
        path.addCircle(s/2f, s/2f, s/2f - 10, Path.Direction.CCW);
        canvas.clipPath(path);
        if (f != null) canvas.drawBitmap(Bitmap.createScaledBitmap(f, s, s, false), 0, 0, null);
        else { p.setColor(Color.WHITE); canvas.drawRect(0,0,s,s,p); }
        return b;
    }
}