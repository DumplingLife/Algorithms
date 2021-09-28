/*
 * Find sum (any invertible, associative operation) from 0 to n in O(log n)
 * Update in O(log n)
 * 
 * Methods
 * constructor:
 * 	arguments: array
 * val:
 * 	arguments: index
 * 	return: f applied to all numbers from 0 to n (inclusive)
 * update:
 * 	arguments: index, delta
 * 		delta: f(old val in arr, delta) = new val in arr
 * 		this would be subtraction with addition, or divison with multiplication
 * 	return: none
 * 	updates the tree given that arr[index] changed by delta
 */

package dataStructures;

public class MyFenwickTree
{
	public static void main(String[] args)
	{
		int[] arr = {1,3,2,4,5};
		FenwickTree myFenwickTree = new FenwickTree(arr);
		System.out.println(myFenwickTree.val(0));
	}
}

/*
 -------8
 ---4
 -2  -6  
01 3 5 7 9

*/

class FenwickTree
{
	//1 based, all indexes passed in as parameters are 0 based, they will be adjusted
	int[] tree;
	public FenwickTree(int[] arr) {
		tree = new int[arr.length + 1];
		for(int i = 0;i<arr.length;i++) {
			update(i, arr[i]);
		}
	}
	public int val(int index) {
		index++;
		
		int res = 0;
		while(index > 0) {
			res = f(res, tree[index]);
			//remove the right-most 1
			index -= (index & -index);
		}
		return res;
	}
	public void update(int index, int delta) {
		index++;
		while(index < tree.length) {
			tree[index] = f(tree[index], delta);
			//supposed index is divisible by 2^i but not 2^(i+1)
			//this will make index the smallest number > index that is divisible by 2^i
				//note this number must also be divisible by 2^(i+1)
			index += (index & -index);
		}
	}
	
	//supports any associative operation, including min
	private static int f(int a, int b) {
		return a+b;
	}
}