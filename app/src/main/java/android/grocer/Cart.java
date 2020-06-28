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

import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Cart extends Fragment implements OnMapReadyCallback {

    public Cart() {
        // Required empty public constructor
    }

    Geocoder geocoder;
    GoogleMap mMap;
    CartItemAdapter adapter;
    Boolean location = false;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext());
        //fetchLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
        }
    }

    private void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;

        }
        Log.d("STATE", "FETCH");

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                Log.d("STATE", "succ");
                if (location != null) {

                    currentLocation = location;
                    //((SupportMapFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.map))).getMapAsync(Cart.this);


                    Log.d("STATE", "L:"+ String.valueOf(currentLocation.getLatitude()));

                    LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

                    //Geocoder geocoder;
                    List<Address> addresses = null;
                    //geocoder = new Geocoder(this.requireContext(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    assert addresses != null;
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()))
                            .title(address)
                            .snippet(city+state+country+postalCode));
                    marker.showInfoWindow();


                    Log.d("STATE",address+city+state+country+postalCode);


                }

            }

        });
        task.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Log.d("STATE", "complete"+task.getResult());
            }

        });

        task.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("STATE", "fail");

            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        geocoder = new Geocoder(requireContext(), Locale.getDefault());
        final MainActivity MA = ((MainActivity) requireActivity());

        RecyclerView recyclerView = view.findViewById(R.id.order_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CartItemAdapter(getActivity(), MA.orderedItemsList, Cart.this, view);
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

                    Toast.makeText(MA.getApplicationContext(), "Please select location.", Toast.LENGTH_SHORT).show();

            }
        });

        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext());
        ((SupportMapFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.map))).getMapAsync(Cart.this);
        fetchLocation();

        return view;

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Log.d("STATE","READY");
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.requireContext(), R.raw.map_style));
        mMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                location = true;

                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(markerOptions);

            }

        });

/*        Log.d("STATE", "L:"+ String.valueOf(currentLocation.getLatitude()));

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this.requireContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()))
                .title(address)
                .snippet(city+state+country+postalCode));
        marker.showInfoWindow();


Log.d("STATE",address+city+state+country+postalCode);
*/
    }


}

