import org.openrndr.application
import org.openrndr.color.ColorHSVa
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.shape.ShapeContour
import kotlin.math.cos
import kotlin.math.sin


fun main() = application {
    configure {
        width = 800
        height = 800
//                fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }


    oliveProgram {
        val horizontalNr = 5
        val verticalNr = 5
        val locations = List(horizontalNr * verticalNr) { index ->
            Vector2(
                ((index % verticalNr).toDouble() * (height / sin(seconds * 0.001))),
                ((index / horizontalNr).toDouble() * (width / cos(seconds * 0.001)))
//                ((index / horizontalNr).toDouble() * (width / 2))
            )
        }
        val amount = 17
        val angle = 360.0 / amount

        extend(NoClear())
        extend {
            val widthNormalized = map(0.0, width.toDouble(), 0.0, 1.0, width.toDouble())
            val heightNormalized = map(0.0, height.toDouble(), 0.0, 1.0, height.toDouble())

//            val mouseXnormalized = map(0.0, , 0.0, width.toDouble()1.0, mouse.position.x)
//            val mouseYnormalized = map(0.0, perlin(1,height.toDouble()), 0.0, 1.0, mouse.position.y)
//            val scale = width / 5 * 0.001
            val scale = mouse.position.x * 0.001
//            val scale = 8.0
            drawer.scale(scale)
            val translateFactor = (1.0 / scale) / 2

            drawer.translate(
                width.toDouble() * translateFactor,
                height.toDouble() * translateFactor
            )
            for (i in 0..amount) {
                drawer.rectangleBatch {
                    for (index in 0..amount) {

                        val sinValue = sin(index * seconds * 0.005) * -cos(index * seconds * 0.001)
                        val cosValue = cos(index * seconds * 0.005) * -sin(index * seconds * 0.001)
                        val sinNormalized = map(-1.0, 1.0, 0.0, 0.8, sinValue)
                        val cosNormalized = map(-1.0, 1.0, 0.0, 0.5, cosValue)
                        val hueNormalized = map(-1.0, 1.0, 0.0, 120.0, sinValue)
                        val opacity = map(0.0, 1.0, 0.0, 0.5, sinNormalized)
//                        val colorHSVa = ColorHSVa(hueNormalized, sinNormalized  , cosValue)
                        val colorHSVa = ColorHSVa(hueNormalized, cosValue, sinValue) // black white yellow brown
//                        val colorHSVa = ColorHSVa(
//                            hueNormalized,
//                            map(0.0, 1.0, 0.0, width.toDouble(), perlin(1, width.toDouble())),
//                            map(0.0, 1.0, 0.0, height.toDouble(), perlin(1, height.toDouble())),
//                            opacity
//                        )
//                        val colorHSVa = ColorHSLa(hueNormalized, cosNormalized, sinNormalized)
//                        val colorHSVa = ColorHSLa(hueNormalized, cosValue, sinNormalized)
//                        val colorHSVa = ColorHSVa(250.0, 0.5, 0.5)

                        drawer.strokeWeight = 0.5
                        if (index % amount == 0) {
                            //                        drawer.isolated {
//                            drawer.rotate(30.0)
                            drawer.strokeWeight = sin(seconds * 0.1)
                            drawer.stroke = colorHSVa.toRGBa()
                            drawer.fill = colorHSVa.toRGBa()
                            //                        }
                        } else {
                            drawer.fill = colorHSVa.toRGBa()
                            drawer.stroke = colorHSVa.toRGBa()
                        }
//                        List(5) {
//                            Vector2(0.0 + i * Random.double(1.0, i.toDouble()), 0.0 + i * Random.double(1.0, i.toDouble()))
//                        }
                        drawer.contour(ShapeContour.fromPoints(
                            List(amount) { index ->
                                Vector2(
                                    ((index % verticalNr).toDouble() * 0.5 * (height / cos(seconds * 0.1))),
                                    ((index / horizontalNr).toDouble() *0.5 * (width / cos(seconds * -0.01)))
//                                    ((index / horizontalNr).toDouble() * (width / 2))
                                )
                            }
//                            List(5) {
//                                Vector2(0.0 + i * Random.double(1.0, i.toDouble()), 0.0 + i * Random.double(1.0, i.toDouble()))
//                            }
                            , false
                        ).hobbyCurve())


                        drawer.rotate(90.0)

//                        drawer.rectangle(
//                            Random.vector2(width.toDouble(), height.toDouble()),
//                            sinNormalized * sinValue * 50,
//                            -cosNormalized / cosValue * 10
//                        )
//                        drawer.rectangle(
//                            location,
//                            hueNormalized * sinValue * 50,
//                            -cosNormalized / cosValue
//                        )
                    }
                }
                drawer.rotate(angle)
            }
            drawer.rotate(-seconds / 360 + 1)

        }
    }
}