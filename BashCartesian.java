/*
Clone or download this program and run it through command line or any java editor of your choice.
Uncomment any testing block of choice in the main function before compiling and running it.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BashCartesian{
	//main

	public static void main(String[] args) throws IOException{ 
		String inputStr;
		//Uncomment and run this to test it with your own input
		/*
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your string or 'y' to quit");
		while((inputStr = br.readLine()) != null){
			if(inputStr.toLowerCase().equals("y"))
				break;
			else
				print(bashCartesianProduct(inputStr));
		}
		 */		
		/*inputStr = new String("a{b,c{d,e}f,g}hi{j,k}"); 
		System.out.println("Input #1: "+inputStr);
		print(bashCartesianProduct(inputStr));
		//expected output:abhij,abhik,acdfhij,acdfhik,acefhij,acefhik,aghij,aghik
		*/
	}

	protected static String bashCartesianProduct(String input){
		StringBuilder temp = new StringBuilder(input);  
		ArrayList<int[]> nestedIndex = nestIndexList(input);            		   //get indices of nested brackets(start and end) if they exist
		if(nestedIndex.size()==0)                                                          //if not nested, directly solve
			return finalProduct(temp.toString());
		else{                                                                              //if nested, open those out first and solve
			for(int i = nestedIndex.size() - 1; i >= 0; i--){            
				int[] innerNest = nestedIndex.get(i);								
				String nestPart = temp.substring(innerNest[0], innerNest[1] + 1);  //extract the bracket part for further nested check 
				String subListCheck = bashCartesianProduct(nestPart);              //recursive call made on the subset for further nested loop check
				temp.replace(innerNest[0], innerNest[1] + 1, subListCheck);        //replace nested part with flattened result
			}      
			return finalProduct(temp.toString());  
		}
	}

	private static ArrayList<int[]> nestIndexList(String input){
		ArrayList<int[]> indices = new ArrayList<int[]>();                                 //an arraylist to store the start and end position of each nested bracket
		int open = 0, start = 0, end = 0;                                                      //list to store the start and end indices of a nest
		boolean nested = false;                                                            //true if a nest is encountered
		for(int i = 0; i < input.length(); i++){			
			if(input.charAt(i) == '{'){                                                //check for nested open bracket
				open++;
				if(!nested && open == 2)
					start = i;
			}			
			if(input.charAt(i) == '}'){                                                //find the closing bracket that matches the above open bracket
				open--;     
				if(!nested && open==1){
					end = i;
					nested = true;                                             //change nested to true since a nest has been found
				}
			} 
			if(nested){                                                                //if a nested loop is found, go into it 
				while(Character.isLetter(input.charAt(start-1)))                   //check if it starts after a character
					start--;
				boolean closed = false;                                            //true if close bracket found else false
				for(int j = end; j < input.length()-1; j++){                       //checking for further nests within the current nest
					if(input.charAt(j) == '}'){
						closed = true;
					}
					if(closed && (input.charAt(j+1) == ',' || input.charAt(j+1) == '}')){
						end = j;
						break;
					}
				}
				i = end;
				updateIndex(start,end,indices);
				nested = false;
			}  //end of if(nested)
		}
		return indices;
	}	
	
	//updates the arrayList which contains the indices of nested braces 
	public static void updateIndex(int s, int e, ArrayList<int[]> indices ){             
		int[] addIndex = new int[2]; 
		addIndex[0] = s;
		addIndex[1] = e;
		indices.add(addIndex);
	}

	private static String finalProduct(String input){
		StringBuilder output = new StringBuilder();                                 
		int count = checkBraces(input);                                                    //total # of open braces encountered		

		int[] openBr = new int[count];                                             	   //stores the indices of open brackets
		int[] closeBr = new int[count];                                            	   //stores the indices of close brackets
		getBraceIndices(input, openBr, closeBr);

		ArrayList<String[]> braceContents = braceContents(input, openBr, closeBr);

		int totalCombos = numOfCombinations(braceContents);                                            	   
		output = generateAllComb(input, totalCombos, braceContents, openBr, closeBr);
		return output.deleteCharAt(output.length()-1).toString();
	}

	//generates all possible outputs for the given combinations
	public static StringBuilder generateAllComb(String input, int total, ArrayList<String[]> braceContents, int[] openBr, int[] closeBr){
		StringBuilder output = new StringBuilder();  
		int[] curItem = new int[openBr.length];                                            //stores the indices of the current options
		
		for(int k = 0; k < total; k++){                                                    //generate all possible outputs by iterating through all the combinations
			StringBuilder temp = new StringBuilder(input);
			for(int j = braceContents.size() - 1; j >= 0; j--){                         //replace the braces with a string based on the current option
				temp.replace(openBr[j], closeBr[j]+1, braceContents.get(j)[curItem[j]]);
			}
			output.append(temp.toString()).append(",");                                //append the value to the output list
			                                                                           //updating the array containing the "current" options and returning back an updated pointer for it                                    
			curItem[openBr.length-1]++;                                                //outer loop
			for(int i = openBr.length - 1; i > 0; i--){
				if(curItem[i] == braceContents.get(i).length){
					curItem[i] = 0;                                            //when current pointer reaches the bound, reset to 0
					curItem[i-1]++;                                            //inner loop 
				}
			}
		}
		return output;
	}

	//total number of possible combinations based on the items in braceContents
	private static int numOfCombinations(ArrayList<String[]> braceContents){
		return braceContents.stream().mapToInt(strings -> strings.length).reduce(1, (a, b) -> a * b);
	}

	//extract the substring from within the brackets and add it to an arrayList
	private static ArrayList<String[]> braceContents(String input,int[] openBr, int[] closeBr){
		ArrayList<String[]> inBraceAsList = new ArrayList<String[]>();              	
		for(int i = 0; i < openBr.length; i++)
		{
			inBraceAsList.add((input.substring(openBr[i] + 1, closeBr[i]).split(","))); 
		}
		return inBraceAsList;
	}

	private static void getBraceIndices(String input, int[] openBr, int[] closeBr){
		int current = 0;
		for(int i = 0; i < input.length(); i++){                                   	   
			if(input.charAt(i) == '{'){
				openBr[current] = i;
			}
			if(input.charAt(i) == '}'){
				closeBr[current++] = i;
			}
		}
	}

	//this checks if the braces are well balanced and returns back the total # of pairs
	private static int checkBraces(String input){
		Stack<Character> braces = new Stack<Character>();                                  
		int count =0;
		for(int i = 0; i < input.length(); i++){                                         
			if(input.charAt(i) == '{'){
				braces.push(input.charAt(i));
				count++; 
			}
			if(input.charAt(i) == '}')
			{
				braces.pop();
			}
		}
		if(!braces.empty()){                                                         	   
			throw new IllegalArgumentException("String not well formed- " + input);
		}
		return count;
	}

	public static void print(String s){                                            		   
		List<String> ip = Arrays.asList(s.split(","));  
		ip.forEach(i->System.out.print(i+" "));
		System.out.println();
	}
}
