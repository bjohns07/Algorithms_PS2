import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class UASortAndMerge {
	//"~" is 126 on the ASCII table, so it is used for sake of the "infinity"
	//flag in the Merge algorithm, since String doesn't have an
	//equivalent to "Integer.MAX_VALUE" (I looked it up and couldn't find an answer).
	final static String MERGE_FLAG = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
	public static void main(String[] args) throws FileNotFoundException {
		String _input_file_path = args[0];
		String _output_file_path = args[1];
		int max_depth = Integer.parseInt(args[2]);
		
		File _input_file = new File(_input_file_path);
		if(_input_file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(_input_file));
			String str_data;
			try {
				str_data = br.readLine();
				br.close();
				String[] input_array = str_data.split(" ");
				MergeSort(input_array, 0, input_array.length, max_depth);
				System.out.println(Arrays.toString(input_array));
				
				PrintWriter _output = new PrintWriter(new BufferedWriter(new FileWriter(_output_file_path)));
				for(var i = 0; i < input_array.length; i++) {
					_output.print(input_array[i] + " ");
				}
				_output.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("File at that path does not exist.  Try using quotations around your paths.\n"
					+ "Path provided: " + _input_file_path);
		}
	}

	//utility function to calc midpoint of array, taking odd lengths into account
	public static int GetMidpoint(int _length) {
		return (_length % 2 != 0) ? (int)(_length/(double)2)+1 : (int)(_length/(double)2);
	}
	
	public static void MergeSort(String[] _array, int _start, int _end, int _depth) {
		if(_depth <= 0 || _end-_start <= 1) {
			if(_start == _end) _end++;
			InsertionSort(_array, _start, _end);
		} else {
			int _mid = GetMidpoint(_end-_start)+_start;
			
			MergeSort(_array, _start, _mid, _depth-1);
			MergeSort(_array, _mid, _end, _depth-1);
			
			Merge(_array, _start, _mid, _end);
		}
	}
	
	//p = start
	//q = middle
	//r = end
	public static void Merge(String[] _array, int p, int q, int r) {
		int n1 = q - p;
		int n2 = r - q;
		String[] L = new String[n1+1];
		String[] R = new String[n2+1];
		for(int i = 0; i < n1; i++) {
			L[i] = _array[p + i];
		}
		for(int j = 0; j < n2; j++) {
			R[j] = _array[q + j];
		}
		
		L[n1] = MERGE_FLAG;
		R[n2] = MERGE_FLAG;
		
		int i = 0;
		int j = 0;
		for(int k = p; k < r; k++) {
			if(L[i].compareTo(R[j]) <= 0) {
				_array[k] = L[i];
				i++;
			} else {
				_array[k] = R[j];
				j++;
			}
		}
	}
	
	public static void InsertionSort(String[] _array, int _start, int _end) {
		//making sure _start and _end are valid integers
		if(_start < 0 || _start >= _array.length || _start > _end || _end < 1 || _end > _array.length) {
			System.out.println("InsertionSort(): _start or _end invalid indexes.");
			System.out.println("_start: " + _start + " : _end: " + _end);
			return;
		}

		for(int j = _start+1; j < _end; j++) {
			String key = _array[j];
			int i = j - 1;
			while(i >= _start && _array[i].compareTo(key) > 0) {
				_array[i+1] = _array[i];
				i--;
			}
			_array[i+1] = key;
		}
	}
}

