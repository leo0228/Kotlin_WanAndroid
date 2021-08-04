package com.moyoi.library_common.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min


/**
 * @Description GameBox
 * @Author Lu
 * @Date 2021/6/24 11:57
 * @Version: 1.0
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val mPaintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintBorder = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mBorderColor = Color.BLACK

    private val mMatrix = Matrix()

    init {
        mPaintBitmap.isAntiAlias = true
        mPaintBitmap.isFilterBitmap = true
        mPaintBitmap.isDither = true

        mPaintBorder.style = Paint.Style.STROKE
        mPaintBorder.color = mBorderColor
        mPaintBorder.strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas?) {
        val sourceBitmap = (drawable as BitmapDrawable).bitmap
        if (sourceBitmap != null) {
            val viewMinSize = min(width, height)
            val dstWidth = viewMinSize.toFloat()
            val dstHeight = viewMinSize.toFloat()

            val shader = BitmapShader(sourceBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mMatrix.setScale(
                dstWidth / sourceBitmap.width,
                dstHeight / sourceBitmap.height
            )
            shader.setLocalMatrix(mMatrix)
            mPaintBitmap.shader = shader

            val radius: Float = viewMinSize / 2.0f
            canvas?.drawCircle(radius, radius , radius, mPaintBorder)
            canvas?.drawCircle(radius, radius , radius, mPaintBitmap)
        } else {
            super.onDraw(canvas)
        }
    }

}
