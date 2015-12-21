package com.example.bobby.extrastormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bobby.extrastormy.R;
import com.example.bobby.extrastormy.weather.Day;

/**
 * Created by bobby on 12/14/15.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private Day[] mDays;

    public DayAdapter(Day[] days) {
        mDays = days;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_list_item, parent, false);
        DayViewHolder viewHolder = new DayViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {

        holder.bindDay(mDays[position]);

    }

    @Override
    public int getItemCount() {
        return mDays.length;
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {

        public TextView mTemperatureMax;
        public TextView mDayNameLabel;
        public ImageView mIconImageView;
        public ImageView mCircleImageView;

        public DayViewHolder (View itemView) {

            super(itemView);

            mTemperatureMax =  (TextView) itemView.findViewById(R.id.temperatureLabel);
            mDayNameLabel = (TextView) itemView.findViewById(R.id.dayNameLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            mCircleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
        }

        public void bindDay (Day day) {

            mTemperatureMax.setText(day.getTemperatureMax() + "");
            mDayNameLabel.setText(day.getDayOfTheWeek());
            mIconImageView.setImageResource(day.getIconId());
            mCircleImageView.setImageResource(R.mipmap.bg_temperature);

        }

    }

}
