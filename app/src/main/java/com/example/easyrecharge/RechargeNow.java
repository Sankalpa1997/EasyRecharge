package com.example.easyrecharge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

import model.RechargeCard;

import static android.app.Activity.RESULT_OK;
import static com.example.easyrecharge.MainActivity.REQUEST_IMAGE_CAPTURE;

public class RechargeNow extends Fragment {

    FloatingActionButton fab;

    TextInputLayout textInputLayout;
    ImageView scannedImage;
    Bitmap imageBitmap;
    Button recharge, save;
    String serviceProviderName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recharge_now, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TelephonyManager telephonyManager =((TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE));
        serviceProviderName = telephonyManager.getNetworkOperatorName();

        scannedImage = getView().findViewById(R.id.scannedImage);
        textInputLayout = getView().findViewById(R.id.textInputLayout);

        fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Opening Camera", Toast.LENGTH_SHORT).show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        recharge = getView().findViewById(R.id.button4);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String rechargeNumber = textInputLayout.getEditText().getText().toString();

                String rechargeStartCode;
                String rechargeEndCode;
                String rechargeFinal;

                Intent dialpad;
                
                if(!rechargeNumber.isEmpty()){
                    //Recharge Number Field is not empty
                    switch(serviceProviderName.toLowerCase()){

                        case "dialog":

                            rechargeStartCode = "#123*";
                            rechargeEndCode = "#";
                            rechargeFinal = rechargeStartCode+rechargeNumber+rechargeEndCode;
                            Toast.makeText(getContext(), rechargeFinal, Toast.LENGTH_SHORT).show();

                            dialpad = new Intent();
                            dialpad.setAction("android.intent.action.DIAL");
                            dialpad.setData(Uri.fromParts("tel",rechargeFinal,"#"));
                            startActivity(dialpad);

                            break;

                        case "mobitel":

                            rechargeStartCode = "*102*";
                            rechargeEndCode = "#";
                            rechargeFinal = rechargeStartCode+rechargeNumber+rechargeEndCode;

                            dialpad = new Intent();
                            dialpad.setAction("android.intent.action.DIAL");
                            dialpad.setData(Uri.fromParts("tel",rechargeFinal,"#"));
                            startActivity(dialpad);

                            Toast.makeText(getContext(), "Mobitel", Toast.LENGTH_SHORT).show();
                            break;

                        case "airtel":

                            rechargeStartCode = "*567#";
                            rechargeEndCode = "#";
                            rechargeFinal = rechargeStartCode+rechargeNumber+rechargeEndCode;

                            dialpad = new Intent();
                            dialpad.setAction("android.intent.action.DIAL");
                            dialpad.setData(Uri.fromParts("tel",rechargeFinal,"#"));
                            startActivity(dialpad);

                            Toast.makeText(getContext(), "Airtel", Toast.LENGTH_SHORT).show();
                            break;

                        case "etisalat":

                            rechargeStartCode = "#133*";
                            rechargeEndCode = "#";
                            rechargeFinal = rechargeStartCode+rechargeNumber+rechargeEndCode;

                            dialpad = new Intent();
                            dialpad.setAction("android.intent.action.DIAL");
                            dialpad.setData(Uri.fromParts("tel",rechargeFinal,"#"));
                            startActivity(dialpad);

                            Toast.makeText(getContext(), "Etisalat", Toast.LENGTH_SHORT).show();
                            break;

                        case "hutch":

                            rechargeStartCode = "*355*";
                            rechargeEndCode = "#";
                            rechargeFinal = rechargeStartCode+rechargeNumber+rechargeEndCode;

                            dialpad = new Intent();
                            dialpad.setAction("android.intent.action.DIAL");
                            dialpad.setData(Uri.fromParts("tel",rechargeFinal,"#"));
                            startActivity(dialpad);

                            Toast.makeText(getContext(), "Hutch", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }else{
                    Toast.makeText(getContext(), "No Number To Recharge! ", Toast.LENGTH_SHORT).show();
                }

                

                //Toast.makeText(getContext(), serviceProviderName+" "+rechargeNumber, Toast.LENGTH_SHORT).show();
            }
        });

        save = getView().findViewById(R.id.button5);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();
            }
        });
    }

    private void saveCard() {
        int id = 001;
        Toast.makeText(getContext(), serviceProviderName, Toast.LENGTH_SHORT).show();
        String rechargeNumber = textInputLayout.getEditText().getText().toString();




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            scannedImage.setImageBitmap(imageBitmap);
            detectImg();
        }
    }

    private void detectImg() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(getContext(), "Sorry, No Text Found", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();

            if (txt.matches("[0-9]+")){

            textInputLayout.getEditText().setText(txt);

            }else{
                Toast.makeText(getContext(), "Sorry, No Suitable Numbers Found", Toast.LENGTH_LONG).show();
            }

        }
    }
}
