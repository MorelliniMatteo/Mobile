package com.example.MyMangaList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MyMangaList.RecyclerView.CardAdapter;
import com.example.MyMangaList.RecyclerView.OnItemListener;
import com.example.MyMangaList.ViewModel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements OnItemListener {

    private static final String LOG_TAG = "HomeFragment";

    private CardAdapter adapter;

    private ListViewModel listViewModel;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.all));

            setRecyclerView(activity);

            listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            listViewModel.getCardItems().observe(activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    adapter.setData(cardItems);
                }
            });

            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_add);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(),
                            AddFragment.class.getSimpleName());
                }
            });
        } else {
            Log.e(LOG_TAG, "Activity is null");
        }
    }

    /**
     * Method to set the RecyclerView and the relative adapter
     * @param activity the current activity
     */
    private void setRecyclerView(final Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final OnItemListener listener = this;
        adapter = new CardAdapter(listener, activity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if (activity != null){
            Utilities.insertFragment((AppCompatActivity) activity, new DetailsFragment(),
                    DetailsFragment.class.getSimpleName());

            listViewModel.setItemSelected(adapter.getItemSelected(position));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // In inflaziona il menu
        inflater.inflate(R.menu.top_app_bar, menu);

        // Gestisci solo gli elementi che esistono nel menu
        MenuItem favoriteItem = menu.findItem(R.id.menu_favorite);
        MenuItem filterItem = menu.findItem(R.id.menu_filter);

        // Se avevi un elemento di ricerca, gestisci qui (per esempio):
        // MenuItem searchItem = menu.findItem(R.id.menu_search);
        // if (searchItem != null) {
        //     SearchView searchView = (SearchView) searchItem.getActionView();
        //     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //         @Override
        //         public boolean onQueryTextSubmit(String query) {
        //             return false;
        //         }
        //         @Override
        //         public boolean onQueryTextChange(String newText) {
        //             adapter.getFilter().filter(newText);
        //             return true;
        //         }
        //     });
        // }
    }

}
