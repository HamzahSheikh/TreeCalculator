// -----------------------------------------------------
// Assignment 3 
// Part 1
// Written by: Hamzah Sheikh 40103993 & Muhammad Ferreira 40113326 
// -----------------------------------------------------

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** 
 * @author Hamzah Sheikh, 40103993
 * @author Muhammad Ferreira, 40113326 
 */
public class BinaryTreeCalculator
{
	Node root;

	//Creating a private Node class for the Binary Tree
	private static class Node 
	{ 
		
	    String key; 
	    Node left, right;
	    

	    /** 
		 * parameterized constructor 1
		 * @param key is the data stored in the node of type String (Variables, integers, operands)
		 */
	    public Node(String key) 
	    { 
	        this.key = key; 
	        left = right = null; 
	    }
	    
	    /** 
		 * parameterized constructor 2
		 * @param key is the data stored in the node of type String (Variables, integers, operands)
		 * @param left is the pointer to the left node of the node
		 * @param right is the pointer to the right node of the node
		 */
	    public Node(String key, Node left, Node right) 
	    { 
	        this.key = key; 
	        this.left = left;
	        this.right = right;
	    } 
	} 
 	

    /** 
	 * parameterized constructor 
	 * @param root the data stored in the root when it is created
	 */
	BinaryTreeCalculator(String root) 
	{ 
		this.root = new Node(root); 
	} 
	
	/** 
	 * Default constructor, takes no parameter
	 */
	BinaryTreeCalculator() 
	{ 
		root = null; 
	} 

