package com.haimei.vod.lib.oss;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

public class Demo {
    private String TAG = "lib_oss";
    LibOss libOss;
    private String ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";
    private String AK = "";
    private String AKS = "";
    private String BUCKET = "";

    private String LOCAL_FILE_DIR = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "oss/";
    private String LOCAL_FILE_NAME = "";
    private String OSS_FILE_DIR = "";
    private String OSS_FILE_NAME = "";


    protected void ossUploadImpl(Bundle savedInstanceState) {

        // TODO 初始化oss
        //OSSLog.enableLog(); //根据需要打开log
        //libOss = new LibOss(this);
        //libOss.initLibOss(ENDPOINT, AK, AKS, mCompleteCb, mProgressCb);

        // TODO 上传服务
        //libOss.asyncPutObj(LOCAL_FILE_DIR + LOCAL_FILE_NAME, BUCKET, OSS_FILE_DIR + OSS_FILE_NAME);

    }

    private OSSCompletedCallback mCompleteCb = new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
        @Override
        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
            Log.d(TAG, "UploadSuccess");
            OSSLog.logDebug(TAG, "ETag" + result.getETag());
            OSSLog.logDebug(TAG, "RequestId" + result.getRequestId());
            OSSLog.logDebug(TAG, "onSuccess: nObject: " + request.getObjectKey()
                    + "\nETag: " + result.getETag()
                    + "\nRequestId: " + result.getRequestId()
                    + "\nCallback: " + result.getServerCallbackReturnBody());
        }

        @Override
        public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
            String info = "";
            // 请求异常
            if (clientExcepion != null) {
                // 本地异常如网络异常等
                clientExcepion.printStackTrace();
                info = clientExcepion.toString();
            }
            if (serviceException != null) {
                // 服务异常
                Log.e(TAG, "ErrorCode" + serviceException.getErrorCode());
                Log.e(TAG, "RequestId" + serviceException.getRequestId());
                Log.e(TAG, "HostId" + serviceException.getHostId());
                Log.e(TAG, "RawMessage" + serviceException.getRawMessage());
                info = serviceException.toString();
            }
            Log.e(TAG, "onFailure: " + info);
        }
    };
    private OSSProgressCallback mProgressCb = new OSSProgressCallback<PutObjectRequest>() {
        @Override
        public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//            OSSLog.logDebug("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            int progress = (int) (100 * currentSize / totalSize);
            OSSLog.logDebug(TAG, "updateProgress: " + String.valueOf(progress) + "%");
        }
    };
}

