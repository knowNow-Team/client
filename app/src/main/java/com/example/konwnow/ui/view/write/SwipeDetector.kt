package com.example.konwnow.ui.view.write

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener


class SwipeDetector : OnTouchListener {
    val HORIZONTAL_MIN_DISTANCE = 250
    val VERTICAL_MIN_DISTANCE = 250

    enum class Action {
        LR,  // Left to Right
        RL,  // Right to Left
        TB,  // Top to bottom
        BT,  // Bottom to Top
        None // when no action was detected
    }

    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f
    var action = Action.None

    fun swipeDetected(): Boolean {
        return action != Action.None
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                action = Action.None
                return false // allow other events like Click to be processed
            }
            MotionEvent.ACTION_MOVE -> {
                upX = event.x
                upY = event.y
                val deltaX = downX - upX
                val deltaY = downY - upY

                // horizontal swipe detection
                if (Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        Log.i(logTag, "Swipe Left to Right")
                        action = Action.LR
                        return true
                    }
                    if (deltaX > 0) {
                        Log.i(logTag, "Swipe Right to Left")
                        action = Action.RL
                        return true
                    }
                } else  // vertical swipe detection
                    if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            Log.i(logTag, "Swipe Top to Bottom")
                            action = Action.TB
                            return false
                        }
                        if (deltaY > 0) {
                            Log.i(logTag, "Swipe Bottom to Top")
                            action = Action.BT
                            return false
                        }
                    }
                return true
            }
        }
        return false
    }

    companion object {
        private const val logTag = "SwipeDetector"
        private const val MIN_DISTANCE = 100
    }
}