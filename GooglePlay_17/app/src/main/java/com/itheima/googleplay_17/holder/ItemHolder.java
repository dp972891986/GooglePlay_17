package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.manager.DownLoadInfo;
import com.itheima.googleplay_17.manager.DownLoadManager;
import com.itheima.googleplay_17.utils.CommonUtils;
import com.itheima.googleplay_17.utils.PrintDownLoadInfo;
import com.itheima.googleplay_17.utils.StringUtils;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.ProgressView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 16:56
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:58:05 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class ItemHolder extends BaseHolder<ItemInfoBean> implements DownLoadManager.DownLoadInfoObserver {

    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;

    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;

    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;

    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;

    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    @Bind(R.id.item_appinfo_progressview)
    ProgressView mProgressView;

    @Override
    public View initHolderView() {
        //1.告知视图
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_info, null);

        //2.找出孩子对象,变成成员变量
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        //重置ProgressView,因为ProgressView的进度只有在下载的时候,才会再次刷新
        mProgressView.setProgress(0);

        mItemAppinfoTvDes.setText(data.des);
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvTitle.setText(data.name);

        mItemAppinfoRbStars.setRating(data.stars);
        //图片处理.
        Picasso.with(UIUtils.getContext())
                .load(Constants.URLS.IMGBASEURL + data.iconUrl)
                .fade(100)
                .into(mItemAppinfoIvIcon);

        /**
         ratingbar
         -->展示功能-->不给你评分
         -->评分功能-->1.5
         */

          /*--------------- 2.根据不同的状态给用户提示(修改下载按钮的ui) ---------------*/

        //DownloadInfo(curState)
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mData);
        refreshProgressViewUi(downLoadInfo);
    }

    /**
     * 根据downLoadInfo里面的状态刷新下载按钮的对应的ui
     *
     * @param downLoadInfo
     */
    private void refreshProgressViewUi(DownLoadInfo downLoadInfo) {
        int curState = downLoadInfo.curState;
        /**

         状态(编程记录)  	|  给用户的提示(ui展现)
         未下载			|下载
         下载中			|显示进度条
         暂停下载		|继续下载
         等待下载		|等待中...
         下载失败 		|重试
         下载完成 		|安装
         已安装 			|打开
         */
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD://未下载
                mProgressView.setNote("下载");
                mProgressView.setIcon(R.drawable.ic_download);
                break;
            case DownLoadManager.STATE_DOWNLOADING://下载中
                mProgressView.setIcon(R.drawable.ic_pause);
                int progress = (int) (downLoadInfo.progress * 1.0 / downLoadInfo.max * 100 + .5f);
                mProgressView.setIsProgressEnable(true);
                mProgressView.setNote(progress + "%");//50  %

                mProgressView.setMax(downLoadInfo.max);
                mProgressView.setProgress(downLoadInfo.progress);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD://暂停下载
                mProgressView.setIcon(R.drawable.ic_resume);
                mProgressView.setNote("继续下载");
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD://等待下载
                mProgressView.setIcon(R.drawable.ic_pause);
                mProgressView.setNote("等待中...");

                break;
            case DownLoadManager.STATE_DOWNLOADFAILED://下载失败
                mProgressView.setIcon(R.drawable.ic_redownload);
                mProgressView.setNote("重试");

                break;
            case DownLoadManager.STATE_DOWNLOADED://下载完成
                mProgressView.setIcon(R.drawable.ic_install);
                mProgressView.setIsProgressEnable(false);
                mProgressView.setNote("安装");

                break;
            case DownLoadManager.STATE_INSTALLED://已安装
                mProgressView.setIcon(R.drawable.ic_install);
                mProgressView.setNote("打开");

                break;

            default:
                break;
        }
    }

    @OnClick(R.id.item_appinfo_progressview)
    public void clickProgressView(View v) {

        /*--------------- 3.根据不同的状态触发不同的操作 ---------------*/
        //DownloadInfo(curState)
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mData);
        int curState = downLoadInfo.curState;
        /**

         状态(编程记录  | 用户行为(触发操作)
         ----------------| -----------------
         未下载			| 去下载
         下载中			| 暂停下载
         暂停下载		| 断点继续下载
         等待下载		| 取消下载
         下载失败 		| 重试下载
         下载完成 		| 安装应用
         已安装 			| 打开应用

         */
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD://未下载
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADING://下载中
                pauseDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD://暂停下载
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD://等待下载
                cancelDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED://下载失败
                doDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADED://下载完成
                installApk(downLoadInfo);
                break;
            case DownLoadManager.STATE_INSTALLED://已安装
                openApk(downLoadInfo);
                break;

            default:
                break;
        }
    }

    /**
     * 打开apk
     *
     * @param downLoadInfo
     */
    private void openApk(DownLoadInfo downLoadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
    }

    /**
     * 安装apk
     *
     * @param downLoadInfo
     */
    private void installApk(DownLoadInfo downLoadInfo) {
        File apkFile = new File(downLoadInfo.savePath);
        CommonUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 开始下载,继续下载,重试下载
     *
     * @param downLoadInfo
     */
    private void doDownLoad(DownLoadInfo downLoadInfo) {
        //下载url
        //apk保存到哪里
       /* DownLoadInfo info = new DownLoadInfo();
        info.max = mItemInfoBean.size;
        info.name = mItemInfoBean.downloadUrl;

        String dir = FileUtils.getDir("apk");//sdcard/Android/data/包目录/apk
        String fileName = mItemInfoBean.packageName + ".apk";
        File saveFile = new File(dir, fileName);

        info.savePath = saveFile.getAbsolutePath();
        info.packageName = mItemInfoBean.packageName;*/

        DownLoadManager.getInstance().downLoad(downLoadInfo);
    }

    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    private void pauseDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().pauseDownLoad(downLoadInfo);
    }

    /**
     * 取消下载
     *
     * @param downLoadInfo
     */
    private void cancelDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().cancelDownLoad(downLoadInfo);
    }


    @Override
    public void onDownLoadInfoChanged(final DownLoadInfo downLoadInfo) {
        //过滤传递过来的downloadInfo信息
        if (!downLoadInfo.packageName.equals(mData.packageName)) {
            return;
        }
        PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                //刷新ui
                refreshProgressViewUi(downLoadInfo);
            }
        });
    }
}
