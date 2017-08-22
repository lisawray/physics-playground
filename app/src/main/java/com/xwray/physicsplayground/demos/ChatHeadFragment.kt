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
import com.xwray.physicsplayground.ui.isNear
import com.xwray.physicsplayground.ui.spring
import kotlinx.android.synthetic.main.demo_chat_head.*


class ChatHeadFragment : Fragment(), DragTouchHelper.OnDragListener,
        DynamicAnimation.OnAnimationUpdateListener, DynamicAnimation.OnAnimationEndListener {

    lateinit var content: View
    var xAnimation: FlingAnimation? = null
    var yAnimation: FlingAnimation? = null
    val threshold: Int by lazy {
        resources.getDimensionPixelSize(R.dimen.nearness_threshold)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_chat_head, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        target.isEnabled = false

        content = view

        DragTouchHelper().apply {
            setDraggable(ball)
            onDragListener = this@ChatHeadFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        xAnimation = view.fling(xVelocity, FlingAnimation.TRANSLATION_X, this, this)
        yAnimation = view.fling(yVelocity, FlingAnimation.TRANSLATION_Y, this, this)
    }

    override fun onDragUpdate(view: View, xPosition: Float, yPosition: Float) {
        view.isNear(threshold, target).let { targetEnabled ->
            if (target.isEnabled != targetEnabled) {
                target.isEnabled = targetEnabled
            }
        }
    }

    override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>,
                                canceled: Boolean, value: Float, velocity: Float) {
        if (canceled) {
            // If the fling was cancelled, it bumped into the edge. Spring it back with any remaining
            // velocity.
            when (animation) {
                xAnimation -> ball.spring(velocity, SpringAnimation.TRANSLATION_X, ball.translationX)
                yAnimation -> ball.spring(velocity, SpringAnimation.TRANSLATION_Y, ball.translationY)
            }
        }
    }

    override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>, value: Float, velocity: Float) {
        if (!content.contains(ball)) {
            // The child is escaping from the parent -- don't let it go!
            animation.cancel()
        }
    }

    override fun onDestroyView() {
        xAnimation?.removeEndListener(this)
        yAnimation?.removeEndListener(this)
        xAnimation?.cancel()
        yAnimation?.cancel()
        super.onDestroyView()
    }
}
