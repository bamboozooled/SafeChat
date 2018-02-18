package com.safechat.android.safechat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SendMediaActivity extends AppCompatActivity {
    Uri uri;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_media);
        image = (ImageView) findViewById(R.id.image);
        Bundle bundle = getIntent().getExtras();
        uri = Uri.parse(bundle.getString("uri"));
        image.setImageURI(uri);

    }

    public void sendImage(View view) {
        Intent intent = new Intent();
        intent.setData(uri);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelImage(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
