/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irrecommen;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author tejeshwar
 */
public class SVD {

    public double getSVDError(int rows,int columns) throws IOException{
    	columns=1000;
double a[][] = new double[rows][columns];
        
        BufferedReader br = new BufferedReader(new FileReader("/media/tejeshwar/E/java codes/IR_Assignment2/movies.csv"));
        HashMap<Integer, Integer> hmap = new HashMap<>();
        int i;
        br.readLine();
        
        //read movie file , store values in the array.
        for (i = 0; i < columns; i++) {
            String line = br.readLine();
            String line1[] = line.split(",");
            hmap.put(Integer.parseInt(line1[0]), i);
        }
        
        br.close();
        
        // read user,movie and rating from file 
        BufferedReader br1 = new BufferedReader(new FileReader("/media/tejeshwar/E/java codes/IR_Assignment2/ratings20.csv"));
        br1.readLine();
        int l,m,j;
        double r;
        int numMovies=0;
        switch(rows){
        case 2:
        	numMovies=5;
        	break;
        case 3:
        	numMovies=81;
        	break;
        case 4:
        	numMovies=101;
        	break;
        case 5:
        	numMovies=172;
        	break;
        case 6:
        	numMovies=198;
        	break;
        case 7:
        	numMovies=205;
        	break;
        case 8:
        	numMovies=274;
        	break;
        case 9:
        	numMovies=300;
        	break;
        case 10:
        	numMovies=315;
        	break;
        case 11:
        	numMovies=333;
        	break;
        case 12:
        	numMovies=343;
        	break;
        case 13:
        	numMovies=358;
        	break;
        case 14:
        	numMovies=375;
        	break;
        case 15:
        	numMovies=377;
        	break;
        case 16:
        	numMovies=646;
        	break;
        case 17:
        	numMovies=651;
        	break;
        case 18:
        	numMovies=733;
        	break;
        case 19:
        	numMovies=784;
        	break;
        
        case 20:
        	numMovies=1120;
        	break;
        case 21:
        	numMovies=1170;
        break;
        		
        }
        for (i = 0; i < numMovies; i++) {
            String line = br1.readLine();
            String line2[] = line.split(",");
            l = Integer.parseInt(line2[0]);
            
            m = hmap.get(Integer.parseInt(line2[1]));
            r = Double.parseDouble(line2[2]);
            a[l][m] = r;
            
        }

        br1.close();

        //matrix a Obtained from the given data
        /*System.out.println();
        System.out.println("**********************************************************");
        System.out.println("A Matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
*/
        //let's make U matrix
        //transpose of A matrix
        double at[][] = new double[columns][rows];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                at[j][i] = a[i][j];
            }
        }

        /*
        // printing a transpose matrix
        for (i = 0; i < columns; i++) {
            for (j = 0; j < rows; j++) {
                System.out.print(at[i][j] + " ");
            }
            System.out.println();
        }*/
        
        //multiply a (rowsxcolumns) with atranspose (columnsxrows) to Matrix U
        double u[][] = new double[rows][rows];
        for (i = 0; i < rows; i++) { 
            for (j = 0; j < rows; j++) { 
                for (int k = 0; k < columns; k++) {
                    u[i][j] += a[i][k] * at[k][j];
                }
            }
        }

       /* for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                System.out.print(u[i][j] + " ");
            }
            System.out.println();
        }*/

        Matrix mat = new Matrix(u);
        EigenvalueDecomposition evd = new EigenvalueDecomposition(mat);
        Matrix sigma = evd.getD();
        double sigmaa[][] = sigma.getArray();

        Matrix m1 = evd.getV();
        double b[][] = m1.getArray();

//        System.out.println("**********************************************************");
//        System.out.println("**********************************************************");
//        System.out.println("Sigma matrix");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                if (sigmaa[i][j] < 0) {
                    sigmaa[i][j] = 0;
                   
                }
                //System.out.print(sigmaa[i][j] + " ");
            }

