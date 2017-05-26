package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.StringUtils;
import com.itheima.googleplay_17.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 14:16
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 14:33:24 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailInfoHolder extends BaseHolder<ItemInfoBean> {


    @Bind(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @Bind(R.id.app_detail_info_tv_name)
    TextView  mAppDetailInfoTvName;
    @Bind(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @Bind(R.id.app_detail_info_tv_downloadnum)
    TextView  mAppDetailInfoTvDownloadnum;
    @Bind(R.id.app_detail_info_tv_version)
    TextView  mAppDetailInfoTvVersion;
    @Bind(R.id.app_detail_info_tv_time)
    TextView  mAppDetailInfoTvTime;
    @Bind(R.id.app_detail_info_tv_size)
    TextView  mAppDetailInfoTvSize;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        //找到孩子对象,转换成成员变量
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        //data
        //view


        String downLoadNum = UIUtils.getString(R.string.detail_downnum, data.downloadNum);
        String size = UIUtils.getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
        String updateTime = UIUtils.getString(R.string.detail_updatetime, data.date);
        String version = UIUtils.getString(R.string.detail_version, data.version);

        mAppDetailInfoTvName.setText(data.name);


        mAppDetailInfoTvDownloadnum.setText(downLoadNum);
        mAppDetailInfoTvSize.setText(size);
        mAppDetailInfoTvTime.setText(updateTime);
        mAppDetailInfoTvVersion.setText(version);


        mAppDetailInfoRbStar.setRating(data.stars);


        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.iconUrl).into(mAppDetailInfoIvIcon);
    }
}
