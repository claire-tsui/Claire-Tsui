/**
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * Sources Used: PA2 Write-up, MyArrayListPublicTester.java
 * 
 * This file contains all the hidden tests and serve as a guide to 
 * write tests to verify MyArrayList implementation 
 */

import static org.junit.Assert.*;
import org.junit.*;

/**
 * This class creates a test fixture and runs multiple tests on 
 * your implementation for MyArrayList.  
 * 
 * Instance variables:
 * arr - Object array 
 * arrInts - Integer array with full capacity
 * arrNullElements - Integer array with null and integers
 */
public class MyArrayListHiddenTester {

    Object[] arr = new Object[10];
    Integer[] arrInts = {1, 2, 3};
    Integer[] arrNullElements = {null, 1, null}; // size=2, capacity=3

    private MyArrayList listInvalid, listNull,listFullCapacity,
         listNullElements, listNoCapacity;

    // Do not change the method signatures!
    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test 
     */
    @Before
    public void setUp() throws Exception {
        listInvalid = new MyArrayList();
        listNull = new MyArrayList(null);
        listFullCapacity = new MyArrayList<Integer>(arrInts);
        listNullElements = new MyArrayList<Integer>(arrNullElements);
        listNullElements.size = 2;
        listNoCapacity = new MyArrayList(0);
    }

    /**
     * Aims to test the constructor when the input argument
     * is not valid
     */
    @Test
    public void testConstructorInvalidArg(){
        boolean test = false;
        try {
            listInvalid = new MyArrayList(-1);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check constructor for invalid input", test);
    }

    /**
     * Aims to test the constructor when the input argument is null
     */
    @Test
    public void testConstructorNullArg(){
        assertArrayEquals("Check null constructor", new Object[5], 
             listNull.data);
        assertEquals("Check size for null constructor",
             0, listNull.size);
        assertEquals("Check capacity for null constructor",
             5, listNull.getCapacity());

    }

    /**
     * Aims to test the constructor when the input array has null elements
     */
    @Test
    public void testConstructorArrayWithNull(){
        assertArrayEquals("Check constructor with null elements", 
             new Integer[]{null, 1, null}, listNullElements.data);
        assertEquals("Check size for constructor with null elements", 
             2, listNullElements.size);
        assertEquals("Check capacity for constructor with null elements", 
             3, listNullElements.getCapacity());
    }

    /**
     * Aims to test the append method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testAppendAtCapacity(){
        listFullCapacity.append(4);
        assertArrayEquals("Check for successful append",
             new Integer[]{1, 2, 3, 4, null, null}, listFullCapacity.data);
        assertEquals("Check size after the append", 
             4, listFullCapacity.size);
        assertEquals("Check capacity after the append", 
             6, listFullCapacity.getCapacity());
    }

    /**
     * Aims to test the append method when null is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testAppendNull(){
        listFullCapacity.append(null);
        assertArrayEquals("Check for successful append",
             new Integer[]{1, 2, 3, null, null, null}, listFullCapacity.data);
        assertEquals("Check size after the append", 
             4, listFullCapacity.size);
        assertEquals("Check capacity after the append", 
             6, listFullCapacity.getCapacity());
    }

    /**
     * Aims to test the prepend method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testPrependAtCapacity(){
        listFullCapacity.prepend(4);
        assertArrayEquals("Check for successful prepend",
             new Integer[]{4, 1, 2, 3, null ,null}, listFullCapacity.data);
        assertEquals("Check size after the prepend", 
             4, listFullCapacity.size);
        assertEquals("Check capacity after the prepend", 
             6, listFullCapacity.getCapacity());
    }
    
    /**
     * Aims to test the prepend method when a null element is added
     * Checks reflection on size and capacity
     * Checks whether null was added successfully
     */
    @Test
    public void testPrependNull(){
        listFullCapacity.prepend(null);
        assertArrayEquals("Check for successful prepend",
             new Integer[]{null, 1, 2, 3, null, null}, listFullCapacity.data);
        assertEquals("Check size after the prepend", 
             4, listFullCapacity.size);
        assertEquals("Check capacity after the prepend", 
             6, listFullCapacity.getCapacity());
    }
    
    /**
     * Aims to test the insert method when input index is out of bounds
     */
    @Test
    public void testInsertOutOfBounds(){
        boolean test = false;
        try {
            listNullElements.insert(-1, 10);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check for invalid index", test);        
    }

    /**
     * Insert multiple (eg. 1000) elements sequentially beyond capacity -
     * Check reflection on size and capacity
     * Hint: for loop could come in handy
     */
    @Test
    public void testInsertMultiple(){
        for (int i = 1; i <= 5; i++) {
            listFullCapacity.insert(3,10);
        }
        assertArrayEquals("Check for successful insert",
             new Integer[]{1, 2, 3, 10, 10, 10, 10, 10, null}, 
             listFullCapacity.data);
        assertEquals("Check size after the insert", 
             8, listFullCapacity.size);
        assertEquals("Check capacity after the insert", 
             9, listFullCapacity.getCapacity());
    }

    /**
     * Aims to test the get method when input index is out of bound
     */
    @Test
    public void testGetOutOfBound(){
        boolean test = false;
        try {
            listFullCapacity.get(-1);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check for invalid index", test);
    }

    /**
     * Aims to test the set method when input index is out of bound
     */
    @Test
    public void testSetOutOfBound(){
        boolean test = false;
        try {
            listFullCapacity.set(-1, 10);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check for invalid index", test);
    }

    /**
     * Aims to test the remove method when removing multiple items from a list
     */
    @Test
    public void testRemoveMultiple(){
        listFullCapacity.remove(2);
        listFullCapacity.remove(1);
        assertArrayEquals("check data", new Integer[]{1, null, null}, 
             listFullCapacity.data);
        assertEquals("check size after removing an element",
             1, listFullCapacity.size);
        assertEquals("Check capacity after removing an element", 
             3, listFullCapacity.getCapacity());
    }

    /**
     * Aims to test the remove method when input index is out of bound
     */
    @Test
    public void testRemoveOutOfBound(){
        boolean test = false;
        try {
            listFullCapacity.remove(4);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check for invalid index", test);        
    }

    /**
     * Aims to test the expandCapacity method when 
     * requiredCapacity is strictly less than the current capacity
     */
    @Test
    public void testExpandCapacitySmaller(){
        boolean test = false;
        try {
            listFullCapacity.expandCapacity(1);
        }
        catch (Exception E) {
            test = true;
        }
        assertTrue("Check for invalid requiredCapacity", test);       
    }

    /**
     * Aims to test the expandCapacity method when 
     * requiredCapacity is greater than current capacity+3 and default capacity
     */
    @Test
    public void testExpandCapacityLarge(){
        listFullCapacity.expandCapacity(10);
        assertArrayEquals("check data after expansion", 
             new Integer[]{1, 2, 3, null, null, null, null, null, null, null},
             listFullCapacity.data);
        assertEquals("check size after expansion", 
             3, listFullCapacity.size);
        assertEquals("Check capacity after expansion", 
             10, listFullCapacity.getCapacity());

        listNoCapacity.expandCapacity(7);
        assertArrayEquals("check data after expansion", 
             new Integer[]{null, null, null, null, null, null, null}, 
             listNoCapacity.data);
        assertEquals("check size after expansion", 
             0, listNoCapacity.size);
        assertEquals("Check capacity after expansion", 
             7, listNoCapacity.getCapacity());
    }
}
