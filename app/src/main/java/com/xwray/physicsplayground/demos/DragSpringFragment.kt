package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.spring
import kotlinx.android.synthetic.main.demo_spring_drag.*


class DragSpringFragment : Fragment(), DragTouchHelper.OnDragListener {

    var animations = Pair<SpringAnimation?, SpringAnimation?>(null, null)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_spring_drag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DragTouchHelper().apply {
            setDraggable(ball)
            onDragListener = this@DragSpringFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        animations = view.spring(xVelocity, yVelocity)
    }

    override fun onDestroyView() {
        animations.first?.cancel()
        animations.second?.cancel()
        super.onDestroyView()
    }
}