//            System.out.println();
        }

        //multiply at (columnsxrows) with a (rowsxcolumns) to obtain U transpose matrix
        double uT[][] = new double[columns][columns];
        for (i = 0; i < columns; i++) { 
            for (j = 0; j < columns; j++) { 
                for (int k = 0; k < rows; k++) { 
                    uT[i][j] += at[i][k] * a[k][j];
                }
            }
        }
        
        Matrix mNew = new Matrix(uT);
        EigenvalueDecomposition evd1 = new EigenvalueDecomposition(mNew);

        Matrix m11 = evd1.getV();
        double b1[][] = m11.getArray();

        double b1t[][] = new double[columns][columns];
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns; j++) {
                b1t[j][i] = b1[i][j];
            }
        }

        double temp;
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns / 2; j++) {
                temp = b1[i][j];
                b1[i][j] = b1[i][columns - j - 1];
                b1[i][columns - j - 1] = temp;
            }
        }

        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows / 2; j++) {
                temp = b[i][j];
                b[i][j] = b[i][rows - j - 1];
                b[i][rows - j - 1] = temp;
            }
        }
        
        /*System.out.println("**********************************************************");
        System.out.println("**********************************************************");
        System.out.println("U matrix");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                System.out.print(b[i][j] + "    ");
            }
            System.out.println();
        }*/

        //for sigma matrix Arranging the terms in decreasing order
        for (i = 0; i < rows / 2; i++) {
            temp = sigmaa[i][i];
            sigmaa[i][i] = sigmaa[rows - i - 1][rows - i - 1];
            sigmaa[rows - i - 1][rows - i - 1] = temp;
        }

        double sigmaFinal[][] = new double[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                sigmaa[i][j] = Math.sqrt(sigmaa[i][j]);
                sigmaFinal[i][j] = sigmaa[i][j];
            }
        }

     /*   System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Sigma Final matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(sigmaFinal[i][j] + "     ");
            }
            System.out.println();
        }
*/
        //for v transpose matrix arranging the terms in Decreasing order
        for (i = 0; i < columns / 2; i++) {
            for (j = 0; j < columns; j++) {
                temp = b1t[i][j];
                b1t[i][j] = b1t[columns - i - 1][j];
                b1t[columns - i - 1][j] = temp;
            }
        }

        /*System.out.println("V Transpose matrix");
        for (i = 0; i < columns; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(b1t[i][j] + "    ");
            }
            System.out.println();
        }*/

        double temp1[][] = new double[rows][columns];
        for (i = 0; i < rows; i++) { 
            for (j = 0; j < columns; j++) {
                for (int k = 0; k < columns; k++) { 
                    temp1[i][j] += (a[i][k] * b1[k][j]);
                }
            }
        }
/*System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated A*V matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(temp1[i][j] + "    ");
            }
            System.out.println();
        }*/

        double finalU[][] = new double[rows][rows];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                if(sigmaFinal[j][j]!=0){
                    finalU[i][j] = temp1[i][j] / sigmaFinal[j][j];
                }
            }
        }

        double temp2[][] = new double[rows][columns];
        for (i = 0; i < rows; i++) { 
            for (j = 0; j < columns; j++) { 
                for (int k = 0; k < rows; k++) { 
                    temp2[i][j] += sigmaa[i][k] * temp1[k][j];
                }
            }
        }

        /**Calulating U matrix for known V matrix so that we get unique eigen values**/
