package com.kgluszczyk.destinations.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.presentation.DestinationsViewModel;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.BaseListItem;
import com.kgluszczyk.destinations.presentation.MyItemRecyclerViewAdapter;
import dagger.android.support.DaggerFragment;
import javax.inject.Inject;

public class DestinationsFragment extends DaggerFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    @Inject
    MyItemRecyclerViewAdapter adapter;

    @Inject
    DestinationsViewModel destinationsViewModel;

    private int mColumnCount = 1;

    @SuppressWarnings("unused")
    public static DestinationsFragment newInstance(int columnCount) {
        DestinationsFragment fragment = new DestinationsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public DestinationsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(adapter);
        destinationsViewModel.getData().observe(this, destinations -> adapter.updateData(destinations));
        return view;
    }

    public MyItemRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(BaseListItem item);

        void onLongClickListener(BaseListItem item);
    }
}
