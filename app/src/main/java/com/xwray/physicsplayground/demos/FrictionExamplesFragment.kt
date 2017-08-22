package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import kotlinx.android.synthetic.main.multiple_damping_fragment.*


class FrictionExamplesFragment : Fragment() {

    val animations = mutableListOf<FlingAnimation>()
    var endListener : DynamicAnimation.OnAnimationEndListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.multiple_friction_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flingAll()
    }

    private fun flingAll(reverse: Boolean = false) {
        with(animations) {
            add(ball1.fling(4f, reverse))
            add(ball2.fling(2f, reverse))
            add(ball3.fling(1f, reverse))
            add(ball4.fling(.5f, reverse))
        }
    }

    private fun View.fling(friction: Float, reverse: Boolean = false): FlingAnimation {
        return FlingAnimation(this, DynamicAnimation.TRANSLATION_Y).apply {
            setStartVelocity(2500f * if (reverse) -1 else 1)
            this.friction = friction

            // When the slowest fling finishes, wait a second and fling them all back.
            if (this@fling == ball4) {
                endListener = DynamicAnimation.OnAnimationEndListener { animation, canceled, value, velocity ->
                    postDelayed({
                        flingAll(!reverse)
                    }, 1000)
                }
                addEndListener(endListener)
            }
            start()
        }
    }

    override fun onDestroyView() {
        animations.filterNotNull().forEach {
            it.removeEndListener(endListener)
            it.cancel()
        }
        super.onDestroyView()
    }
}
