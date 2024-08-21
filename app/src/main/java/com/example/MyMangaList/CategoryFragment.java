package com.example.MyMangaList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class CategoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If true, the fragment has menu items to contribute.
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.app_name));


            view.findViewById(R.id.buttonAll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonTops).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new TopsFragment(), TopsFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonBottoms).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new BottomsFragment(), BottomsFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonOutwear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new OutwearFragment(), OutwearFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonAccessories).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new AccessoriesFragment(), AccessoriesFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonBags).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new BagsFragment(), BagsFragment.class.getSimpleName());
                }
            });
            view.findViewById(R.id.buttonFootwear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new FootwearFragment(), FootwearFragment.class.getSimpleName());
                }
            });

            view.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(),
                            AddFragment.class.getSimpleName());
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.app_bar_search).setVisible(false);

    }
}
