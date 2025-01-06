import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;


/* Test failure record:
 * 1.   [@Test void add]  [add]   [Expected java.lang.ArithmeticException to be thrown, but nothing was thrown.]
 * 2.   [@Test void multiplyWithScalar]   [multiplyWithScalar]   [Multiplying by a negative did not negate the matrix correctly.]
 * 3.   [@Test void multiplyWithMatrix]   [multiplyWithMatrix]   [ The method did not correctly handle multiplication when the result matrix is an operand.]
 * 4.
 * 5.
 *  ...
 */
class MatrixTest {

    @Test
    void testEquals() {
    }

    @Test
    void negate() {
    }

    @Test
    void add() {
        // Description: This test checks if the add method correctly handles cases where element addition results in overflow.
        // Type: White box
        // Assumptions: The method should detect overflow and handle it, possibly by throwing an exception or by saturating at the maximum value.

        Matrix matrixA = new Matrix(1, 1);
        Matrix matrixB = new Matrix(1, 1);
        matrixA.setElem(1, 1, Double.MAX_VALUE);
        matrixB.setElem(1, 1, Double.MAX_VALUE);

        Exception exception = assertThrows(ArithmeticException.class, () -> {
            matrixA.add(matrixB, new Matrix(1, 1));
        });

        assertNotNull(exception);
    }


    @Test
    void multiplyWithScalar() {
        // Description: This test verifies that multiplying a matrix by -1 negates all its elements.
        // Type: Black box
        // Assumptions: The multiplyWithScalar method should correctly multiply each element by the scalar.

        Matrix matrix = new Matrix(2, 2);
        Matrix result = new Matrix(2, 2);
        Matrix expected = new Matrix(2, 2);

        // Initialize matrix with elements 1, 2, 3, 4
        matrix.setElem(1, 1, 1);
        matrix.setElem(1, 2, 2);
        matrix.setElem(2, 1, 3);
        matrix.setElem(2, 2, 4);

        // Initialize expected matrix with negated elements -1, -2, -3, -4
        expected.setElem(1, 1, -1);
        expected.setElem(1, 2, -2);
        expected.setElem(2, 1, -3);
        expected.setElem(2, 2, -4);

        // Perform multiplication with scalar -1
        matrix.multiplyWithScalar(-1, result);

        // Assert that the result matrix matches the expected negated matrix
        assertTrue(result.equals(expected),
                "Multiplying by a negative did not negate the matrix correctly.");
    }

    @Test
    void multiplyWithMatrix() {
        // Description: This test checks if the method correctly handles the case when the result matrix is also one of the operands.
        // Type: Black box
        // Assumptions: The method should correctly handle the case and not use the result matrix for reading during the operation.

        Matrix matrixA = new Matrix(2, 2);
        Matrix matrixB = new Matrix(2, 2);

        // Initialize matrixA with some values
        matrixA.setElem(1, 1, 1);
        matrixA.setElem(1, 2, 2);
        matrixA.setElem(2, 1, 3);
        matrixA.setElem(2, 2, 4);

        // Initialize matrixB with some values
        matrixB.setElem(1, 1, 2);
        matrixB.setElem(1, 2, 0);
        matrixB.setElem(2, 1, 1);
        matrixB.setElem(2, 2, 2);

        // Expected result after multiplication
        Matrix expected = new Matrix(2, 2);
        expected.setElem(1, 1, 4);
        expected.setElem(1, 2, 4);
        expected.setElem(2, 1, 10);
        expected.setElem(2, 2, 8);

        // Perform multiplication using matrixB as the result matrix
        matrixA.multiplyWithMatrix(matrixB, matrixB);

        // Assert that the result is as expected
        assertTrue(matrixB.equals(expected),
                "The method did not correctly handle multiplication when the result matrix is an operand.");

    }

    @Test
    void getElem() {
    }

    @Test
    void setElem() {
    }

    @Test
    void getHeight() {
    }

    @Test
    void getWidth() {
    }
}