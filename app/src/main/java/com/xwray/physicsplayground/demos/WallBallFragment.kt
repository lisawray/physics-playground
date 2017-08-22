package com.xwray.physicsplayground.demos

import android.graphics.Rect
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.SpringAnimation
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.fling
import com.xwray.physicsplayground.ui.spring
import kotlinx.android.synthetic.main.demo_drag_fling.*

/**
 * If you fling the ball at the edge of the device, it springs it back to 0f.
 */
class WallBallFragment : Fragment(), DragTouchHelper.OnDragListener,
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
            onDragListener = this@WallBallFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        xFlingAnimation = view.fling(xVelocity, FlingAnimation.TRANSLATION_X, this, this)
        yFlingAnimation = view.fling(yVelocity, FlingAnimation.TRANSLATION_Y, this, this)
    }

    override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>, canceled: Boolean, value: Float, velocity: Float) {
        // If the fling was cancelled, it bumped into the edge. Spring it back with any remaining
        // velocity.
        if (canceled) {
            when (animation) {
                xFlingAnimation -> ball.spring(velocity, SpringAnimation.TRANSLATION_X)
                yFlingAnimation -> ball.spring(velocity, SpringAnimation.TRANSLATION_Y)
            }
        } else {
            // The user didn't fling far enough to rest on the edge; spring it there for them.
            // TODO
        }
    }

    override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>, value: Float, velocity: Float) {
        val parentRect = Rect();
        content.getHitRect(parentRect)

        val childRect = Rect()
        ball.getHitRect(childRect)

        if (!parentRect.contains(childRect)) {
            // The child is escaping from the parent -- don't let it go!
            animation.cancel()
        }
    }
}
