class Vector2 {



    constructor(x, y) {
        this.x = x;
        this.y = y;
    }

    xy() {
        return Vector2(this.x, this.y);
    }

    x() {
        return this.x;
    }

    y() {
        return this.y;
    }

    toString() {
        return "{" + this.x + ", " + this.y + "}";
    }
}

class Point : Vector2 {

}

var point = Point(10, 20);

println(point.toString());

