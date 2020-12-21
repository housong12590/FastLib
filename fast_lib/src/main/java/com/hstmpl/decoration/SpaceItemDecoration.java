package com.hstmpl.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;
    private int headerCount;
    private boolean side;

    public SpaceItemDecoration(int space) {
        this.space = dp2px(space);
    }

    public SpaceItemDecoration(int space, int headerCount) {
        this.space = dp2px(space);
        this.headerCount = headerCount;
    }

    public SpaceItemDecoration(int space, boolean side) {
        this.space = dp2px(space);
        this.side = side;
    }


    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) manager;
            gridLayoutManager(outRect, parent.getChildAdapterPosition(view), layoutManager);
        } else if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            linearLayoutManager(outRect, parent.getChildAdapterPosition(view), layoutManager);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;
            staggeredGridLayoutManager(outRect, parent.getChildAdapterPosition(view), layoutManager);
        }
    }

    private void staggeredGridLayoutManager(Rect rect, int position, StaggeredGridLayoutManager layoutManager) {
        rect.left = space / 2;
        rect.right = space / 2;
        if (position != 0) {
            rect.top = space;
        }
    }

    private void gridLayoutManager(Rect rect, int position, GridLayoutManager layoutManager) {
        int spanCount = layoutManager.getSpanCount();
        int orientation = layoutManager.getOrientation();
        position = position - headerCount;
        if (position < 0) {
            return;
        }
        if (orientation == GridLayoutManager.VERTICAL) {
            if (position >= spanCount || side) {//非side下,第一行不加top间距
                rect.top = space;
            }
            if (position % spanCount < spanCount - 1 || side) { //非side下,最右边不加right间距
                rect.right = space / 2;
            }
            if (position % spanCount != 0 || side) { //非side下,最左边不加left间距
                rect.left = space / 2;
            }
        } else if (orientation == GridLayoutManager.HORIZONTAL) {
            if (position >= spanCount) {
                rect.left = space;
            }
            if (position % spanCount < spanCount - 1) {
                rect.bottom = space / 2;
            }
            if (position % spanCount != 0) {
                rect.top = space / 2;
            }
        }
    }

    private void linearLayoutManager(Rect rect, int position, LinearLayoutManager layoutManager) {
        int orientation = layoutManager.getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) { //纵向不加left
            if (position != 0) {
                rect.left = space;
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {//垂直不加top
            if (position != 0) {
                rect.top = space;
            }
        }
    }

    public int dp2px(int space) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * space);
    }
}
