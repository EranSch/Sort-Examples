package sh.eran.SortExamples;

import java.util.Random;

/**
 * @author Eran
 */

/*
 * This is a very basic concept without much fluff... Mostly just trying to 
 * understand the concept for myself here. Maybe this would be useful for
 * someone else doing the same.
 */

public class SortExamples {
    
    /*
     * Three sets of data, one approximatley random, one inverted, and a third
     * which is already sorted. I've prepared three sort tests with each
     * data set. 
     */
    
    private static int NUMBERS_TO_SORT = 200;

    public static void main(String[] args) {
        
        final int [] randomData   =     generateRandomNumberArray(NUMBERS_TO_SORT);
        final int [] invertedData =     generateNumberArray(NUMBERS_TO_SORT,false);
        final int [] sortedData   =     generateNumberArray(NUMBERS_TO_SORT,true); 
        
        SortExamples test = new SortExamples();
        
        System.out.println("\nInsertion Sort Tests");
        test.insertionSortDemo( randomData, invertedData, sortedData );
        
        System.out.println("\nSelection Sort Tests");
        test.selectionSortDemo( randomData, invertedData, sortedData );
    }
    
    
    private void insertionSortDemo( int[] randomData, int[] invertedData, int[] sortedData ){
        insertionSort(randomData.clone());
        insertionSort(invertedData.clone());
        insertionSort(sortedData.clone());
    }

    private void selectionSortDemo( int[] randomData, int[] invertedData, int[] sortedData ){
        selectionSort(randomData.clone());
        selectionSort(invertedData.clone());
        selectionSort(sortedData.clone());
    }

    private int[] insertionSort( int[] array ) {
        
        Data data = new Data( array );
        
        /*
         * This just some stuff that's going to help reflect on the final
         * product. Nothing to worry about for now...
         */
        StringBuilder output = new StringBuilder();
        output.append( "IS: " );
        if( String.valueOf(data.print()).length() > 9 ){
            output  .append(data.print().substring(0, 6))
                    .append( "...");
        }else{
            output.append( data.print() );
        }
        
        int steps = 0; // For tracking efficiency
        
        /*
         * This is the actual sorting algorithm. I think I've got it dialed in
         * to be true to Insertion Sort. I would definitley welcome input 
         * if I'm off though.
         */
      
       for( int i = 1; i < data.size(); i++ ){
           
           /*
            * This loop will iterate through each position in the list, one by 
            * one, starting on the second item. It's complete when it reaches 
            * the end of the data.
            */
                   
           int p = i; // Our pointer for where the number will be inserted
           
           while(p != 0 && data.get(i) < data.get(p-1)){
               /*
                * This loop will recurse back through the positions of the list
                * and will terminate if the pointer reaches 0 or if the value being
                * placed is greater than that of the pointers. Note that
                * it's only decremeniting the p variable, values will only
                * be moved once the criteria is met.
                */
               p--;
               steps++;
           }
           
           
           /*
            * I wrapped the insertion in an if statment just to prevent insertions
            * when they are unneeded. This seems to have eliminated some wasted
            * processes.
            */
            if( data.get(p) > data.get(i) ){               
                 steps += data.insert( i, p );
            }
                      
       }
       
        output  .append("\t => Steps: ")
                .append(steps)
                .append("\t => \t");
                if( String.valueOf(data.print()).length() > 9 ){
                    output  .append(data.print().substring(0, 6))
                            .append( "...");
                }else{
                    output.append( data.print() );
                }      
                
       System.out.println( output.toString() );
       
       return data.getData();
       
    }
    
