package com.changepassword;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

import static androidx.core.content.FileProvider.getUriForFile;

//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private final int GALLERY_CODE = 1;
    private final int CAMERA_CODE = 2;
    private final int CROP_CODE = 3;
    DrawerLayout drawerLayout;
    Button changePass;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView profile, camera;
    Uri imageUri;
    View header;
    String fileName;
    private ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        camera = header.findViewById(R.id.camera);
        profile = header.findViewById(R.id.profile);

        Fragment fragment;


        // Drawable navIcon = toolbar.getNavigationIcon();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.calendar:
                        Intent cal = new Intent(MainActivity.this, CalendarViewActivity.class);
                        startActivity(cal);
                        break;
                    case R.id.barchart:
                        Fragment fragment = new BarChartFragment(MainActivity.this);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                        break;
//                        Intent bar = new Intent(MainActivity.this,BarChartFragment.class);
//                        startActivity(bar);
                    case R.id.piechart:
                        Fragment fragment_pie = new PieChartFragment(MainActivity.this);
                        FragmentManager fragmentManager_pie = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction_pie = fragmentManager_pie.beginTransaction().addToBackStack(null);
                        fragmentTransaction_pie.replace(R.id.fragment_container, fragment_pie).commit();
                        break;
//                        Intent pie = new Intent(MainActivity.this,PieChartFragment.class);
//                        startActivity(pie);
                }
                return false;
            }
        });
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                toggle.syncState();
            }
        });

        changePass = findViewById(R.id.btn_change_password);

        changePass.setOnClickListener(view -> {
            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);
            boolean focusable = true;
            int height = 1200;
            int width = 1000;
            PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImageFrom();
//                CropImage.startPickImageActivity(MainActivity.this);
            }
        });
    }

    private void SelectImageFrom() {
        final CharSequence[] items = {
                "Take Photo", "Choose from gallery",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                if (checkPermission()) {
                    takePictureFromCamera();
                }
            } else if (items[item].equals("Choose from gallery")) {
                takePictureFromGallery();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void takePictureFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        fileName = System.currentTimeMillis() + ".jpg";
        startActivityForResult(pickPhoto, GALLERY_CODE);
    }

    private void takePictureFromCamera() {
        Intent takePhoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileName = System.currentTimeMillis() + ".jpg";
        File img = new File(Environment.getExternalStorageDirectory(), fileName);
        imageUri = getUriForFile(MainActivity.this, getPackageName() + ".provider", img);
//        imageUri = Uri.fromFile(img);
//        takePhoto.setDataAndType(imageUri,"image/*");
        takePhoto.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        takePhoto.putExtra("return-data", true);
        startActivityForResult(takePhoto, CAMERA_CODE);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int camerPermission = ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA);
            if (camerPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera();
        } else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("profile", "Inside result activity!");
        Log.d("profile", "Request code: " + requestCode);
        Log.d("profile", "Result code: " + resultCode);
        switch (requestCode) {
            case GALLERY_CODE:
//                if (requestCode == MainActivity.CRO)
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
//                    Log.d("profile", "Result ok!! setting picture");
                    cropImage();
                    //profile.setImageURI(imageUri);
                }
                break;
            case CAMERA_CODE:
//                cropImage();


                break;
            case CROP_CODE:
                Log.d("profile", "inside CROP_CODE");
                Log.d("profile", "inside CROP_CODE , resultcode : " + resultCode);
                Log.d("profile", imageUri.toString());
//                Log.d("profile", data.getDataString());
//                Bitmap selectedBitmap = (Bitmap) data.getExtras().get("data");
//                Log.d("profile", selectedBitmap.toString());
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Log.d("profile", "got the data!");
                        Bitmap selectedBitmap = extras.getParcelable("data");
//                        profile.invalidate();
//                        profile.setImageURI(imageUri);
                        profile.setImageBitmap(selectedBitmap);
                    } else {
                        Log.d("profile", "DATA IS NULL");
//                        Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show();
                    }
                    //profile.setImageURI(imageUri);
                    Log.d("profile", "Result ok!! setting picture");

                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }

    }


    private void cropImage() {
        Log.d("profile", "Cropping image");
        Intent intent = new Intent("com.android.camera.action.CROP");


        intent.setDataAndType(imageUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_CODE);

        //        Log.d("profile","Returning cropped image");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}