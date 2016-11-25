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
    			q= Math.max(q, input[i] + result[j - i]); //j to n, j times to check optimal solution= n^2
    			result[j]= q;		// Store the result of subproblem in an array and use it fur­ther rather than solv­ing it again(dynamic)
    		}
    	}
    	return result[m]; 			// returns the last index of new array, which is optimal solution
    }

    @Override
    public int matrix(int[][] input) {
    	int rows=input.length;
    	int columns=input[0].length;
    	if (rows<1 || columns<1){
    		return 0;
    	}
    	int maxSum=0;
    	
    	int h = maxSumHorizontal(input, rows, columns);
    	int v = maxSumVertical(input, rows, columns);
    	int d = maxSumDiagonal(input, rows, columns);
    	int c = maxSumAntiDiagonal(input, rows, columns);
    	
    	maxSum=h+v+d+c;	
		return maxSum; 
    }

	private int maxSumHorizontal(int[][] input, int rows, int columns) {
		
		int max=0;
		int [][]tempMax = new int[rows][columns];
		tempMax[0][0] = Math.max(input[0][0],0);
		max=tempMax[0][0];
		for(int r=0; r<rows; r++){
			for (int c=1; c<columns; c++){
				tempMax [r][c]=Math.max(0, tempMax[r][c-1]+input[r][c]);
				max=Math.max(max,tempMax [r][c]);
			}
		}return max;
	}
	
	private int maxSumVertical(int[][] input, int rows, int columns) {
		// TODO Auto-generated method stub
		return 0;
	}

    private int maxSumDiagonal(int[][] input, int rows, int columns) {
		
		return 0;
	}
    
	private int maxSumAntiDiagonal(int[][] input, int rows, int columns) {
		// TODO Auto-generated method stub
		return 0;
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
