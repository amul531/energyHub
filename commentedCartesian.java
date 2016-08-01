import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class commentedCartesian{
	//main
	
	public static void main(String[] args) throws IOException{ 
		/*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		System.out.println("Enter your string or 'y' to quit");
		while((input = br.readLine()) != null){
			if(input.toLowerCase().equals("y"))
				break;
			else
				print(bashCartesianProduct(input));
		}*/
		
	    String input = new String("afe{g,h{k,l{o,p}q}m}ij{x,y}"); 
		System.out.println("Input #1: "+input);
		print(bashCartesianProduct(input));

		input = new String("afe{{k,l{o,p}q}m}ij"); 
		System.out.println("\n\nInput #2: "+input);
		print(bashCartesianProduct(input));

		input = new String("ab{c,d}e{f,g}");  
		System.out.println("\n\nInput #3: "+input);
		print(bashCartesianProduct(input));

		input = new String("{a,b}d{e,f{g,h}}ij");
		System.out.println("\n\nInput #4: "+input);
		print(bashCartesianProduct(input));
		/*

		String input1 = new String();
		String[] input;
		String[] output;
        boolean flag = false;
        
	    //test#1 :non-nested string
		input1 = "a{b,c}d{e,f}g";
        System.out.println("Test#1 :"+input1);	
        input = bashCartesianProduct(input1).split(",");
        output = "abdeg,abdfg,acdeg,acdfg".split(",");
        for(int i=0;i<input.length;i++)
        	if(input[i].equals(output[i]));
         		flag = true;
        System.out.println(flag);
        
        //test#2 :non-nested string
        input1 = "{a,b}{c,d}";
        System.out.println("Test#2 :"+input1);	
        input = bashCartesianProduct(input1).split(",");
        output = "ac,ad,bc,bd".split(",");
	    flag = false;
        for(int i=0;i<input.length;i++)
        	if(input[i].equals(output[i]));
         		flag = true;
        System.out.println(flag);
        
        //test#3 :nested string
        input1 = "a{b,c{d,e}f,g}hi{j,k}";
        System.out.println("Test#3 :"+input1);	
        input = bashCartesianProduct(input1).split(",");
        output = "aabhij,abhik,acdfhij,acdfhik,acefhij,acefhik,aghij,aghik".split(",");
	    flag = false;
        for(int i=0;i<input.length;i++)
        	if(input[i].equals(output[i]));
         		flag = true;
        System.out.println(flag);
                        
        //test#4 : multiple-nested string
        input1 = "{{{{a,b},c}d,e}f,g}";
        System.out.println("Test#4 :"+input1);	
        input = bashCartesianProduct(input1).split(",");
	    output = "acd,bcd,cdf,ef,g".split(",");
	    flag = false;
        for(int i=0;i<input.length;i++)
        	if(input[i].equals(output[i]));
         		flag = true;
        System.out.println(flag);
        */
	}

	protected static String bashCartesianProduct(String input){
		StringBuilder temp = new StringBuilder(input);  
		ArrayList<int[]> nestedIndex = nestIndexList(input);                       //get indices of nested brackets(start and end) if they exist
		if(nestedIndex.size()==0)                                                  //if not nested, directly solve
			return finalProduct(temp.toString());
		else{                                                                      //if nested, open those out first and solve
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
		ArrayList<int[]> indices = new ArrayList<int[]>();                         //an arraylist to store the start and end position of each nested bracket
		int open = 0, start = 0, end = 0;
		int[] addIndex = new int[2];                                               //list to store the start and end indices of a nest
		boolean nested = false;                                                    //true if a nest is encountered
		for(int i = 0; i < input.length(); i++){			
			if(input.charAt(i) == '{'){                                            //check for nested open bracket
				open++;
				if(!nested && open == 2){
					start = i;
				System.out.println("start:"+start);
				}
			}
			if(input.charAt(i) == '}'){                                            //find the closing bracket that matches the above open bracket
				open--;     
				if(!nested && open == 1){
					end = i;
					System.out.println("end:"+end);
					nested = true;                                                 //change nested to true since a nest has been found
				}
			} 
			if(nested){                                                            //if a nested loop is found, go into it 
                while(Character.isLetter(input.charAt(start-1)))                   //check if it starts after a character
                	start--;
                boolean closed = false;                                            //true if close bracket found else false
				for(int j = end; j < input.length()-1; j++){                       //checking for further nests within the current nest
					System.out.println("char @ j:"+j+" "+input.charAt(j));
					if(input.charAt(j) == '}')
						closed = true;					
					if(closed && (Character.isLetter(input.charAt(j)) || input.charAt(j) == '}') && (input.charAt(j+1) == ',' || input.charAt(j+1) == '}')){
						end = j;
						System.out.println("new end:"+end);
						break;
					}
				}
				addIndex[0] = start;
				addIndex[1] = end;
				System.out.println("adding to array:"+start+ " "+end);
				indices.add(addIndex);
				nested = false;
				i = end;
			}                                                                      //end of if(nested)
		}
		return indices;
	}

	private static String finalProduct(String input){
		StringBuilder output = new StringBuilder();
		int open = 0;                                                              //to keep track of # of open(++) and close(--) braces. 0 indicates well balanced braces
		int count = 0;                                                             //total # of open braces encountered
		for(int i = 0; i < input.length(); i++){                                   //check to see if braces are well balanced
			if(input.charAt(i) == '{'){
				open++;
				count++; 
			}
			if(input.charAt(i) == '}')
				open--; 
		}
		if(open != 0)                                                              //error thrown if braces not well balanced
			throw new IllegalArgumentException("not well-formed: " + input);
		int[] openBr = new int[count];                                             //stores the indices of open brackets
		int[] closeBr = new int[count];                                            //stores the indices of close brackets
		int[] curOptn = new int[count];                                            //stores the indices of the current options
		int current = 0;

		for(int i = 0; i < input.length(); i++){                                   //get the indices of all the open and close braces
			if(input.charAt(i) == '{')
				openBr[current] = i;
			if(input.charAt(i) == '}')
				closeBr[current++] = i;
		}
		
		ArrayList<String[]> optionsList = new ArrayList<String[]>();
		for(int i = 0; i < count; i++)
			optionsList.add((input.substring(openBr[i] + 1, closeBr[i]).split(","))); //extract the substring from within the brackets and add it to the optionsLists

		System.out.println("--Size of OPTLIST:"+optionsList.size());
		int totalCombos = 1;                                                          
		for(int i = 0; i < optionsList.size(); i++){                               //total number of possible combinations based on the items in the optionsList
			totalCombos *= optionsList.get(i).length;
			System.out.println("total # of options for optionsList(i):"+i+"  "+totalCombos);
			String[] t =  optionsList.get(i);			
			for(int j=0;j<t.length;j++)
				System.out.print(t[j]+" ");
			System.out.println();
		}  
		
		for(int k = 0; k < totalCombos; k++){                                      //generate all possible outputs by iterating through all the combinations
			StringBuilder temp = new StringBuilder(input);
			for(int j = optionsList.size() - 1; j >= 0; j--)                       //replace the braces with a string based on the current option
			{
				System.out.println("\nitem in optList @ curOptn["+ j +"] pos: " +curOptn[j]+"  " +optionsList.get(j)[curOptn[j]]);
				System.out.print("Before replace:"+temp);
				temp.replace(openBr[j], closeBr[j]+1, optionsList.get(j)[curOptn[j]]);
				System.out.println("  after replace:"+temp);
			}
			output.append(temp.toString()).append(",");                            //append the value to the output list
			                                                                       //updating the array containing the "current" options and returning back an updated pointer for it                                    
			curOptn[count-1]++;                                                    //outer loop
			for(int i = count - 1; i > 0; i--){
				if(curOptn[i] == optionsList.get(i).length){
					curOptn[i] = 0;                                                //when current pointer reaches the bound, reset to 0
					curOptn[i-1]++;                                                //inner loop 
				}
			}
		}
		return output.deleteCharAt(output.length()-1).toString();
	}

	//helper function: to print a string array/list
	public static void print(String s){
		List<String> ip = Arrays.asList(s.split(","));  
		ip.forEach(i->System.out.print(i+" "));
		System.out.println();
	}
}