/*System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated U matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < rows; j++) {
                System.out.print(finalU[i][j] + "    ");
            }
            System.out.println();
        }*/
        
        double error = 0;
        int k = 0;
        
        // Calculating U * Sigma Matrix
        double usigcal[][] = new double[rows][columns];
        for (i = 0; i < rows; i++) { 
            for (j = 0; j < columns; j++) { 
                for (k = 0; k < rows; k++) { 
                    usigcal[i][j] += finalU[i][k] * sigmaFinal[k][j];
                }
            }
        }

        // Calcuating U * Sigma * V Transpose matrix
        double amatcal[][] = new double[rows][columns];
        for (i = 0; i < rows; i++) { 
            for (j = 0; j < columns; j++) { 
                for (k = 0; k < columns; k++) { 
                    amatcal[i][j] += usigcal[i][k] * b1t[k][j];
                }
            }
        }

        //error calculation
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                error += Math.pow((a[i][j] - amatcal[i][j]), 2);
            }
        }

      /*  System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Calculated finally the A matrix");
        System.out.println("**********************************************************");
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(amatcal[i][j] + "    ");
            }
            System.out.println();
        }*/
        error = Math.sqrt(error);
       /* System.out.println();
        System.out.println("**********************************************************");
        System.out.println("Error is  " + error);
        System.out.println("**********************************************************");*/
        hmap.clear();
        return error;
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
//    public static void main(String[] args) throws IOException {
//        
//        double a[][] = new double[21][1000];
//        
//        BufferedReader br = new BufferedReader(new FileReader("/home/tejeshwar/Downloads/movies.csv"));
//        HashMap<Integer, Integer> hmap = new HashMap<>();
//        int i, c = 0;
//        br.readLine();
//        
//        //read movie file , store values in the array.
//        for (i = 0; i < 1000; i++) {
//            String line = br.readLine();
//            String line1[] = line.split(",");
//            hmap.put(Integer.parseInt(line1[0]), i);
//        }
//        
//        br.close();
//        
//        // read user,movie and rating from file 
//        BufferedReader br1 = new BufferedReader(new FileReader("/home/tejeshwar/Downloads/ratings20.csv"));
//        br1.readLine();
//        int l,m,j;
//        double r;
//        for (i = 0; i < 1170; i++) {
//            String line = br1.readLine();
//            String line2[] = line.split(",");
//            l = Integer.parseInt(line2[0]);
//            m = hmap.get(Integer.parseInt(line2[1]));
//            r = Double.parseDouble(line2[2]);
//            a[l][m] = r;
//        }
//
//        br1.close();
//
//        //matrix a Obtained from the given data
//        System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("A Matrix");
//        System.out.println("**********************************************************");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                System.out.print(a[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        //let's make U matrix
//        //transpose of A matrix
//        double at[][] = new double[1000][21];
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                at[j][i] = a[i][j];
//            }
//        }
//
//        /*
//        // printing a transpose matrix
//        for (i = 0; i < 1000; i++) {
//            for (j = 0; j < 21; j++) {
//                System.out.print(at[i][j] + " ");
//            }
//            System.out.println();
//        }*/
//        
//        //multiply a (21x1000) with atranspose (1000x21) to Matrix U
//        double u[][] = new double[21][21];
//        for (i = 0; i < 21; i++) { 
//            for (j = 0; j < 21; j++) { 
//                for (int k = 0; k < 1000; k++) {
//                    u[i][j] += a[i][k] * at[k][j];
//                }
//            }
//        }
//
//       /* for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                System.out.print(u[i][j] + " ");
//            }
//            System.out.println();
//        }*/
//
//        Matrix mat = new Matrix(u);
//        EigenvalueDecomposition evd = new EigenvalueDecomposition(mat);
//        Matrix sigma = evd.getD();
//        double sigmaa[][] = sigma.getArray();
//
//        Matrix m1 = evd.getV();
//        double b[][] = m1.getArray();
//
////        System.out.println("**********************************************************");
////        System.out.println("**********************************************************");
////        System.out.println("Sigma matrix");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                if (sigmaa[i][j] < 0) {
//                    sigmaa[i][j] = 0;
//                    c++;
//                }
//                //System.out.print(sigmaa[i][j] + " ");
//            }
//
////            System.out.println();
//        }
//
//        //multiply at (1000x21) with a (21x1000) to obtain U transpose matrix
//        double uT[][] = new double[1000][1000];
//        for (i = 0; i < 1000; i++) { 
//            for (j = 0; j < 1000; j++) { 
//                for (int k = 0; k < 21; k++) { 
//                    uT[i][j] += at[i][k] * a[k][j];
//                }
//            }
//        }
//        
//        Matrix mNew = new Matrix(uT);
//        EigenvalueDecomposition evd1 = new EigenvalueDecomposition(mNew);
//
//        Matrix m11 = evd1.getV();
//        double b1[][] = m11.getArray();
//
//        double b1t[][] = new double[1000][1000];
//        for (i = 0; i < 1000; i++) {
//            for (j = 0; j < 1000; j++) {
//                b1t[j][i] = b1[i][j];
//            }
//        }
//
//        double temp;
//        for (i = 0; i < 1000; i++) {
//            for (j = 0; j < 1000 / 2; j++) {
//                temp = b1[i][j];
//                b1[i][j] = b1[i][1000 - j - 1];
//                b1[i][1000 - j - 1] = temp;
//            }
//        }
//
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21 / 2; j++) {
//                temp = b[i][j];
//                b[i][j] = b[i][21 - j - 1];
//                b[i][21 - j - 1] = temp;
//            }
//        }
//        
//        /*System.out.println("**********************************************************");
//        System.out.println("**********************************************************");
//        System.out.println("U matrix");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                System.out.print(b[i][j] + "    ");
//            }
//            System.out.println();
//        }*/
//
//        //for sigma matrix Arranging the terms in decreasing order
//        for (i = 0; i < 21 / 2; i++) {
//            temp = sigmaa[i][i];
//            sigmaa[i][i] = sigmaa[21 - i - 1][21 - i - 1];
//            sigmaa[21 - i - 1][21 - i - 1] = temp;
//        }
//
//        double sigmaFinal[][] = new double[21][1000];
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                sigmaa[i][j] = Math.sqrt(sigmaa[i][j]);
//                sigmaFinal[i][j] = sigmaa[i][j];
//            }
//        }
//
//        System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("Sigma Final matrix");
//        System.out.println("**********************************************************");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                System.out.print(sigmaFinal[i][j] + "     ");
//            }
//            System.out.println();
//        }
//
//        //for v transpose matrix arranging the terms in Decreasing order
//        for (i = 0; i < 1000 / 2; i++) {
//            for (j = 0; j < 1000; j++) {
//                temp = b1t[i][j];
//                b1t[i][j] = b1t[1000 - i - 1][j];
//                b1t[1000 - i - 1][j] = temp;
//            }
//        }
//
//        /*System.out.println("V Transpose matrix");
//        for (i = 0; i < 1000; i++) {
//            for (j = 0; j < 1000; j++) {
//                System.out.print(b1t[i][j] + "    ");
//            }
//            System.out.println();
//        }*/
//
//        double temp1[][] = new double[21][1000];
//        for (i = 0; i < 21; i++) { 
//            for (j = 0; j < 1000; j++) {
//                for (int k = 0; k < 1000; k++) { 
//                    temp1[i][j] += (a[i][k] * b1[k][j]);
//                }
//            }
//        }
//System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("Calculated A*V matrix");
//        System.out.println("**********************************************************");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                System.out.print(temp1[i][j] + "    ");
//            }
//            System.out.println();
//        }
//
//        double finalU[][] = new double[21][21];
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                if(sigmaFinal[j][j]!=0){
//                    finalU[i][j] = temp1[i][j] / sigmaFinal[j][j];
//                }
//            }
//        }
//
//        double temp2[][] = new double[21][1000];
//        for (i = 0; i < 21; i++) { 
//            for (j = 0; j < 1000; j++) { 
//                for (int k = 0; k < 21; k++) { 
//                    temp2[i][j] += sigmaa[i][k] * temp1[k][j];
//                }
//            }
//        }
//
//        /**Calulating U matrix for known V matrix so that we get unique eigen values**/
//System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("Calculated U matrix");
//        System.out.println("**********************************************************");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 21; j++) {
//                System.out.print(finalU[i][j] + "    ");
//            }
//            System.out.println();
//        }
//        
//        double error = 0;
//        int k = 0;
//        
//        // Calculating U * Sigma Matrix
//        double usigcal[][] = new double[21][1000];
//        for (i = 0; i < 21; i++) { 
//            for (j = 0; j < 1000; j++) { 
//                for (k = 0; k < 21; k++) { 
//                    usigcal[i][j] += finalU[i][k] * sigmaFinal[k][j];
//                }
//            }
//        }
//
//        // Calcuating U * Sigma * V Transpose matrix
//        double amatcal[][] = new double[21][1000];
//        for (i = 0; i < 21; i++) { 
//            for (j = 0; j < 1000; j++) { 
//                for (k = 0; k < 1000; k++) { 
//                    amatcal[i][j] += usigcal[i][k] * b1t[k][j];
//                }
//            }
//        }
//
//        //error calculation
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                error += Math.pow((a[i][j] - amatcal[i][j]), 2);
//            }
//        }
//
//        System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("Calculated finally the A matrix");
//        System.out.println("**********************************************************");
//        for (i = 0; i < 21; i++) {
//            for (j = 0; j < 1000; j++) {
//                System.out.print(amatcal[i][j] + "    ");
//            }
//            System.out.println();
//        }
//        error = Math.sqrt(error);
//        System.out.println();
//        System.out.println("**********************************************************");
//        System.out.println("Error is  " + error);
//        System.out.println("**********************************************************");
//    }
}
