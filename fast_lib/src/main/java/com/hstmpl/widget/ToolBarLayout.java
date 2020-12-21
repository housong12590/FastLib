package com.hstmpl.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hstmpl.fastlib.R;
import com.hstmpl.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;


public class ToolBarLayout extends LinearLayout implements View.OnClickListener {

    private static final int DEFAULT_VIEW_ID = -1;
    private static final int DEFAULT_BACK_ID = 0xff0011;
    private static final int NO_RES_ID = -1;
    private RelativeLayout mContentView;

    /**
     * 标题文本
     */
    private String mToolBarTitleText;
    /**
     * 标题栏高度
     */
    private int mToolBarHeight;
    /**
     * bar背景颜色
     */
    private int mToolBarBackground;
    /**
     * title文本颜色
     */
    private int mToolBarTitleColor;
    /**
     * 文本对齐方式,left,right,center
     */
    private int mToolBarTitleGravity;
    /**
     * 菜单按钮的宽度
     */
    private int mToolBarBtnWidth;
    /**
     * 菜单按钮的间距(只对左右两边生效)
     */
    private int mToolBarMenuMargin;
    /**
     * 控件左右两边的padding
     */
    private int mToolBarPadding;
    /**
     * title文本字体大小
     */
    private int mToolBarTitleSize;
    /**
     * title 左右两边的margin
     */
    private int mToolBarTitleMargin;
    /**
     * 默认返回键图标
     */
    private int mToolBarBackImage;
    /**
     * 菜单文字大小
     */
    private int mToolBarMenuTextSize;
    /**
     * 是否有默认返回键
     */
    private boolean mToolBarHasDefaultBack;
    /**
     * 菜单按钮字体颜色
     */
    private int mToolBarMenuTextColor;

    private int mRightLastViewId;
    private int mLeftLastViewId;
    private List<View> mRightViewList;
    private List<View> mLeftViewList;
    private TextView mTitleView;

    public ToolBarLayout(Context context) {
        this(context, null);
    }

