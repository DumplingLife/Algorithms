package string;

public class MyPrefixHasher
{
	public static void main(String[] args) {
		//compare test
		/*
		for(int i = 0;i<1000000;i++) {
			String a = "";
			String b = "";
			for(int j = 0;j<5;j++) {
				a += (char) (Math.random()*26 + 'a');
				b += (char) (Math.random()*26 + 'a');
			}
			
			if((a.compareTo(b) > 0 && compare(a,b) < 0) || (a.compareTo(b) < 0 && compare(a,b) > 0)) {
				System.out.println("--------------");
				System.out.println(a);
				System.out.println(b);
			}
		}
		
		String a = "ab";
		String b = "ac";
		System.out.println(a.compareTo(b));
		System.out.println(compare(a,b));
		*/
		PrefixHasher a = new PrefixHasher("abcd");
		System.out.println(a.hash(2,3));
		PrefixHasher b = new PrefixHasher("cd");
		System.out.println(a.hash(1,1));
		
	}
	public static int compare(String a, String b) {
		//in practice, a and b should already have PrefixHasher initialized
		PrefixHasher aHasher = new PrefixHasher(a);
		PrefixHasher bHasher = new PrefixHasher(b);
		
		int smallerSize = Math.min(a.length(), b.length());
		if(aHasher.hash(0, smallerSize-1) == bHasher.hash(0, smallerSize-1)) {
			if(a.length() < b.length()) return -1;
			if(a.length() == b.length()) return 0;
			if(a.length() > b.length()) return 1;
		}
		
		int l = 0;
		int r = smallerSize;
		while(l < r) {
			int m = (l+r)/2;
			if(aHasher.hash(0,m) == bHasher.hash(0,m)) {
				l = m+1;
			}
			else r = m;
		}
		//now l = r = place where they first differ		
		if(a.charAt(l) < b.charAt(l)) return -1;
		else return 1;
	}
	
	static class PrefixHasher
	{
		String str;
		long[] prefixHashes;
		final int B = 31;
		long[] expTable;
		public PrefixHasher(String str) {
			this.str = str;
			prefixHashes = new long[str.length()];
			expTable = new long[str.length()];
			build();
			buildExpTable();
		}
		public void build() {
			prefixHashes[0] = str.charAt(0);
			for(int i = 1;i<prefixHashes.length;i++) {
				prefixHashes[i] = prefixHashes[i-1] * B + str.charAt(i);
			}
		}
		public void buildExpTable() {
			expTable[0] = 1;
			for(int i = 1;i<expTable.length;i++) {
				expTable[i] = expTable[i-1] * B;
			}
		}
		//get hash of substring between l and r, inclusive
		public long hash(int l, int r) {
			if(l == 0) return prefixHashes[r];
			
			return prefixHashes[r] - prefixHashes[l-1] * expTable[r-l+1];
		}
	}
}
