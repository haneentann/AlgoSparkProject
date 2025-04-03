package com.example.algosparkproject.mobile.ui;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.algosparkproject.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.algosparkproject.data.ExerciseRepository;
import com.example.algosparkproject.mobile.utils.GeminiCallback;
import com.example.algosparkproject.mobile.utils.GeminiManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Button chooseImageButton;
    private Button sendImageButton;
    private TextView imageResponseTextView;

    private ExerciseRepository exerciseRepository;
    private FirebaseFirestore firestore;
    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.imageView);
        chooseImageButton = findViewById(R.id.chooseImageButton);
        sendImageButton = findViewById(R.id.sendImageButton);
        imageResponseTextView = findViewById(R.id.imageResponseTextView);

        exerciseRepository = new ExerciseRepository();

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static final String TAG = "ExerciseActivityHaneen";

    private void sendImage() {
        Log.d(TAG, "sendImage() called");

        if (selectedBitmap != null) {
            Log.d(TAG, "selectedBitmap is NOT null");

            String base64Image = encodeImage(selectedBitmap);
            Log.d(TAG, "Base64 image encoded successfully");

            GeminiManager.getInstance().sendMessageWithPhoto(
                    "Analyze this image: ", selectedBitmap, new GeminiCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d(TAG, "Gemini API success: " + result);
                            runOnUiThread(() -> imageResponseTextView.setText(result));
                        }

                        @Override
                        public void onError(Throwable error) {
                            Log.e(TAG, "Gemini API error", error);
                            runOnUiThread(() ->
                                    Toast.makeText(ImageActivity.this, "Gemini API error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                        }
                    }
            );
        } else {
            Log.w(TAG, "selectedBitmap is NULL");
            Toast.makeText(this, "Please select an image first.", Toast.LENGTH_SHORT).show();
        }
    }


    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}