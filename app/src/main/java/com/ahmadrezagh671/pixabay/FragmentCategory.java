package com.ahmadrezagh671.pixabay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadrezagh671.pixabay.Adapters.AdapterCategories;
import com.ahmadrezagh671.pixabay.Models.Category;
import com.ahmadrezagh671.pixabay.Utilities.UrlApiUtil;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FragmentCategory extends Fragment {

    RecyclerView recyclerView;
    AdapterCategories adapterCategories;
    List<Category> categories;
    FlexboxLayoutManager layoutManagerFlex;

    RequestQueue requestQueue;

    FragmentExplore fragmentExplore;

    public FragmentCategory() {

    }

    public FragmentCategory(FragmentExplore fragmentExplore) {
        this.fragmentExplore = fragmentExplore;
    }

    public static FragmentCategory newInstance() {
        FragmentCategory fragment = new FragmentCategory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categories = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        layoutManagerFlex = new FlexboxLayoutManager(getContext());
        layoutManagerFlex.setFlexDirection(FlexDirection.ROW);
        layoutManagerFlex.setJustifyContent(JustifyContent.SPACE_EVENLY);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManagerFlex);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue.stop();

        String[] category_array= getResources().getStringArray(R.array.category_array);

        for (String category:category_array) {
            String url = UrlApiUtil.getCategoryUrl(getResources().getString(R.string.API_Key),category);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = response.getJSONArray("hits").getJSONObject(new Random().nextInt(3));

                        Category newCategory = new Category(category,jsonObject.getString("webformatURL"));
                        categories.add(newCategory);

                        if (category.equals(category_array[category_array.length-1])){
                            adapterCategories = new AdapterCategories(categories,getContext(),getActivity(),fragmentExplore);
                            recyclerView.setAdapter(adapterCategories);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Category newCategory = new Category(category,"");
                    categories.add(newCategory);

                    if (category.equals(category_array[category_array.length-1])){
                        adapterCategories = new AdapterCategories(categories,getContext(),getActivity(),fragmentExplore);
                        recyclerView.setAdapter(adapterCategories);
                    }
                }
            });

            requestQueue.add(jsonObjectRequest);
        }
        requestQueue.start();

    }
}