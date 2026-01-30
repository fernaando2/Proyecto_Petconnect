package com.example.proyecto_petconnect;

import android.database.Cursor;
import android.graphics.*;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import java.io.File;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        db = new DatabaseHelper(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear(); // Limpiamos mapa antes de empezar

        Cursor c = db.obtenerMascotasFiltradas("Todos");
        double lat = 37.3891, lon = -5.9845; // Coordenadas base

        if (c != null) {
            while (c.moveToNext()) {
                try {
                    String nombre = c.getString(1);
                    String estado = c.getString(4);
                    String path = c.getString(5);

                    int color = Color.BLUE;
                    if ("Perdido".equalsIgnoreCase(estado)) color = Color.RED;
                    else if ("Localizado".equalsIgnoreCase(estado)) color = Color.GREEN;

                    Bitmap foto = null;
                    if (path != null && !path.isEmpty() && !path.equals("sin_foto")) {
                        File imgFile = new File(path);
                        if (imgFile.exists()) {
                            foto = BitmapFactory.decodeFile(path);
                        }
                    }

                    // USAMOS EL MÉTODO BLINDADO
                    Bitmap iconoFinal = crearIconoSeguro(foto, color);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat += 0.004, lon += 0.004))
                            .title(nombre)
                            .snippet(estado)
                            .icon(BitmapDescriptorFactory.fromBitmap(iconoFinal)));

                } catch (Exception e) {
                    e.printStackTrace(); // Si una mascota falla, la app NO se cierra
                }
            }
            c.close();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3891, -5.9845), 12f));
    }

    // MÉTODO BLINDADO: Nunca devuelve null, evita el cierre de la app
    private Bitmap crearIconoSeguro(Bitmap f, int col) {
        int s = 120; // tamaño
        Bitmap b = Bitmap.createBitmap(s, s, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Dibujar círculo exterior de color
        p.setColor(col);
        canvas.drawCircle(s/2f, s/2f, s/2f, p);

        // Recorte circular para la foto
        Path path = new Path();
        path.addCircle(s/2f, s/2f, s/2f - 10, Path.Direction.CCW);
        canvas.save();
        canvas.clipPath(path);

        if (f != null) {
            canvas.drawBitmap(Bitmap.createScaledBitmap(f, s, s, false), 0, 0, null);
        } else {
            p.setColor(Color.WHITE);
            canvas.drawRect(0, 0, s, s, p); // Si no hay foto, fondo blanco
        }
        canvas.restore();
        return b;
    }
}