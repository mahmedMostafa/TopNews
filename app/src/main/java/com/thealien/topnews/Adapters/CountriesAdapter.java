package com.thealien.topnews.Adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thealien.topnews.Models.Country;
import com.thealien.topnews.R;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

    private ArrayList<Country> countriesList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public CountriesAdapter(ArrayList<Country> countriesList, Context context) {
        this.countriesList = countriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item,viewGroup,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder viewHolder, int i) {

        Country currentCountry = countriesList.get(i);

        String name = currentCountry.getCountryName();
        int imageUrl = currentCountry.getCountryImageUrl();

        viewHolder.imageView.setImageResource(imageUrl);
        viewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.country_image_view);
            textView = itemView.findViewById(R.id.country_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
