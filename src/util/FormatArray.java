package util;

public class FormatArray {
	//basic array display, 2 digits
	public static String formatRowCol(int[][] arr) {
		String ret = "";
		for(int i = 0;i<arr.length;i++) {
			String line = "";
			for(int j = 0;j<arr[i].length;j++) {
				line += (arr[i][j] + "   ").substring(0,2);
			}
			ret += line + "\n";
		}
		return ret;
	}
	//2 digits, change anything less than 1 billion to "X"
	//use this when using Integer.MIN_VALUE as -inf. Sometimes you still add to Integer.MIN_VALUE, so this
	//has leeway (anything less than 1B)
	public static String formatRowColWithNegInf(int[][] arr) {
		String ret = "";
		for(int i = 0;i<arr.length;i++) {
			String line = "";
			for(int j = 0;j<arr[i].length;j++) {
				String str = arr[i][j] + "   ";
				if(arr[i][j] < -1000000000) str = "X   ";
				line += str.substring(0,2);
			}
			ret += line + "\n";
		}
		return ret;
	}
	
	//bottom left origin, 2 digits
	public static String formatXY(int[][] arr) {
		String ret = "";
		for(int y = arr[0].length-1;y>=0;y--) {
			String line = "";
			for(int x = 0;x<arr.length;x++) {
				line += (arr[x][y] + "  ").substring(0,2);
			}
			ret += line + "\n";
		}
		return ret;
	}
	//bottom left origin, true = X, false = .
	public static String formatXY(boolean[][] arr) {
		String ret = "";
		for(int y = arr[0].length-1;y>=0;y--) {
			String line = "";
			for(int x = 0;x<arr.length;x++) {
				line += ((arr[x][y] ? "X" : ".") + "  ").substring(0,2);
			}
			ret += line + "\n";
		}
		return ret;
	}
}