    private int[] selectionSort( int[] array ) {

        Data data = new Data( array );
        
        /*
         * This just some stuff that's going to help reflect on the final
         * product. Nothing to worry about for now...
         */
        StringBuilder output = new StringBuilder();
        output.append( "SS: " );
        if( String.valueOf(data.print()).length() > 9 ){
            output  .append(data.print().substring(0, 6))
                    .append( "...");
        }else{
            output.append( data.print() );
        }
        
        int steps = 0;
        
        /*
         * Whew... I think I got this right but I would love some peer review
         * on the matter. Basically, there are two loops-- One is going to 
         * iterate through the entire array one by one. We're going to use its
         * counter as the starting point for the inner array, however, as each
         * full iteration will replace the number we're starting from.
         * 
         * This sort works by creating a sublist that grows and slowly takes the 
         * place of the original list. I think this will work...
         * 
         */

        for( int p = 0; p < data.size(); p++ ){
            
            /*
             * Because we don't want to loose the position we're starting with
             * we'll duplicate it into n.
             */
            int n = p;
            
            for( int i = p; i < data.size(); i++ ){
                
                /*
                 * Now we can start with the second loop. The starting point will
                 * be wherever we are in the parent loop. This loop has only a 
                 * single purpose: iterate through all the remaining values in 
                 * the list and find the lowest one. Luckily, that's a pretty 
                 * easy task...
                 * 
                 * We start by assuming that the first position is the lowest 
                 * number. As this child loop goes over each position in the 
                 * array, if it finds a lower number, it sets n to the value of
                 * THAT number's position. The loop continues until the end of the
                 * unsorted sublist is reached.
                 */
                
                if( data.get(n) > data.get(i) ) n = i;
                steps++;
                
            }
            
            /*
             * Once again, in order to reduce the number of pointless inserts, 
             * Just a simple conditional.
             */
            
            if( data.get(n) < data.get(p)  ){
                steps += data.insert( n, p );
            }

        }

        output  .append("\t => Steps: ")
                .append(steps)
                .append("\t => \t");
                if( String.valueOf(data.print()).length() > 9 ){
                    output  .append(data.print().substring(0, 6))
                            .append( "...");
                }else{
                    output.append( data.print() );
                }
                
        System.out.println( output.toString() );

        return data.getData();
        
    }
    
    private static int[] generateRandomNumberArray( int n ){
        
        /*
         * Random number generating logic shamelessly stolen from: 
         * http://stackoverflow.com/a/7940623
         * 
         */
        
            int [] a = new int[n];
            Random rg = new Random();
            
            for (int i = 0; i < a.length; i++){
                int r = rg.nextInt(i+1);
                a[i] = a[r];
                a[r] = i+1;
            }
        
        return a;
    }
    
    private static int[] generateNumberArray( int n, boolean ascending ){
        
        int[] array = new int[n];
        
        for( int i=0; i < array.length; i++ ){
            
            if(ascending) {
                array[i] = (i + 1);
            }else{
                array[i] = ( array.length - i );
            }
            
        }
        
        return array;
        
    }
    
    class Data{
        
        /*
         * I wanted to keep this simple and avoid using any higher level list
         * structures in the demo. Unfortunatley, primitive arrays don't offer
         * an insertion method so I had to make something up. I wrapped it all
         * in a class for easy portability.
         */
        
        private int data []; // array for the data
    
        Data( int[] incomingData ){
            data = incomingData;
        }
        
        public int size(){
            return data.length;
        }

        public int[] getData() {
            return data;
        }

        public void setData(int[] data) {
            this.data = data;
        }
        
        public int get(int position){
            return data[position]; // Return a single value
        }
        
        public int insert( int oldPosition, int newPosition ){
           
            /*
             * Writing this stupid method took as much time as the sorting algorithm!
             * The method takes two arguments and has no error handling... Beware!
             * 
             * @param A: Position to move
             * @param B: New position
             * 
             * We basically just store the value of the number getting moved in a
             * variable and then recursively move/overwrite variables backwards
             * until we get to the new position. Then we just write the traveling
             * integer into its new home!
             * 
             * Looking at this now, I suppose the biggest limitation is that the
             * method can only move to positions before and not after the integer
             * to be moved. That could probably be fixed pretty easily but I 
             * don't care about it for now.
             */
            
            int steps = 0;
            
            int valueToMove = data[oldPosition];
            int currentPosition = oldPosition;
            int nextPosition;

             while( currentPosition != newPosition ){
                 nextPosition = currentPosition - 1;
                 data[currentPosition] = data[nextPosition];
                 steps++;
                 currentPosition--;
             }

             data[newPosition] = valueToMove;
             steps++;
                     
             return steps;
            
        }
        
        public String print(){
            
            /*
             * Finally, this method just prints the contents of the array
             * held by the object. Down and dirty, baby!
             */
            
            String output = "";
            
            for ( int i : data ){
               output += String.valueOf(i);
            }
            
            return output;
                        
        }
    
    }
    
}
