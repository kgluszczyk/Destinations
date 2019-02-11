package com.kgluszczyk.myapplication;

import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Media;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kgluszczyk.myapplication.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.myapplication.dummy.StaticContent;
import com.kgluszczyk.myapplication.dummy.StaticContent.BazowyListItem;
import com.kgluszczyk.myapplication.dummy.StaticContent.ListItemType;
import com.kgluszczyk.myapplication.dummy.StaticContent.UniwersytetListItem;
import com.kgluszczyk.myapplication.dummy.StaticContent.ZabytekItem;
import java.io.IOException;
import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.BaseViewHolder> {

    private final List<StaticContent.BazowyListItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<BazowyListItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItemType.UNIWERSYTET.ordinal()) {
            return new UniwersytetViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.uniwersytet_element, parent, false));
        } else if (viewType == ListItemType.ZABYTEK.ordinal()) {
            return new ZabytekViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.zabytek_element, parent, false));
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

    public class UniwersytetViewHolder extends BaseViewHolder {
        public final TextView title;
        public final TextView description;
        public final ImageView logo;

        public UniwersytetViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            logo = view.findViewById(R.id.logo);
        }

        @Override
        public void bind(final BazowyListItem mItem, final OnListFragmentInteractionListener mListener) {
            super.bind(mItem, mListener);
            if (mItem instanceof UniwersytetListItem) {
                title.setText(((UniwersytetListItem) mItem).content);
                description.setText(((UniwersytetListItem) mItem).details);
                logo.setImageDrawable(logo.getContext().getResources().getDrawable(((UniwersytetListItem) mItem).logo));
            }
        }
    }

    public class ZabytekViewHolder extends BaseViewHolder {
        public final ImageView logo;

        public ZabytekViewHolder(View view) {
            super(view);
            logo = view.findViewById(R.id.zabytek_obraz);
        }

        @Override
        public void bind(final BazowyListItem mItem, final OnListFragmentInteractionListener mListener) {
            super.bind(mItem, mListener);
            if (mItem instanceof ZabytekItem) {
                Bitmap bitmap = ((ZabytekItem) mItem).getImageBitmap();
                if (((ZabytekItem) mItem).getUri() != null) {
                    try {
                        bitmap = Media.getBitmap(logo.getContext().getContentResolver(), ((ZabytekItem) mItem).getUri());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bitmap != null) {
                    logo.setImageBitmap(bitmap);
                } else {
                    logo.setImageDrawable(logo.getContext().getResources().getDrawable(((ZabytekItem) mItem).logo));
                }
            }
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public StaticContent.BazowyListItem mItem;

        public BaseViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem + "'";
        }

        public void bind(final BazowyListItem mItem, final OnListFragmentInteractionListener mListener) {
            this.mItem = mItem;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(mItem);
                    }
                }
            });
        }
    }
}
