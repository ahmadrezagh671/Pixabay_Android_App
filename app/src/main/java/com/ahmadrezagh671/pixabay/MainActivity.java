package com.ahmadrezagh671.pixabay;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ahmadrezagh671.pixabay.Utilities.FragmentUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    FragmentExplore fragmentExplore;
    FragmentCategory fragmentCategory;
    FragmentColor fragmentColor;

    BottomNavigationView buttonNavView;

    FragmentManager fragmentManager;
    Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonNavView = findViewById(R.id.buttonNavView);

        // fix bottom padding bug
        buttonNavView.setOnApplyWindowInsetsListener(null);
        buttonNavView.setPadding(0,0,0,0);

        fragmentManager = getSupportFragmentManager();
        fragmentExplore = new FragmentExplore();
        fragmentCategory = new FragmentCategory(fragmentExplore);
        fragmentColor = new FragmentColor(fragmentExplore);

        buttonNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menuExplore){
                    lastFragment = FragmentUtil.openFragment(fragmentManager,R.id.frame_Layout, fragmentExplore,lastFragment);
                } else if (id == R.id.menuCategory) {
                    lastFragment = FragmentUtil.openFragment(fragmentManager, R.id.frame_Layout, fragmentCategory, lastFragment);
                } else if (id == R.id.menuColor) {
                    lastFragment = FragmentUtil.openFragment(fragmentManager, R.id.frame_Layout, fragmentColor, lastFragment);
                }

                return true;
            }
        });

        buttonNavView.setSelectedItemId(R.id.menuExplore);
    }

    public void changeFragment(int id){
        buttonNavView.setSelectedItemId(id);
    }

}