package com.sortingappuniquex.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SortingController {

	final static String outputFile = "C:\\Users\\HP\\OneDrive\\Desktop\\sorted-files";
	
	@RequestMapping("/")
	public String showPage() {
		return "get-file";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam("file") MultipartFile file) {

		HashMap<String, HashMap<Integer, Long>> finalResult = new HashMap<String, HashMap<Integer, Long>>();
		//HashMap<String, String> finalResult = new HashMap<String, String>();
		
		if (!file.isEmpty()) {
			try {
				LinkedHashMap<String, Double> studentData = null;
				studentData = dataMap(file);
				HashMap<Integer, Long> bubbleSortResult = null;
				bubbleSortResult = bubbleSort(studentData);
				finalResult.put("Bubble sort result", bubbleSortResult);
				HashMap<Integer, Long> mergeSortResult = null;
				mergeSortResult = mergeSort(studentData);
				finalResult.put("Merge sort result", mergeSortResult);
				HashMap<Integer, Long> heapSortResult = null;
				heapSortResult = heapSort(studentData);
				finalResult.put("Heap sort result", heapSortResult);
				
				
			} catch (Exception e) {
			}
		} 
		ModelAndView model = new ModelAndView();
		model.setViewName("showdata");
		model.addObject("data", finalResult);
		return model;
	}
	
	
	//method to create dataMap
	private LinkedHashMap<String, Double> dataMap(MultipartFile file){
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		 String line;
		 try (BufferedReader reader = new BufferedReader(new 
				 InputStreamReader(file.getInputStream(), "UTF-8"))) {
		        while ((line = reader.readLine()) != null) {
		            String[] keyValuePair = line.split(",", 2);
		            if (keyValuePair.length > 1) {
		                String key = keyValuePair[0];
		                String value = keyValuePair[1];
		                
		                    map.put(key, Double.parseDouble(value));
		               } else {
		                System.out.println("No Key:Value found in line, ignoring: " + line);
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return map;
		
	}
	
	//bubble sort logic
	private HashMap<Integer, Long> bubbleSort(LinkedHashMap<String, Double> studentData) {
		
		ArrayList<Double> marks = new ArrayList<Double>(studentData.values());
		Double[] marksArray = new Double[marks.size()];
		marksArray = marks.toArray(marksArray);
		int length= marksArray.length;
		int i, j;
		double  temp;
		long startTime = System.nanoTime();
        //bubble sort logic
        for (i = 0; i < length - 1; i++)
        {
            for (j = 0; j < length - i - 1; j++)
            {
                if (marksArray[j] < marksArray[j + 1])
                {
                    temp = marksArray[j];
                    marksArray[j] = marksArray[j + 1];
                    marksArray[j + 1] = temp;
                    
                }
            }
 
        }
        List<Double> sortedMarks = new ArrayList<Double>();
        sortedMarks.addAll(Arrays.asList(marksArray));
        LinkedHashMap<String, Double> sortadedMap = new LinkedHashMap<String, Double>();        
        for (Double str : sortedMarks) {
            for (Entry<String, Double> entry : studentData.entrySet()) {
                if (entry.getValue().equals(str)) {
                	sortadedMap.put(entry.getKey(), str);
                }
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //writing the sorted data to a file
        File file = new File(outputFile+"\\bubblesort"+startTime+".txt"); 
        BufferedWriter bf = null; 
        try {   
            bf = new BufferedWriter(new FileWriter(file)); 
            for (Map.Entry<String, Double> entry : 
            	sortadedMap.entrySet()) { 
                bf.write(entry.getKey() + ","
                         + entry.getValue()); 
                bf.newLine(); 
            } 
            bf.flush(); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
            try { 
                bf.close(); 
            } 
            catch (Exception e) { 
            } 
        }
        
        HashMap<Integer, Long> result = new HashMap<Integer, Long>();
        result.put(sortadedMap.size(), duration);
        return result;
	}
	
	//merge sort
	private HashMap<Integer, Long> mergeSort(LinkedHashMap<String, Double> studentData) {
		
		ArrayList<Double> marks = new ArrayList<Double>(studentData.values());
		Double[] marksArray = new Double[marks.size()];
		marksArray = marks.toArray(marksArray);
		int length= marksArray.length;
		long startTime = System.nanoTime();
        mergeSort(marksArray, 0, length - 1);
        
        HashMap<Integer, Long> result = new HashMap<Integer, Long>();
        LinkedHashMap<String, Double> sortadedMap = new LinkedHashMap<String, Double>();        
        for (Double str : marksArray) {
            for (Entry<String, Double> entry : studentData.entrySet()) {
                if (entry.getValue().equals(str)) {
                	sortadedMap.put(entry.getKey(), str);
                }
            }
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //writing the sorted data to a file
        File file = new File(outputFile+"\\mergesort"+startTime+".txt"); 
        BufferedWriter bf = null; 
        try {   
            bf = new BufferedWriter(new FileWriter(file)); 
            for (Map.Entry<String, Double> entry : 
            	sortadedMap.entrySet()) { 
  
                bf.write(entry.getKey() + ","
                         + entry.getValue()); 
                bf.newLine(); 
            } 
  
            bf.flush(); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
            try { 
                bf.close(); 
            } 
            catch (Exception e) { 
            } 
        }
        result.put(sortadedMap.size(), duration);
        return result;
		
	}
	
	private void merge(Double[] marksArray, int beg, int mid, int end)    
	{    
	    int i, j, k;  
	    int n1 = mid - beg + 1;    
	    int n2 = end - mid;    
	      
        double LeftArray[] = new double[n1];  
        double RightArray[] = new double[n2];  
	      
	    for (i = 0; i < n1; i++)    
	    LeftArray[i] = marksArray[beg + i];    
	    for (j = 0; j < n2; j++)    
	    RightArray[j] = marksArray[mid + 1 + j];    
	      
	    i = 0; 
	    j = 0; 
	    k = beg; 
	      
	    while (i < n1 && j < n2)    
	    {    
	        if(LeftArray[i] >= RightArray[j])    
	        {    
	            marksArray[k] = LeftArray[i];    
	            i++;    
	        }    
	        else    
	        {    
	            marksArray[k] = RightArray[j];    
	            j++;    
	        }    
	        k++;    
	    }    
	    while (i<n1)    
	    {    
	        marksArray[k] = LeftArray[i];    
	        i++;    
	        k++;    
	    }    
	      
	    while (j<n2)    
	    {    
	        marksArray[k] = RightArray[j];    
	        j++;    
	        k++;    
	    }    
	}
	
	private void mergeSort(Double[] marksArray, int beg, int end)  
	{  
	    if (beg < end)   
	    {  
	        int mid = (beg + end) / 2;  
	        mergeSort(marksArray, beg, mid);  
	        mergeSort(marksArray, mid + 1, end);  
	        merge(marksArray, beg, mid, end);  
	    }  
	}  
	
	private HashMap<Integer, Long> heapSort(LinkedHashMap<String, Double> studentData) {
		ArrayList<Double> marks = new ArrayList<Double>(studentData.values());
		Double[] marksArray = new Double[marks.size()];
		marksArray = marks.toArray(marksArray);
		int length= marksArray.length;
		long startTime = System.nanoTime();
		sort(marksArray);
		HashMap<Integer, Long> result = new HashMap<Integer, Long>();
        LinkedHashMap<String, Double> sortadedMap = new LinkedHashMap<String, Double>();        
        for (Double str : marksArray) {
            for (Entry<String, Double> entry : studentData.entrySet()) {
                if (entry.getValue().equals(str)) {
                	sortadedMap.put(entry.getKey(), str);
                }
            }
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //writing the sorted data to a file
        File file = new File(outputFile+"\\heapsort"+startTime+".txt"); 
        BufferedWriter bf = null; 
        try {   
            bf = new BufferedWriter(new FileWriter(file)); 
            for (Map.Entry<String, Double> entry : 
            	sortadedMap.entrySet()) { 
  
                bf.write(entry.getKey() + ","
                         + entry.getValue()); 
                bf.newLine(); 
            } 
  
            bf.flush(); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
            try { 
                bf.close(); 
            } 
            catch (Exception e) { 
            } 
        }
        result.put(sortadedMap.size(), duration);
        return result;
	}

	private void sort(Double[] marksArray) {
		int length = marksArray.length;
		 
        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(marksArray, length, i);
 
        for (int i = length - 1; i > 0; i--) {
            Double temp = marksArray[0];
            marksArray[0] = marksArray[i];
            marksArray[i] = temp;
 
            heapify(marksArray, i, 0);
        }
		
	}

	private void heapify(Double[] marksArray, int length, int i) {
		 int largest = i;
	        int l = 2 * i + 1;
	        int r = 2 * i + 2;
	 
	        if (l < length && marksArray[l] < marksArray[largest])
	            largest = l;
	 
	        if (r < length && marksArray[r] < marksArray[largest])
	            largest = r;
	 
	        if (largest != i) {
	            Double swap = marksArray[i];
	            marksArray[i] = marksArray[largest];
	            marksArray[largest] = swap;
	 
	            heapify(marksArray, length, largest);
	        }
		
	}
	
}

