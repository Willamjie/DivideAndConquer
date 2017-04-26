// priorityQ.java
// demonstrates priority queue
// to run this program: C>java PriorityQApp
////////////////////////////////////////////////////////////////
package lab.pkg4;

import java.io.IOException;
import lab.pkg4.ClosestPair.point;
import lab.pkg4.ClosestPair.pair;
/**
 * The original file @author Dr. Qing Yang
 * and then edited by Jared Kamp for use in
 * CSCI 232
 * Lab 4 - Divide & Conquer
 */
class PriorityQ
{
	// array in sorted order, from max at 0 to min at size-1
	private int maxSize;
	private point[] queArray; //queArray[][0] = xValue, queArray[][1] = yValue
	private int nItems;
	//-------------------------------------------------------------
	public PriorityQ(int s) // constructor
	{
		maxSize = s;
		queArray = new point[maxSize];
		nItems = 0;
	}
	//-------------------------------------------------------------
	public void insertX(point item) // insert item
	{
            int j;
            if(nItems==0) // if no items,
                    queArray[nItems++] = item; // insert at 0
            else // if items,
            {
                for(j=nItems-1; j>=0; j--) // start at end,
                {
                    if( item.x > queArray[j].x){ // if new item larger,
                            queArray[j+1] = queArray[j]; // shift upward
                    }
                    else // if smaller,
                            break; // done shifting
                } // end for
                queArray[j+1] = item; // insert it
                nItems++;
            } // end else (nItems > 0)
	} // end insert()
	public void insertY(point item) // insert item
	{
            int j;
            if(nItems==0) // if no items,
                    queArray[nItems++] = item; // insert at 0
            else // if items,
            {
                for(j=nItems-1; j>=0; j--) // start at end,
                {
                    if( item.y > queArray[j].y){ // if new item larger,
                            queArray[j+1] = queArray[j]; // shift upward
                    }
                    else // if smaller,
                            break; // done shifting
                } // end for
                queArray[j+1] = item; // insert it
                nItems++;
            } // end else (nItems > 0)
	}//-------------------------------------------------------------
	public point remove() // remove minimum item
	{ return queArray[--nItems]; }
	//-------------------------------------------------------------
	public point peekMin() // peek at minimum item
	{ 
            return queArray[nItems-1]; 
        }
        public point peekMid() // peek at middle item
	{ 
            return queArray[nItems/2]; 
        }
	//-------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{ return (nItems<=0); }
	//-------------------------------------------------------------
	public boolean isFull() // true if queue is full
	{ return (nItems == maxSize); }
	//-------------------------------------------------------------
} // end class PriorityQ
//////////////////////////////////////////////////////////////
class PriorityQApp
{
	public static void main(String[] args) throws IOException
	{
            PriorityQ thePQ = new PriorityQ(5);
            point temp = new point(30, 0);
            thePQ.insertX(temp);
            temp = new point(50, 0);
            thePQ.insertX(temp);
            temp = new point(10, 0);
            thePQ.insertX(temp);
            temp = new point(20, 0);
            thePQ.insertX(temp);
            temp = new point(40, 0);
            thePQ.insertX(temp);
            while( !thePQ.isEmpty() )
            {
                point item = thePQ.remove();
                System.out.print(item + " "); // 10, 20, 30, 40, 50
            } // end while
            System.out.println("");
	} // end main()
	//-------------------------------------------------------------
} // end class PriorityQApp
