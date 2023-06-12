public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {  
    	/*in this task we put each element we check inside the stack and then return it 
    	 * back according to the forward/back that we receive */
    	int curr_index = 0;
    	while(curr_index < arr.length)	{
    		for(int i = 0 ; i < forward & curr_index < arr.length  ; i = i + 1) {
    			if(arr[curr_index] == x) {
    				return curr_index;
    			}
    			myStack.push(arr[curr_index]);
    			curr_index = curr_index + 1;
    		}
    		for(int i = 0 ; i < back & curr_index < arr.length; i = i + 1) {
    			myStack.pop();
    			curr_index = curr_index - 1;
    		}
    	}			
    	return -1; // temporal return command to prevent compilation error 
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	/* in this task in each  step we call isConsistent(arr) function to give us how much step we 
    	 * should return back to keep Consistency and in every step we put inside the stack three elemnts
    	 *  1.low+2.high+3.arr[mid] to help us know where were we*/
        int low = 0;
        int high = arr.length - 1;
        while(high >= low) {      
        	int mid = (high+low)/2;
        	if(arr[mid] == x) {
        		return mid;
        	}
        	
        	if(arr[mid] < x) {
        		low = mid + 1;
        	}else {
        		high = mid - 1;
        	}
        	myStack.push(low);
        	myStack.push(high);
        	myStack.push(arr[mid]);
        	
        	int inconsistencies = Consistency.isConsistent(arr);
        	for(int i = 0 ; i < inconsistencies ; i = i + 1) {
        		myStack.pop();
        		high = (int)myStack.pop();
        		low = (int)myStack.pop();
    		}
        }
    	return -1; // temporal return command to prevent compilation error
    }
   
}
