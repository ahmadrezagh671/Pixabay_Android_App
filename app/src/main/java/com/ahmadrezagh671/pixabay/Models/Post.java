package com.ahmadrezagh671.pixabay.Models;

public class Post {

    int id,previewWidth,previewHeight,webformatWidth,webformatHeight,imageWidth,imageHeight,imageSize,views,downloads,collections,likes,comments,user_id;
    String pageURL,type,tags,previewURL,webformatURL,largeImageURL,user,userImageURL;
    boolean noAiTraining;

    public Post(int id, int previewWidth, int previewHeight, int webformatWidth, int webformatHeight, int imageWidth, int imageHeight, int imageSize, int views, int downloads, int collections, int likes, int comments, int user_id, String pageURL, String type, String tags, String previewURL, String webformatURL, String largeImageURL, String user, String userImageURL, boolean noAiTraining) {
        this.id = id;
        this.previewWidth = previewWidth;
        this.previewHeight = previewHeight;
        this.webformatWidth = webformatWidth;
        this.webformatHeight = webformatHeight;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageSize = imageSize;
        this.views = views;
        this.downloads = downloads;
        this.collections = collections;
        this.likes = likes;
        this.comments = comments;
        this.user_id = user_id;
        this.pageURL = pageURL;
        this.type = type;
        this.tags = tags;
        this.previewURL = previewURL;
        this.webformatURL = webformatURL;
        this.largeImageURL = largeImageURL;
        this.user = user;
        this.userImageURL = userImageURL;
        this.noAiTraining = noAiTraining;
    }

    public int getId() {
        return id;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageSize() {
        return imageSize;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getCollections() {
        return collections;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public boolean isNoAiTraining() {
        return noAiTraining;
    }
}
