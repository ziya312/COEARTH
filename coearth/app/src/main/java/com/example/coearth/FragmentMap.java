package com.example.coearth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.geometry.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivities;

public class FragmentMap extends Fragment implements NaverMap.OnMapClickListener, OnMapReadyCallback, Overlay.OnClickListener, NaverMap.OnCameraChangeListener {
    //지도 객체 변수
    private MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000; // 위치 권한 확인 위한 변수
    EditText srchtext;
    Button srcbtn;
    ImageButton go_detail;

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    // 네이버 맵 연결 관련 변수
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    // 마커 관련 변수
    private InfoWindow infoWindow;
    private List<Store> storeList = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    /* hi*/
    // 데이터베이스 관련 변수
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databasedReference;
    LatLng current_location;

    private ImageButton detail_btn;

    public FragmentMap() {
    }

    public static FragmentMap newInstance() {
        FragmentMap fragment = new FragmentMap();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //파이어베이스 DB
        firebaseDatabase = FirebaseDatabase.getInstance(); // 파이어베이스 DB 연동
        databasedReference = firebaseDatabase.getReference("stores"); // DB 테이블 연결

        databasedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 DB의 데이터를 받아오는 곳
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Store stores = dataSnapshot.getValue(Store.class);
                    storeList.add(stores);
                    updateMarkers(storeList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB 가져오던 중 에러 발생
            }
        });

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map,
                container, false);

        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        srchtext = (EditText) rootView.findViewById(R.id.srchtext);
        srcbtn = (Button) rootView.findViewById(R.id.search_btn);

        srcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = srchtext.getText().toString();
                if (s.contains("강남")) {
                    CameraUpdate cameraUpdate1 = CameraUpdate.scrollTo(new LatLng(37.49737284910393, 127.06278777741936));
                    naverMap.moveCamera(cameraUpdate1);
                    Toast.makeText(getActivity(), "강남구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("강동")) {
                    CameraUpdate cameraUpdate2 = CameraUpdate.scrollTo(new LatLng(37.55036749295186, 127.14779896069199));
                    naverMap.moveCamera(cameraUpdate2);
                    Toast.makeText(getActivity(), "강동구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("강북")) {
                    CameraUpdate cameraUpdate4 = CameraUpdate.scrollTo(new LatLng(37.63298357051687, 127.01966792035306));
                    naverMap.moveCamera(cameraUpdate4);
                    Toast.makeText(getActivity(), "강북구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("강서")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.56128114630405, 126.82546875660827));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "강서구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("구로")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.49574437566399, 126.85409136833476));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "구로구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("관악")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.478866054587535, 126.95118194127997));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "관악구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("광진")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.54667473908015, 127.08569849237743));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "광진구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("금천")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.461619598299514, 126.90092273048607));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "금천구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("노원")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.65200041054022, 127.06392120839735));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "노원구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("도봉")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.66465190657072, 127.04157241605112));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "도봉구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("동대문")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.58207387115956, 127.05467217679));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "동대문구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("동작")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.5025582451458, 126.94866936709913));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "동작구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("마포")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.56063135817608, 126.90982042981753));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "마포구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("서대문")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.48650994942116, 127.01591546825614));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "서대문구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("성동")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.55446033571277, 127.04077713278839));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "성동구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("성북")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.602328156844266, 127.01854108885614));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "성북구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("송파")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.50582525993265, 127.11360612273961));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "송파구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("양천")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.52409561907216, 126.85962306923388));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "양천구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("영등포")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.52340871707057, 126.90870187879047));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "영등포구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("용산")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.53220157646376, 126.9899339474268));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "용산구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("은평")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.61471115248485, 126.9266012093883));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "은평구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("중구")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.56332063228667, 126.99656849198777));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "중구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("중랑")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.598386945810105, 127.09167901985337));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "중랑구", Toast.LENGTH_SHORT).show();
                } else if (s.contains("종로")) {
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.58184343427234, 126.98219162038627));
                    naverMap.moveCamera(cameraUpdate);
                    Toast.makeText(getActivity(), "종로구", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) // 카메라 초기 위치 설정
    {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        //건물 표시
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); //현위치

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setScaleBarEnabled(true);
        uiSettings.setZoomControlEnabled(true);
        uiSettings.setLocationButtonEnabled(true); //현위치


        // 마커 정보창 설정
        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(getActivity()) {
            @NonNull
            @Override
            protected View getContentView(@NonNull InfoWindow infoWindow) {
                Marker marker = infoWindow.getMarker();
                Store stores = (Store) marker.getTag();
                View view = View.inflate(getActivity(), R.layout.st_point, null);

                float rating;
                TextView ratingNum;

                Log.d("image HERE",stores.getImg());

                /*
                ImageView img = (ImageView) view.findViewById(R.id.img_store);
                Glide.with(FragmentMap.this).load(stores.getImg()).into(img);*/

                ((TextView) view.findViewById(R.id.stName)).setText(stores.getTitle());
                ((TextView) view.findViewById(R.id.stDetail)).setText(stores.getDetail());
                ((TextView) view.findViewById(R.id.stCategory)).setText(stores.getCategory());

                ratingNum = view.findViewById(R.id.rating);
                ratingNum.setText(""+stores.getRat());
                return view;
            }
        });
    }
    public void updateMarkers(List<Store> storeList) {
        resetMarkerList();
        for (Store stores : this.storeList) {
            Marker marker = new Marker();
            marker.setTag(stores);
            marker.setPosition(new LatLng(stores.getLat(), stores.getLng()));
            String c = stores.getCategory();

            if (c.equals("생활용품")) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.lifeitem));
            } else if (c.equals("카페")) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.cafe));
            } else if (c.equals("화장품")) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.fashion));
            } else if (c.equals("베이커리")) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.cafe));
            } else
                marker.setIcon(OverlayImage.fromResource(R.drawable.spot));

            marker.setAnchor(new PointF(0.5f, 1.0f));
            marker.setMap(naverMap);
            marker.setOnClickListener(this);
            markerList.add(marker);
        }
    }


    private void resetMarkerList() {
        if (markerList != null && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.setMap(null);
            }
            markerList.clear();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            Marker marker = (Marker) overlay;
            if (marker.getInfoWindow() != null) {
                infoWindow.close();
            } else {
                infoWindow.open(marker);
            }
            return true;
        }
        return false;
    }


    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        if (infoWindow.getMarker() != null) {
            infoWindow.close();
        }
    }

    @Override
    public void onCameraChange(int i, boolean b) {

    }
}
