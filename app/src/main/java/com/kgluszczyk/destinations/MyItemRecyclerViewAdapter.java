package com.kgluszczyk.destinations;

import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kgluszczyk.destinations.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.destinations.ListItemsFactory.BaseListItem;
import com.kgluszczyk.destinations.ListItemsFactory.Country;
import com.kgluszczyk.destinations.ListItemsFactory.DestinationListItem;
import com.kgluszczyk.destinations.ListItemsFactory.ListItemType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.BaseViewHolder> {

    private List<BaseListItem> mValues = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;

    @Inject
    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItemType.DESTINATION.ordinal()) {
            return new DestinationViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.destination_item, parent, false));
        } else if (viewType == ListItemType.COUNTRY.ordinal()) {
            return new CountryViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.country_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.bind(mValues.get(position), mListener);
    }

    @Override
    public int getItemViewType(final int position) {
        return mValues.get(position).getItem().ordinal();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateData(List<BaseListItem> items) {
        this.mValues = items;
        notifyDataSetChanged();
    }

    private void loadWithGlide(String url, ImageView view, @DrawableRes int placeholder) {
        GlideApp.with(view.getContext())
                .load(url)
                .centerCrop()
                .placeholder(placeholder)
                .into(view);
    }

    public class DestinationViewHolder extends BaseViewHolder {
        public final TextView title;
        public final TextView description;
        public final ImageView logo;

        public DestinationViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            logo = view.findViewById(R.id.logo);
        }

        @Override
        public void bind(final BaseListItem mItem, final OnListFragmentInteractionListener mListener) {
            super.bind(mItem, mListener);
            if (mItem instanceof DestinationListItem) {
                title.setText(((DestinationListItem) mItem).content);
                description.setText(((DestinationListItem) mItem).details);
                loadWithGlide(((DestinationListItem) mItem).uri.toString(), logo, R.drawable.ic_place_black_24dp);
            }
        }
    }

    public class CountryViewHolder extends BaseViewHolder {
        public final TextView country;
        public final ImageView background;

        public CountryViewHolder(View view) {
            super(view);
            country = view.findViewById(R.id.country);
            background = view.findViewById(R.id.background);
        }

        @Override
        public void bind(final BaseListItem mItem, final OnListFragmentInteractionListener mListener) {
            super.bind(mItem, mListener);
            if (mItem instanceof Country) {
                country.setText(((Country) mItem).getCountry());
                Bitmap bitmap = ((Country) mItem).getImageBitmap();
                if (((Country) mItem).getUri() != null) {
                    try {
                        bitmap = Media.getBitmap(itemView.getContext().getContentResolver(), ((Country) mItem).getUri());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bitmap != null) {
                    background.setImageBitmap(bitmap);
                } else if (((Country) mItem).logo != null) {
                    background.setImageDrawable(country.getContext().getResources().getDrawable(((Country) mItem).logo));
                }
            }
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public BaseListItem mItem;

        public BaseViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem + "'";
        }

        public void bind(final BaseListItem mItem, final OnListFragmentInteractionListener mListener) {
            this.mItem = mItem;
            final GestureDetector gestureDetector = new GestureDetector(mView.getContext(), new SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent event) {
                    Log.d("TAG", "onDown: " + event.toString());
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    Log.i("TAG", "onSingleTapConfirmed: ");
                    if (mListener != null) {
                        mListener.onListFragmentInteraction(mItem);
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.i("TAG", "onLongPress: ");
                    if (mListener != null) {
                        mListener.onLongClickListener(mItem);
                    }
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.i("TAG", "onDoubleTap: ");
                    return true;
                }
            });
            mView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        }
    }
}
