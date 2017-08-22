package com.xwray.physicsplayground.demos

import android.graphics.Rect
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View

class DragTouchHelper : View.OnTouchListener {
    private val velocityTracker: VelocityTracker = VelocityTracker.obtain()
    private var downX = 0f
    private var downY = 0f
    private val childHitRect = Rect()
    private var selectedChild: View? = null
    private val draggableChildren = ArrayList<View>()
    var onDragListener: OnDragListener? = null

    fun setDraggable(child: View) {
        draggableChildren.add(child)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                draggableChildren.forEach {
                    if (handleDown(it, event)) return true
                }
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                selectedChild?.apply {
                    translationX += event.x - downX
                    translationY += event.y - downY
                }
                downX = event.x
                downY = event.y
                selectedChild?.let {
                    onDragListener?.onDragUpdate(it, event.x, event.y)
                }
                velocityTracker.addMovement(event)
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                velocityTracker.apply {
                    computeCurrentVelocity(1000)
                    selectedChild?.let {
                        onDragListener?.onDragEnd(it, xVelocity, yVelocity)
                    }
                    velocityTracker.clear()
                }
                selectedChild = null
                return true
            }
        }
        return false
    }

    private fun handleDown(child: View, event: MotionEvent): Boolean {
        child.getHitRect(childHitRect)
        if (childHitRect.contains(event.x.toInt(), event.y.toInt())) {
            downX = event.x
            downY = event.y
            velocityTracker.addMovement(event)
            selectedChild = child
            onDragListener?.onDragStart(child, event.x, event.y)
            return true
        }
        return false
    }

    interface OnDragListener {
        fun onDragStart(view: View, xPosition: Float, yPosition: Float) {}
        fun onDragUpdate(view: View, xPosition: Float, yPosition: Float) {}
        fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {}
    }

}
