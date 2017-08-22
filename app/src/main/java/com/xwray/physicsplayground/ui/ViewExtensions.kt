package com.xwray.physicsplayground.ui

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.graphics.Rect
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.annotation.ColorRes
import android.view.View

fun View.changeColor(@ColorRes oldColor: Int, @ColorRes newColor: Int, endListener: Animator.AnimatorListener): ValueAnimator {

    return ValueAnimator.ofObject(ArgbEvaluator(), oldColor, newColor).apply {
        duration = 250
        addUpdateListener { animator ->
            with(animator.animatedValue as Int) {
                background.mutate().setColorFilter(this, PorterDuff.Mode.SRC_IN)
            }
        }
        addListener(endListener)
        start()
    }
}

fun View.fling(xVelocity: Float, yVelocity: Float): Pair<FlingAnimation?, FlingAnimation?> {
    return Pair(fling(xVelocity, DynamicAnimation.TRANSLATION_X),
            fling(yVelocity, DynamicAnimation.TRANSLATION_Y))
}

fun View.fling(xVelocity: Float,
               yVelocity: Float,
               updateListener: DynamicAnimation.OnAnimationUpdateListener,
               endListener: DynamicAnimation.OnAnimationEndListener): Pair<FlingAnimation?, FlingAnimation?> {
    return Pair(fling(xVelocity, DynamicAnimation.TRANSLATION_X, updateListener, endListener),
            fling(yVelocity, DynamicAnimation.TRANSLATION_Y, updateListener, endListener))
}

fun View.fling(velocity: Float, value: DynamicAnimation.ViewProperty,
               updateListener: DynamicAnimation.OnAnimationUpdateListener? = null,
               endListener: DynamicAnimation.OnAnimationEndListener? = null): FlingAnimation? {
    return if (velocity != 0f) {
        FlingAnimation(this, value).apply {
            updateListener?.let { addUpdateListener(it) }
            endListener?.let { addEndListener(it) }
            setStartVelocity(velocity)
            start()
        }
    } else null
}

fun View.spring(xVelocity: Float, yVelocity: Float): Pair<SpringAnimation?, SpringAnimation?> {
    var first: SpringAnimation? = null
    var second: SpringAnimation? = null

    if (xVelocity != 0f) {
        first = SpringAnimation(this, SpringAnimation.TRANSLATION_X, 0f).apply {
            setStartVelocity(xVelocity)
            start()
        }
    }
    if (yVelocity != 0f) {
        second = SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0f).apply {
            setStartVelocity(yVelocity)
            start()
        }
    }
    return Pair(first, second)
}

private fun View.spring(damping: Float) {
    SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0f).apply {
        spring.apply {
            dampingRatio = damping
            stiffness = SpringForce.STIFFNESS_LOW
        }
        setStartVelocity(20000f)
        start()
    }
}

fun View.spring(velocity: Float, value: DynamicAnimation.ViewProperty, endValue: Float = 0f): SpringAnimation {
    return SpringAnimation(this, value, endValue).apply {
        setStartVelocity(velocity)
        start()
    }
}

fun View.isNear(threshold: Int, view: View): Boolean {
    fun isBetween(value: Float, boundary1: Float, boundary2: Float): Boolean {
        return (value > boundary1 && value < boundary2) || (value > boundary2 && value < boundary1)
    }

    fun isHorizontallyNear(): Boolean {
        val boundaryRight = view.right + view.translationX + threshold
        val boundaryLeft = view.left + view.translationX - threshold
        return isBetween(left + translationX, boundaryLeft, boundaryRight) || isBetween(right + translationX, boundaryLeft, boundaryRight)
    }

    fun isVerticallyNear(): Boolean {
        val boundaryTop = view.top + view.translationY - threshold
        val boundaryBottom = view.bottom + view.translationY + threshold
        return isBetween(bottom + translationY, boundaryBottom, boundaryTop) || isBetween(top + translationY, boundaryBottom, boundaryTop)
    }
    return isHorizontallyNear() && isVerticallyNear()
}

fun View.contains(view: View): Boolean {
    val parentRect = Rect()
    getHitRect(parentRect)

    val childRect = Rect()
    view.getHitRect(childRect)

    return parentRect.contains(childRect)
}