	/**
	 * Method used to be able to conduct the arithmetic calculation of the given equation
	 * @param z is the inserted equation of type String
	 */
	public static void arithmeticCalculator(String z, PrintWriter x)
	{
		//integer used to keep track of how many variables have been already stored in the array value.
		//this will be useful when adding the variable value into the array. We will now the first empty position.
		int place = 0;
		
		//Scanner used to take user input to allow the user to give a value to a variable
		Scanner in = new Scanner(System.in);
		
		//Array List which will store all the different variables, integers, operands within the equation
		ArrayList<String> a = new ArrayList<String>();
		
		//2D array used to store variable values so program can refer to it if there is a same variable repeated inside the equation
		//[x][0] will contain the variable
		//[x][1] will contain the designated value
		String[][] value = new String[z.length()][2];
		
		//for loop which will go character by character for the whole inserted string z
		for(int i = 0; i < z.length(); i++)
		{
			//Initialize q to the index+1 of the character being accessed  
			int q = i+1;
			
			//checks if the current char is a digit
			if(Character.isDigit(z.charAt(i)))
			{	
				//will keep on looping while there are still digits, adding +1 to q. This will allow numbers containing more than 1 digit to be recorded as one entity 
				while(q < z.length() && Character.isDigit(z.charAt(q)))
				{
					q++;
				}	
			}
			
			//will check if there is a negative operand at the previous index. If there is a -, this part of code will remove the - from the array list
			//replacing it with a +, adding the - to the number that follows.
			// ex. - 34 => + -34
			if(a.size() != 0 && a.get(a.size()-1).equalsIgnoreCase("-")) 
			{
				//removes the -
				a.remove(a.size()-1);
				
				//checks if the number is being divided or multiplied. In this case it will not add +
				if(a.size() == 0 || a.get(a.size()-1).equalsIgnoreCase("*") || a.get(a.size()-1).equalsIgnoreCase("/") || a.get(a.size()-1).equalsIgnoreCase("(")) 
					a.add("-"+z.substring(i,q));
				else 
				{
					a.add("+");
					a.add("-"+z.substring(i,q));
				}
				
				//If there is a - in front of the ( ) it will multiply the () with -1
				//ex. - (4 + 3 ) => -1 * ( 4 + 3 )
				if(a.get(a.size()-1).equalsIgnoreCase("-(")) {
					a.remove(a.size()-1);
					a.add("-1");
					a.add("*");
					a.add("(");
				}
			}
			//otherwise it will just add the number to the arraylist
			else
				a.add(z.substring(i,q));
		
			//this will check if the char being looked  at is a variable
			if( ( a.get(a.size()-1).length() == 1 && Character.isAlphabetic(a.get(a.size()-1).charAt(0)) ) || ( a.get(a.size()-1).length() > 1 && Character.isAlphabetic(a.get(a.size()-1).charAt(1)) ) )
			{
				//Initializes found to false, which will be used to determine if the value is contained within the array storing all previous variables information
				boolean found = false;
				//will save the index of where the variable information was found
				int where = 0;
				//will tell if the variable is negative
				boolean negative = false;
				
				//Will look inside the value array to see if the variable was already saved, while also checking if it is a - 
				for(int t = 0; t < place; t++)
				{
					if(a.get(a.size()-1).equalsIgnoreCase(value[t][0]))
					{
						found = true;
						where = t;
					}
					
					if(a.get(a.size()-1).equalsIgnoreCase("-"+value[t][0]))
					{
						found = true;
						where = t;
						negative = true;
					}
					
				}
				
				//if it was found, it will remove the variable and add the correct value
				if(found)
				{
					if(!negative) {
						a.remove(a.size()-1);
						a.add(value[where][1]);
					}
					else {
						a.remove(a.size()-1);
						a.add("-"+value[where][1]);
					}
				}
				//it will ask the user to register the value of the variable and save it if ever the variable is repeated
				else
				{
					String e = a.get(a.size()-1);
					
					if(e.charAt(0) == '-') 
					{
						System.out.print("What is the value of variable "+e.substring(1)+": ");
						a.remove(a.size()-1);
						String d = in.nextLine();
						a.add("-"+d);
						value[place][1] =d;
						value[place][0] = e.substring(1);
						place++;
					}
					else 
					{
						System.out.print("What is the value of variable "+e+": ");
						a.remove(a.size()-1);
						String d = in.nextLine();
						a.add(d);
						value[place][0] = e;
						value[place][1] =d;
						place++;
					}
				}
			}
			
			//reinitializes the i depending on how many characters were skipped
			i = q-1;
		}
		
		System.out.println();
		
	//*********************************************************
		for(int i = 0; i<a.size(); i++)
			System.out.print(a.get(i)+" ");
	//*********************************************************
		
		System.out.println();
		
		//creates an array that will store all the data contained in the array list 
		String [] t = new String[a.size()];
		
		//Transfer of data from Array list a to array t 
		for(int i = 0; i<a.size(); i++)
			t[i] = a.get(i);
			
		//will create a tree using all the values inside the array t
		Node w = addArray(t);

		//Prints initial eqn
		x.println(z);
		//Prints the obtained answer
		System.out.println();
		System.out.print(z+": ");
		x.println(doCalc(w));
		System.out.println(doCalc(w));
		//Skips a line
		x.println();
		//Flushes
		x.flush();
	
	}
	
	/**
	 * Recursive Method used to add values within an array properly into a binary tree
	 * @param a is the inserted array of type String
	 * @return is a Node, aka binary tree
	 */
	public static Node addArray(String[] a)
	{
		
		Node temp = new Node(null);
		int root = 0;
		int para = 0;
		int check = 0;
		int prec = -1;
		boolean ParaCheck = false;

		//Stopping case, where if the size is 0, we know its the end of the tree
		if(a.length == 0)
		{
			return null;
		}
		
		//Stopping case 2, this will basically know when the last item added is a integer
		if(a.length == 1)
		{
			temp.key = a[0];
			temp.left = new Node(null);
			temp.right = new Node(null);
			return temp;
		}
	
		//will keep looping until a break;
		while(ParaCheck == false) 
		{
			//loop which will look for the item with the lowest precedence. We know that that will be the root
			for(int i = 0; i < a.length; i++)
			{
				if(a[i].charAt(0) == '(')
					para++;
			
				if(para <= 0 && a[i].length() == 1 && (findPrec(a[i].charAt(0)) >= prec) )
				{
					root = i;
					prec = findPrec(a[i].charAt(0));
					ParaCheck = true;
				}
				
				if(a[i].charAt(0) == ')')
					para--;
			}
			
			if(ParaCheck == true)
				break;
			
			para--;
			check++;
		}
		
		//will create 2 new arrays with everything on the left of the array and right of the array from the index at which the lowest precedence item was found
		String[] aL = Arrays.copyOfRange(a, check, root);
		String[] aR = Arrays.copyOfRange(a, root+1, a.length-check);

		//lowest precedence item will be saves as the key
		temp.key = a[root];
		
		//Recursive call which will keep looping until an integer is the last item 
		temp.right = addArray(aR);
		temp.left = addArray(aL);
		
		//return the binary tree
		return temp;
		
	}
	
