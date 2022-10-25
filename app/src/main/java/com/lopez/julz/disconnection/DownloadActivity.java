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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lopez.julz.disconnection.adapters.DownloadAdapter;
import com.lopez.julz.disconnection.api.RequestPlaceHolder;
import com.lopez.julz.disconnection.api.RetrofitBuilder;
import com.lopez.julz.disconnection.dao.AppDatabase;
import com.lopez.julz.disconnection.dao.DisconnectionList;
import com.lopez.julz.disconnection.dao.Settings;
import com.lopez.julz.disconnection.dao.Users;
import com.lopez.julz.disconnection.helpers.AlertHelpers;
import com.lopez.julz.disconnection.helpers.ObjectHelpers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadActivity extends AppCompatActivity {

    public RecyclerView downloadRecyclerview;
    public DownloadAdapter downloadAdapter;
    public Toolbar toolbar;

    public RetrofitBuilder retrofitBuilder;
    private RequestPlaceHolder requestPlaceHolder;

    public AppDatabase db;
    public Settings settings;

    public List<DisconnectionList> disconnectionListList;

    public FloatingActionButton downloadBtn;
    public Button filterByMeterReaderBtn, filterByRouteBtn;
    public EditText meterReaderId, groupCode, townCode, routeCode;
    public TextView downloadTitle;
    public String userId;
    public Spinner period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        toolbar = findViewById(R.id.downloadToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getString("USERID");

        db = Room.databaseBuilder(this,
                AppDatabase.class, ObjectHelpers.dbName()).fallbackToDestructiveMigration().build();
        downloadBtn = findViewById(R.id.downloadBtn);
        filterByMeterReaderBtn = findViewById(R.id.filterByMeterReaderBtn);
        filterByRouteBtn = findViewById(R.id.filterByRouteBtn);
        meterReaderId = findViewById(R.id.meterReaderId);
        groupCode = findViewById(R.id.groupCode);
        townCode = findViewById(R.id.townCode);
        routeCode = findViewById(R.id.routeCode);
        period = findViewById(R.id.period);
        downloadTitle = findViewById(R.id.downloadTitle);

        meterReaderId.setText(userId);
        addPrevMonths();

        new FetchSettings().execute();

        filterByMeterReaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object group = groupCode.getText();
                if (!group.toString().isEmpty()) {
                    fetchByMeterReader();
                } else {
                    AlertHelpers.showMessageDialog(DownloadActivity.this, "No Group Code", "Please provide group code/day to continue.");
                }
            }
        });

        filterByRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object town = townCode.getText();
                Object area = routeCode.getText();
                if (town.toString().isEmpty() | area.toString().isEmpty()) {
                    AlertHelpers.showMessageDialog(DownloadActivity.this, "Incomplete Data Supplied", "Please provide TOWN CODE and ROUTE CODE.");
                } else {
                    fetchByRoute();

                }
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveDownloadables().execute(disconnectionListList);
            }
        });
    }

    public void addPrevMonths() {
        String[] monthsGet = ObjectHelpers.getPreviousMonths(5);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthsGet);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period.setAdapter(monthAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchByRoute() {
        try {
            Call<List<DisconnectionList>> disListCall = requestPlaceHolder.getDisconnectionListByRoute(townCode.getText().toString(), period.getSelectedItem().toString(), routeCode.getText().toString());

            disListCall.enqueue(new Callback<List<DisconnectionList>>() {
                @Override
                public void onResponse(Call<List<DisconnectionList>> call, Response<List<DisconnectionList>> response) {
                    if (response.isSuccessful()) {
                        new AssessDownloaded().execute(response.body());
                    } else {
                        AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", response.raw() + "\n" + response.message());
                        Log.e("ERR_FETCH_DISCO_DL", response.raw() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<DisconnectionList>> call, Throwable t) {
                    AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", t.getMessage());
                    Log.e("ERR_FETCH_DISCO_DL", t.getMessage() + "");
                }
            });
        } catch (Exception e) {
            Log.e("ERR_GET_LIST_B_MR", e.getMessage());
            AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", e.getMessage());
        }
    }

    public void fetchByMeterReader() {
        try {
            Call<List<DisconnectionList>> disListCall = requestPlaceHolder.getDisconnectionListByMeterReader(meterReaderId.getText().toString(), period.getSelectedItem().toString(), groupCode.getText().toString());

            disListCall.enqueue(new Callback<List<DisconnectionList>>() {
                @Override
                public void onResponse(Call<List<DisconnectionList>> call, Response<List<DisconnectionList>> response) {
                    if (response.isSuccessful()) {
                        new AssessDownloaded().execute(response.body());
                    } else {
                        AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", response.raw() + "\n" + response.message());
                        Log.e("ERR_FETCH_DISCO_DL", response.raw() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<DisconnectionList>> call, Throwable t) {
                    AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", t.getMessage());
                    Log.e("ERR_FETCH_DISCO_DL", t.getMessage() + "");
                }
            });
        } catch (Exception e) {
            Log.e("ERR_GET_LIST_B_MR", e.getMessage());
            AlertHelpers.showMessageDialog(DownloadActivity.this, "Error Getting Data", e.getMessage());
        }
    }

    public void fetchDownloadableDisco(String office) {
        try {
            Call<List<DisconnectionList>> discoListCall = requestPlaceHolder.getDisconnectionList(office);

            discoListCall.enqueue(new Callback<List<DisconnectionList>>() {
                @Override
                public void onResponse(Call<List<DisconnectionList>> call, Response<List<DisconnectionList>> response) {
                    if (response.isSuccessful()) {
                        new AssessDownloaded().execute(response.body());
                    } else {
                        Toast.makeText(DownloadActivity.this, "Error fetching downloadables", Toast.LENGTH_SHORT).show();
                        Log.e("ERR_FETCH_DISCO_DL", response.raw() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<DisconnectionList>> call, Throwable t) {
                    Toast.makeText(DownloadActivity.this, "Error fetching downloadables", Toast.LENGTH_SHORT).show();
                    Log.e("ERR_FETCH_DISCO_DL", t.getMessage());
                }
            });
        } catch (Exception e) {
            Toast.makeText(DownloadActivity.this, "Error fetching downloadables", Toast.LENGTH_SHORT).show();
            Log.e("ERR_FETCH_DISCO_DL", e.getMessage());
        }
    }

    public class AssessDownloaded extends AsyncTask<List<DisconnectionList>, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            disconnectionListList.clear();
        }

        @Override
        protected Void doInBackground(List<DisconnectionList>... lists) {
            try {
                if (lists != null) {
                    List<DisconnectionList> tmpList = lists[0];

                    for (int i=0; i< tmpList.size(); i++) {
                        DisconnectionList disconnectionList = db.disconnectionListDao().getOne(tmpList.get(i).getAccountNumber(), tmpList.get(i).getServicePeriod());

                        if (disconnectionList != null) {

                        } else {
                            tmpList.get(i).setId(ObjectHelpers.getTimeInMillis() + "-" + ObjectHelpers.generateRandomString());
                            disconnectionListList.add(tmpList.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("ERR_ASSESS_DSC", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            downloadAdapter.notifyDataSetChanged();
            downloadTitle.setText(disconnectionListList.size() + " Accounts to Be Disconnected");
        }
    }

    public class SaveDownloadables extends AsyncTask<List<DisconnectionList>, Void, Void> {

        @Override
        protected Void doInBackground(List<DisconnectionList>... lists) {
            try {
                if (lists != null) {
                    List<DisconnectionList> disconnectionLists = lists[0];

                    for (int i=0; i< disconnectionListList.size(); i++) {
                        DisconnectionList disconnectionList = disconnectionLists.get(i);

                        db.disconnectionListDao().insertAll(disconnectionList);
                    }
                }
            } catch (Exception e) {
                Log.e("ERR_SV_DSCO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(DownloadActivity.this, "Disconnection list downloaded", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public class FetchSettings extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                settings = db.settingsDao().getSettings();
            } catch (Exception e) {
                Log.e("ERR_FETCH_SETTINGS", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (settings != null) {
                retrofitBuilder = new RetrofitBuilder(settings.getDefaultServer());
                requestPlaceHolder = retrofitBuilder.getRetrofit().create(RequestPlaceHolder.class);

                downloadRecyclerview = findViewById(R.id.downloadRecyclerview);

                disconnectionListList = new ArrayList<>();
                downloadAdapter = new DownloadAdapter(disconnectionListList, DownloadActivity.this);
                downloadRecyclerview.setAdapter(downloadAdapter);
                downloadRecyclerview.setLayoutManager(new LinearLayoutManager(DownloadActivity.this));

//                fetchDownloadableDisco(settings.getDefaultOffice());
            } else {
                AlertHelpers.showMessageDialog(DownloadActivity.this, "Settings Not Initialized", "Failed to load settings. Go to settings and set all necessary parameters to continue.");
            }
        }
    }
}