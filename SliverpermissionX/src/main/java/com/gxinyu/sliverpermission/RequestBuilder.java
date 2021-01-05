package com.gxinyu.sliverpermission;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 申请权限的参数
 * 后续需要再添加
 */

public class RequestBuilder {

    //两种模式
    public static final int NORMAL = 0x01;//一次性
    public static final int CHAIN = 0x02;//链状


    @IntDef({NORMAL, CHAIN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MODE {
    }

    public int mode = NORMAL;
    public RequestCallBack callBack;
    public String[] permissions;
    public Object target;

    public RequestBuilder(Object target) {
        this.target = target;
    }

    /**
     * 设置模式
     *
     * @param mode
     * @return
     */
    public RequestBuilder mode(@MODE int mode) {
        this.mode = mode;
        return this;
    }

    /**
     * 设置回调
     *
     * @param requestCallBack
     * @return
     */
    public RequestBuilder callback(RequestCallBack requestCallBack) {
        callBack = requestCallBack;
        return this;
    }

    /**
     * 设置申请的权限
     *
     * @param permissions
     * @return
     */
    public RequestBuilder permissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * 开始申请
     */
    public void apply() {
        Request request = new Request();
        request.apply(this);
    }

}
