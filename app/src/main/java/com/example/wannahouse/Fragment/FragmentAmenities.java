package com.example.wannahouse.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.EditHouseActivity;
import com.example.wannahouse.Adapter.ImageChooseAdapter;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.foder;
import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Fragment.FragmentInformation.houseEdit;

public class FragmentAmenities extends Fragment {
    public static float alpha = (float) 0.15;

    private TextInputLayout layoutImage;
    private Button button_takePicture;
    private Button button_next3;
    public static int CAMERA_REQUEST_CODE = 1000;
    public static int PICK_IMAGE_REQUEST_CODE = 1001;
    public static int UPLOAD_IMAGE_REQUEST_CODE = 1002;
    private GridView gridView_imageChoose;
    private ImageChooseAdapter imageChooseAdapter;
    private Context context;

    public ArrayList<Bitmap> arrayListBitmap;
    public ArrayList<Uri> arrayListUri;
    private ArrayList<String> urlImage;

    ViewGroup air;
    ViewGroup privateWC;
    public static ViewGroup parking;
    public static ViewGroup internet;
    ViewGroup security;
    ViewGroup noOwner;
    ViewGroup noCurfew;
    ViewGroup cooking;
    ViewGroup bed;
    ViewGroup window;
    ViewGroup waterHeater;
    ViewGroup washing;
    ViewGroup wardrobe;
    ViewGroup fridge;
    ViewGroup loft;
    ViewGroup television;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_amenities, container, false);

        arrayListBitmap = new ArrayList<>();
        arrayListUri = new ArrayList<>();
        urlImage = new ArrayList<>();

        button_takePicture = view.findViewById(R.id.button_takePicture);
        gridView_imageChoose = view.findViewById(R.id.gridView_imageChoose);
        layoutImage = view.findViewById(R.id.layout_Image);
        context = getContext();

        if( houseEdit == null ) {
            setIconViewGroupClick(houseNew);
        }
        else {
            setIconViewGroupClick(houseEdit);
            Log.d("TTT", "Result " + houseEdit.getRoom_id());
            editAmenities(houseEdit);
        }

        gridView_imageChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayListBitmap.remove(position);
                urlImage.remove(position);
                imageChooseAdapter.notifyDataSetChanged();
            }
        });

        button_takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"Take Photo", "Choose from Library", "Cancel"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Photo!");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openCamera();
                        } else if (which == 1) {
                            pickFromGallery();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        button_next3 = view.findViewById(R.id.button_next3);
        button_next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next3(v);
            }
        });

        return view;
    }

    public static Uri photoPath;
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        Log.d("KEYAA", image.toString() + " image");
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("KEYAA", photoFile.toString() + " error");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                photoPath = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                getActivity().startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                Log.d("KEYAA", photoURI.toString() + " uri");
            }
        }
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        getActivity().startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    public void showImageInGridView(Intent data) {
        if (data != null) {
            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri selectedImage = clipData.getItemAt(i).getUri();
                arrayListUri.add(selectedImage);
                try {
                    InputStream is = context.getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    arrayListBitmap.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Log.d("KEYAA", "uri");
            data.putParcelableArrayListExtra("LISTURI", arrayListUri);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            arrayListBitmap.add(bitmap);
        }
        imageChooseAdapter = new ImageChooseAdapter(context, arrayListBitmap);
        gridView_imageChoose.setAdapter(imageChooseAdapter);
    }

    public void next3(View v) {
    //    Log.d("KEYAA", arrayListUri.toString() + " " + arrayListUri.size() + "Uri");
    //    Log.d("KEYAA", arrayListBitmap.toString() + " " + arrayListBitmap.size() + "bitmap");
        if (urlImage.size() < 4) {
            layoutImage.setError("you must take more than 4 picture");
            return;
        } else {
            if( houseEdit == null ) {
                savingData(houseNew);
            }
            else{
                savingData(houseEdit);
            }
            ((ListYourSpaceActivity) getActivity()).next33(v);
        }


    }

    private void savingData(House houseNew) {
        houseNew.setImage(urlImage);
        if (air.getAlpha() == 1) {
            houseNew.setAirConditioner(true);
        } else {
            houseNew.setAirConditioner(false);
        }
        if (privateWC.getAlpha() == 1) {
            houseNew.setPrivateWC(true);
        } else {
            houseNew.setPrivateWC(false);
        }
        if (parking.getAlpha() == 1) {
            houseNew.setParkingLot(true);
        } else {
            houseNew.setParkingLot(false);
        }
        if (internet.getAlpha() == 1) {
            houseNew.setInternet(true);
        } else {
            houseNew.setInternet(false);
        }
        if (security.getAlpha() == 1) {
            houseNew.setSecurity(true);
        } else {
            houseNew.setSecurity(false);
        }
        if (noOwner.getAlpha() == 1) {
            houseNew.setNoOwner(true);
        } else {
            houseNew.setNoOwner(false);
        }
        if (noCurfew.getAlpha() == 1) {
            houseNew.setNoCurfew(true);
        } else {
            houseNew.setNoCurfew(false);
        }
        if (cooking.getAlpha() == 1) {
            houseNew.setCook(true);
        } else {
            houseNew.setCook(false);
        }
        if (bed.getAlpha() == 1) {
            houseNew.setBed(true);
        } else {
            houseNew.setBed(false);
        }
        if (window.getAlpha() == 1) {
            houseNew.setWindow(true);
        } else {
            houseNew.setWindow(false);
        }
        if (waterHeater.getAlpha() == 1) {
            houseNew.setWaterHeater(true);
        } else {
            houseNew.setWaterHeater(false);
        }
        if (washing.getAlpha() == 1) {
            houseNew.setWashing(true);
        } else {
            houseNew.setWashing(false);
        }
        if (wardrobe.getAlpha() == 1) {
            houseNew.setWardrobe(true);
        } else {
            houseNew.setWardrobe(false);
        }
        if (fridge.getAlpha() == 1) {
            houseNew.setFridge(true);
        } else {
            houseNew.setFridge(false);
        }
        if (loft.getAlpha() == 1) {
            houseNew.setLoft(true);
        } else {
            houseNew.setLoft(false);
        }
        if (television.getAlpha() == 1) {
            houseNew.setTelevision(true);
        } else {
            houseNew.setTelevision(false);
        }
    }

    public static void iconViewGroupClick(final ViewGroup viewGroup) {
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewGroup.getAlpha() == 1) {
                    viewGroup.setAlpha(alpha);
                } else {
                    viewGroup.setAlpha(1);
                }
            }
        });
    }

    private void setIconViewGroupClick(House houseNew) {
        air = view.findViewById(R.id.amenitiesAir);
        privateWC = view.findViewById(R.id.amenitiesPrivateWC);
        parking = view.findViewById(R.id.amenitiesParking);
        internet = view.findViewById(R.id.amenitiesInternet);
        security = view.findViewById(R.id.amenitiesSecurity);
        noOwner = view.findViewById(R.id.amenitiesNoOwner);
        noCurfew = view.findViewById(R.id.amenitiesNoCurfew);
        cooking = view.findViewById(R.id.amenitiesCooking);
        bed = view.findViewById(R.id.amenitiesBed);
        window = view.findViewById(R.id.amenitiesWindow);
        waterHeater = view.findViewById(R.id.amenitiesWaterHeater);
        washing = view.findViewById(R.id.amenitiesWashing);
        wardrobe = view.findViewById(R.id.amenitiesWardrobe);
        fridge = view.findViewById(R.id.amenitiesFridge);
        loft = view.findViewById(R.id.amenitiesLoft);
        television = view.findViewById(R.id.amenitiesTelevision);

        if (!houseNew.isAirConditioner()) air.setAlpha(alpha);
        if (!houseNew.isPrivateWC()) privateWC.setAlpha(alpha);
        if (!houseNew.isParkingLot()) parking.setAlpha(alpha);
        if (!houseNew.isInternet()) internet.setAlpha(alpha);
        if (!houseNew.isSecurity()) security.setAlpha(alpha);
        if (!houseNew.isNoOwner()) noOwner.setAlpha(alpha);
        if (!houseNew.isNoCurfew()) noCurfew.setAlpha(alpha);
        if (!houseNew.isCook()) cooking.setAlpha(alpha);
        if (!houseNew.isBed()) bed.setAlpha(alpha);
        if (!houseNew.isWindow()) window.setAlpha(alpha);
        if (!houseNew.isWaterHeater()) waterHeater.setAlpha(alpha);
        if (!houseNew.isWashing()) washing.setAlpha(alpha);
        if (!houseNew.isWardrobe()) wardrobe.setAlpha(alpha);
        if (!houseNew.isFridge()) fridge.setAlpha(alpha);
        if (!houseNew.isLoft()) loft.setAlpha(alpha);
        if (!houseNew.isTelevision()) television.setAlpha(alpha);

        iconViewGroupClick(air);
        iconViewGroupClick(privateWC);
        iconViewGroupClick(parking);
        iconViewGroupClick(internet);
        iconViewGroupClick(security);
        iconViewGroupClick(noOwner);
        iconViewGroupClick(noCurfew);
        iconViewGroupClick(cooking);
        iconViewGroupClick(bed);
        iconViewGroupClick(window);
        iconViewGroupClick(waterHeater);
        iconViewGroupClick(washing);
        iconViewGroupClick(wardrobe);
        iconViewGroupClick(fridge);
        iconViewGroupClick(loft);
        iconViewGroupClick(television);
    }

    public void uploadImageToCloud(ArrayList<Uri> arrayList) {
        Log.d("KEYAA", "uploading");
        for(int i = 0 ; i < arrayList.size() ; i++) {
            Uri temp = arrayList.get(i);
            final StorageReference imageName = foder.child( "image" + temp.getLastPathSegment());
            imageName.putFile(temp).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlImage.add(( uri.toString()));
                            Log.d("KEYAA", "url " + urlImage.size() +  " " + uri.toString());

                        }
                    });
                }
            });
        }
        Toast.makeText(getActivity(),"Upload Done",Toast.LENGTH_SHORT).show();
    }

    void editAmenities(House houseEdit) {
        urlImage = houseEdit.getImage();
        for(int i = 0 ; i < houseEdit.getImage().size() ; i++) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                URL url = new URL(houseEdit.getImage().get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.d("QWE", "url" + url.toString());
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                arrayListBitmap.add(bitmap);
            } catch (IOException e) {
                Toast.makeText(getActivity(),"Load Error",Toast.LENGTH_SHORT).show();
            }
        }
        imageChooseAdapter = new ImageChooseAdapter(context, arrayListBitmap);
        gridView_imageChoose.setAdapter(imageChooseAdapter);
    }
}

