package com.ahmadrezagh671.pixabay.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ablanco.zoomy.Zoomy;
import com.ahmadrezagh671.pixabay.Models.Post;
import com.ahmadrezagh671.pixabay.R;
import com.ahmadrezagh671.pixabay.Utilities.AppUtil;
import com.ahmadrezagh671.pixabay.Utilities.Display;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder> {

    List<Post> posts;
    Context context;
    Activity activity;

    public AdapterPosts(List<Post> posts, Context context, Activity activity) {
        this.posts = posts;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_post,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPosts.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.setImageViewHeightAndWidth(post.getWebformatHeight(), post.getWebformatWidth());
        holder.setImageView(post.getWebformatURL());
        holder.setProfilePic(post.getUserImageURL());
        holder.usernameTV.setText(post.getUser());
        holder.postTypeTV.setText(post.getType());
        holder.tagsTV.setText(post.getTags());
        holder.likeTV.setText(String.valueOf(post.getLikes()));
        holder.commentTV.setText(String.valueOf(post.getComments()));
        holder.downloadTV.setText(String.valueOf(post.getDownloads()));

        holder.moreIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, v);
                popup.getMenuInflater().inflate(R.menu.more_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menuOpenPage){
                            AppUtil.openLink(post.getPageURL(),context);
                        } else if (id == R.id.menuDownload) {
                            String[] temp = post.getLargeImageURL().split("//.");
                            String fileName = post.getUser() + post.getId() + temp[temp.length-1];
                            AppUtil.downloadFile(post.getLargeImageURL(),fileName,activity);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,profilePic;
        TextView usernameTV,postTypeTV;
        TextView likeTV,commentTV,downloadTV,tagsTV;
        ImageButton moreIB;

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.imageView);
            profilePic = view.findViewById(R.id.profilePic);
            usernameTV = view.findViewById(R.id.usernameTV);
            postTypeTV = view.findViewById(R.id.postTypeTV);
            likeTV = view.findViewById(R.id.likeTV);
            commentTV = view.findViewById(R.id.commentTV);
            downloadTV = view.findViewById(R.id.downloadTV);
            moreIB = view.findViewById(R.id.moreIB);
            tagsTV = view.findViewById(R.id.tagsTV);

        }

        public void setImageView(String url){
            Glide.with(context).load(url).fitCenter().into(imageView);
            new Zoomy.Builder(activity)
                    .target(imageView)
                    .enableImmersiveMode(false)
                    .animateZooming(false)
                    .register();
        }
        public void setProfilePic(String url){
            if (!url.isEmpty())
                Glide.with(context).load(url).fitCenter().into(profilePic);
            else
                profilePic.setImageResource(R.drawable.example_profile);
        }
        public void setImageViewHeightAndWidth(int height, int width) {
            Display.setImageViewHeightAndWidth(imageView, height, width, activity);
        }
    }
}
