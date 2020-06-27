package android.grocer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;


public class Cart extends Fragment implements OnMapReadyCallback {

    public Cart() {
        // Required empty public constructor
    }


    CartItemAdapter adapter;
    Boolean location = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        final MainActivity MA = ((MainActivity) requireActivity());

        RecyclerView recyclerView = view.findViewById(R.id.order_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new CartItemAdapter(getActivity(), MA.orderedItemsList, Cart.this, view);
        recyclerView.setAdapter(adapter);

        MA.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
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

        ((SupportMapFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.map))).getMapAsync(this);

        return view;

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.requireContext(), R.raw.map_style));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                requireView().findViewById(R.id.cart_loc_reminder).setVisibility(View.INVISIBLE);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                location = true;

                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(markerOptions);

            }

        });

    }

}

