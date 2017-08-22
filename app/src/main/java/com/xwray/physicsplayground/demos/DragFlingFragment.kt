package com.xwray.physicsplayground.demos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.physicsplayground.R
import com.xwray.physicsplayground.ui.fling
import kotlinx.android.synthetic.main.demo_drag_fling.*


class DragFlingFragment : Fragment(), DragTouchHelper.OnDragListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_drag_fling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DragTouchHelper().apply {
            setDraggable(ball)
            onDragListener = this@DragFlingFragment
            view.setOnTouchListener(this)
        }
    }

    override fun onDragEnd(view: View, xVelocity: Float, yVelocity: Float) {
        view.fling(xVelocity, yVelocity)
    }
}
