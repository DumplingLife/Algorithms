package string;

public class MySubstring {
	public static void main(String[] args) {
		String s = "abcdef";
		Substring s1 = new Substring(s, 0, 2);
		Substring s2 = new Substring(s, 1, 3);
		Substring s3 = new Substring(s, 0, 4);
		//should be "bcd".charAt(1) = c
		System.out.println(s2.charAt(1));
	}
	
	static class Substring implements CharSequence {
		String s;
		int l, r;
		public Substring(String s, int l, int r) {
			this.s = s;
			this.l = l;
			this.r = r;
		}
		public char charAt(int index) {
			return s.charAt(l + index);
		}
		public int length() {
			return r-l+1;
		}
		
		public CharSequence subSequence(int start, int end) {
			System.out.println("error: subSequence called");
			return null;
		}
	}
}
