package com.moyoi.library_common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moyoi.library_base.utils.MetricsUtil.dp2px
import com.moyoi.library_common.R
import kotlin.math.min

/**
 * @Description 自定义悬停标签
 * ItemDecoration 允许应用给具体的View添加具体的图画或者layout的偏移，对于绘制View之间的分割线，视觉分组边界等等是非常有用
 * 调用 addItemDecoration()方法，RecyclerView就会调用该类的onDraw方法去绘制分隔线
 * @Author Lu
 * @Date 2021/6/11 13:36
 * @Version: 1.0
 */
class TopItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    /**
     * 获取悬停标签
     */
    lateinit var tagListener: (Int) -> String

    //设置每个ItemView上边预留高度
    private val mHeight = dp2px(30f)

    private val mPaint = Paint()
    //标签画笔
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRound = Rect()

    private val mContext: Context = context

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        mPaint.apply {
            color = ContextCompat.getColor(mContext, R.color.background)
        }

        textPaint.apply {
            color = ContextCompat.getColor(mContext, R.color.text_666)
            textSize = 40f
        }
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val bottom = childView.top.toFloat()
            val top = bottom - mHeight
            //绘制灰底矩形间隔
            c.drawRect(left, top, right, bottom, mPaint)
            //根据位置获取当前item的标签
            val tag = tagListener(parent.getChildAdapterPosition(childView))
            //绘制标签文本内容
            textPaint.getTextBounds(tag, 0, tag.length, mRound)
            c.drawText(
                tag,
                left + textPaint.textSize,
                bottom - mHeight / 2 + mRound.height() / 2,
                textPaint
            )
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val manager = parent.layoutManager as LinearLayoutManager
        //第一个可见item位置
        val index = manager.findFirstVisibleItemPosition()
        if (index != -1) {
            //获取指定位置item的View信息
            val childView = parent.findViewHolderForLayoutPosition(index)!!.itemView
            val top = parent.paddingTop.toFloat()
            var bottom = parent.paddingTop + mHeight.toFloat()
            val tag = tagListener(index)
            //悬浮置顶判断，其实也就是一直在绘制一个矩形加文本内容(上滑时取值bottom，下滑时取值childView.bottom.toFloat())
            bottom = min(childView.bottom.toFloat(), bottom)
            c.drawRect(0f, top, right, bottom, mPaint)
            textPaint.getTextBounds(tag, 0, tag.length, mRound)
            c.drawText(
                tag,
                left + textPaint.textSize,
                bottom - mHeight / 2 + mRound.height() / 2,
                textPaint
            )

        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //设置间隔高度
        outRect.top = mHeight.toInt()
    }
}