package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.fling
import com.xwray.physicsplayground.ui.spring
import kotlinx.android.synthetic.main.demo_spring_fling_drag.*

class DragSpringAndFlingFragment : Fragment(), DragTouchHelper.OnDragListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_spring_fling_drag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DragTouchHelper().apply {
            setDraggable(ball1)
            setDraggable(ball2)
            onDragListener = this@DragSpringAndFlingFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        when (view) {
            ball1 -> view.spring(xVelocity, yVelocity)
            ball2 -> view.fling(xVelocity, yVelocity)
        }
    }
}
