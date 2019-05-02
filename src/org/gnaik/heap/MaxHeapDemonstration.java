package org.gnaik.heap;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 */
public class MaxHeapDemonstration {
    public static void main(String[] args) {

        /**
         *
         */
        class Node implements Comparable<Node> {
            int data;

            public Node(int data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return String.valueOf(data);
            }

            @Override
            public int compareTo(Node o) {
                if (this.data == o.data)
                    return 0;
                return this.data < o.data ? -1 : 1;
            }
        }

        MaxHeap heap = new MaxHeap(Node.class, 10);
        heap.add(new Node(10));
        System.out.println(heap);
        heap.add(new Node(20));
        System.out.println(heap);
        heap.add(new Node(30));
        System.out.println(heap);
        heap.remove();
        System.out.println(heap);
        heap.remove();
        System.out.println(heap);
    }
}


/**
 * @param <T>
 */
class MaxHeap<T extends Comparable<T>> {

    /**
     *
     */
    private int size;
    /**
     *
     */
    private T[] item;
    /**
     *
     */
    private int capacity;

    /**
     * @param type
     * @param initialCapacity
     */
    public MaxHeap(Class<T> type, int initialCapacity) {
        this.capacity = initialCapacity;
        this.item = (T[]) Array.newInstance(type, initialCapacity);
    }

    /**
     * @param type
     */
    public MaxHeap(Class<T> type) {
        this(type, 10);
    }

    /**
     * @param value
     */
    public void add(T value) {
        if (size == 0) {
            this.item[size++] = value;
            return;
        }
        ensureCapacity();
        this.item[size++] = value;
        heapifyUp();
    }

    /**
     * @return
     */
    public T remove() {
        if (size == 0)
            throw new IllegalStateException("Heap is empty");
        T removedItem = this.item[0];
        this.item[0] = this.item[--size];
        item[size] = null;
        heapifyDown();
        return removedItem;
    }

    /**
     *
     */
    private void ensureCapacity() {
        if (this.size >= this.capacity) {
            this.capacity *= 2;
            this.item = Arrays.copyOf(this.item, this.capacity);
        }
    }

    /**
     *
     */
    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && getParent(index).compareTo(this.item[index]) < 0) {
            int parentIndex = getParentIndex(index);
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     *
     */
    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int higherValueChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && item[higherValueChildIndex].compareTo(getRightChild(index)) < 0) {
                higherValueChildIndex = getRightChildIndex(index);
            }
            if (item[index].compareTo(this.item[higherValueChildIndex]) < 0) {
                swap(higherValueChildIndex, index);
                index = higherValueChildIndex;
            } else {
                break;
            }
        }
    }

    /**
     * @param childIndex
     * @return
     */
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    /**
     * @param parentIndex
     * @return
     */
    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    /**
     * @param parentIndex
     * @return
     */
    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    /**
     * @param parentIndex
     * @return
     */
    private boolean hasLeftChild(int parentIndex) {
        return (getLeftChildIndex(parentIndex) < size) ? true : false;
    }

    /**
     * @param parentIndex
     * @return
     */
    private boolean hasRightChild(int parentIndex) {
        return (getRightChildIndex(parentIndex) < size) ? true : false;
    }

    /**
     * @param childIndex
     * @return
     */
    private boolean hasParent(int childIndex) {
        return (getParentIndex(childIndex) >= 0) ? true : false;
    }

    /**
     * @param parentIndex
     * @return
     */
    private T getLeftChild(int parentIndex) {
        return this.item[getLeftChildIndex(parentIndex)];
    }

    /**
     * @param parentIndex
     * @return
     */
    private T getRightChild(int parentIndex) {
        return this.item[getRightChildIndex(parentIndex)];
    }

    /**
     * @param childIndex
     * @return
     */
    private T getParent(int childIndex) {
        return this.item[getParentIndex(childIndex)];
    }

    /**
     * @param index1
     * @param index2
     */
    private void swap(int index1, int index2) {
        T temp = this.item[index1];
        this.item[index1] = this.item[index2];
        this.item[index2] = temp;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        return new StringBuilder("MaxHeap " + Arrays.toString(Arrays.copyOf(this.item, this.size)))
                .append(lineSeparator)
                .append("Size:     ").append(this.size)
                .append(lineSeparator)
                .append("Capacity: ").append(this.capacity)
                .toString();
    }
}
