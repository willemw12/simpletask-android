package com.mobeta.android.dslv;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

/**
 * Simple implementation of the FloatViewManager class. Uses list
 * items as they appear in the ListView to create the floating View.
 */
public class SimpleFloatViewManager implements DragSortListView.FloatViewManager {
    private final ListView mListView;
    @Nullable
    private Bitmap mFloatBitmap;
    private ImageView mImageView;
    private int mFloatBGColor = Color.BLACK;

    public SimpleFloatViewManager(ListView lv) {
        mListView = lv;
    }

    public void setBackgroundColor(int color) {
        mFloatBGColor = color;
    }

    /**
     * This simple implementation creates a Bitmap copy of the
     * list item currently shown at ListView <code>position</code>.
     */
    @Nullable
    @Override
    public View onCreateFloatView(int position) {
        // Guaranteed that this will not be null?
        // I think so. Nope, got a NullPointerException once...
        View v = mListView.getChildAt(position + mListView.getHeaderViewsCount() - mListView.getFirstVisiblePosition());

        if (v == null) {
            return null;
        }

        v.setPressed(false);

        // Create a copy of the drawing cache so that it does not get
        // recycled by the framework when the list tries to clean up memory
        //v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        v.setDrawingCacheEnabled(true);
        mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        if (mImageView == null) {
            mImageView = new ImageView(mListView.getContext());
        }
        mImageView.setBackgroundColor(mFloatBGColor);
        mImageView.setPadding(0, 0, 0, 0);
        mImageView.setImageBitmap(mFloatBitmap);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(v.getWidth(), v.getHeight()));

        return mImageView;
    }

    /**
     * This does nothing
     */
    @Override
    public void onDragFloatView(View floatView, Point position, Point touch) {
        // Do nothing
    }

    /**
     * Removes the Bitmap from the ImageView created in
     * onCreateFloatView() and tells the system to recycle it.
     */
    @Override
    public void onDestroyFloatView(@NonNull View floatView) {
        ((ImageView) floatView).setImageDrawable(null);
        if (mFloatBitmap != null) {
            mFloatBitmap.recycle();
        }
        mFloatBitmap = null;
    }
}