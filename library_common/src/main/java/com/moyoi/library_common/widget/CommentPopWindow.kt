package com.moyoi.library_common.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.moyoi.library_common.R
import com.moyoi.library_common.adapter.CommentAdapter
import com.moyoi.library_common.databinding.LayoutCommentPopBinding
import com.moyoi.library_common.viewmodel.CommentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Description CommentPopWindow
 * @Author Lu
 * @Date 2021/7/9 13:42
 * @Version: 1.0
 */
class CommentPopWindow @JvmOverloads constructor(
    val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    animStyle: Int = R.style.PopWindowAnimation,
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
) : PopupWindow(context, attrs, defStyleAttr), CoroutineScope by MainScope() {

    private val mCommentAdapter = CommentAdapter()
    private val model = CommentViewModel()
    private var mRootView: LayoutCommentPopBinding =
        LayoutCommentPopBinding.inflate(LayoutInflater.from(context))

    init {
        contentView = mRootView.root
        setWidth(width)
        setHeight(height)
        animationStyle = animStyle
        isFocusable = true
        isOutsideTouchable = true
        val dw = ColorDrawable(ContextCompat.getColor(context, R.color.background))
        setBackgroundDrawable(dw)
        mRootView.recyclerView.adapter = mCommentAdapter
        mRootView.close.setOnClickListener {
            dismiss()
        }
    }

    fun showPop(id: String) {
        model.getWendaComments(id)
        model.dataResult.observe(context as FragmentActivity, { result ->
            if (result != null) {
                mCommentAdapter.setNewData(result)
                mRootView.answerNum.text = context.getString(R.string.answer, result.size)
                showAtLocation(contentView.rootView, Gravity.BOTTOM, 0, 0);
            }
        })

        //相对某个控件的位置（正左下方），无偏移
//        showAsDropDown(view)
        //相对某个控件的位置，有偏移
//        showAsDropDown(tv, 0, 0);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    override fun dismiss() {
        super.dismiss()
        cancel()
    }
}