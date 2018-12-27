import java.io.FileNotFoundException;
import java.lang.Math;

public class Main implements DS2Interface {


    /* Implement these methods */

    @Override
    public int recursiveRodCutting(int[] input) {
    	int n = input.length; 	// assign the length of an array to n
    	return rodcut(input, n); //
    }
  
    int rodcut(int[] input, int n) {
    	if (n<=1) {				// if arraysize is smaller or equal to 1, return 0, satisfying the additional requirement A[0] = 0
    		return 0;
    	}		
    	int q=Integer.MIN_VALUE;
    	for(int i=1;i<n;i++){	// for i to n-1, decide optimal solution by choosing to cut or not
			q = Math.max(q, input[i] + rodcut(input, n-i)); // 2 choice up to n-1, so 2^n
    	}
    	return q;	
	}


	@Override
    public int dynamicRodCutting(int[] input) {
    	int m = input.length; 		// assign the length of an array to m
    	int[] result= new int[m+1]; // make a new empty array
    	result[0]=0;				// first index 0 becomes zero
    	
    	for(int j=1;j<=m;j++){		
    		int q=Integer.MIN_VALUE; //assign minimum integer to q				
    		for (int i=1; i<j; i++){
    			q= Math.max(q, input[i] + result[j - i]); //j to m, j times to check optimal solution= n^2
    			result[j]= q;		// Store the result of subproblem in an array and use it fur­ther rather than solv­ing it again(dynamic)
    		}
    	}
    	return result[m]; 			// returns the last index of new array, which is optimal solution
    }

    @Override
    public int matrix(int[][] input) {
    	int rows=input.length;
    	int columns=rows;
    	//declare the length of rows and columns with property matrix = n x n
    	
    	int maxSum=0;	//initialize maxSum at 0
    	
    	if(input==null || input.length==0){
    		return 0;
    	}
    	// if there are no elements in the array, return 0

    	int h = maxSumHorizontal(input, rows, columns); 
    	int v = maxSumVertical(input, rows, columns);	
    	int d = maxSumDiagonal(input, rows, columns);	 
    	int c = maxSumADiagonal(input, rows, columns); 
    	//create functions for MaxSum of every direction 
    	
    	return maxSum=h+v+d+c;						// returns maxSum, all the maxSum-directions combined!	 						
    }

	private int maxSumHorizontal(int[][] input, int rows, int columns) {	
		int maxSumH=0;	// initialize maxSum horizontal at 0
		int [] horizontal = new int[columns]; //make a horizontal array where you save the Max values 
		int [] tempMax = new int[columns];	//make a temporary array where you save the result
		
		//loop through matrix
		for(int r=0; r<rows; r++){	
			int max=0;				//for every row initialize new max 
			tempMax[0] = Math.max(input[r][0],0);	//for every first element of every row, check max compared to 0
			max=tempMax[0];
			for (int c=1; c<columns; c++){			
				tempMax[c]=Math.max(0, tempMax[c-1]+input[r][c]);	// from column 1 to last column (nested for loop n^2), save subresult in temporary array(dynamic)
				max=Math.max(max,tempMax[c]); //saves max by comparing old max with temporary max
			}
			horizontal[r]=max;	//saves all the horizontal max in an array
		}
		for (int i = 0; i < horizontal.length; i++) {
			if (maxSumH < horizontal[i]) {
				maxSumH = horizontal[i];
			}
		}
		//compares all the horizontal max and returns the biggest
		return maxSumH;
	}
	
	private int maxSumVertical(int[][] input, int rows, int columns) {
    	int maxSumV=0;		// initialize maxSum vertical with value 0
    	int [] vertical = new int[rows];	//make a vertical array where you save the Max values
		int [] tempMax = new int[rows];		//Mmke a temporary array where you save the result
		
		//loop through matrix
		for(int c=0; c<columns; c++){
			int max=0;	//for every column initialize new max 
			tempMax[0] = Math.max(input[0][c],0);	//for every first element of every check, check max compared to 0
			max=tempMax[0];
			for (int r=1; r<rows; r++){
				tempMax [r]=Math.max(0, tempMax[r-1]+input[r][c]); // from row 1 to last row (nested for loop n^2), save subresult in temporary array(dynamic)
				max=Math.max(max,tempMax[r]);	//saves max by comparing old max with temporary max
			}
			vertical[c]=max; //saves all the vertical max in an array
		}
		for (int i = 0; i < vertical.length; i++) {
			if (maxSumV < vertical[i]) {
				maxSumV = vertical[i];
			}
		}
		//compares all the vertical max and returns the biggest
		return maxSumV;
	}

