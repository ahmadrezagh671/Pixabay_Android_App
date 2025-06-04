package com.ahmadrezagh671.pixabay.Utilities;

import androidx.annotation.Nullable;

public class UrlApiUtil {

    public static String getPhotosUrl(String API_KEY,@Nullable String[] words,String category,String color,boolean latest){
        StringBuilder startUrl = new StringBuilder("https://pixabay.com/api/?key=").append(API_KEY);
        if (words != null) {
            startUrl.append("&q=");
            for (String word:words) {
                startUrl.append(word).append("+");
            }
        }
        startUrl.append("&image_type=all"); //all, photo, illustration, vector

        if (latest){
            startUrl.append("&order=latest"); //popular, latest
        }else {
            startUrl.append("&order=popular");
        }

        startUrl.append("&orientation=all"); //all, horizontal, vertical
        startUrl.append("&page=1"); //1, 2, 3, ...
        startUrl.append("&per_page=20"); //20, 40, 3 - 200
        startUrl.append("&safesearch=false"); //true, false

        if (!color.equals("none")){
            startUrl.append("&colors=").append(color);
        }
        if (!category.equals("none")){
            startUrl.append("&category=").append(category);
        }

        return startUrl.toString();
    }

    public static String getCategoryUrl(String API_KEY,String category){
        return "https://pixabay.com/api/?key=" + API_KEY +
                "&category=" + category +
                "&per_page=5";
    }

    public static String getColorUrl(String API_KEY,String color){
        return "https://pixabay.com/api/?key=" + API_KEY +
                "&colors=" + color +
                "&per_page=5";
    }

}
