/* ********************************************************
* Student Name: Brennan Johnston
* Class: CS 3103 Algorithms
* Assignment: Problem Set 2
* Due Date: September 7th, 2021
* ********************************************************/

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
		if(args.length < 3) {
			System.out.println("Invalid argument count, expected 3. Exiting.");
			return;
		}
		String inputFilePath = args[0];
		String outputFilePath = args[1];
		int maxDepth = Integer.parseInt(args[2]);
		
		File inputFile = new File(inputFilePath);
		if(inputFile.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String strData;
			try {
				strData = br.readLine();
				br.close();
				String[] inputArray = strData.split(" ");
				MergeSort(inputArray, 0, inputArray.length, maxDepth);
				
				PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)));
				for(var i = 0; i < inputArray.length; i++) {
					output.print(inputArray[i] + " ");
				}
				output.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("File at that path does not exist. Try using quotations around your paths.\n"
					+ "Path provided: " + inputFilePath);
		}
	}

	//utility function to calc midpoint of array, taking odd lengths into account
	public static int GetMidpoint(int length) {
		return (length % 2 != 0) ? (int)(length/(double)2)+1 : (int)(length/(double)2);
	}
	
	public static void MergeSort(String[] array, int start, int end, int depth) {
		if(depth <= 0 || end-start <= 1) {
			if(start == end) end++;
			InsertionSort(array, start, end);
		} else {
			int _mid = GetMidpoint(end-start) + start;
			
			MergeSort(array, start, _mid, depth-1);
			MergeSort(array, _mid, end, depth-1);
			
			Merge(array, start, _mid, end);
		}
	}
	
	//p = start
	//q = middle
	//r = end
	public static void Merge(String[] array, int p, int q, int r) {
		int n1 = q - p;
		int n2 = r - q;
		String[] L = new String[n1+1];
		String[] R = new String[n2+1];
		for(int i = 0; i < n1; i++) {
			L[i] = array[p + i];
		}
		for(int j = 0; j < n2; j++) {
			R[j] = array[q + j];
		}
		
		L[n1] = MERGE_FLAG;
		R[n2] = MERGE_FLAG;
		
		int i = 0;
		int j = 0;
		for(int k = p; k < r; k++) {
			if(L[i].compareTo(R[j]) <= 0) {
				array[k] = L[i];
				i++;
			} else {
				array[k] = R[j];
				j++;
			}
		}
	}
	
	public static void InsertionSort(String[] array, int start, int end) {
		//making sure start and end are valid integers
		if(start < 0 || start >= array.length || start > end || end < 1 || end > array.length) {
			System.out.println("InsertionSort(): start or end invalid indexes.");
			System.out.println("start: " + start + " : end: " + end);
			return;
		}

		for(int j = start+1; j < end; j++) {
			String key = array[j];
			int i = j - 1;
			while(i >= start && array[i].compareTo(key) > 0) {
				array[i+1] = array[i];
				i--;
			}
			array[i+1] = key;
		}
	}
}

