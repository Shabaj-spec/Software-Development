/**
 *  File: Matrix.java
 *  Author: Alex Brodsky
 *  Date: September 11, 2015
 *  Purpose: CSCI 1110, Assignment 3 Solution
 *
 *  Description: This class implements Matrix objects for manipulating matrices.
 */

import java.io.PrintStream;
import java.util.Scanner;

/**
 * This is the Matrix class for loading and manipulatinx matricexs.
 * It stores the matrix in a 2D array for a dense representation that
 * is easy to manipulate.
 */
public class Matrix {
    private double [][] matrix;  // 2D array stores matrix of size height x width
    int height;                  // number of rows in the matrix
    int width;                   // number of columns in the matrix

    /**
     * @description: Constructor creates a zero matrix of size m x n.
     * @param  m: height of the matrix
     * @param  n: width of the matrix
     */
    public Matrix(int m, int n) {
        matrix = new double[m][n];
        height = m;
        width = n;
    }

    /**
     * @description Constructor duplicates the passed matrix
     * @param   mtx: matrix to be cloned
     */
    public Matrix(Matrix mtx) {
        height = mtx.getHeight();
        width = mtx.getWidth();
        matrix = new double[height][width];

       for (int i = 0; i < height; i++) {
           for (int j = 0; j < width; j++) {
                matrix[i][j] = mtx.matrix[i][j];
            }
        }
    }

    /**
     * @description Constructor reads in a matrix from the Scanner object
     * @param   s: Scanner object used to read in the matrix
     */
    public Matrix(Scanner s) {
        height = s.nextInt();
        width = s.nextInt();
        matrix = new double[height][width];

        // Assume row-major order
       for (int i = 0; i < height; i++) {
           for (int j = 0; j < width; j++) {
                matrix[i][j] = s.nextDouble();
            }
        }
    }

    /**
     * @description Return true if this matrix equals the one passed to this
     * method.
     * @param   a:   matrix to be compared against the current matrix
     * @return returns true if and only if the matrices are element-wise the same
     */
    public boolean equals(Matrix a) {
        if ((a == null) || (a.getHeight() != height) || (a.getWidth() != width) ) {
            return false;
        }

       for (int i = 0; i < height; i++) {
           for (int j = 0; j < width; j++) {
                if (matrix[i][j] != a.matrix[i][j]) {
                    // at least one element is not the same in these matrices
                    return false;
                };
            }
        }

        return true;
    }

    /**
     * @description Negate all entries in this matrix
     */
    public void negate() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = -matrix[i][j];
            }
        }
    }

    /**
     * @description Add Matrix b with the current matrix and store the results
     * in res.  If matrices are not allocated or correctly sized, return null.
     * @param   b:   matrix to be added with the current matrix
     * @param   res: destination matrix where the result is placed.
     * @return  returns res matrix for programming convenience.
     */
    public Matrix add(Matrix b, Matrix res) {
        if ((res == null) || (b == null) ||
                (b.getHeight() != height) || (b.getWidth() != width) ||
                (res.getHeight() != height) || (res.getWidth() != width)) {
            return res;
        }

        // Addition
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.matrix[i][j] = matrix[i][j] + b.matrix[i][j];
            }
        }

        return res;
    }

    /**
     * @description Multiply scalar s with the current matrix and store the
     * results in res.  If matrices are not allocated or correctly sized,
     * return null.
     * @param   s:   scalar to be multiplied by this matrix
     * @param   res: destination matrix where the result is placed.
     * @return returns res matrix for programming convenience.
     */
    public Matrix multiplyWithScalar(double s, Matrix res) {
        if (res != null) {
            if ((res.getHeight() != height) || (res.getWidth() != width)) {
                return null;
            }

            // Scalar multiply
           for (int i = 1; i < height; i++) {
               for (int j = 1; j < width; j++) {
                    res.matrix[i][j] = s * matrix[i][j];
                }
            }
        }

        return res;
    }

    /**
     * @description Multiply the current matrix with matrix b and store the
     * results in res.  If matrices are not allocated or correctly sized,
     * return null.
     * @param   b:   matrix to be multiplied with the current matrix
     * @param   res: destination matrix where the result is placed.
     * @return  returns res matrix for programming convenience.
     */
    public Matrix multiplyWithMatrix(Matrix b, Matrix res) {
        if ((b == null) || (b == null)) {
            return null;
        }

        int n = b.getWidth();
        if ((b.getHeight() != width) || (res.getHeight() != height) ||
                (res.getWidth() != n)) {
            return null;
        }

        // Multiplication
       for (int i = 0; i < height; i++) {
           for (int j = 0; j < n; j++) {
                res.matrix[i][j] = 0;
               for (int k = 0; k < width; k++) {
                    res.matrix[i][j] += matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        return res;
    }

    /**
     * @description Write the matrix out to the PrintStream, (as if using
     * System.out).  The format is:
     *   First line has 2 integers: height and width, separated by a space 
     *   Next 'height' lines contain `width` dobules, separated by spaces
     * @param  out: The PrintStream where the output should go.
     */
    public void write(PrintStream out) {
        // Output matrix dimensions: height and width
        out.println(height + " " + width);

        // Output 'height' rows
       for (int i = 0; i < height; i++) {
            // First column
            String row = "";
            if (width > 0) {
                row += matrix[i][0];
            }

            // Remaining columns
           for (int j = 1; j < width; j++) {
                row += " " + matrix[i][j];
            }

            out.println(row);
        }
    }

    /**
     * @description Return element E{i,j] of the matrix.  If i or j is less
     * than 1 or greater than height or width, respectively, the behaviour is
     * undefined.
     * @param   i: row of entry
     * @param   j: column of entry
     * @return element E[i,j]
     */
    public double getElem(int i, int j) {
        return matrix[i - 1][j - 1];
    }

    /**
     * @description Set element E{i,j] of the matrix to value v.  If i or j is
     * less than 1 or greater than height or width, respectively, the behaviour
     * is undefined.
     * @param   i: row of entry
     * @param   j: column of entry
     * @param   v: new value of the entry
     */
    public void setElem(int i, int j, double v) {
        matrix[i - 1][j - 1] = v;
    }

    /**
     * @return the number of rows in the matrix.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the number of columns in the matrix.
     */
    public int getWidth() {
        return width;
    }
}
