package com.moyoi.library_base.loadsir;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.moyoi.library_base.R;
import com.moyoi.library_base.data.ARouterData;

/**
 * Description:TODO
 * Create Time:2017/9/2 16:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class NoLoginCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_nologin;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation();
        return false;
    }

}
