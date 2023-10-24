import org.openrndr.application
import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.shape.Segment
import java.time.LocalDateTime
import kotlin.math.cos
import kotlin.math.sin


fun main() = application {
    configure {
        width = 800
        height = 800
//                fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }




    oliveProgram {
        val colors = listOf(
//        ColorRGBa.fromHex(0x001219),
            ColorRGBa.fromHex(0x005f73),
            ColorRGBa.fromHex(0x0a9396),
            ColorRGBa.fromHex(0x94d2bd),
            ColorRGBa.fromHex(0xe9d8a6),
            ColorRGBa.fromHex(0xee9b00),
            ColorRGBa.fromHex(0xca6702),
            ColorRGBa.fromHex(0xbb3e03),
            ColorRGBa.fromHex(0xae2012),
            ColorRGBa.fromHex(0x9b2226)
        )

//        val amount = colors.size
        val amount = 16
        val angle = 360.0 / amount

        extend(NoClear())
        extend {
//            val sec = seconds
            val sec = LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC).toDouble()
            drawer.strokeWeight = /*sin(sec * 0.5) + */ 0.01
            drawer.translate(
                width.toDouble() / 2,
                height.toDouble() / 2
            )
            for (i in 0 .. amount) {
                val color = colors[amount * i % (colors.size)].opacify(0.01)
                color.toHSVa().saturate(sin(sec))
                drawer.stroke = color
                val segment = Segment(
                    start = Vector2(0.0, 0.0),
                    end = Vector2(0.0, sin(sec * 0.03) * 250.0 - 150.0),
                    control = arrayOf(
                        Vector2(250.0, sin(sec * 0.05) * 250.0 - 250.0),
                        Vector2(50.0, -cos(sec* 0.073) * 250.0 - 250.0)
                    )
                ).contour

                val segment2 = Segment(
                    start = Vector2(0.0, 0.0),
                    end = Vector2(0.0, sin(sec * 0.03) * 250.0 + 150.0),
                    control = arrayOf(
                        Vector2(-250.0, -sin(sec * 0.063) * sin(sec * 0.02) * 150.0 + 150.0 + 250.0),
                        Vector2(250.0, -cos(sec * 0.07) * sin(sec * 0.05) * -299.0 + 150.0 - 250.0)
                    ),
                    corner = false
                ).

                drawer.contours(listOf(
//                    segment
//                    ,
                    segment2
                ))
                drawer.rotate(angle)
            }
        }
    }
}