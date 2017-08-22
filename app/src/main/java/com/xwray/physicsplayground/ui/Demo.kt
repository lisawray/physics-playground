package com.xwray.physicsplayground.ui

enum class Demo(val title: String, val description: String) {
    SIMPLE_SPRING("Spring demo", "A spring pulled repeatedly at a fixed initial velocity"),
    MULTIPLE_DAMPING("Damping", "Demonstrates different spring damping ratios"),
    MULTIPLE_STIFFNESS("Stiffness", "Demonstrates different spring stiffnesses"),
    FLING("Fling demo", "A repeated fling animation at a fixed initial velocity"),
    MULTIPLE_FRICTION("Friction", "Demonstrates different fling frictions"),
    SPRING_TOUCH("Spring", "Pull a view with a spring animation"),
    FLING_TOUCH("Fling", "Drag and fling a view"),
    SPRING_AND_FLING_TOUCH("Spring and fling", "Play with two views with a spring and fling animation side by side."),
    CHAT_HEAD("Fling to spring", "Example of transferring momentum from a fling to spring animations, " +
            "inspired by Facebook chat heads"),
    FLING_BOUNCE("Fling to fling", "Example of transferring momentum from one fling to another to " +
            "create a reflection effect "),
    CHAINED_SPRINGS("Chained springs", "Simple demo of chained springs with balls"),
    SCROLLING_FRAGMENT("Chained springs transition", "An Android UI example using chained springs")
}
