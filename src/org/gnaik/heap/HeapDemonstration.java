package org.gnaik.heap;

public class HeapDemonstration {
    public static void main(String[] args) {

        Node heapRoot = null;
    }

    void add(Node root, int value) {
        if(root == null) {
            root = new Node(value, null);
            return;
        }

    }

    void delete(int value) {

    }
}

class Node {
    int data;
    Node node;

    public Node(int data, Node node) {
        this.data = data;
        this.node = node;
    }

    public Node() {
    }
}
