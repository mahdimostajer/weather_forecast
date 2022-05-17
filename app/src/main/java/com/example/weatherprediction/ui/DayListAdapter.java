package com.example.weatherprediction.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherprediction.R;
import com.example.weatherprediction.models.Day;

import java.util.LinkedList;

public class DayListAdapter extends
        RecyclerView.Adapter<DayListAdapter.DayViewHolder>  {
    private final LinkedList<Day> mDayList;
    private LayoutInflater mInflater;

    public DayListAdapter(Context context,
                           LinkedList<Day> dayList) {
        mInflater = LayoutInflater.from(context);
        this.mDayList = dayList;
    }



    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.weather_row,
                parent, false);
        return new DayViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day mCurrent = mDayList.get(position);
        holder.maxTemp.setText(String.format("%.1f",mCurrent.temp.max));
        holder.minTemp.setText(String.format("%.1f",mCurrent.temp.min));
        holder.feelsLikeTemp.setText(String.format("%.1f",mCurrent.wind_speed));
        String iconUrl = "http://openweathermap.org/img/w/" + mCurrent.weather.get(0).icon  + ".png";
        Glide.with(mInflater.getContext()).load(iconUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }


    class DayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView maxTemp;
        public final TextView minTemp;
        public final TextView feelsLikeTemp;
        public final ImageView image;
        final DayListAdapter mAdapter;

        public DayViewHolder(View itemView, DayListAdapter adapter) {
            super(itemView);
            maxTemp = itemView.findViewById(R.id.row_max_temp);
            minTemp = itemView.findViewById(R.id.row_min_temp);
            feelsLikeTemp = itemView.findViewById(R.id.row_wind);
            image = itemView.findViewById(R.id.row_image);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Day element = mDayList.get(mPosition);
            Toast.makeText(view.getContext(), "to details page", Toast.LENGTH_LONG).show();
            // go to details page
            mAdapter.notifyDataSetChanged();
        }
    }
}
