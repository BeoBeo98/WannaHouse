package com.example.wannahouse;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragmentAmenities extends Fragment {
    private Button button_takePicture;
    public static int CAMERA_REQUEST_CODE = 1000;
    public static int PICK_IMAGE_REQUEST_CODE = 1001;
    private GridView gridView_imageChoose;
    private ImageChooseAdapter imageChooseAdapter;
    private Context context;

    private ImageView imageViewTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities, container, false);
        button_takePicture = view.findViewById(R.id.button_takePicture);
        gridView_imageChoose = view.findViewById(R.id.gridView_imageChoose);

        context = view.getContext();

        imageViewTest = view.findViewById(R.id.imageViewTest);

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
        final ArrayList<Uri> arrayList = new ArrayList<>();
        ClipData clipData = data.getClipData();
        Log.d("KEYAA", clipData.getItemCount() + " abc");
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri uri = clipData.getItemAt(i).getUri();
                arrayList.add(uri);
                Log.d("KEYAA", uri + " " + arrayList.size());
            }
        }
        imageChooseAdapter = new ImageChooseAdapter(context, arrayList);
        Log.d("KEYAA", imageChooseAdapter.toString() + " " + imageChooseAdapter.getCount());
        gridView_imageChoose.setAdapter(imageChooseAdapter);
    }
}

