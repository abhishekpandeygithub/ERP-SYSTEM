package com.mastercoding.admincollageapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class UploadPdfActivity extends AppCompatActivity {

    Button uploadPdfBtn;
    EditText pdfTitle;
    private CardView addPdf;
    private TextView pdfTextView;
    private String pdfName, title;


    StorageReference storageReference;
    DatabaseReference databaseReference;
    private ProgressDialog Pd;
    private Uri pdfData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        pdfTextView = findViewById(R.id.pdfTextView);


        uploadPdfBtn = findViewById(R.id.uploadPdfBtn);
        pdfTitle = findViewById(R.id.pdfTitle);

        addPdf = findViewById(R.id.addPdf);

        Pd = new ProgressDialog(this);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdf");

        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        uploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(UploadPdfActivity.this, "Please upload pdf", Toast.LENGTH_SHORT).show();

                }else{

                    UploadFiles(pdfData);

                }

            }
        });

    }
    private void UploadFiles(Uri pdfData) {

        ProgressDialog Pd = new ProgressDialog(this);
        Pd.setTitle("Please wait...");
        Pd.setMessage("Uploading pdf");
        Pd.show();


        StorageReference reference = storageReference.child("pdf" +pdfName+"-"+ System.currentTimeMillis() + ".pdf");
        reference.putFile(this.pdfData)

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        uploadData(String.valueOf(uri));


                        Toast.makeText(UploadPdfActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        Pd.dismiss();



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        Pd.setMessage("Uploading:"+(int) progress+"%");

                    }
                });
    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf Files..."),1);

    }


    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("pdf").push().getKey();
        HashMap<String, String> data = new HashMap<>();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);
        data.put("pdfName",pdfName);



        assert uniqueKey != null;
        databaseReference.child("pdf").child(uniqueKey).setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Pd.dismiss();
                Toast.makeText(UploadPdfActivity.this, "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText(title);

                pdfClass pdfClass = new pdfClass(pdfData);

                databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(pdfClass);
                Intent intent = new Intent(UploadPdfActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Pd.dismiss();
                Toast.makeText(UploadPdfActivity.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            pdfData = data.getData();


            if (pdfData.toString().startsWith("content://")){

                Cursor cursor = null;
                try {
                    cursor = UploadPdfActivity.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor != null && cursor.moveToFirst()){
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(pdfData.toString().startsWith("file://")){
                pdfName = new File(pdfData.toString()).getName();



            }
           pdfTextView.setText(pdfName);



        }






    }


}





