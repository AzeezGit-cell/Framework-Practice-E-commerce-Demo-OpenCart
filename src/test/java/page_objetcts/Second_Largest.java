package page_objetcts;

public class Second_Largest {
	public static void main(String[] args) {
		
		int arr[] = {10, 34, 86, 98, 98, 34, 85};
		
		int  l = arr[0];
		
		int sl = arr[0];
		
		for(int i=1;i<arr.length;i++) {
			if(arr[i]> l) {
				
				sl=l;
				l=arr[i];
			}
			else if(arr[i] > sl && arr[i]!=l) {
				sl=arr[i];
			}
		}
		System.out.println(sl);
	}
}
