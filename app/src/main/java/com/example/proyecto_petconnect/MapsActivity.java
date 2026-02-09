package com.example.proyecto_petconnect;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

// CAMBIO 1: Ya no heredamos de FragmentActivity directamente, sino de BaseActivity
// BaseActivity extiende de AppCompatActivity, que a su vez soporta Fragmentos.
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // CAMBIO 2: Activar la barra de navegación marcando "Mapa"
        configurarNavegacion(R.id.nav_mapa);

        db = new DatabaseHelper(this);

        // Inicializamos el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear(); // Limpiamos mapa antes de empezar

        // Centramos la cámara en Sevilla (o tu ubicación base)
        LatLng ubicacionBase = new LatLng(37.3891, -5.9845);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionBase, 12f));

        Cursor c = db.obtenerMascotasFiltradas("Todos");

        // Variables para simular ubicaciones distintas si no tienes GPS real guardado
        double lat = 37.3891;
        double lon = -5.9845;

        if (c != null) {
            while (c.moveToNext()) {
                try {
                    String nombre = c.getString(1);
                    String estado = c.getString(4); // Perdido, Localizado, etc.
                    String path = c.getString(5);   // Ruta de la foto

                    // Asignar color según estado
                    int color = Color.BLUE; // Por defecto
                    if ("Perdido".equalsIgnoreCase(estado)) color = Color.RED;
                    else if ("Localizado".equalsIgnoreCase(estado)) color = Color.GREEN;

                    // Cargar foto si existe
                    Bitmap foto = null;
                    if (path != null && !path.isEmpty() && !path.equals("sin_foto")) {
                        File imgFile = new File(path);
                        if (imgFile.exists()) {
                            foto = BitmapFactory.decodeFile(path);
                        }
                    }

                    // USAMOS EL MÉTODO BLINDADO PARA CREAR EL ICONO
                    Bitmap iconoFinal = crearIconoSeguro(foto, color);

                    // Simulación de dispersión de marcadores (para que no salgan todos montados)
                    // En una app real, usarías c.getDouble(latitud) y c.getDouble(longitud)
                    lat += 0.004;
                    lon += 0.004;

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(nombre)
                            .snippet(estado)
                            .icon(BitmapDescriptorFactory.fromBitmap(iconoFinal)));

                } catch (Exception e) {
                    e.printStackTrace(); // Si una mascota falla, la app NO se cierra
                }
            }
            c.close();
        }
    }

    // MÉTODO BLINDADO: Crea el icono circular con borde de color
    private Bitmap crearIconoSeguro(Bitmap f, int col) {
        int s = 120; // Tamaño del icono (píxeles)
        Bitmap b = Bitmap.createBitmap(s, s, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 1. Dibujar círculo exterior de color (Estado)
        p.setColor(col);
        canvas.drawCircle(s/2f, s/2f, s/2f, p);

        // 2. Crear recorte circular para la foto (un poco más pequeño que el borde)
        Path path = new Path();
        float radioFoto = s/2f - 10;
        path.addCircle(s/2f, s/2f, radioFoto, Path.Direction.CCW);

        canvas.save();
        canvas.clipPath(path);

        // 3. Dibujar la foto o un relleno blanco si no hay foto
        if (f != null) {
            // Escalamos la foto para que llene el icono
            Bitmap fotoEscalada = Bitmap.createScaledBitmap(f, s, s, false);
            canvas.drawBitmap(fotoEscalada, 0, 0, null);
        } else {
            p.setColor(Color.WHITE);
            canvas.drawRect(0, 0, s, s, p); // Fondo blanco
        }

        canvas.restore();
        return b;
    }
}