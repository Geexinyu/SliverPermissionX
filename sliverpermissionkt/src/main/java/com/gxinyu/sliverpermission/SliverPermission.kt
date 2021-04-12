package com.gxinyu.sliverpermission

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * 校验生命周期
 * 版本校验
 * 还有其他设置
 */
class SliverPermission private constructor() {

    private object SingletonHolder {
        val holder = SliverPermission()
    }

    //防止反序列化重新创建对象
    private fun readResolve(): Any {
        return SliverPermission.INSTANCE
    }

    fun getRequestRetriever(): RequestRetriever {
        return RequestRetriever()
    }

    companion object {
        val INSTANCE = SingletonHolder.holder

        @JvmStatic
        fun with(activity: Activity): RequestBuilder {
            return RequestBuilder(check(activity))
        }

        @JvmStatic
        fun with(activity: FragmentActivity): RequestBuilder {
            return RequestBuilder(check(activity))
        }

        @JvmStatic
        fun with(fragment: android.app.Fragment): RequestBuilder {
            return RequestBuilder(check(fragment.activity))
        }

        @JvmStatic
        fun with(fragment: Fragment): RequestBuilder {
            return RequestBuilder(check(fragment.activity))
        }

        private fun check(context: Context?): Context {
            check(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            requireNotNull(context)//要求不能为空
            require(context is Activity) { "You context must be activity for apply permission!" }
            require(!context.isFinishing) { "You cannot start a load for a finishing activity" }
            require(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.isDestroyed))
            { "You cannot start a load for a destroyed activity" }
            return context
        }
    }
}