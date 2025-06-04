package com.ahmadrezagh671.pixabay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadrezagh671.pixabay.Adapters.AdapterColors;
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


public class FragmentColor extends Fragment {

    RecyclerView recyclerView;
    AdapterColors adapterColors;
    List<Category> colors;
    FlexboxLayoutManager layoutManagerFlex;

    RequestQueue requestQueue;

    FragmentExplore fragmentExplore;

    public FragmentColor() {

    }

    public FragmentColor(FragmentExplore fragmentExplore) {
        this.fragmentExplore = fragmentExplore;
    }

    public static FragmentColor newInstance() {
        FragmentColor fragment = new FragmentColor();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        colors = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color, container, false);

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

        String[] color_array= getResources().getStringArray(R.array.color_array);

        for (String color:color_array) {
            String url = UrlApiUtil.getColorUrl(getResources().getString(R.string.API_Key),color);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = response.getJSONArray("hits").getJSONObject(new Random().nextInt(3));

                        Category newCategory = new Category(color,jsonObject.getString("webformatURL"));
                        colors.add(newCategory);

                        if (color.equals(color_array[color_array.length-1])){
                            adapterColors = new AdapterColors(colors,getContext(),getActivity(),fragmentExplore);
                            recyclerView.setAdapter(adapterColors);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Category newCategory = new Category(color,"");
                    colors.add(newCategory);

                    if (color.equals(color_array[color_array.length-1])){
                        adapterColors = new AdapterColors(colors,getContext(),getActivity(),fragmentExplore);
                        recyclerView.setAdapter(adapterColors);
                    }
                }
            });

            requestQueue.add(jsonObjectRequest);
        }
        requestQueue.start();

    }
}