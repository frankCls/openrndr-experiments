import org.openrndr.Fullscreen
import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.color.ColorHSVa
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.noise.perlin
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.hobbyCurve
import org.openrndr.math.Polar
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
        val horizontalNr = 10
        val verticalNr = 10
        val locations = List(horizontalNr * verticalNr) { index ->
            Vector2(
                ((index % verticalNr).toDouble() * (height / verticalNr)),
                ((index / horizontalNr).toDouble() * (width / horizontalNr))
            )
        }
        val amount = 15
        val angle = 360.0 / amount
        var position = Vector2.ZERO

        extend(NoClear())
        extend {
            val widthNormalized = map(0.0, width.toDouble(), 0.0, 1.0, width.toDouble())
            val heightNormalized = map(0.0, height.toDouble(), 0.0, 1.0, height.toDouble())

//            val mouseXnormalized = map(0.0, , 0.0, width.toDouble()1.0, mouse.position.x)
//            val mouseYnormalized = map(0.0, perlin(1,height.toDouble()), 0.0, 1.0, mouse.position.y)
            val scale = width / 5  * 0.001
//            val scale = 8.0
            drawer.scale(scale)
            val translateFactor = (1.0 /scale   ) /2

            drawer.translate(
                width.toDouble() * translateFactor,
                height.toDouble() * translateFactor
            )
            for (i in 0..amount) {
                drawer.rectangleBatch {
                    locations.forEachIndexed { index, location ->

                        val sinValue = sin(index * seconds * 0.005) * -cos(index * seconds * 0.001)
                        val cosValue = cos(index * seconds * 0.005) * -sin(index * seconds * 0.001)
                        val sinNormalized = map(-1.0, 1.0, 0.0, 0.8, sinValue)
                        val cosNormalized = map(-1.0, 1.0, 0.0, 0.5, cosValue)
                        val hueNormalized = map(-1.0, 1.0, 20.0, 120.0, sinValue)
                        val opacity = map(0.0, 1.0, 0.0, 0.5, sinNormalized)
                        val colorHSVa = ColorHSVa(hueNormalized, sinNormalized  , cosValue)
//                        val colorHSVa = ColorHSVa(hueNormalized, cosValue, sinValue) // black white yellow brown
//                        val colorHSVa = ColorHSVa(
//                            hueNormalized,
//                            map(0.0, 1.0, 0.0, width.toDouble(), perlin(1, width.toDouble())),
//                            map(0.0, 1.0, 0.0, height.toDouble(), perlin(1, height.toDouble())),
//                            opacity
//                        )
//                        val colorHSVa = ColorHSLa(hueNormalized, cosNormalized, sinNormalized)
//                        val colorHSVa = ColorHSLa(hueNormalized, cosValue, sinNormalized)
//                        val colorHSVa = ColorHSLa(hueNormalized, cosValue, 0.0)

                        drawer.strokeWeight = sinNormalized
                        if (index % amount == 0) {
                            //                        drawer.isolated {
//                            drawer.rotate(30.0)
                            drawer.strokeWeight = sin(seconds * 0.1)
                            drawer.stroke = colorHSVa.toRGBa().opacify(sinNormalized)
                            drawer.fill = colorHSVa.toRGBa().opacify(cosNormalized)
                            //                        }
                        } else {
                            drawer.fill = colorHSVa.toRGBa().opacify(0.1)
                            drawer.stroke = colorHSVa.toRGBa().opacify(opacity)
                        }
                        drawer.contour(ShapeContour.fromPoints(
                            listOf(
                                Vector2.ZERO,
                                Random.vector2(width.toDouble(), height.toDouble()),
                                Vector2(widthNormalized, heightNormalized)
                                )
                            , false
                        ).hobbyCurve())

//                        val points = List(3) {
//                            drawer.bounds.center + Polar(it * 360.0 / amount, width * Random.double(0.1, 0.4)).cartesian
//                        }
//                        val pointyContour = ShapeContour.fromPoints(points, true)
//                        val smoothContour = pointyContour.hobbyCurve()
//                        drawer.contour(smoothContour)


                        drawer.translate(width.toDouble(), 0.0)
                        drawer.translate(sin(seconds * 0.1) * width, 0.01)
//                        drawer.translate(seconds * 0.1, 0.0)
//                        drawer.translate(mouse.position.x, mouse.position.y)
                        drawer.translate(position)
                        position = Vector2(
                            position.x + Random.perlin(position) * sinValue,
                            position.y + Random.perlin(position) * cosValue
                        )
                        drawer.rotate(90.0)

//                        drawer.rectangle(
//                            location,
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

        }
    }
}