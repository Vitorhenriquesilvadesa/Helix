class Node {
    constructor(value) {
        this.value = value;
        this.next = null;
    }
}

class LinkedList {
    constructor() {
        this.first = null;
        this.last = null;
        this.length = 0;
    }

    add(element) {
        var newNode = Node(element);

        if (this.length == 0) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.next = newNode;
            this.last = newNode;
        }

        this.length = this.length + 1;
    }

    get(index) {

        if(index >= this.length or index < 0) {
            exception("Index out of range.");
        }

        var node = this.first;

        for (var i = 0; i < index; i = i + 1) {
            if (node == null) {
                return null;
            }
            node = node.next;
        }

        return node.value;
    }

    remove(index) {
        if (index >= this.length or index < 0) {
            exception("Index out of range.");
        }

        if (index == 0) {
            this.first = this.first.next;
            this.length = this.length - 1;

            if (this.length == 0) {
                this.last = null;
            }
            return;
        }

        var node = this.first;

        for (var i = 0; i < index - 1; i = i + 1) {
            node = node.next;
        }

        node.next = node.next.next;
        this.length = this.length - 1;

        if (index == this.length - 1) {
            this.last = node;
        }
    }
}


var list = LinkedList();

list.add(2);
list.add(3);
list.add(10);
list.add(-323);

println(list.get(2));
list.remove(2);
println(list.get(2));
