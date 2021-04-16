import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static int head, idx;
	public static int[] e, ne;
	public static void init() {
		head = -1;  // head
		idx = 0;	// bian hao
	}
	public static void add(int k, int x) {
		e[idx] = x;
		ne[idx] = ne[k];
		ne[k] = idx++;
	}
	public static void addToHead(int x) {
		e[idx] = x;
		ne[idx] = head;
		head = idx++;
	}
	public static void remove(int k, int x) {
		ne[k] = ne[ne[k]];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		init();
		while (n -- > 0) {
			// "H", add x to head;
			// "D", remove pos == k;
			// "I", insert x after pos == k;
			String[] input = reader.readLine().split(" ");
			if (input[0].equals("H")) addToHead(Integer.parseInt(input[1]));
			else if (input[0].equals("D")) {
				// if remove the head
				if (input[1].equals("0")) 
					head = ne[head];
				else remove(Integer.parseInt(input[1]) - 1); // 记得-1
			}
			else {
				// insert
				int pos = Integer.parseInt(input[1]), x = Integer.parseInt(input[2]);
				add(pos - 1, x); // 记得-1
			}
		}
		for (int i = head; i != -1; i = ne[i]) System.out.printf("%d ", e[i]);
	}
}