package com.ahmadrezagh671.pixabay.Adapters;

import static com.ahmadrezagh671.pixabay.Utilities.AppUtil.capitalizeFirstLetter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrezagh671.pixabay.FragmentExplore;
import com.ahmadrezagh671.pixabay.MainActivity;
import com.ahmadrezagh671.pixabay.Models.Category;
import com.ahmadrezagh671.pixabay.R;
import com.ahmadrezagh671.pixabay.Utilities.Display;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterColors extends RecyclerView.Adapter<AdapterColors.ViewHolder> {

    List<Category> categories;
    Context context;
    Activity activity;
    FragmentExplore fragmentExplore;

    public AdapterColors(List<Category> categories, Context context, Activity activity, FragmentExplore fragmentExplore) {
        this.categories = categories;
        this.context = context;
        this.activity = activity;
        this.fragmentExplore = fragmentExplore;
    }

    @NonNull
    @Override
    public AdapterColors.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterColors.ViewHolder holder, int position) {
        Category item = categories.get(position);

        holder.setImageViewWidth();
        holder.setImageView(item.getImageURL());
        holder.textView.setText(capitalizeFirstLetter(item.getName()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentExplore.setColor(capitalizeFirstLetter(item.getName()));
                ((MainActivity)activity).changeFragment(R.id.menuExplore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            view = itemView;
        }

        public void setImageView(String url){
            Glide.with(context).load(url).fitCenter().into(imageView);
        }

        public void setImageViewWidth() {
            Display.setImageViewWidthHalfScreen(imageView, activity);
        }
    }
}
