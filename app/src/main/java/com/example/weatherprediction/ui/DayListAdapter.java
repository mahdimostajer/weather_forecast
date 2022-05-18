package com.example.weatherprediction.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherprediction.R;
import com.example.weatherprediction.models.Day;
import com.google.gson.Gson;

import java.util.LinkedList;

public class DayListAdapter extends
        RecyclerView.Adapter<DayListAdapter.DayViewHolder>  {
    private final LinkedList<Day> mDayList;
    private LayoutInflater mInflater;
    Fragment fragment;

    public DayListAdapter(Fragment fragment,Context context,
                           LinkedList<Day> dayList) {
        this.fragment = fragment;
        mInflater = LayoutInflater.from(context);
        this.mDayList = dayList;
    }



    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.weather_row,
                parent, false);
        return new DayViewHolder(this.fragment,mItemView, this);
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
        Fragment mainFragment;

        public DayViewHolder(Fragment mainFragment, View itemView, DayListAdapter adapter) {
            super(itemView);
            this.mainFragment = mainFragment;
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
            Day day = mDayList.get(mPosition);
            Fragment fragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("day" ,new Gson().toJson(day));
            fragment.setArguments(bundle);
            FragmentTransaction ft = mainFragment.getParentFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_activity_main, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null).commit();
//            Toast.makeText(view.getContext(), "to details page", Toast.LENGTH_LONG).show();
            // go to details page
            mAdapter.notifyDataSetChanged();
        }
    }
}