	/**
	 * Method used to print out the tree in Inorder. This was used to look if items were properly saved inside the tree while writing the program
	 * @param node is the root of that binary tree
	 */
	static void printInorder(Node node) 
    { 
		//Stoping case is when a null is found (following an external node)
        if (node == null) 
        {
        	System.out.print("null ");
        	 return;
        }
            
  
        /* first recur on left child */
        printInorder(node.left); 
  
        /* then print the data of node */
        System.out.print(node.key + " "); 
  
        /* now recur on right child */
        printInorder(node.right); 
    } 
	
	/**
	 * Method that will carry out the calculations within the tree.
	 * @param a is the root of the tree being processed
	 * @return int value of the final answer
	 */
	public static int doCalc(Node a)
	{
		//Stopping case. When you hit an external node
		if (Character.isDigit(a.key.charAt(0)) || a.key.length()>1) 
        {
        	return Integer.parseInt(a.key);
        }

		//recursion, will keep doing calculation. will do higher precedence first 
        return doIt(doCalc(a.left) ,doCalc(a.right) , (a.key).charAt(0));
	}
	
	
	/**
	 * Simple method used to do operations 
	 * @param dummy is a int that will be on the left 
	 * @param dummy2 is a int that will be on the right
	 * @param opDummy is what operation is being carried out
	 * @return a int value of the result of the operation
	 */
	public static int doIt(int dummy, int dummy2, char opDummy) 
	{ 
		switch(opDummy) 
		{ 
			case '+': return dummy + dummy2; 
			
			
			case '-': return dummy - dummy2; 
			
			
			case '*': return dummy * dummy2; 
			
			
			case'/': return dummy/dummy2;  
			
			
			default: return 0;
		}
	}
	
	/**
	 * Method used to find the precedence of an operation
	 * @param a is the operand being assessed
	 * @return int value used to rank the operands precedence
	 */
	public static int findPrec(char a) 
	{ 
		switch(a) 
		{
			
			case '!': return 2;
			
			case '3':return 3;
			
			case '^': return 4;
			
			case '*': case '/': return 5;
			
			case '+':case '-': return 6;
			
			case '>': case '≥': case '≤': case '<': return 7;
			
			case '=': return 8;
		}
		return -1;
	}
	
	public static void main(String[] args)
	{
		System.out.println("WELCOME to the BINARY TREE CALCULATOR!!");
		System.out.println();
		
		//Declaring new BufferedReader to read file with equations 
		BufferedReader in = null;
		
		//Opening of the file
		try 
		{
			in = new BufferedReader(new FileReader("TEST.txt"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//String used to save a whole line worth of content in the file
		String content;

		//ArrayList used to save the data, each index is one line
		ArrayList<String> eqn = new ArrayList<String>();
		
		//Loop to add the values inside the ArrayList
		try 
		{
			while((content = in.readLine()) != null)
			{
			    eqn.add(content.replace("\\s", ""));
			}
		} 
		catch (IOException e1) 
		{
	
			System.out.print("There was an Error");
		}
		
		
		//Creating the New File to write to
		File ans = new File("ANS.txt");
		
		//Opening the PrintWritter to allow for writing inside the newly created file. 
		try 
		{
			PrintWriter hey = new PrintWriter(new FileOutputStream(ans));
			
			//loop which will get the answers of the equation of each line. The PW is sent into the method as well so it can be used there.
			for(int i = 0; i<eqn.size(); i++) 
			{
				System.out.println(i+"=>");
				arithmeticCalculator(((String)eqn.get(i)).replaceAll("\\s",""), hey);
			}

			//closing the PW
			hey.close();
			
		} 
		catch (FileNotFoundException e1) 
		{
			
			e1.printStackTrace();
		}
	}
}
