package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.Button;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.manager.DownLoadInfo;
import com.itheima.googleplay_17.manager.DownLoadManager;
import com.itheima.googleplay_17.utils.CommonUtils;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.ProgressButton;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 14:16
 * 描述	     观察者-->接收消息的人
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 11:26:23 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailBottomHolder extends BaseHolder<ItemInfoBean> implements DownLoadManager.DownLoadInfoObserver {


    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;

    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;

    @Bind(R.id.app_detail_download_btn_download)
    ProgressButton mAppDetailDownloadBtnDownload;
    private ItemInfoBean mItemInfoBean;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_detail_bottom, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean itemInfoBean) {
        //保存数据到成员变量
        mItemInfoBean = itemInfoBean;
        
        /*--------------- 2.根据不同的状态给用户提示(修改下载按钮的ui) ---------------*/

        //DownloadInfo(curState)
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mItemInfoBean);
        refreshProgressButtonUi(downLoadInfo);
    }

    /**
     * 根据downLoadInfo里面的状态刷新下载按钮的对应的ui
     *
     * @param downLoadInfo
     */
    private void refreshProgressButtonUi(DownLoadInfo downLoadInfo) {
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
        mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD://未下载
                mAppDetailDownloadBtnDownload.setText("下载");
                break;
            case DownLoadManager.STATE_DOWNLOADING://下载中
                mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);

                int progress = (int) (downLoadInfo.progress * 1.0 / downLoadInfo.max * 100 + .5f);
                mAppDetailDownloadBtnDownload.setIsProgressEnable(true);
                mAppDetailDownloadBtnDownload.setText(progress + "%");//50  %

                mAppDetailDownloadBtnDownload.setMax(downLoadInfo.max);
                mAppDetailDownloadBtnDownload.setProgress(downLoadInfo.progress);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD://暂停下载
                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD://等待下载
                mAppDetailDownloadBtnDownload.setText("等待中...");

                break;
            case DownLoadManager.STATE_DOWNLOADFAILED://下载失败
                mAppDetailDownloadBtnDownload.setText("重试");

                break;
            case DownLoadManager.STATE_DOWNLOADED://下载完成
                mAppDetailDownloadBtnDownload.setIsProgressEnable(false);
                mAppDetailDownloadBtnDownload.setText("安装");

                break;
            case DownLoadManager.STATE_INSTALLED://已安装
                mAppDetailDownloadBtnDownload.setText("打开");

                break;

            default:
                break;
        }
    }

    @OnClick(R.id.app_detail_download_btn_download)
    public void clickBtnDownLoad(View view) {

        /*--------------- 3.根据不同的状态触发不同的操作 ---------------*/
        //DownloadInfo(curState)
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mItemInfoBean);
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



    /**
     * 接收到数据信息改变的通知
     *
     * @param downLoadInfo
     */
    @Override
    public void onDownLoadInfoChanged(final DownLoadInfo downLoadInfo) {//陌陌downLoadInfo
        //过滤传递过来的downloadInfo信息
        if(!downLoadInfo.packageName.equals(mItemInfoBean.packageName)){
            return;
        }
        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                //刷新ui
                refreshProgressButtonUi(downLoadInfo);
            }
        });
    }
}
