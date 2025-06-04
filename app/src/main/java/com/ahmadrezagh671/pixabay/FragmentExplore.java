package com.ahmadrezagh671.pixabay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.ahmadrezagh671.pixabay.Adapters.AdapterPosts;
import com.ahmadrezagh671.pixabay.Models.Post;
import com.ahmadrezagh671.pixabay.Utilities.UrlApiUtil;
import com.ahmadrezagh671.pixabay.Utilities.VolleySingleton;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentExplore extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final String TAG = "FragmentExplore";
    RecyclerView recyclerView;
    AdapterPosts adapterPosts;
    List<Post> posts;
    RequestQueue requestQueue;

    SearchView searchView;
    String[] lastSearchWords;

    SwipeRefreshLayout swipeRefreshLayout;
    Button categorySelectorBTN,colorSelectorBTN,latestBTN;
    ImageButton settingsIB;

    ConstraintLayout topDiscoverLayout;
    LinearLayout topFilterLayout;

    boolean latestEnable = false;

    public FragmentExplore() {

    }


    public static FragmentExplore newInstance() {
        FragmentExplore fragment = new FragmentExplore();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        posts = new ArrayList<>();
        lastSearchWords = new String[]{};

        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        topDiscoverLayout = view.findViewById(R.id.topDiscoverLayout);
        topFilterLayout = view.findViewById(R.id.topFilterLayout);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(this::onSearchViewClick);

        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        settingsIB = view.findViewById(R.id.settingsIB);
        settingsIB.setOnClickListener(this::onSettingsClick);

        categorySelectorBTN = view.findViewById(R.id.categorySelectorBTN);
        categorySelectorBTN.setOnClickListener(this::onClickCategorySelectorBTN);
        colorSelectorBTN = view.findViewById(R.id.colorSelectorBTN);
        colorSelectorBTN.setOnClickListener(this::onClickColorSelectorBTN);
        latestBTN = view.findViewById(R.id.latestBTN);
        latestBTN.setOnClickListener(this::onClickLatestBTN);

        final int[] state = new int[1];

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state[0] = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if(firstVisibleItem == 0){
                    topItemsVisible(true);
                } else if (dy > 50){
                    if (state[0] == 0 || state[0] == 2){
                        topItemsVisible(false);
                    }
                }else if (dy < -160){
                    if (state[0] == 0 || state[0] == 2){
                        topItemsVisible(true);
                    }
                }
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reload();
    }

    private void requestPostsFromApi(@Nullable String[] words) {
        refreshing(true);

        String category = getCategoryF();
        String color = getColorF();

        requestQueue.cancelAll(true);

        lastSearchWords = words==null ? new String[]{} : words;

        String url = UrlApiUtil.getPhotosUrl(getResources().getString(R.string.API_Key),lastSearchWords,category,color,latestEnable);
        Log.i(TAG, "requestPostsFromApi: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    Gson gson = new Gson();

                    posts.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Post item = gson.fromJson(jsonObject.toString(), Post.class);
                        posts.add(item);
                    }

                    if(adapterPosts == null) {
                        adapterPosts = new AdapterPosts(posts, getContext(), getActivity());
                        recyclerView.setAdapter(adapterPosts);
                    }else {
                        recyclerView.scrollToPosition(0);
                        adapterPosts.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                refreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                refreshing(false);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRefresh() {
        reload();
    }

    public String[] getSearchViewQuery(){
        lastSearchWords = searchView.getQuery().toString().split(" ");
        if (lastSearchWords.length == 0)
            return null;
        else
            return lastSearchWords;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        reload();
        topItemsVisible(true);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty() && lastSearchWords.length >= 1) {
            reload();
        }
        return false;
    }

    private void refreshing(boolean state){
        swipeRefreshLayout.setRefreshing(state);
    }

    private String getCategoryF(){
        String category = categorySelectorBTN.getText().toString().split(" ")[0].toLowerCase();
        if (category.equals("category")){
            return "none";
        }else {
            return category;
        }
    }
    private String getColorF(){
        String color = colorSelectorBTN.getText().toString().split(" ")[0].toLowerCase();
        if (color.equals("color")){
            return "none";
        }else {
            return color;
        }
    }

    public void onClickLatestBTN(View view) {
        if (latestEnable){
            latestEnable = false;
            latestBTN.setBackground(getResources().getDrawable(R.drawable.shape_rounded));
        }else {
            latestEnable = true;
            latestBTN.setBackground(getResources().getDrawable(R.drawable.shape_rounded_gradient));
        }
        reload();
    }

    private void onSearchViewClick(View view) {
        topItemsVisible(true);
    }

    public void onClickColorSelectorBTN(View view) {
        ((MainActivity)getActivity()).changeFragment(R.id.menuColor);
    }

    public void onClickCategorySelectorBTN(View view) {
        ((MainActivity)getActivity()).changeFragment(R.id.menuCategory);
    }

    private void onSettingsClick(View view) {
        startActivity(new Intent(getContext(),ActivitySettings.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void topItemsVisible(boolean b) {
        if (b){
            topDiscoverLayout.setVisibility(View.VISIBLE);
            topFilterLayout.setVisibility(View.VISIBLE);
        }else {
            topFilterLayout.setVisibility(View.GONE);
            topDiscoverLayout.setVisibility(View.GONE);
        }
    }

    private void reload(){
        requestPostsFromApi(getSearchViewQuery());
    }

    public void setCategory(String string){
        if (string.equals("None")) string = "Category";
        categorySelectorBTN.setText(string + "   ☰");
        reload();
    }
    public void setColor(String string){
        if (string.equals("None")) string = "Color";
        colorSelectorBTN.setText(string + "   ☰");
        reload();
    }
}