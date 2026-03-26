package com.sample.myapplication.oop

// ── Classes & Inheritance ──────────────────────────────────────────────────────
// Demonstrates: primary/secondary constructors, init blocks, open classes,
//               abstract classes, interfaces with default impls, multiple interface impl

// Primary constructor — shorthand property declaration with val/var
class Point(val x: Double, val y: Double) {
    // init block executes at construction time in declaration order
    init {
        require(x.isFinite() && y.isFinite()) { "Coordinates must be finite" }
    }

    fun distanceTo(other: Point): Double =
        Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))

    override fun toString() = "Point($x, $y)"
}

// Secondary constructor — must delegate to primary via this(...)
class Rectangle(val width: Double, val height: Double) {
    val area: Double = width * height

    // Square convenience secondary constructor
    constructor(side: Double) : this(side, side) {
        println("  Square constructed, side=$side")
    }

    override fun toString() = "Rectangle(${width}×${height}, area=$area)"
}

// Abstract class — cannot be instantiated directly; defines a contract
abstract class Shape(val name: String) {
    abstract fun area(): Double
    abstract fun perimeter(): Double
    // Concrete function shared by all subclasses
    fun describe() = "$name — area=${"%.2f".format(area())}, perimeter=${"%.2f".format(perimeter())}"
}

// open — explicitly allows subclassing (all classes are final by default in Kotlin)
open class Circle(val radius: Double) : Shape("Circle") {
    override fun area() = Math.PI * radius * radius
    override fun perimeter() = 2 * Math.PI * radius
    open fun scale(factor: Double): Circle = Circle(radius * factor)
}

// Subclass of an open class
class AnimatedCircle(radius: Double, val color: String) : Circle(radius) {
    override fun scale(factor: Double) = AnimatedCircle(radius * factor, color)
    override fun toString() = "AnimatedCircle(r=$radius, color=$color)"
}

// Interface with default implementations (no mutable state)
interface Drawable {
    val id: String                          // abstract property
    fun draw()                              // abstract function
    fun erase() = println("  Erasing $id") // default implementation
}

interface Resizable {
    fun resize(factor: Double)
    fun resetSize() = resize(1.0)           // default implementation
}

// Class implementing multiple interfaces
class Canvas(override val id: String) : Drawable, Resizable {
    private var scale = 1.0

    override fun draw()                = println("  Drawing canvas '$id' at scale=$scale")
    override fun resize(factor: Double) {
        scale *= factor
        println("  Canvas '$id' resized → scale=$scale")
    }
}

fun demoClassesAndInheritance() {
    println("\n=== Classes & Inheritance ===")

    val p1 = Point(0.0, 0.0)
    val p2 = Point(3.0, 4.0)
    println("  $p1 → $p2 distance = ${p1.distanceTo(p2)}")

    println(Rectangle(4.0, 5.0))
    println(Rectangle(3.0)) // secondary constructor

    val shapes: List<Shape> = listOf(Circle(5.0), AnimatedCircle(3.0, "red"))
    shapes.forEach { println("  ${it.describe()}") }

    val canvas = Canvas("main")
    canvas.draw()
    canvas.resize(2.0)
    canvas.erase()    // default implementation
    canvas.resetSize() // default implementation from Resizable
    canvas.draw()
}
