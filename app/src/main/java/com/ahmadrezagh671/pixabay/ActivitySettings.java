package com.ahmadrezagh671.pixabay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ahmadrezagh671.pixabay.SettingsActivities.ActivityAboutUs;
import com.ahmadrezagh671.pixabay.Utilities.AppUtil;

public class ActivitySettings extends AppCompatActivity {

    ImageButton backButton;
    TextView appVersionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appVersionTV = findViewById(R.id.appVersionTV);
        appVersionTV.setText("Version " + AppUtil.getAppVersionStr(this));

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void userAccount(View view) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void dataUsage(View view) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    public void aboutUs(View view) {
        startActivity(new Intent(ActivitySettings.this, ActivityAboutUs.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}