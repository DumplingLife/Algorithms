package temp.practiceaime;

public class problem9 {
	public static void main(String[] args) {
		long ways = 0;
		long total = 0;
		for(int a=0; a<=18; a++) {
			for(int b=0; b<=9; b++) {
				for(int c=0; c<=18; c++) {
					for(int d=0; d<=9; d++) {
						for(int e=0; e<=18; e++) {
							for(int f=0; f<=9; f++) {
								if(a<=c && b<=d && c<=e && d<=f) ways++;
								total++;
							}
						}
					}
				}
			}
		}
		System.out.println(ways + " " + total);
	}
}
