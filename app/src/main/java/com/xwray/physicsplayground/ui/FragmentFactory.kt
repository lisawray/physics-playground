package com.xwray.physicsplayground.ui

import android.support.v4.app.Fragment
import com.xwray.physicsplayground.ChainedSpringsFragment
import com.xwray.physicsplayground.ScrollingFragment
import com.xwray.physicsplayground.demos.*

object FragmentFactory {
    fun create(demo: Demo): Fragment {
        return when (demo) {
            Demo.SIMPLE_SPRING -> SimpleSpringFragment()
            Demo.SPRING_TOUCH -> DragSpringFragment()
            Demo.MULTIPLE_DAMPING -> DampingExamplesFragment()
            Demo.MULTIPLE_STIFFNESS -> StiffnessExamplesFragment()
            Demo.FLING -> SimpleFlingFragment()
            Demo.MULTIPLE_FRICTION -> FrictionExamplesFragment()
            Demo.FLING_TOUCH -> DragFlingFragment()
            Demo.SPRING_AND_FLING_TOUCH -> DragSpringAndFlingFragment()
            Demo.CHAT_HEAD -> ChatHeadFragment()
            Demo.FLING_BOUNCE -> FlingBounceFragment()
            Demo.SCROLLING_FRAGMENT -> ScrollingFragment()
            Demo.CHAINED_SPRINGS -> ChainedSpringsFragment()
        }
    }
}