    private int maxSumDiagonal(int[][] input, int rows, int columns) {		
    	int maxSumD=0;
    	int [] diagonal = new int[rows+columns-1];//There can be at most  n + n -1 diagonals to be printed
		int [] tempMax = new int[rows+1];
		int counter=0;		//counter to save all max diagonals in the diagonal array
		
		
		//split the looping problem in two halves
		for(int r=0; r<rows; r++){		//top half
			int max=0;
			tempMax[0] = 0;
			for (int c=0; c<=r; c++){
				int i=r-c;
				tempMax [c+1]=Math.max(0, tempMax[c]+input[i][c]);
				max=Math.max(max,tempMax[c+1]);
			}
			diagonal[counter]=max;	// save the top half diagonal max in an array
			counter++;
		}
		
		for(int r=rows-1; r>0; r--){	//lower half
			int max=0;
			int counter2=0;
			tempMax[0] = 0;
			for (int c=columns-1, j=0; r<=c; c--, j++){
				int i=r+j;
				tempMax[counter2+1]=Math.max(0, tempMax[counter2]+input[i][c]);
				max=Math.max(max,tempMax[counter2+1]);
				counter2++;
			}
			diagonal[counter]=max;	// save the lower half diagonal max in an array
			counter++;
		}
		
		//compares the max values in the diagonal array and returns the biggest
		for (int i = 0; i < diagonal.length; i++) {
			if (maxSumD < diagonal[i]) {
				maxSumD = diagonal[i];
			}
		} 	
    	return maxSumD;
	}
    
	private int maxSumADiagonal(int[][] input, int rows, int columns) {
    	int maxSumA=0;
    	int [] adiagonal = new int[rows+columns-1]; //There can be at most  rows + columns -1 diagonals to go through
		int [] tempMax = new int[rows+1];
		int counter=0;
		
		//split the looping problem in two halves
		for(int r=rows-1; r>0; r--){	//lower half
			int max=0;
			tempMax[0] = 0;
			for (int c=0, x=r; x<=rows-1; c++, x++){
				tempMax [c+1]=Math.max(0, tempMax[c]+input[x][c]);
				max=Math.max(max,tempMax[c+1]);
			}
			adiagonal[counter]=max;
			counter++;
		}
		
		for(int r=0; r<=rows-1; r++){	//top half
			int max=0;
			tempMax[0] = 0;
			for (int c=0, y=r; y<=rows-1; c++, y++){
				tempMax [c+1]=Math.max(0, tempMax[c]+input[c][y]);
				max=Math.max(max,tempMax[c+1]);
			}
			adiagonal[counter]=max;	// save the top half adiagonal max in an array
			counter++;
		}
		
		//compares the max values in the adiagonal array and returns the biggest 
		for (int i = 0; i < adiagonal.length; i++) {
			if (maxSumA < adiagonal[i]) {
				maxSumA = adiagonal[i];
			}
		}
		return maxSumA;
	}



	/* BEGIN UTIL FUNCTION. DO NOT TOUCH */



    void start(String assignment, String file) throws FileNotFoundException {
        switch (assignment) {
            case "rod": {
                int result = recursiveRodCutting(Util.readInput(file));
                System.out.printf("%d\n", result);
            }
                break;
            case "dynrod": {
                int result = dynamicRodCutting(Util.readInput(file));
                System.out.printf("%d\n", result);
                break;
            }
            case "matrix": {
                int result = matrix(Util.readMatrix(file));
                System.out.printf("%d\n", result);
                break;
            }
            default:
                System.out.printf("Invalid assignment provided: %s\n", assignment);
                printHelp(null);
                System.exit(1);
        }
    }

    static void printArray(int[] array) {
        for (int e: array) {
            System.out.printf("%d ", e);
        }
    }

    static void printMatrix(int[][] matrix) {
        for (int[] l: matrix) {
            printArray(l);
            System.out.printf("\n");
        }
    }

    static void printHelp(String[] argv) {
        System.out.printf("Usage: java -jar DS2.jar <assignment> [<input_file>]\n");
        System.out.printf("\t<algorithm>: rod, dynrod, matrix\n");
        System.out.printf("E.g.: java -jar DS2.jar rod example.txt\n");
    }

    public static void main(String argv[]) {
        if (argv.length == 0) {
            printHelp(argv);
            System.exit(1);
        }
        try {
            (new Main()).start(argv[0], argv.length < 2 ? null : argv[1]);
        } catch (FileNotFoundException e) {
            System.out.printf("File not found: %s", e.getMessage());
        }

    }

}
