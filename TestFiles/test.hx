namespace Test {
    class ConcreteClass : AbstractClass {

        var a
        var b

        def constructor(var a, var b) {
            this.a = a
            this.b = b
        }

        def concreteMethod() {
            if (a >= b) {
                print "Hello World"
            }
            print "This is a concrete method"
        }
    }
}

def main(args) {

    var Window = Window(800, 600, "Hello World", true)

    var firstScene = Scene()
    firstScene.addLayer("background")
}

// Comment