    public ToolBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initVars();
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ToolBarLayout
                , R.attr.toolBarStyle, 0);
        mToolBarHeight = (int) ta.getDimension(R.styleable.ToolBarLayout_toolbar_height, 0);
        mToolBarBackground = ta.getColor(R.styleable.ToolBarLayout_toolbar_background, 0);
        mToolBarTitleColor = ta.getColor(R.styleable.ToolBarLayout_toolbar_title_color, 0);
        mToolBarTitleText = ta.getString(R.styleable.ToolBarLayout_toolbar_title_text);
        mToolBarTitleGravity = ta.getInt(R.styleable.ToolBarLayout_toolbar_title_gravity, 0);//0左1中2右
        mToolBarBtnWidth = (int) ta.getDimension(R.styleable.ToolBarLayout_toolbar_btn_width, 0);
        mToolBarMenuMargin = (int) ta.getDimension(R.styleable.ToolBarLayout_toolbar_menu_margin, 0);
        mToolBarPadding = (int) ta.getDimension(R.styleable.ToolBarLayout_toolbar_padding, 0);
        mToolBarTitleSize = ta.getDimensionPixelSize(R.styleable.ToolBarLayout_toolbar_title_size, 0);
        mToolBarTitleMargin = (int) ta.getDimension(R.styleable.ToolBarLayout_toolbar_title_margin, 0);
        mToolBarBackImage = ta.getResourceId(R.styleable.ToolBarLayout_toolbar_back_image, NO_RES_ID);
        mToolBarMenuTextSize = ta.getDimensionPixelSize(R.styleable.ToolBarLayout_toolbar_menu_text_size, 0);
        mToolBarHasDefaultBack = ta.getBoolean(R.styleable.ToolBarLayout_toolbar_has_default_back, false);
        mToolBarMenuTextColor = ta.getColor(R.styleable.ToolBarLayout_toolbar_menu_text_color, Color.WHITE);
        ta.recycle();
    }

    private void initVars() {
        mRightLastViewId = DEFAULT_VIEW_ID;
        mLeftLastViewId = DEFAULT_VIEW_ID;
        mRightViewList = new ArrayList<>();
        mLeftViewList = new ArrayList<>();
    }

    private void initView() {
        setBackgroundColor(mToolBarBackground);
        mContentView = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams rlLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mToolBarHeight);
        mContentView.setLayoutParams(rlLayoutParams);
        mContentView.setPadding(mToolBarPadding, 0, mToolBarPadding, 0);
        mContentView.setGravity(Gravity.CENTER_VERTICAL);

        mContentView.addView(createTitleView());

        if (!TextUtils.isEmpty(mToolBarTitleText)) {
            mTitleView.setText(mToolBarTitleText);
        }
        addView(mContentView);
        if (mToolBarHasDefaultBack) {
            setDefaultBackButton();
        }
    }

    /**
     * 添加右菜单按钮
     *
     * @param view   需要添加的VIEW
     * @param viewId viewID
     */
    public ToolBarLayout addRightView(View view, int viewId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = getMenuButtonLayoutParams();
        }
        this.addRightView(view, viewId, layoutParams);
        return this;
    }

    /**
     * 添加右菜单按钮
     *
     * @param view         需要添加的VIEW
     * @param viewId       viewID
     * @param layoutParams 布局参数
     */
    public ToolBarLayout addRightView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        view.setId(viewId);
        if (mRightLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.LEFT_OF, mRightLastViewId);
        }
        layoutParams.alignWithParent = true;
        mRightLastViewId = viewId;
        view.setLayoutParams(layoutParams);
        mRightViewList.add(view);
        view.setOnClickListener(this);
        mContentView.addView(view);
        refreshTitleView();
        return this;
    }

    /**
     * 添加左菜单View
     *
     * @param view   需要添加的View
     * @param viewId viewID
     */
    public ToolBarLayout addLeftView(View view, int viewId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = getMenuButtonLayoutParams();
        }
        this.addLeftView(view, viewId, layoutParams);
        return this;
    }

    /**
     * 添加左菜单View
     *
     * @param view         需要添加的View
     * @param viewId       viewID
     * @param layoutParams 布局参数
     */
    public ToolBarLayout addLeftView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        view.setId(viewId);
        if (mLeftLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftLastViewId);
        }
        layoutParams.alignWithParent = true;
        mLeftLastViewId = viewId;
        view.setLayoutParams(layoutParams);
        mLeftViewList.add(view);
        view.setOnClickListener(this);
        mContentView.addView(view);
        refreshTitleView();
        return this;
    }

    /**
     * 生成图片按钮
     *
     * @param imageResourceId 图片资源ID
     */
    private ImageButton generateImageButton(int imageResourceId) {
        AlphaImageButton button = new AlphaImageButton(getContext());
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setImageResource(imageResourceId);
        return button;
    }

    /**
     * 生成文字按钮
     *
     * @param text          按钮文字
     * @param backgroundRes 背景资源ID
     */
    private TextView generateTextButton(String text, int backgroundRes) {
        TextView button = new AlphaTextView(getContext());
        if (backgroundRes == NO_RES_ID) {
            button.setBackgroundColor(Color.TRANSPARENT);
        } else {
            button.setBackgroundResource(backgroundRes);
        }
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(mToolBarMenuTextColor);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, mToolBarMenuTextSize);
        return button;
    }

    /**
     * 添加左边图片按钮
     *
     * @param drawableResId 图片资源ID
     * @param viewId        viewID
     */
    public ToolBarLayout addLeftImageButton(int drawableResId, int viewId) {
        ImageButton leftButton = generateImageButton(drawableResId);
        this.addLeftView(leftButton, viewId, getMenuButtonLayoutParams());
        return this;
    }

    /**
     * 添加右边图片按钮
     *
     * @param drawableResId 图片资源ID
     * @param viewId        viewID
     */
    public ToolBarLayout addRightImageButton(int drawableResId, int viewId) {
        ImageButton rightButton = generateImageButton(drawableResId);
        this.addRightView(rightButton, viewId, getMenuButtonLayoutParams());
        return this;
    }

    /**
     * 添加左边文字按钮
     *
     * @param text   文字
     * @param viewId viewID
     */
    public ToolBarLayout addLeftTextButton(String text, int viewId) {
        this.addLeftTextButton(text, viewId, NO_RES_ID);
        return this;
    }

    /**
     * 添加左边文字按钮
     *
     * @param text          按钮文字
     * @param viewId        viewID
     * @param backgroundRes 背景资源ID
     */
    public ToolBarLayout addLeftTextButton(String text, int viewId, int backgroundRes) {
        TextView button = generateTextButton(text, backgroundRes);
        this.addLeftView(button, viewId, getMenuButtonLayoutParams());
        refreshTitleView();
        return this;
    }

    /**
     * 添加右边文字按钮
     *
     * @param text   按钮文字
     * @param viewId viewID
     */
    public ToolBarLayout addRightTextButton(String text, int viewId) {
        this.addRightTextButton(text, viewId, NO_RES_ID);
        return this;
    }

    /**
     * 添加右边文字按钮
     *
     * @param text          文本
     * @param viewId        viewID
     * @param backgroundRes 背景资源ID
     */
    public ToolBarLayout addRightTextButton(String text, int viewId, int backgroundRes) {
        TextView button = generateTextButton(text, backgroundRes);
        this.addRightView(button, viewId, getMenuButtonLayoutParams());
        refreshTitleView();
        return this;
    }

    public ToolBarLayout addRightTextButton(String text, int viewId, RelativeLayout.LayoutParams layoutParams) {
        TextView button = generateTextButton(text, NO_RES_ID);
        this.addRightView(button, viewId, layoutParams);
        refreshTitleView();
        return this;
    }

    /**
     * 添加右边文字和图片按钮
     *
     * @param text   文本
     * @param icon   图片资源
     * @param viewId viewID
     * @param type   图片对齐方式
     */
    public ToolBarLayout addRightTextAndImageButton(String text, int icon, int viewId, Type type, int padding) {
        TextView button = generateTextButton(text, NO_RES_ID);
        RelativeLayout.LayoutParams params = getMenuTextAndImageButtonLayoutParams(button, icon, type, padding);
        this.addRightView(button, viewId, params);
        return this;
    }


    /**
     * 添加左边文字和图片按钮
     *
     * @param text   文本
     * @param icon   图片资源
     * @param viewId viewID
     * @param type   图片对齐方式
     */
    public ToolBarLayout addLeftTextAndImageButton(String text, int icon, int viewId, Type type, int padding) {
        TextView button = generateTextButton(text, NO_RES_ID);
        RelativeLayout.LayoutParams params = getMenuTextAndImageButtonLayoutParams(button, icon, type, padding);
        this.addLeftView(button, viewId, params);
        return this;
    }


    public RelativeLayout.LayoutParams getMenuTextAndImageButtonLayoutParams(TextView button, int icon,
                                                                             Type type, int padding) {
        RelativeLayout.LayoutParams params = getMenuButtonLayoutParams();
        Drawable drawable = getResources().getDrawable(icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (type) {
            case LEFT:
                params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                button.setCompoundDrawables(drawable, null, null, null);
                break;
            case TOP:
                params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                button.setCompoundDrawables(null, drawable, null, null);
                break;
            case RIGHT:
                params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                button.setCompoundDrawables(null, null, drawable, null);
                break;
            case BOTTOM:
                params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                button.setCompoundDrawables(null, null, null, drawable);
                break;
        }
        button.setCompoundDrawablePadding(DensityUtils.dp2px(getContext(), padding));
        return params;
    }

    /**
     * @return 获取菜单布局参数
     */
    private RelativeLayout.LayoutParams getMenuButtonLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mToolBarBtnWidth,
                mToolBarHeight);
        layoutParams.setMargins(mToolBarMenuMargin, 0, mToolBarMenuMargin, 0);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        return layoutParams;
    }


    /**
     * 添加自定义布局,需在菜单按钮添加完成之后再添加些项
     */
    public ToolBarLayout addCenterView(View view) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        if (mLeftLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftLastViewId);
        }
        if (mRightLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.LEFT_OF, mRightLastViewId);
        }
        view.setLayoutParams(layoutParams);
        mContentView.addView(view);
        return this;
    }

    /**
     * @return 获取titleView
     */
    private TextView createTitleView() {
        if (mTitleView == null) {
            mTitleView = new TextView(getContext());
            mTitleView.setTextColor(mToolBarTitleColor);
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mToolBarTitleSize);
            mTitleView.setGravity(Gravity.CENTER);
            mTitleView.setSingleLine(true);
            mTitleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            mTitleView.setLayoutParams(getTitleViewLayoutParams());
            mTitleView.getPaint().setFakeBoldText(true);
        }
        return mTitleView;
    }

    /**
     * 添加菜单之后要重新刷新title布局参数，否则不起作用
     */
    private void refreshTitleView() {
        if (mTitleView == null) {
            return;
        }
        mTitleView.setLayoutParams(getTitleViewLayoutParams());
    }

    /**
     * @return 获取titleView布局参数
     */
    private RelativeLayout.LayoutParams getTitleViewLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (mToolBarTitleGravity) {
            case 0: //left
                if (mLeftLastViewId == DEFAULT_VIEW_ID) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftLastViewId);
                }
                break;
            case 1: //center
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            case 2: //right
                if (mRightLastViewId == DEFAULT_VIEW_ID) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    layoutParams.addRule(RelativeLayout.LEFT_OF, mRightLastViewId);
                }
                break;
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.alignWithParent = true;
        layoutParams.setMargins(mToolBarTitleMargin, 0, mToolBarTitleMargin, 0);
        return layoutParams;
    }

    /**
     * 设置默认的左返回按钮，带返回点击事件
     */
    public ToolBarLayout setDefaultBackButton() {
        if (mToolBarBackImage == NO_RES_ID) {
            throw new NullPointerException("toolbar no setup default back image resource...");
        }
        addLeftImageButton(mToolBarBackImage, DEFAULT_BACK_ID);
        getView(DEFAULT_BACK_ID).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getContext();
                activity.onBackPressed();
            }
        });
        return this;
    }

    public View getDefaultBackView() {
        return getView(DEFAULT_BACK_ID);
    }

    /**
     * 设置默认的左返回按钮，不带返回点击事件
     */
    public ToolBarLayout setDefaultBackButton(OnClickListener listener) {
        if (mToolBarBackImage == NO_RES_ID) {
            throw new NullPointerException("toolbar no setup default back image resource...");
        }
        addLeftImageButton(mToolBarBackImage, DEFAULT_BACK_ID);
        getView(DEFAULT_BACK_ID).setOnClickListener(listener);
        return this;
    }

    /**
     * 根据ID 移除View
     *
     * @param id 要移除View的ID
     */
    public void removeView(int id) {
        List<View> tempList = new ArrayList<>();
        tempList.addAll(mLeftViewList);
        tempList.addAll(mRightViewList);
        for (View view : tempList) {
            if (view.getId() == id) {
                if (id == mRightLastViewId) {
                    mRightLastViewId = DEFAULT_VIEW_ID;
                } else if (id == mLeftLastViewId) {
                    mLeftLastViewId = DEFAULT_VIEW_ID;
                }
                removeView(view);
                break;
            }
        }
    }

    /**
     * 移除所有的菜单view,但不包括titleView
     */
    public void removeAllMenuViews() {
        removeAllLeftViews();
        removeAllRightViews();
    }

    /**
     * 移除左边所有菜单view
     */
    public void removeAllLeftViews() {
        for (View view : mLeftViewList) {
            removeView(view);
        }
        mLeftLastViewId = DEFAULT_VIEW_ID;
        mLeftViewList.clear();
    }

    /**
     * 称除右边所有菜单view
     */
    public void removeAllRightViews() {
        for (View view : mRightViewList) {
            removeView(view);
        }
        mRightLastViewId = DEFAULT_VIEW_ID;
        mRightViewList.clear();
    }

    /**
     * 设置标题
     *
     * @param resId 资源ID
     */
    public void setTitle(int resId) {
        String title = getContext().getString(resId);
        setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param title 文本
     */
    public void setTitle(String title) {
        if (title != null) {
            mTitleView.setText(title);
        }
    }

    public String getTitle() {
        return mTitleView.getText().toString();
    }

    /**
     * 根据ID 获取对应的View
     *
     * @param id  要获取view的ID
     * @param <T> 返回view的类型,有（TextView,imageButton）
     */
    public <T extends View> T getView(int id) {
        List<View> tempList = new ArrayList<>();
        tempList.addAll(mLeftViewList);
        tempList.addAll(mRightViewList);
        for (View view : tempList) {
            if (view.getId() == id) {
                return (T) view;
            }
        }
        return null;
    }


    /**
     * 设置 TopBar 背景的透明度
     *
     * @param alpha 取值范围：[0, 255]，255表示不透明
     */
    public void setBackgroundAlpha(int alpha) {
        this.getBackground().setAlpha(alpha);
    }

    /**
     * 根据当前 offset、透明度变化的初始 offset 和目标 offset，计算并设置 Topbar 的透明度
     *
     * @param currentOffset     当前 offset
     * @param alphaBeginOffset  透明度开始变化的offset，即当 currentOffset == alphaBeginOffset 时，透明度为0
     * @param alphaTargetOffset 透明度变化的目标offset，即当 currentOffset == alphaTargetOffset 时，透明度为1
     */
    public int computeAndSetBackgroundAlpha(int currentOffset, int alphaBeginOffset, int alphaTargetOffset) {
        double alpha = (float) (currentOffset - alphaBeginOffset) / (alphaTargetOffset - alphaBeginOffset);
        alpha = Math.max(0, Math.min(alpha, 1)); // from 0 to 1
        int alphaInt = (int) (alpha * 255);
        this.setBackgroundAlpha(alphaInt);
        return alphaInt;
    }


    public enum Type {
        LEFT, TOP, RIGHT, BOTTOM
    }


    public interface OnActionListener {

        void onActionClick(View view);
    }

    private OnActionListener listener;

    public ToolBarLayout setOnActionListener(OnActionListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onActionClick(v);
        }
    }
}
