package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.SubjectInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 11:15
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 11:24:51 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class SubjectHolder extends BaseHolder<SubjectInfoBean> {

    @Bind(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;

    @Bind(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        //找孩子.变成成员变量
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void refreshHolderView(SubjectInfoBean data) {
        //view
        //data
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext())
                .load(Constants.URLS.IMGBASEURL + data.url)
                .into(mItemSubjectIvIcon);
    }
}
