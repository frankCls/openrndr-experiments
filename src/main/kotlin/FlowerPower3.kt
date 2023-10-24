import org.openrndr.application
import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.shape.Segment
import org.openrndr.shape.ShapeContour
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


fun main() = application {
    configure {
        width = 800
        height = 800
//                fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }


    oliveProgram {

        val amount = 17
        val angle = 360.0 / amount

        extend(NoClear())
        extend {
            drawer.stroke = ColorHSVa(
                sin(seconds * 0.5) * 0.0 + 50.0,
                0.5,
                sin(seconds)
            ).toRGBa().opacify(0.3)
            drawer.strokeWeight = sin(seconds * 0.5) + 0.2
            drawer.translate(
                width.toDouble() / 2,
                height.toDouble() / 2
            )
            for (i in 0..amount) {

                val segment = Segment(
                    start = Vector2(0.0, 0.0),
                    end = Vector2(0.0, 300.0),
                    control = arrayOf(
                        Vector2(250.0, sin(seconds * 0.05) * 250.0 - 250.0),
                        Vector2(50.0, -cos(seconds* 0.073) * 250.0 - 250.0)
                    )
                ).contour

                val segment2 = Segment(
                    start = Vector2(0.0, 0.0),
                    end = Vector2(0.0, sin(seconds * 0.03) * 250.0 + 150.0),
                    control = arrayOf(
                        Vector2(-250.0, -sin(seconds * 0.063) * sin(seconds * 0.02) * 150.0 + 150.0 + 250.0),
                        Vector2(50.0, -cos(seconds * 0.07) * sin(seconds * 0.05) * 299.0 + 150.0 - 250.0)
                    ),
                    corner = false
                ).contour

                drawer.contours(listOf(segment, segment2))
                drawer.rotate(angle)
            }
        }
    }
}