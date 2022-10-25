package com.lopez.julz.disconnection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lopez.julz.disconnection.R;
import com.lopez.julz.disconnection.dao.DisconnectionList;
import com.lopez.julz.disconnection.helpers.ObjectHelpers;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    public List<DisconnectionList> disconnectionLists;
    public Context context;

    public DownloadAdapter(List<DisconnectionList> disconnectionLists, Context context) {
        this.disconnectionLists = disconnectionLists;
        this.context = context;
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recyclerview_layout_download, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {
        DisconnectionList disconnectionList = disconnectionLists.get(position);

        holder.accountNo.setText(disconnectionList.getOldAccountNo());
        holder.consumerName.setText(disconnectionList.getServiceAccountName());
        holder.address.setText(disconnectionList.getAddress());
        holder.period.setText(ObjectHelpers.formatShortDate(disconnectionList.getServicePeriod()));
    }

    @Override
    public int getItemCount() {
        return disconnectionLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView accountNo, consumerName, address, period;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accountNo = itemView.findViewById(R.id.accountNo);
            consumerName = itemView.findViewById(R.id.consumerName);
            address = itemView.findViewById(R.id.address);
            period = itemView.findViewById(R.id.period);
        }
    }
}
