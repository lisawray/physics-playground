package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import kotlinx.android.synthetic.main.multiple_damping_fragment.*


class StiffnessExamplesFragment : Fragment() {

    val animations = mutableListOf<SpringAnimation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.multiple_damping_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.postDelayed({
            with(animations) {
                add(ball1.spring(SpringForce.STIFFNESS_VERY_LOW))
                add(ball2.spring(SpringForce.STIFFNESS_LOW))
                add(ball3.spring(SpringForce.STIFFNESS_MEDIUM))
                add(ball4.spring(SpringForce.STIFFNESS_HIGH))
            }
        }, 2000)
    }

    private fun View.spring(springStiffness: Float): SpringAnimation {
        return SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring.apply {
                dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
                stiffness = springStiffness
            }
            setStartVelocity(20000f)
            start()
        }
    }

    override fun onDestroyView() {
        animations.filterNotNull().forEach {
            it.cancel()
        }
        super.onDestroyView()
    }
}
