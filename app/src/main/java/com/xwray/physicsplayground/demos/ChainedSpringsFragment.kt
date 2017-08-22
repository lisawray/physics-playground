package com.xwray.physicsplayground

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.demos.DragTouchHelper
import kotlinx.android.synthetic.main.demo_chained_spring.*

class ChainedSpringsFragment : Fragment(), DragTouchHelper.OnDragListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_chained_spring, container, false)
    }

    val damping = SpringForce.DAMPING_RATIO_NO_BOUNCY
    val stiffness = SpringForce.STIFFNESS_VERY_LOW

    val ball3YAnim by lazy {
        SpringAnimation(ball3, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
        }
    }

    val ball3XAnim by lazy {
        SpringAnimation(ball3, SpringAnimation.TRANSLATION_X, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
        }
    }

    val ball3YListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
        ball3YAnim.animateToFinalPosition(value)
    }

    val ball3XListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
        ball3XAnim.animateToFinalPosition(value)
    }

    val ball2YAnim by lazy {
        SpringAnimation(ball2, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            addUpdateListener(ball3YListener)
        }
    }

    val ball2XAnim by lazy {
        SpringAnimation(ball2, SpringAnimation.TRANSLATION_X, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            addUpdateListener(ball3XListener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DragTouchHelper().apply {
            setDraggable(ball1)
            onDragListener = this@ChainedSpringsFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragUpdate(view: View, xPosition: Float, yPosition: Float) {
        super.onDragUpdate(view, xPosition, yPosition)
        ball2YAnim.animateToFinalPosition(view.translationY)
        ball2XAnim.animateToFinalPosition(view.translationX)
    }
}

