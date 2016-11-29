package com.weavey.umeng;


import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zqu.weavey.ppcount.R;


import android.content.Context;
import android.widget.Toast;

/**
 * @author Weavey
 * @date 2015-11-24
 * @explain
 */
public class SharedUtils {

    private static Context mContext;
    private static SharedUtils shared = null;
    private static UMSocialService mController;
    private String mTargetURL = "http://www.baidu.com/";
    private String mTitle = "时记--记事App";
    private String mContent = "时记是一款简单易用的记事App，快来下载吧！";
    private int mImage = R.drawable.logo;

    private final static String wxAppID = "wx7884df87f419769e";
    private final static String wxAppSecret =
            "72efbbdf02fb6080b664aa97cb12be71";

    public static SharedUtils getInstance(Context context) {

        mContext = context;

        if (shared == null) {

            synchronized (SharedUtils.class) {

                if (shared == null) {

                    shared = new SharedUtils();
                    initSharedPlatForm();
                }
            }
        }

        return shared;
    }

    private static void initSharedPlatForm() {

        mController = UMServiceFactory
                .getUMSocialService("com.umeng.share");
        mController.getConfig().closeToast(); //关闭回调显示的toast
        com.umeng.socialize.utils.Log.LOG = false;
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mContext, wxAppID, wxAppSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mContext, wxAppID,
                wxAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();


    }

    public void sharedWX() {

        // 设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        // 设置分享文字
        weixinContent.setShareContent(mContent);
        // 设置title
        weixinContent.setTitle(mTitle);
        // 设置分享内容跳转URL
        weixinContent.setTargetUrl(mTargetURL);
        // 设置分享图片
        weixinContent.setShareImage(new UMImage(mContext, mImage));
        mController.setShareMedia(weixinContent);
        goShared(SHARE_MEDIA.WEIXIN);
    }

    public void sharedWXCircle() {

        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(mContent);
        // 设置朋友圈title
        circleMedia.setTitle("时记App");
        circleMedia.setShareImage(new UMImage(mContext, mImage));
        circleMedia.setTargetUrl(mTargetURL);
        mController.setShareMedia(circleMedia);

        goShared(SHARE_MEDIA.WEIXIN_CIRCLE);

    }


    private void goShared(SHARE_MEDIA tag) {

        mController.postShare(mContext, tag, new SocializeListeners
                .SnsPostListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode,
                                   SocializeEntity entity) {

                if (eCode == 200) {
                    // Toast.makeText(mContext, "分享成功.",
                    // Toast.LENGTH_SHORT).show();
                } else {
                    String eMsg = "";
                    if (eCode == -101) {
                        eMsg = "没有授权";
                    }
                    Toast.makeText(mContext, "分享失败[" + eCode + "] " + eMsg,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStart() {

                // Toast.makeText(mContext, "正在分享···",
                // Toast.LENGTH_SHORT).show();
            }

        });

    }

}
