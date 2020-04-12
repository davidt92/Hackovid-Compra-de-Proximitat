package org.hackovid.compradeproximitat.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeActivity extends AppCompatActivity
{
    ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        qrCodeImage = findViewById(R.id.qrCode);

        Intent intent = getIntent();
        String qrCodeData = intent.getStringExtra(GlobalVariables.QR_CODE);
        
        System.out.println(qrCodeData);

        QRGEncoder qrgEncoder = new QRGEncoder(qrCodeData, null, QRGContents.Type.TEXT, 200);

        try
        {
            qrCodeImage.setImageBitmap(qrgEncoder.encodeAsBitmap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
