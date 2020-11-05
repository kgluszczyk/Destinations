package com.kgluszczyk.destinations.presentation;

import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.kgluszczyk.destinations.GlideApp;
import com.kgluszczyk.destinations.databinding.CountryItemBinding;
import com.kgluszczyk.destinations.databinding.DestinationItemBinding;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.BaseListItem;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.Country;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.DestinationListItem;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.ListItemType;
import com.kgluszczyk.destinations.view.DestinationsFragment.OnListFragmentInteractionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DestinationViewAdapter extends RecyclerView.Adapter<DestinationViewAdapter.BaseViewHolder> {

    private List<BaseListItem> mValues = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;

    @Inject
    public DestinationViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItemType.DESTINATION.ordinal()) {
            return new DestinationViewHolder(DestinationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else if (viewType == ListItemType.COUNTRY.ordinal()) {
            return new CountryViewHolder(CountryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        final ImageView logo;

        DestinationViewHolder(DestinationItemBinding binding) {
            super(binding);
            title = binding.title;
            description = binding.description;
            logo = binding.logo;
        }

        @Override
        public void bind(final BaseListItem mItem, final OnListFragmentInteractionListener mListener) {
            super.bind(mItem, mListener);
            if (mItem instanceof DestinationListItem) {
                title.setText(((DestinationListItem) mItem).content);
                description.setText(((DestinationListItem) mItem).details);
                loadWithGlide(((DestinationListItem) mItem).uri.toString(), logo, android.R.color.darker_gray);
            }
        }
    }

    public class CountryViewHolder extends BaseViewHolder {
        public final TextView country;
        final ImageView background;

        CountryViewHolder(CountryItemBinding binding) {
            super(binding);
            country = binding.country;
            background = binding.background;
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
        public final ViewDataBinding binding;
        BaseListItem mItem;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem + "'";
        }

        public void bind(final BaseListItem mItem, final OnListFragmentInteractionListener mListener) {
            this.mItem = mItem;
            final GestureDetector gestureDetector = new GestureDetector(binding.getRoot().getContext(), new SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent event) {
                    Log.d("TAG", "onDown: " + event.toString());
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    Log.i("TAG", "onSingleTapConfirmed: ");
                    if (mListener != null) {
                        //Commented out due to play store policy requirement for CAMERA permission
                        //mListener.onListFragmentInteraction(mItem);
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
            binding.getRoot().setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        }
    }
}
