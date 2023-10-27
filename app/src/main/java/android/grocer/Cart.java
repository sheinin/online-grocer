package android.grocer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Cart extends Fragment implements OnMapReadyCallback {

    public Cart() {}

    Boolean location = false;
    CartItemAdapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    GoogleMap mMap;
    MainActivity MA;

    private static final int REQUEST_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.order_cart);
        MA = ((MainActivity) requireActivity());
        adapter = new CartItemAdapter(getActivity(), MA.orderedItemsList, Cart.this, view);
        geocoder = new Geocoder(requireContext(), Locale.getDefault());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Cart.this).navigate(R.id.action_CartFragment_to_MenuFragment);
            }
        });

        view.findViewById(R.id.cart_order_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (location)

                    NavHostFragment.findNavController(Cart.this).navigate(R.id.action_CartFragment_to_FinishFragment);

                else

                    Toast.makeText(MA.getApplicationContext(), view.getContext().getText(R.string.cart_location_none), Toast.LENGTH_SHORT).show();

            }

        });


        ((SupportMapFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.map))).getMapAsync(Cart.this);

        view.findViewById(R.id.map_locate).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                fetchLocation();

            }

        } );

        return view;

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.requireContext(), R.raw.map_style));
        mMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                showLocation(latLng);

            }

        });

    }

    private void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;

        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {

                if (location != null)

                    showLocation(new LatLng(location.getLatitude(), location.getLongitude()));

                else

                    Toast.makeText(MA.getApplicationContext(), getText(R.string.cart_location_fail), Toast.LENGTH_SHORT).show();

            }

        });

    }

    void showLocation(final LatLng latLng) {

        location = true;

        mMap.clear();

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng));

        assert marker != null;
        marker.showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

                List<Address> addresses = null;
                Marker marker;

                try {

                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                } catch (IOException e) {

                   // e.printStackTrace();

                }

                if (addresses != null) {

                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();

                    String[] addressArray = {city, state, country, postalCode};

                    List<String> list = new ArrayList<>();

                    for (String s : addressArray)

                        if (s != null && !s.isEmpty())

                            list.add(s);

                    addressArray = list.toArray(new String[0]);

                    String result = "";

                    if (addressArray.length > 0) {

                        StringBuilder sb = new StringBuilder();

                        for (String s : addressArray)

                            sb.append(s).append(", ");

                        result = sb.deleteCharAt(sb.length() - 2).toString();

                    }

                    marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(address != null ? address : "")
                            .snippet(result));

                    assert marker != null;
                    marker.showInfoWindow();

                }

            }

            @Override
            public void onCancel() {

            }
        });


    }

}

