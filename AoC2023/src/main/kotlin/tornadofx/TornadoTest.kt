package tornadofx

import javafx.geometry.Pos

/**
 * The main class of the board application.
 * @see App
 */
class DemoApp : App(DemoView::class) {
    init {
        println("on startup")
    }
}

fun main() {
    launch<DemoApp>()
}

class DemoView() : View("Demo View") {
    override val root = vbox {
        minWidth = 600.0
        minHeight = 800.0
        alignment = Pos.CENTER

        label("hello world")
    }
}




