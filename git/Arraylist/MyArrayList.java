/**
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * Sources Used: PA2 Write-up, MyList.java
 * 
 * This file implements methods in MyList and complete the functionality 
 * of each method.
 */

/**
 * This class sets up behavior of each method's instruction.
 * 
 * Instance variables:
 * data - the input data or default data with default capacity
 * size - numbers of element inside the data
 */
public class MyArrayList<E> implements MyList<E>{

    private static final int DEFAULT_CAPACITY = 5;
    private static final int MY_CAPACITY = 3;

    Object[] data;
    int size;

    /**
     * Initialize the constructor with default capacity
     */
    public MyArrayList() {
        data = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Initialize the constructor with input capacity
     *
     * @param initialCapacity input capacity
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        else {
            data = new Object[initialCapacity];
        }
    }

    /**
     * Initialize the constructor with input object array
     *
     * @param arr input array
     */
    public MyArrayList (E[] arr) {
        if (arr != null) {
            data = arr;
            size = arr.length;
        }
        else {
            data = new Object[DEFAULT_CAPACITY];
        }
    }

    /**
     * Increase the capacity of underlying array if needed
     *
     * @param requiredCapacity minimum capacity after expanding
     */
    public void expandCapacity (int requiredCapacity) {
        Object[] expandedData = new Object[0];
        if (requiredCapacity < data.length) {
            throw new IllegalArgumentException();
        }
        else if (data.length == 0) {
            if (requiredCapacity <= DEFAULT_CAPACITY) {
                expandedData = new Object[DEFAULT_CAPACITY];
            }
            else {
                expandedData = new Object[requiredCapacity];
            }
        }
        else {
            expandedData = new Object[data.length + MY_CAPACITY];
            if (expandedData.length < requiredCapacity) {
                expandedData = new Object[requiredCapacity];
            }
        }
        for (int i = 0; i < data.length; i++) {
            expandedData[i] = data[i];
        } 
        data = expandedData;
    }

    /**
     * Get the amount of elements array can hold
     *
     * @return number of elements that can be held
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * Add an element at the specified index
     *
     * @param index   position to insert the element
     * @param element the element to insert
     */
    public void insert(int index, E element) {
        if (index < 0 || index >= data.length) { 
            throw new IndexOutOfBoundsException();
        }
        else {
            if (data.length == size || index == data.length) {
                expandCapacity(data.length + 1);
            }
            for (int i = data.length-1; i > index; i--) {
                data[i] = data[i - 1];
            }
            data[index] = element;
            size++;
        }
    }

    /**
     * Add an element to the end of the list
     *
     * @param element the element to append
     */
    public void append(E element) {
        if (data.length == size) {
            expandCapacity(data.length + 1);
        }
        data[size] = element;
        size++;
    }

    /**
     * Add an element to the beginning of the list 
     *
     * @param element the element to prepend
     */
    public void prepend(E element) {
        if (data.length == size) {
            expandCapacity(data.length + 1);
        }
        for (int i = data.length-1; i >= 1; i--) {
            data[i] = data[i - 1];
        }
        data[0] = element;
        size++;
    }

    /**
     * Get the element at the given index
     *
     * @param index the index at which to return the element
     * @return the element at the index
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        else {
            return (E) data[index];
        }
    }

    /**
     * Replaces an element at the specified index with a new element and return
     * the original element
     *
     * @param index   the index at which to replace
     * @param element the element with which to replace
     * @return the original element
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        else {
            E overwittenElement = (E) data[index];
            data[index] = element;
            return overwittenElement;
        }
    }

     /**
     * Remove the element at the specified index and return the removed element
     *
     * @param index the index at which to remove the element
     * @return the removed element
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        E removed = (E) data[index]; 
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        else if (index == data.length-1) {
            data[index] = null;
        }
        else {
            for (int i = index; i < data.length-1; i++) {
                data[i] = data[i + 1];
            }
        }
        size--;
        return removed;
    }

    /**
     * Get the number of elements in the list
     *
     * @return number of elements in the list
     */
    public int size() {
        return this.size;
    }
}
