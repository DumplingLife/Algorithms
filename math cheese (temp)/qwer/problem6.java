package qwer;

public class problem6 {
	public static void main(String[] args) {
		int ways = 0;
		for(int a=6; a<30; a++) {
			for(int b=a+1; b<30; b++) {
				int[] arr = {3,4,5,a,b,30,40,50};
				boolean works = true;
				for(int i=0; i<arr.length; i++) {
					for(int j=i+1; j<arr.length; j++) {
						for(int k=j+1; k<arr.length; k++) {
							for(int l=k+1; l<arr.length; l++) {
								if(arr[l]-arr[k] == arr[k]-arr[j] && arr[k]-arr[j] == arr[j]-arr[i]) {
									works = false;
								}
							}
						}
					}
				}
				if(works) ways++;
			}
		}
		System.out.println(ways);
	}
}
