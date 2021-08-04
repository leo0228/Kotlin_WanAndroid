package com.moyoi.module_home.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_home.bean.BannerBean
import com.youth.banner.adapter.BannerAdapter

/**
 * @Description CustomBannerAdapter
 * @Author Lu
 * @Date 2021/6/7 18:21
 * @Version: 1.0
 */
class CustomBannerAdapter : BannerAdapter<BannerBean, CustomBannerAdapter.BannerViewHolder>(null) {

    fun setBannerList(dates: List<BannerBean>) {
        setDatas(dates)
        notifyDataSetChanged()
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: BannerBean,
        position: Int,
        size: Int
    ) {
        val context = holder.itemView.context
        Glide.with(context)
            .load(data.imagePath)
            .into(holder.itemView as ImageView)

        holder.itemView.setOnClickListener {
            ARouter.getInstance()
                .build(ARouterData.PATH_WEBVIEW)
                .withString(Keys.ID, data.id)
                .withString(Keys.TITLE, data.title)
                .withString(Keys.URL, data.url)
                .navigation(context)
        }

    }

    inner class BannerViewHolder(imageView : ImageView) : RecyclerView.ViewHolder(imageView)
}