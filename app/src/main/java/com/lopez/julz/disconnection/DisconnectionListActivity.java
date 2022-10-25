package com.lopez.julz.disconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

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
    public EditText searchList;

    public AppDatabase db;

    String userId, town, meterReader, groupCode, period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnection_list);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("USERID");
        groupCode = bundle.getString("DAY");
        town = bundle.getString("TOWN");
        meterReader = bundle.getString("METER_READER");
        period = bundle.getString("PERIOD");

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
        searchList = findViewById(R.id.searchList);
        disconnectionList = new ArrayList<>();
        discoListAdapter = new DiscoListAdapter(disconnectionList, this, userId);
        discoListRecyclerview.setAdapter(discoListAdapter);
        discoListRecyclerview.setLayoutManager(new LinearLayoutManager(this));

//        new FetchAllDisco().execute();
        searchList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new Search().execute(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public class Search extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            disconnectionList.clear();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if (strings[0] != null) {
                    String searchRegex = "%" + strings[0] + "%";
                    if (groupCode.equals("BAPA")) {
                        disconnectionList.addAll(db.disconnectionListDao().getSearchBapa(searchRegex, town, period));
                    } else {
                        disconnectionList.addAll(db.disconnectionListDao().getSearch(searchRegex, groupCode, meterReader, town, period));
                    }
                } else {
                    if (groupCode.equals("BAPA")) {
                        disconnectionList.addAll(db.disconnectionListDao().getAllBapa(town, period));
                    } else {
                        disconnectionList.addAll(db.disconnectionListDao().getAll(groupCode, meterReader, town, period));
                    }
                }

            } catch (Exception e) {
                Log.e("ERR_GET_SEARCH", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            discoListAdapter.notifyDataSetChanged();
        }
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
                if (groupCode.equals("BAPA")) {
                    disconnectionList.addAll(db.disconnectionListDao().getAllBapa(town, period));
                } else {
                    disconnectionList.addAll(db.disconnectionListDao().getAll(groupCode, meterReader, town, period));
                }
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