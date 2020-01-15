package com.codigoj.studyapp.Interface

interface ItemTouchHelperAdapter{

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemSwipe(position: Int)
}