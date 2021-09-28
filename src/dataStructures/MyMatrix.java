package dataStructures;

import java.util.Arrays;

public class MyMatrix
{
	public static void main(String[] args) {
		Matrix a = new Matrix(3, 3);
		a.vals[0][0] = 1;
		a.vals[0][1] = 2;
		a.vals[0][2] = 1;
		a.vals[1][0] = 3;
		a.vals[1][1] = 1;
		a.vals[1][2] = 5;
		a.vals[2][0] = 1;
		a.vals[2][1] = 5;
		a.vals[2][2] = 1;
		Matrix b = a.power(10);
		int x = 0;
	}
}

class Matrix
{
	int[][] vals;
	int rows;
	int cols;
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.vals = new int[this.rows][this.cols];
	}
	public Matrix(Matrix m) {
		this.rows = m.rows;
		this.cols = m.cols;
		this.vals = new int[this.rows][this.cols];
		for(int row = 0;row<rows;row++) {
			for(int col = 0;col<cols;col++) {
				this.vals[row][col] = m.vals[row][col];
			}
		}
	}

	public static Matrix multiply(Matrix a, Matrix b) {
		//a.cols = b.rows assumed
		Matrix c = new Matrix(a.rows, b.cols);
		for(int row = 0; row < a.rows; row++) {
			for(int col = 0; col < b.cols; col++) {
				int res = 0;
				for(int i = 0;i<a.cols;i++) {
					res += a.vals[row][i] * b.vals[i][col];
				}
				c.vals[row][col] = res;
			}
		}
		return c;
	}
	public Matrix square() {
		//assume square matrix
		return Matrix.multiply(this, this);
	}
	public Matrix power(int exp) {
		//assume square matrix
		Matrix res = new Matrix(this);
		Matrix base = new Matrix(this);
		exp--;
		while(true) {
			if(exp % 2 == 1) {
				res = Matrix.multiply(res, base);
				exp--;
			}
			if(exp == 0) break;
			base = base.square();
			exp = exp/2;
		}
		return res;
	}
	@Override
	public String toString() {
		String res = "";
		for(int i = 0;i<vals.length;i++) {
			res += "[";
			for(int j = 0;j<vals.length-1;j++) {
				res += vals[i][j] + ", ";
			}
			res += vals[i][vals.length-1];
			res += "]\n";
		}
		return res;
	}
}