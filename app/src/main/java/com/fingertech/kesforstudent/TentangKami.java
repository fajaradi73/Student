package com.fingertech.kesforstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TentangKami extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap maptentang;
    TextView alamat_tentang,office1,office2,mobile,web,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang_kami);
        final Toolbar toolbar = findViewById(R.id.toolbar_tentang);
        alamat_tentang        = findViewById(R.id.alamat_tentang);
        office1               = findViewById(R.id.office1);
        office2               = findViewById(R.id.office2);
        mobile                  = findViewById(R.id.Mobile);
        web                     = findViewById(R.id.web);
        email                   = findViewById(R.id.Email);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTentang);
        mapFragment.getMapAsync(this);

        Linkify.addLinks(office1, Linkify.ALL);
        office1.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(office2, Linkify.ALL);
        office2.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(mobile, Linkify.ALL);
        mobile.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(web, Linkify.ALL);
        web.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(email, Linkify.ALL);
        email.setLinkTextColor(Color.parseColor("#ffffff"));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        maptentang = googleMap;
        final LatLng latLng = new LatLng(-6.110631, 106.776096);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
        final MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("PT Gaia Persada")
                .icon(bitmapDescriptorFromVector(TentangKami.this, R.drawable.ic_map));

        //move map camera
        maptentang.addMarker(markerOptions);
        maptentang.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        maptentang.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
