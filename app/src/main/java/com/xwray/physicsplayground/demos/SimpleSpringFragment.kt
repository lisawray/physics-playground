package com.xwray.physicsplayground.demos

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.changeColor
import com.xwray.physicsplayground.ui.getColorArray
import kotlinx.android.synthetic.main.demo_detail.*


class SimpleSpringFragment : Fragment() {

    private val springDampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    private val springStiffness = SpringForce.STIFFNESS_LOW

    private val springForce: SpringForce by lazy {
        SpringForce().apply {
            dampingRatio = springDampingRatio
            stiffness = springStiffness
            finalPosition = 0f
        }
    }

    private val colors: IntArray by lazy {
        getColorArray(R.array.brights)
    }

    private var currentColorIndex = 0

    private val changeColorListener = DynamicAnimation.OnAnimationEndListener { animation, canceled, value, velocity ->
        val oldColor = colors[currentColorIndex]
        currentColorIndex = (currentColorIndex + 1) % colors.size
        val newColor = colors[currentColorIndex]
        colorChangeAnimation = ball.changeColor(oldColor, newColor, springListener)
    }

    private val springListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            spring()
        }
    }

    private var springAnimation: SpringAnimation? = null
    private var colorChangeAnimation: ValueAnimator? = null

    private fun spring() {
        springAnimation = SpringAnimation(ball, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring = springForce
            setStartVelocity(20000f)
            addEndListener(changeColorListener)
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spring()
    }

    override fun onDestroyView() {
        springAnimation?.apply {
            removeEndListener(changeColorListener)
            cancel()
        }
        colorChangeAnimation?.apply {
            removeAllListeners()
            cancel()
        }
        super.onDestroyView()
    }
}
