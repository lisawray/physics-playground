package com.xwray.physicsplayground.demos

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.changeColor
import com.xwray.physicsplayground.ui.getColorArray
import kotlinx.android.synthetic.main.demo_detail.*


open class SimpleFlingFragment : Fragment() {

    protected val colors: IntArray by lazy {
        getColorArray(R.array.brights)
    }

    private var currentColorIndex = 0

    inner class ChangeColorListener(val flingListener: Animator.AnimatorListener) :
            DynamicAnimation.OnAnimationEndListener {
        override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>,
                                    canceled: Boolean, value: Float, velocity: Float) {
            val oldColor = colors[currentColorIndex]
            currentColorIndex = (currentColorIndex + 1) % colors.size
            val newColor = colors[currentColorIndex]
            colorChangeAnimation = ball.changeColor(oldColor, newColor, flingListener)
        }
    }

    private val flingListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            fling()
        }
    }

    private val flingBackListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            flingBack()
        }
    }



    private var flingAnimation: FlingAnimation? = null
    private var colorChangeAnimation: ValueAnimator? = null
    private var colorListener: ChangeColorListener? = null



    private fun fling() {
        flingAnimation = FlingAnimation(ball, DynamicAnimation.TRANSLATION_Y).apply {
            setStartVelocity(5000f)
            friction = 1.5f
            colorListener = ChangeColorListener(flingBackListener)
            addEndListener(colorListener)
            start()
        }
    }

    private fun flingBack() {
        flingAnimation = FlingAnimation(ball, DynamicAnimation.TRANSLATION_Y).apply {
            setStartVelocity(-5000f)
            friction = 1.5f
            colorListener = ChangeColorListener(flingListener)
            addEndListener(colorListener)
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.postDelayed({ fling() }, 1000)
        //fling()
    }

    override fun onDestroy() {
        flingAnimation?.apply {
            removeEndListener(colorListener)
            cancel()
        }
        colorChangeAnimation?.apply {
            removeAllListeners()
            cancel()
        }
        super.onDestroy()
    }
}
