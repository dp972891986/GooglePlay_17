package com.itheima.googleplay_17.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.CategoryInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 15:29
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 16:33:36 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class CategoryNormalHolder extends BaseHolder<CategoryInfoBean> {


    @Bind(R.id.item_category_icon_1)
    ImageView    mItemCategoryIcon1;
    @Bind(R.id.item_category_name_1)
    TextView     mItemCategoryName1;
    @Bind(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;

    @Bind(R.id.item_category_icon_2)
    ImageView    mItemCategoryIcon2;
    @Bind(R.id.item_category_name_2)
    TextView     mItemCategoryName2;
    @Bind(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;

    @Bind(R.id.item_category_icon_3)
    ImageView    mItemCategoryIcon3;
    @Bind(R.id.item_category_name_3)
    TextView     mItemCategoryName3;
    @Bind(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
       /* mItemCategoryName1.setText(data.name1);
        mItemCategoryName2.setText(data.name2);
        mItemCategoryName3.setText(data.name3);

        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.url1).into(mItemCategoryIcon1);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.url2).into(mItemCategoryIcon2);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.url3).into(mItemCategoryIcon3);*/

        initData(data.name1, data.url1, mItemCategoryIcon1, mItemCategoryName1);
        initData(data.name2, data.url2, mItemCategoryIcon2, mItemCategoryName2);
        initData(data.name3, data.url3, mItemCategoryIcon3, mItemCategoryName3);

    }

    public void initData(final String name, String url, ImageView iv, TextView tv) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(url)) {
            tv.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(iv);

            ViewParent parent = tv.getParent();
            //点击事件执行写一次
            ((ViewGroup) parent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });

            ((ViewGroup) parent).setVisibility(View.VISIBLE);
        } else {
            ViewParent parent = tv.getParent();
            ((ViewGroup) parent).setVisibility(View.INVISIBLE);

        }
    }
}
