package kotlin1.com.programmingKotlin.chapter1
  
// A sample class to demonstrate extension functions
class Circle (val radius: Double){
    // member function of class
    fun area(): Double{
        return Math.PI * radius * radius;
    }
}
fun main(){
    // Extension function created for a class Circle
    fun Circle.perimeter(): Double{
        return 2*Math.PI*radius;
    }
    // create object for class Circle
    val newCircle = Circle(2.5);
    // invoke member function
    println("Area of the circle is ${newCircle.area()}")
    //invoke extension function
    println("Perimeter of the circle is ${newCircle.perimeter()}")
}
package kotlin1.com.programmingKotlin.chapter1
  
// A sample class to demonstrate extension functions
class Circle (val radius: Double){
    // member function of class
    fun area(): Double{
        return Math.PI * radius * radius;
    }
}
fun main(){
    // Extension function created for a class Circle
    fun Circle.perimeter(): Double{
        return 2*Math.PI*radius;
    }
    // create object for class Circle
    val newCircle = Circle(2.5);
    // invoke member function
    println("Area of the circle is ${newCircle.area()}")
    //invoke extension function
    println("Perimeter of the circle is ${newCircle.perimeter()}")
}
package kotlin1.com.programmingKotlin.chapter1
  
// A sample class to demonstrate extension functions
class Circle (val radius: Double){
    // member function of class
    fun area(): Double{
        return Math.PI * radius * radius;
    }
}
fun main(){
    // Extension function created for a class Circle
    fun Circle.perimeter(): Double{
        return 2*Math.PI*radius;
    }
    // create object for class Circle
    val newCircle = Circle(2.5);
    // invoke member function
    println("Area of the circle is ${newCircle.area()}")
    //invoke extension function
    println("Perimeter of the circle is ${newCircle.perimeter()}")
}
