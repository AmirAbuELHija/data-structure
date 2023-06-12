import java.util.NoSuchElementException;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
	private Stack stack;
	public int[] arr; // This field is public for grading purposes. By coding conventions and best
						// practice it should be private.
	private int last_index;
	// TODO: implement your code here

	// Do not change the constructor's signature
	public BacktrackingSortedArray(Stack stack, int size) {
		this.stack = stack;
		arr = new int[size];
		last_index = -1;
	}

	@Override
	public Integer get(int index) {
		if (index < 0 | index > last_index) {
			throw new IllegalArgumentException();
		}
		return arr[index]; // temporal return command to prevent compilation error
	}

	@Override
	public Integer search(int k) {
		/* in search we used the binary search algorithm for it time complexity */
		int low = 0;
		int high = last_index;
		while (high >= low) {
			int mid = (high + low) / 2;
			if (arr[mid] == k) {
				return mid;
			}

			if (arr[mid] < k) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1; // temporal return command to prevent compilation error
	}

	@Override
	public void insert(Integer x) {
		/*
		 * in insert we insert the element when we find a value that it is bigger than x
		 * and then pulling them one index ahead and then putting x in wanted_index
		 * putting inside the stack 3 elements 1. value of the element that we put x
		 * instead 2. index 3. 1 to help us know that it is insertion
		 */
		if (last_index == arr.length - 1) {
			// throw new RuntimeException();
			throw new ArrayIndexOutOfBoundsException();
		}
		boolean found = false;
		int wanted_index = last_index + 1;
		if (last_index >= 0 && arr[0] > x) {
			wanted_index = 0;
			found = true;
		}
		for (int i = 0; i < last_index & !found; i = i + 1) {
			if (arr[i] <= x & arr[i + 1] >= x) {
				wanted_index = i + 1;
			}
		}
		for (int j = last_index; j >= wanted_index; j = j - 1) {
			arr[j + 1] = arr[j];
		}
		last_index = last_index + 1;
		stack.push(arr[last_index]);
		stack.push(wanted_index);
		stack.push(1);
		arr[wanted_index] = x;

	}

	@Override
	public void delete(Integer index) {
		/*
		 * in deletion we first putting the value that we want to delete inside the
		 * stack with the index and -1 to help us know that it is deletion and the
		 * pulling the rest of the array from the index+1 to index
		 */
		if (index < 0 | index > last_index) {
			throw new IllegalArgumentException();
		}
		stack.push(index);
		stack.push(arr[index]);
		stack.push(-1);
		for (int i = index; i < last_index; i = i + 1) {
			arr[i] = arr[i + 1];
		}
		arr[last_index] = 0;
		last_index = last_index - 1;

	}

	@Override
	public Integer minimum() {
		if (last_index == -1) {
			throw new NoSuchElementException();
		}
		return 0; // temporal return command to prevent compilation error
	}

	@Override
	public Integer maximum() {
		if (last_index == -1) {
			throw new NoSuchElementException();
		}
		return last_index; // temporal return command to prevent compilation error
	}

	@Override
	public Integer successor(Integer index) {
		/*
		 * in successor we return the index+1 because it is a sorted array so the value
		 * that after the given index is what we want
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
		return index + 1; // temporal return command to prevent compilation error
	}

	@Override
	public Integer predecessor(Integer index) {
		/*
		 * in predecessor we return the index-1 because it is a sorted array so the
		 * value that after the given index is what we want
		 */
		if (index <= 0 | index > last_index) {
			throw new IllegalArgumentException();
		}
		if (last_index <= 0) {
			throw new NoSuchElementException();
		}
		if (index == minimum()) {
			throw new NoSuchElementException();
		}
		return index - 1; // temporal return command to prevent compilation error
	}

	@Override
	public void backtrack() {
		/*
		 * in backtrack we pull out one element to check if the last step if it is
		 * (1)insert or (-1)delete if insert we pull out two element , index of the
		 * insertion place and the value that was in this index before insertion to help
		 * us return the array to it original look before the insertion if deletion we
		 * pull out two elements the deleted element and the his index to return the
		 * value back to its original place
		 */
		if (stack.isEmpty()) {
			return;
		}
		int prev_step = (int) stack.pop();
		if (prev_step == 1) {
			int wanted_index = (int) stack.pop();
			int prev_temp = (int) stack.pop();
			for (int i = wanted_index; i < last_index; i = i + 1) {
				arr[i] = arr[i + 1];
			}
			arr[last_index] = prev_temp;
			last_index = last_index - 1;
		}
		if (prev_step == -1) {
			int deleted_element = (int) stack.pop();
			int deleted_index = (int) stack.pop();
			for (int j = last_index; j >= deleted_index; j = j - 1) {
				arr[j + 1] = arr[j];
			}
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
			// throw new NoSuchElementException();
			throw new ArrayIndexOutOfBoundsException();
		}
		for (int i = 0; i < last_index; i = i + 1) {
			System.out.print(arr[i] + " ");
		}
		System.out.print(arr[last_index]);
	}

}
