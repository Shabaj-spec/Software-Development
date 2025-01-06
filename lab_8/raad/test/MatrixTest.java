/**
 *  File: MatrixTester.java
 *  Author: Alex Brodsky
 *  Date: October 1, 2019
 *  Purpose: CSCI 2134, Lab 4
 *
 *  Description: This class implements Matrix objects for manipulating matrices.
 */

/* Developer1 Name: Shabaj Islam Raad
   Developer1 Banner ID: B00946674

   Developer2 Name: Abdullah Durrani
   Developer2 Banner ID: B00898531
*/

/*  FIXLIST: Missing Test Cases based on specification requirements
    0. Passing bad indices to getElem() not tested.  getElem should generate an exception in this case.
    1. Operation with undefined result should be avoided in multiplyWithMatrixUndefinedResult(). DimensionMismatchException to be thrown, but nothing was thrown. (2:28pm) - Specification issue
    2. Dimension mismatch not thrown in multiplyWithMatrixDimensionAndNullCheck(). DimensionMismatchException to be thrown, but nothing was thrown. (2:33pm) - Specification issue
    3. No exception thrown for negative row dimension in constructorWithNegativeDimensions(). (2:42pm) - Specification issue
    4. Add method failed when adding this matrix to itself in addThisMatrixToItself(). (2:45pm)  - Testing or Specification issue
    5. MultiplyWithScalar failed when this matrix is res in multiplyWithScalarThisMatrixAsRes(). (2:48pm) - Testing or Specification issue
    6.
    7.
    8.
    9.
    10.
    ... Add as many as necessary
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

class MatrixTest {
    private final static String simpleMatrix = "2 2 1 2 3 4"; // [ 1 2 ]
    // [ 3 4 ]
    private final static String nonSqMatrix = "3 2 1 2 3 4 5 6"; // [ 1 2 ]
    // [ 3 4 ]

    @Test
    void getElem() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertEquals(2, m.getElem(1,2), "getElem() did not return correct value");
    }

    @Test
    // Example for item 0 in the FIX LIST above
    void getElem_BadIndex() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        try {
            m.getElem(1, 0);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    void setElem() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        m.setElem(2, 1, 5);
        assertEquals(5, m.getElem(2,1), "setElem() may not have set correct value");
    }

    @Test
    void negate() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        m.negate();
        assertEquals(-1, m.getElem(1,1), "negate() did not negate element (1,1)");
        assertEquals(-2, m.getElem(1,2), "negate() did not negate element (1,2)");
        assertEquals(-3, m.getElem(2,1), "negate() did not negate element (2,1)");
        assertEquals(-4, m.getElem(2,2), "negate() did not negate element (2,2)");
    }

    @Test
    void add() {
        try {
            Matrix m = new Matrix(new Scanner(simpleMatrix));
            Matrix n = new Matrix(new Scanner(simpleMatrix));
            Matrix res = new Matrix(2, 2);
            n.negate();
            m.add(n, res);
            assertEquals(0, res.getElem(1, 1), "negate() did not add element (1,1))");
            assertEquals(0, res.getElem(1, 2), "negate() did not add element (1,2)");
            assertEquals(0, res.getElem(2, 1), "negate() did not add element (2,1)");
            assertEquals(0, res.getElem(2, 2), "negate() did not add element (2,2)");
        } catch (Matrix.DimensionMismatchException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        } catch (Matrix.NullMatrixException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        }
    }

    @Test
    void equals() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        assertTrue(m.equals(n), "equals() has a false negative");
    }

    @Test
    void equal2s() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        n.negate();
        assertFalse(m.equals(n), "equals() has a false positive");
    }

    @Test
    void multiplyWithScalar() {
        try {
            Matrix m = new Matrix(new Scanner(simpleMatrix));
            Matrix n = new Matrix(m);
            Matrix res = new Matrix(m);
            n.negate();
            m.multiplyWithScalar(-1, res);
            assertTrue(res.equals(n), "matrix not scaled by -1");
        } catch (Matrix.DimensionMismatchException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        } catch (Matrix.NullMatrixException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        }
    }

    @Test
    void multiplyWithMatrix() {
        try {
            Matrix m = new Matrix(new Scanner(simpleMatrix));
            Matrix n = new Matrix(m);
            Matrix res = new Matrix(m);
            n.negate();
            m.multiplyWithScalar(-1, res);
            assertTrue(res.equals(n), "matrix not scaled by -1");
        } catch (Matrix.DimensionMismatchException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        } catch (Matrix.NullMatrixException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        }
    }

    @Test
    void getHeight() {
        Matrix m = new Matrix(new Scanner(nonSqMatrix));
        assertEquals(3, m.getHeight(), "getHeight() did not return correct height");
    }

    @Test
    void getWidth() {
        Matrix m = new Matrix(new Scanner(nonSqMatrix));
        assertEquals(2, m.getWidth(), "getWidth() did not return correct width");
    }
    @Test
    void multiplyWithMatrixUndefinedResult() {
        Matrix m = new Matrix(new Scanner(simpleMatrix)); // 2x2 matrix
        Matrix n = new Matrix(2, 2); // Correct dimensions
        // Using same matrix for both b and res should lead to undefined behavior
        // There's no explicit exception thrown for this in the spec, but it's a case to be aware of
        Matrix res = m; // res is the same as this matrix
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.multiplyWithMatrix(n, res), "Operation with undefined result should be avoided");
    }

    @Test
    void multiplyWithMatrixDimensionAndNullCheck() {
        Matrix m = new Matrix(new Scanner(simpleMatrix)); // 2x2 matrix
        Matrix n = new Matrix(2, 3); // Incorrect height
        Matrix res = new Matrix(2, 3);
        assertThrows(Matrix.NullMatrixException.class, () -> m.multiplyWithMatrix(null, res), "Null matrix exception not thrown for b");
        assertThrows(Matrix.NullMatrixException.class, () -> m.multiplyWithMatrix(n, null), "Null matrix exception not thrown for res");
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.multiplyWithMatrix(n, res), "Dimension mismatch not thrown");
    }

    @Test
    void multiplyWithScalarNullOrMismatchedDimensions() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertThrows(Matrix.NullMatrixException.class, () -> m.multiplyWithScalar(2, null), "Null matrix exception not thrown");
        Matrix res = new Matrix(3, 3); // Different dimensions
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.multiplyWithScalar(2, res), "Dimension mismatch not thrown");
    }

    @Test
    void addNullMatrix() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertThrows(Matrix.NullMatrixException.class, () -> m.add(null, new Matrix(2, 2)), "Null matrix exception not thrown for b");
        assertThrows(Matrix.NullMatrixException.class, () -> m.add(new Matrix(2, 2), null), "Null matrix exception not thrown for res");
    }

    @Test
    void matrixInitialization() {
        int rows = 3;
        int cols = 4;
        Matrix m = new Matrix(rows, cols);
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                assertEquals(0, m.getElem(i, j), "Matrix not initialized to zero at [" + i + "," + j + "]");
            }
        }
    }
    @Test
    void matrixDeepCopy() {
        Matrix original = new Matrix(new Scanner(simpleMatrix));
        Matrix copy = new Matrix(original);
        original.setElem(1, 1, 100); // Change original matrix
        assertNotEquals(original.getElem(1, 1), copy.getElem(1, 1), "Deep copy failed, elements are linked");
    }

    @Test
    void equalsDifferentDimensions() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(new Scanner(nonSqMatrix));
        assertFalse(m.equals(n), "Equals should be false for matrices of different dimensions");
    }

    @Test
    void negateSameInstance() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix negated = m.negate();
        assertSame(m, negated, "Negate should return the same matrix instance");
    }

    @Test
    void addDimensionMismatch() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(3, 3); // Different dimensions
        Matrix res = new Matrix(2, 2);
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.add(n, res), "Dimension mismatch not thrown");
    }

    @Test
    void multiplyWithScalarDimensionMismatch() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix res = new Matrix(3, 3); // Different dimensions
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.multiplyWithScalar(2, res), "Dimension mismatch not thrown");
    }

    @Test
    void multiplyWithMatrixDimensionMismatch() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(3, 2); // Different dimensions
        Matrix res = new Matrix(2, 2);
        assertThrows(Matrix.DimensionMismatchException.class, () -> m.multiplyWithMatrix(n, res), "Dimension mismatch not thrown");
    }

    @Test
    void getElemOutOfBounds() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertThrows(IndexOutOfBoundsException.class, () -> m.getElem(m.getHeight() + 1, 1), "Index out of bounds not thrown for row");
        assertThrows(IndexOutOfBoundsException.class, () -> m.getElem(1, m.getWidth() + 1), "Index out of bounds not thrown for column");
    }


    @Test
    void setElemOutOfBounds() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertThrows(IndexOutOfBoundsException.class, () -> m.setElem(m.getHeight() + 1, 1, 10), "Index out of bounds not thrown for row");
        assertThrows(IndexOutOfBoundsException.class, () -> m.setElem(1, m.getWidth() + 1, 10), "Index out of bounds not thrown for column");
    }


    @Test
    void constructorWithNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(-1, 3), "No exception thrown for negative row dimension");
        assertThrows(IllegalArgumentException.class, () -> new Matrix(3, -1), "No exception thrown for negative column dimension");
    }

    @Test
    void equalsWithNullMatrix() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertFalse(m.equals(null), "Equals method should return false when compared with null");
    }

    @Test
    void addThisMatrixToItself() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        try {
            m.add(m, m);
            assertEquals(2 * m.getElem(1, 1), m.getElem(1, 1), "Add method failed when adding this matrix to itself");
        } catch (Matrix.DimensionMismatchException | Matrix.NullMatrixException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        }
    }

    @Test
    void multiplyWithScalarThisMatrixAsRes() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        double scalar = 2;
        try {
            m.multiplyWithScalar(scalar, m);
            for (int i = 1; i <= m.getHeight(); i++) {
                for (int j = 1; j <= m.getWidth(); j++) {
                    assertEquals(scalar * m.getElem(i, j), m.getElem(i, j), "MultiplyWithScalar failed when this matrix is res");
                }
            }
        } catch (Matrix.DimensionMismatchException | Matrix.NullMatrixException e) {
            fail("Exception occurred where none should have " + e.getMessage());
        }
    }

}

