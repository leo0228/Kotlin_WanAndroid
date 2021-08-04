package com.moyoi.library_common.adapter

import android.content.Context
import android.net.Uri
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_base.utils.showToast
import com.moyoi.library_common.R
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.bean.CollectDataBus
import com.moyoi.library_common.constant.Constants
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.ItemNormalArticleBinding
import com.moyoi.library_common.databinding.ItemProjectArticleBinding
import com.moyoi.library_common.widget.CommentPopWindow
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.library_common.viewmodel.CollectionModel

/**
 * @Description 文章列表适配器
 * @Author Lu
 * @Date 2021/6/9 13:57
 * @Version: 1.0
 */
class ArticleListAdapter : BaseAdapter<ArticleBean>() {

    private val mCollectionModel: CollectionModel<List<ArticleBean>> by lazy {
        CollectionModel()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).realSuperChapterId == "293") 1 else 0
    }

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        if (viewType == 1)
            return ItemProjectArticleBinding::inflate
        return ItemNormalArticleBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: ArticleBean) {
        val context = holder.binding.root.context

        //正常文章列表
        if (getItemViewType(position) == 0) {
            val binding = holder.binding as ItemNormalArticleBinding

            binding.root.safeClick {
                //收藏界面中collect为空并带有originId
                ARouter.getInstance()
                    .build(ARouterData.PATH_WEBVIEW)
                    .withString(Keys.ID, item.id)
                    .withString(Keys.TITLE, item.title)
                    .withString(Keys.ORIGINID, item.originId)
                    .withString(Keys.URL, item.link)
                    .withBoolean(Keys.COLLECT, item.collect ?: true)
                    .navigation(context)

                SimpleLiveDataBus.getInstance().with<CollectDataBus>(Constants.USER_COLLECT_UPDATE)
                    .observe(context as FragmentActivity, {
                        if (it.id == item.id) {
                            item.collect = it.collect
                            notifyDataSetChanged()
                            if (item.originId != "-1" && !it.collect) {
                                //收藏界面
                                removeData(position)
                            }
                        }
                    })
            }

            binding.articleNew.visibility = if (item.fresh) View.VISIBLE else View.GONE

            if (item.author.isNotBlank()) {
                binding.author.visibility = View.VISIBLE
                binding.shareUser.visibility = View.GONE
                binding.author.text = context.getString(R.string.author, item.author)
                binding.author.safeClick {
                    ARouter.getInstance()
                        .build(ARouterData.PATH_SEARCH)
                        .withString(Keys.KEY, item.author)
                        .withString(Keys.TYPE, "author")
                        .navigation()
                }
            }

            if (item.shareUser.isNotBlank()) {
                binding.author.visibility = View.GONE
                binding.shareUser.visibility = View.VISIBLE
                binding.shareUser.text = context.getString(R.string.share_user, item.shareUser)
                binding.shareUser.safeClick {
                    ARouter.getInstance()
                        .build(ARouterData.PATH_USER_SHARE)
                        .withString(Keys.TITLE, item.shareUser)
                        .withString(Keys.ID, item.userId)
                        .navigation()
                }
            }

            if (item.tags.isNullOrEmpty()) {
                binding.authorTag.visibility = View.GONE
            } else {
                binding.authorTag.visibility = View.VISIBLE
                binding.authorTag.text = item.tags[0].name
                binding.authorTag.tag = item.tags[0].url
                binding.authorTag.safeClick {
                    urlToSystemList(context, binding.authorTag.tag.toString())
                }
            }

            binding.uploadTime.text = item.niceDate

            if (item.envelopePic.isNotBlank()) {
                Glide.with(context).load(item.envelopePic).into(binding.articleImg)
                binding.articleImg.visibility = View.VISIBLE
            } else {
                binding.articleImg.visibility = View.GONE
            }

            binding.articleText.text = item.title
            if (item.desc.isNotBlank()) {
                binding.articleText.isSingleLine = true
                binding.articleDesc.text = Html.fromHtml(item.desc)
                binding.articleDesc.visibility = View.VISIBLE
            } else {
                binding.articleText.isSingleLine = false
                binding.articleDesc.visibility = View.GONE
            }

            binding.articleTop.visibility = if (item.top) View.VISIBLE else View.GONE

            if (item.superChapterName.isBlank()) {
                binding.articleChapter.text = context.getString(R.string.category, item.chapterName)
            } else {
                binding.articleChapter.text = context.getString(
                    R.string.category,
                    formatChapterName(item.superChapterName, item.chapterName)
                )
            }

            binding.articleChapter.safeClick {
                chapterIdToSystemList(context, item.realSuperChapterId, item.chapterId)
            }

            if (item.realSuperChapterId == "439") { //问答
                binding.comment.visibility = View.VISIBLE
                binding.comment.safeClick {
                    val pop = CommentPopWindow(context)
                    pop.showPop(item.id)
                }
                binding.flLike.visibility = View.VISIBLE
                binding.likeNum.text = item.zan
            } else {
                binding.comment.visibility = View.GONE
                binding.flLike.visibility = View.GONE
            }

            if (item.collect != null) {
                if (item.collect!!) {
                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_red_24)
                } else {
                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            } else {
                binding.collect.setImageResource(R.drawable.ic_baseline_favorite_red_24)
            }

            binding.collect.safeClick {
                if (item.collect != null) {
                    //文章展示界面取消收藏或添加收藏
                    if (item.collect!!) {
                        mCollectionModel.removeCollectArticle(item.id)
                        mCollectionModel.removeCollectResult.observe(context as FragmentActivity, { result ->
                                if (result) {
                                    item.collect = false
                                    R.string.collect_cancel_success.showToast()
                                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                                } else {
                                    R.string.collect_cancel_failed.showToast()
                                }
                            })
                    } else {
                        mCollectionModel.addCollectArticle(item.id)
                        mCollectionModel.addCollectResult.observe(context as FragmentActivity, { result ->
                                if (result) {
                                    item.collect = true
                                    R.string.collect_success.showToast()
                                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_red_24)
                                } else {
                                    R.string.collect_failed.showToast()
                                }
                            })
                    }
                } else {
                    //收藏界面取消收藏
                    mCollectionModel.removeCollectPage(item.id, item.originId)
                    mCollectionModel.removeCollectResult.observe(context as FragmentActivity, { result ->
                            if (result) {
                                item.collect = false
                                removeData(position)
                                R.string.collect_cancel_success.showToast()
                                binding.collect.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            } else {
                                R.string.collect_cancel_failed.showToast()
                            }
                        })
                }
            }
        }

        //项目中文章列表
        if (getItemViewType(position) == 1) {
            val binding = holder.binding as ItemProjectArticleBinding

            binding.root.safeClick {
                ARouter.getInstance()
                    .build(ARouterData.PATH_WEBVIEW)
                    .withString(Keys.ID, item.id)
                    .withString(Keys.TITLE, item.title)
                    .withString(Keys.URL, item.link)
                    .withBoolean(Keys.COLLECT, item.collect!!)
                    .navigation(context)

                SimpleLiveDataBus.getInstance().with<CollectDataBus>(Constants.USER_COLLECT_UPDATE)
                    .observe(context as FragmentActivity, {
                        if (it.id == item.id) {
                            item.collect = it.collect
                            notifyDataSetChanged()
                        }
                    })
            }

            binding.author.text = context.getString(R.string.author, item.author)
            binding.author.safeClick {
                ARouter.getInstance()
                    .build(ARouterData.PATH_SEARCH)
                    .withString(Keys.KEY, item.author)
                    .withString(Keys.TYPE, "author")
                    .navigation()
            }

            Glide.with(context).load(item.envelopePic).into(binding.articleImg)

            binding.articleText.text = item.title

            binding.articleDesc.text = Html.fromHtml(item.desc)

            binding.uploadTime.text = item.niceDate

            if (item.collect != null) {
                if (item.collect!!) {
                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_red_24)
                } else {
                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }

            binding.collect.safeClick {
                if (item.collect != null) {
                    if (item.collect!!) {
                        mCollectionModel.removeCollectArticle(item.id)
                        mCollectionModel.removeCollectResult.observe(
                            context as FragmentActivity,
                            { result ->
                                if (result) {
                                    item.collect = false
                                    R.string.collect_cancel_success.showToast()
                                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                                } else {
                                    R.string.collect_cancel_failed.showToast()
                                }
                            })
                    } else {
                        mCollectionModel.addCollectArticle(item.id)
                        mCollectionModel.addCollectResult.observe(
                            context as FragmentActivity,
                            { result ->
                                if (result) {
                                    item.collect = true
                                    R.string.collect_success.showToast()
                                    binding.collect.setImageResource(R.drawable.ic_baseline_favorite_red_24)
                                } else {
                                    R.string.collect_failed.showToast()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun formatChapterName(vararg names: String): String {
        val format = StringBuilder()
        for (name in names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.isNotEmpty()) {
                    format.append("/")
                }
                format.append(name)
            }
        }
        return format.toString()
    }

    private fun urlToSystemList(context: Context, url: String) {
        try {
            val uri = Uri.parse("https://www.wanandroid.com/$url")
            var chapterId = uri.getQueryParameter("cid")
            if (chapterId.isNullOrBlank()) {
                val paths = uri.pathSegments
                if (paths != null && paths.size >= 3) {
                    chapterId = paths[2]
                }
            }
            if (chapterId != null) {
                DataHelper.getTreeList().observe(context as FragmentActivity, { list ->
                    list.forEach { treeBean ->
                        treeBean.children?.forEachIndexed { index, childrenTreeBean ->
                            if (childrenTreeBean.id == chapterId) {
                                treeBean.childrenSelectPosition = index
                                ARouter.getInstance()
                                    .build(ARouterData.PATH_SYSTEM)
                                    .withParcelable(Keys.BEAN, treeBean)
                                    .navigation()
                                return@forEach
                            }
                        }
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun chapterIdToSystemList(
        context: Context,
        realSuperChapterId: String,
        chapterId: String
    ) {
        DataHelper.getTreeList().observe(context as FragmentActivity, { list ->
            list.forEach { treeBean ->
                if (treeBean.id == realSuperChapterId) {
                    treeBean.children?.forEachIndexed { index, childrenTreeBean ->
                        if (childrenTreeBean.id == chapterId) {
                            treeBean.childrenSelectPosition = index
                        }
                    }
                    ARouter.getInstance()
                        .build(ARouterData.PATH_SYSTEM)
                        .withParcelable(Keys.BEAN, treeBean)
                        .navigation()
                    return@forEach
                }
            }
        })
    }


}
