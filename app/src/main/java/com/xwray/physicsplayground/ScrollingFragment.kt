package com.xwray.physicsplayground

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.demo_scrolling.*

class ScrollingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_scrolling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val slideInDistance = resources.getDimensionPixelSize(R.dimen.slide_in)
        val damping = SpringForce.DAMPING_RATIO_NO_BOUNCY
        val stiffness = SpringForce.STIFFNESS_VERY_LOW

        val fabTranslation = SpringAnimation(fab, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            setStartValue(slideInDistance.toFloat())
        }

        val fabScaleX = SpringAnimation(fab, SpringAnimation.SCALE_X, 1f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            setStartValue(0f)
        }

        val fabScaleY = SpringAnimation(fab, SpringAnimation.SCALE_Y, 1f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            setStartValue(0f)
        }

        val fabAlpha = SpringAnimation(fab, SpringAnimation.ALPHA, 1f).apply {
            spring.dampingRatio = damping
            spring.stiffness = SpringForce.STIFFNESS_HIGH
            setStartValue(0f)
        }

        val fabTranslationUpdateListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
            fabTranslation.animateToFinalPosition(value)
        }

        val fabAlphaScaleUpdateListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
            fabScaleX.animateToFinalPosition(value)
            fabScaleY.animateToFinalPosition(value)
            fabAlpha.animateToFinalPosition(value)
        }

        val animTranslation3 = SpringAnimation(text3, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            setStartValue(slideInDistance.toFloat())
            addUpdateListener(fabTranslationUpdateListener)
        }

        val animAlpha3 = SpringAnimation(text3, SpringAnimation.ALPHA, 1f).apply {
            spring.dampingRatio = damping
            spring.stiffness = stiffness
            setStartValue(0f)
            addUpdateListener(fabAlphaScaleUpdateListener)
        }

        val translationUpdateListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
            animTranslation3.animateToFinalPosition(value)
        }

        val alphaUpdateListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
            animAlpha3.animateToFinalPosition(value)
        }

        arrayOf(text1, text2, icons).forEach {
            SpringAnimation(it, SpringAnimation.TRANSLATION_Y, 0f).apply {
                spring.dampingRatio = damping
                spring.stiffness = stiffness
                setStartValue(slideInDistance.toFloat())
                setMaxValue(slideInDistance.toFloat())
                if (it == text1) addUpdateListener(translationUpdateListener)
                start()
            }

            SpringAnimation(it, SpringAnimation.ALPHA, 1f).apply {
                spring.dampingRatio = damping
                spring.stiffness = stiffness
                setStartValue(0f)
                setMinValue(0f)
                if (it == text1) addUpdateListener(alphaUpdateListener)
                start()
            }
        }
    }
}
