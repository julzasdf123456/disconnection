package com.lopez.julz.disconnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lopez.julz.disconnection.adapters.DisconnectionMenuAdapter;
import com.lopez.julz.disconnection.helpers.HomeMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView menu_recyclerview_disco;
    public List<HomeMenu> homeMenuList;
    public DisconnectionMenuAdapter homeMenuAdapter;

    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        userId = getIntent().getExtras().getString("USERID");

        menu_recyclerview_disco = findViewById(R.id.menu_recyclerview_disco);
        homeMenuList = new ArrayList<>();
        homeMenuAdapter = new DisconnectionMenuAdapter(homeMenuList, this, userId);
        menu_recyclerview_disco.setAdapter(homeMenuAdapter);
        menu_recyclerview_disco.setLayoutManager(new GridLayoutManager(this, 2));

        addMenu();
    }

    public void addMenu() {
        try {
            homeMenuList.add(new HomeMenu(getDrawable(R.drawable.ic_baseline_download_24), "Download", "#4caf50"));
            homeMenuList.add(new HomeMenu(getDrawable(R.drawable.ic_baseline_file_upload_24), "Upload", "#ff7043"));
            homeMenuList.add(new HomeMenu(getDrawable(R.drawable.ic_baseline_data_thresholding_24), "Disconnection List", "#5c6bc0"));

            homeMenuAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("ERR_ADD_MENU", e.getMessage());
        }
    }
}