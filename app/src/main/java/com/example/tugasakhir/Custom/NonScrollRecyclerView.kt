package com.example.tugasakhir.Custom

import android.content.Context
import android.util.AttributeSet
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView

class NonScrollRecyclerView : FamiliarRecyclerView {

    constructor(context : Context) : super(context)

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)

    constructor(context : Context, attrs : AttributeSet, defStyle : Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, heightMeasureSpec_custom)
        layoutParams.height = measuredHeight
    }
}