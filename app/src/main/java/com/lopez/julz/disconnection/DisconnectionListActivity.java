package com.lopez.julz.disconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.lopez.julz.disconnection.adapters.DiscoListAdapter;
import com.lopez.julz.disconnection.dao.AppDatabase;
import com.lopez.julz.disconnection.dao.DisconnectionList;
import com.lopez.julz.disconnection.helpers.ObjectHelpers;

import java.util.ArrayList;
import java.util.List;

public class DisconnectionListActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public RecyclerView discoListRecyclerview;
    public List<DisconnectionList> disconnectionList;
    public DiscoListAdapter discoListAdapter;

    public AppDatabase db;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnection_list);

        toolbar = findViewById(R.id.discoListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(this,
                AppDatabase.class, ObjectHelpers.dbName()).fallbackToDestructiveMigration().build();

        userId = getIntent().getExtras().getString("USERID");

        discoListRecyclerview = findViewById(R.id.discoListRecyclerview);
        disconnectionList = new ArrayList<>();
        discoListAdapter = new DiscoListAdapter(disconnectionList, this, userId);
        discoListRecyclerview.setAdapter(discoListAdapter);
        discoListRecyclerview.setLayoutManager(new LinearLayoutManager(this));

//        new FetchAllDisco().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchAllDisco().execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchAllDisco extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            disconnectionList.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                disconnectionList.addAll(db.disconnectionListDao().getAll());
            } catch (Exception e) {
                Log.e("ERR_GET_DSCO_LST", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            discoListAdapter.notifyDataSetChanged();
        }
    }
}