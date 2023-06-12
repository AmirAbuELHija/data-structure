import java.util.NoSuchElementException;

public class BacktrackingArray implements Array<Integer>, Backtrack {
	private Stack stack;
	private int[] arr;
	private int last_index;
	// TODO: implement your code here

	// Do not change the constructor's signature
	public BacktrackingArray(Stack stack, int size) {
		this.stack = stack;
		arr = new int[size];
		last_index = -1;
	}

	/*
	 * we added a variable called(last_index)to the implementation to help us know
	 * at which point we are each step
	 */
	@Override
	public Integer get(int index) {
		if (index < 0 | index > last_index) {
			throw new RuntimeException();
		}
		return arr[index]; // temporal return command to prevent compilation error
	}

	@Override
	public Integer search(int k) {
		/*
		 * in the function we go over all the array and checking each element if it
		 * equal to k
		 */
		for (int i = 0; i <= last_index; i = i + 1) {
			if (arr[i] == k) {
				return i;
			}
		}
		return -1; // temporal return command to prevent compilation error
	}

	@Override
	public void insert(Integer x) {
		/*
		 * in insert we are putting the element in the last_index+1 position and
		 * inserting 1 to help us know that it is insertion step
		 */
		if (last_index == arr.length - 1) {
			throw new RuntimeException();
		}
		arr[last_index + 1] = x;
		last_index = last_index + 1;
		stack.push(1);
	}

	@Override
	public void delete(Integer index) {
		/*
		 * in deletion we save the deleted value in the stack for backtracking and its
		 * index together and also -1 to help us know that it is deletion step
		 */
		if (index < 0 | index > last_index) {
			throw new RuntimeException();
		}
		stack.push(arr[index]);
		stack.push(index);
		stack.push(-1);
		arr[index] = arr[last_index];
		last_index = last_index - 1;
	}

	@Override
	public Integer minimum() {
		/* to find minimum we need to go over the hall array because it is not sorted */
		if (last_index == -1) {
			throw new NoSuchElementException();
		}
		int min = 0;
		for (int i = 0; i <= last_index; i = i + 1) {
			if (arr[i] < arr[min]) {
				min = i;
			}
		}
		return min; // temporal return command to prevent compilation error
	}

	@Override
	public Integer maximum() {
		/* to find maximum we need to go over the hall array because it is not sorted */
		if (last_index == -1) {
			throw new NoSuchElementException();
		}
		int max = 0;
		for (int i = 0; i <= last_index; i = i + 1) {
			if (arr[i] > arr[max]) {
				max = i;
			}
		}
		return max; // temporal return command to prevent compilation error
	}

	@Override
	public Integer successor(Integer index) {
		/*
		 * here we are searching for the biggest value that it is smaller than the value
		 * in the given
		 */
		if (index < 0 | index > last_index) {
			throw new IllegalArgumentException();
		}

		if (last_index <= 0) {
			throw new NoSuchElementException();
		}
		if (index == maximum()) {
			throw new NoSuchElementException();
		}

		int succ = 0;
		for (int i = 0; i <= last_index; i = i + 1) {
			if (arr[succ] <= arr[index] & arr[i] > arr[succ]) {
				succ = i;
			}
			if (arr[i] < arr[succ] & arr[index] < arr[i]) {
				succ = i;
			}
		}
		return succ; // temporal return command to prevent compilation error
	}

	@Override
	public Integer predecessor(Integer index) {
		/*
		 * here we are searching for the smallest value that it is bigger than the value
		 * in the given
		 */
		if (index < 0 | index > last_index) {
			throw new IllegalArgumentException();
		}
		if (last_index <= 0) {
			throw new NoSuchElementException();
		}
		if (index == minimum()) {
			throw new NoSuchElementException();
		}
		int pred = 0;
		for (int i = 0; i <= last_index; i = i + 1) {
			if (arr[pred] >= arr[index] & arr[i] < arr[pred]) {
				pred = i;
			}
			if (arr[i] > arr[pred] & arr[index] > arr[i]) {
				pred = i;
			}
		}
		return pred; // temporal return command to prevent compilation error
	}

	@Override
	public void backtrack() {
		/*
		 * for backtracking we first we pull out the last element to know what the last
		 * step that we did (1)for insertion (-1) for deletion if (1) we moving the
		 * index 1 backwards and ignoring the value inside the last insertion if (-1) we
		 * pull out two more elements from the stack that we put to save the value and
		 * the index of the last deletion and return it back by returning the value in
		 * the deleted_index to the end of the array and putting the deleted_element in
		 * the deleted_index
		 */
		if (stack.isEmpty()) {
			return;
		}
		int prev_step = (int) stack.pop();
		if (prev_step == 1) {
			last_index = last_index - 1;
		}
		if (prev_step == -1) {
			int deleted_index = (int) stack.pop();
			int deleted_element = (int) stack.pop();
			arr[last_index + 1] = arr[deleted_index];
			last_index = last_index + 1;
			arr[deleted_index] = deleted_element;
		}
	}

	@Override
	public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
	}

	@Override
	public void print() {

		if (last_index == -1) {
			return;
		}

		for (int i = 0; i < last_index; i = i + 1) {
			System.out.print(arr[i] + " ");
		}
		System.out.print(arr[last_index]);
	}

}
