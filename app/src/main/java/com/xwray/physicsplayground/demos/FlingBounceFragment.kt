package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.SpringAnimation
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.contains
import com.xwray.physicsplayground.ui.fling
import kotlinx.android.synthetic.main.demo_drag_fling.*

/**
 * If you fling the ball at the edge of the device, it flings it back with the remaining velocity,
 * as if it's bouncing off the edge.
 */
class FlingBounceFragment : Fragment(), DragTouchHelper.OnDragListener,
        DynamicAnimation.OnAnimationUpdateListener, DynamicAnimation.OnAnimationEndListener {

    lateinit var content: View
    var xFlingAnimation: FlingAnimation? = null
    var yFlingAnimation: FlingAnimation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_drag_fling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        content = view

        DragTouchHelper().apply {
            setDraggable(ball)
            onDragListener = this@FlingBounceFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        xFlingAnimation = view.fling(xVelocity, FlingAnimation.TRANSLATION_X, this, this)
        yFlingAnimation = view.fling(yVelocity, FlingAnimation.TRANSLATION_Y, this, this)
    }

    override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>, canceled: Boolean, value: Float, velocity: Float) {
        // If the fling was cancelled, it bumped into the edge. Have it continue the fling in the
        // opposite direction, as if it bounced off.
        if (canceled) {
            when (animation) {
                xFlingAnimation -> {
                    val multiplier = if (ball.isOnHorizontalEdge()) -1 else 1
                    ball.fling(multiplier * velocity, SpringAnimation.TRANSLATION_X)
                }
                yFlingAnimation -> {
                    val multiplier = if (ball.isOnVerticalEdge()) -1 else 1
                    ball.fling(multiplier * velocity, SpringAnimation.TRANSLATION_Y)
                }
            }
        }
    }

    fun View.isOnHorizontalEdge(): Boolean {
        return left + translationX < content.left || right + translationX > content.right
    }

    fun View.isOnVerticalEdge(): Boolean {
        return top + translationY < content.top || bottom + translationY > content.bottom
    }

    override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>, value: Float, velocity: Float) {
        if (!content.contains(ball)) {
            // The child is escaping from the parent -- don't let it go!
            animation.cancel()
        }
    }
}
