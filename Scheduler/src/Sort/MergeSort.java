package Sort;

/* Imports */
import Process.Process;

public class MergeSort
{
    public Process[] sort(Process[] A, String attribute)
    {
        Process[] temp = (Process[]) new Comparable[A.length];
        return mergesort(A,temp,0,A.length-1,attribute);
    }

    public Process[] mergesort(Process[] A , Process[] temp, int left, int right, String attribute)
    {
        int mid = (left+right)/2; // Find Midpoint

        // Check if there is one element
        if(left == right)
        {
            return null;
        }

        // Merge sorts
        mergesort(A,temp,left,mid, attribute); // First Half
        mergesort(A,temp,mid+1,right, attribute); // Second Half

        for(int i = left;i<=right;i++)
        {
            temp[i] = A[i];
        }

        // Do Operations back to A
        int i1 = left;
        int i2 = mid+1;

        for(int curr = left; curr<= right;curr++)
        {
            if((i1<mid+1) && (i2<=right))
            {
                if(temp[i1].compareTo(temp[i2],attribute)) // Get Smaller
                {
                    A[curr] = temp[i1++];
                }
                else
                {
                    A[curr] = temp[i2++];
                }
            }
            else if((i1==mid+1) && (i2<=right)) // Left Sublist Exhausted
            {
                A[curr] = temp[i2++];
            }
            else
            {
                A[curr] = temp[i1++];
            }
        }

        return A;
    }

}