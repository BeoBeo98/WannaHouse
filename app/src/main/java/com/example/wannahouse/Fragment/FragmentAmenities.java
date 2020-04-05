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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Adapter.ImageChooseAdapter;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;

public class FragmentAmenities extends Fragment {
    float alpha = (float) 0.15;

    private TextInputLayout layoutImage;
    private Button button_takePicture;
    private Button button_next3;
    public static int CAMERA_REQUEST_CODE = 1000;
    public static int PICK_IMAGE_REQUEST_CODE = 1001;
    private GridView gridView_imageChoose;
    private ImageChooseAdapter imageChooseAdapter;
    private Context context;

    private ImageView imageViewTest;

    private String currentPhotoPath;

    public ArrayList<Bitmap> arrayListBitmap = new ArrayList<>();
    public ArrayList<Uri> arrayListUri = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_amenities, container, false);
        button_takePicture = view.findViewById(R.id.button_takePicture);
        gridView_imageChoose = view.findViewById(R.id.gridView_imageChoose);
        layoutImage = view.findViewById(R.id.layout_Image);

        context = view.getContext();

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
        setIconViewGroupClick();

        button_next3 = view.findViewById(R.id.button_next3);
        button_next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next3(v);
            }
        });
        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        getActivity().startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    public void showImageInGridView(Intent data) {
        ClipData clipData = data.getClipData();
        if (clipData != null) {
            Log.d("KEYAA", clipData.getItemCount() + " abc");
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri selectedImage = clipData.getItemAt(i).getUri();
                arrayListUri.add(selectedImage);
                try {
                    InputStream is = context.getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    arrayListBitmap.add(scaled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            arrayListBitmap.add(scaled);
 //           saveImage();
        }
        Log.d("KEYAA", arrayListBitmap.toString() + " " + arrayListBitmap.size());
        imageChooseAdapter = new ImageChooseAdapter(context, arrayListBitmap);
        gridView_imageChoose.setAdapter(imageChooseAdapter);
    }

    public void next3(View v) {
        if ( arrayListUri.size() < 4 ) {
            layoutImage.setError("you must take more than 4 picture");
            return;
        }
        else {
            savingData();
            ((ListYourSpaceActivity) getActivity()).next33(v);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.example.wannahouse.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            getActivity().startActivityForResult(takePictureIntent, 0);
        }
    }

    private void savingData() {

    }

    private void iconViewGroupClick(final ViewGroup viewGroup) {
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( viewGroup.getAlpha() == 1 ) {
                    viewGroup.setAlpha(alpha);
                }
                else {
                    viewGroup.setAlpha(1);
                }
            }
        });
    }
    
    private void setIconViewGroupClick() {
        ViewGroup air = view.findViewById(R.id.amenitiesAir);
        ViewGroup privateWC = view.findViewById(R.id.amenitiesPrivateWC);
        ViewGroup parking = view.findViewById(R.id.amenitiesParking);
        ViewGroup internet = view.findViewById(R.id.amenitiesInternet);
        ViewGroup security = view.findViewById(R.id.amenitiesSecurity);
        ViewGroup noOwner = view.findViewById(R.id.amenitiesNoOwner);
        ViewGroup noCurfew = view.findViewById(R.id.amenitiesNoCurfew);
        ViewGroup cooking = view.findViewById(R.id.amenitiesCooking);
        ViewGroup bed = view.findViewById(R.id.amenitiesBed);
        ViewGroup window = view.findViewById(R.id.amenitiesWindow);
        ViewGroup waterHeater = view.findViewById(R.id.amenitiesWaterHeater);
        ViewGroup washing = view.findViewById(R.id.amenitiesWashing);
        ViewGroup wardrobe = view.findViewById(R.id.amenitiesWardrobe);
        ViewGroup fridge = view.findViewById(R.id.amenitiesFridge);
        ViewGroup loft = view.findViewById(R.id.amenitiesLoft);
        ViewGroup television = view.findViewById(R.id.amenitiesTelevision);

        if( !houseNew.isAirConditioner() ) air.setAlpha(alpha);
        if( !houseNew.isPrivateWC() ) privateWC.setAlpha(alpha);
        if( !houseNew.isParkingLot() ) parking.setAlpha(alpha);
        if( !houseNew.isInternet() ) internet.setAlpha(alpha);
        if( !houseNew.isSecurity() ) security.setAlpha(alpha);
        if( !houseNew.isNoOwner() ) noOwner.setAlpha(alpha);
        if( !houseNew.isNoCurfew() ) noCurfew.setAlpha(alpha);
        if( !houseNew.isCook() ) cooking.setAlpha(alpha);
        if( !houseNew.isBed() ) bed.setAlpha(alpha);
        if( !houseNew.isWindow() ) window.setAlpha(alpha);
        if( !houseNew.isWaterHeater() ) waterHeater.setAlpha(alpha);
        if( !houseNew.isWashing() ) washing.setAlpha(alpha);
        if( !houseNew.isWardrobe() ) wardrobe.setAlpha(alpha);
        if( !houseNew.isFridge() ) fridge.setAlpha(alpha);
        if( !houseNew.isLoft() ) loft.setAlpha(alpha);
        if( !houseNew.isTelevision() ) television.setAlpha(alpha);

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
}

