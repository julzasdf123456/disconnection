package com.lopez.julz.disconnection.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.lopez.julz.disconnection.DisconnectionListActivity;
import com.lopez.julz.disconnection.R;
import com.lopez.julz.disconnection.dao.DisconnectionList;
import com.lopez.julz.disconnection.helpers.ObjectHelpers;
import com.lopez.julz.disconnection.objects.DiscoGroup;

import java.util.List;

public class DiscoGroupAdapter  extends RecyclerView.Adapter<DiscoGroupAdapter.ViewHolder> {

    public List<DiscoGroup> disconnectionLists;
    public Context context;
    public String userId;

    public DiscoGroupAdapter(List<DiscoGroup> disconnectionLists, Context context, String userId) {
        this.disconnectionLists = disconnectionLists;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public DiscoGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_view_disco_group, parent, false);

        return new DiscoGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoGroupAdapter.ViewHolder holder, int position) {
        DiscoGroup discoGroup = disconnectionLists.get(position);

        holder.area.setText("Town: " + discoGroup.getTown() + " | Group/Day: " + (discoGroup.getGroupCode() != null ? discoGroup.getGroupCode() : "BAPA"));
        holder.meterReader.setText("Meter Reader ID: " + (discoGroup.getMeterReader() != null ? discoGroup.getMeterReader() : "-"));
        holder.billingMonth.setText(ObjectHelpers.formatShortDate(discoGroup.getServicePeriod()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisconnectionListActivity.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("METER_READER", (discoGroup.getMeterReader() != null ? discoGroup.getMeterReader() : "BAPA"));
                intent.putExtra("PERIOD", discoGroup.getServicePeriod());
                intent.putExtra("TOWN", discoGroup.getTown());
                intent.putExtra("DAY", (discoGroup.getGroupCode() != null ? discoGroup.getGroupCode() : "BAPA"));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return disconnectionLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView area, meterReader, billingMonth;
        public MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            area = itemView.findViewById(R.id.area);
            meterReader = itemView.findViewById(R.id.meterReader);
            billingMonth = itemView.findViewById(R.id.billingMonth);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
