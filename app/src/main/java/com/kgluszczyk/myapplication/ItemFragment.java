package com.kgluszczyk.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kgluszczyk.myapplication.dummy.ListItemsFactory;
import com.kgluszczyk.myapplication.dummy.ListItemsFactory.BaseListItem;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    private static final int CONNECTION_TIMEOUT_S = 20;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter adapter;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Builder builder = new Builder()
                    .readTimeout(CONNECTION_TIMEOUT_S, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIMEOUT_S, TimeUnit.SECONDS)
                    .addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://my-json-server.typicode.com/kgluszczyk/")
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();

            final DestinationsService service = retrofit.create(DestinationsService.class);

            service.getDestinationsSingle()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        adapter = new MyItemRecyclerViewAdapter(convertResponse(response), mListener);
                        recyclerView.setAdapter(adapter);
                    }, error -> Log.e("INTERNET", "Upse...", error));

       /*    service.getDestinationsCall().enqueue(new Callback<List<Destination>>() {
                @Override
                public void onResponse(final Call<List<Destination>> call, final Response<List<Destination>> response) {
                    adapter = new MyItemRecyclerViewAdapter(convertResponse(response.body()), mListener);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(final Call<List<Destination>> call, final Throwable t) {
                    Toast.makeText(ItemFragment.this.getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            });*/

        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public MyItemRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    private List<BaseListItem> convertResponse(List<Destination> destinations) {
        List<BaseListItem> listItems = new ArrayList<>();
        for (int i = 0; i < destinations.size(); i++) {
            Destination destination = destinations.get(i);
            if (i == 0 || !destinations.get(i - 1).getCountry().equals(destination.getCountry())) {
                listItems.add(ListItemsFactory.createCountry(destination));
            }
            listItems.add(ListItemsFactory.createDestination(destination));
        }
        return listItems;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(BaseListItem item);

        void onLongClickListener(BaseListItem item);
    }
}
