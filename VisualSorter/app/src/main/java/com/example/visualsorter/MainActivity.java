package com.example.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;
    private final int numberOfElements = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sortedLV = this.findViewById(R.id.sortedLV);
        this.unsortedLV = this.findViewById(R.id.unsortedLV);

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();
    }

    private void insertionSort(int[] ar)
    {
        int theFollower, swap;

        for(int currStart = 1; currStart < ar.length; currStart++)
        {
            theFollower = currStart;
            while(theFollower > 0 && ar[theFollower] < ar[theFollower-1])
            {
                swap = ar[theFollower];
                ar[theFollower] = ar[theFollower-1];
                ar[theFollower-1] = swap;
                theFollower--;
            }
        }
    }

    /*
     This was my third attempt to create the code, with inspiration taken from
     an online source so that I could understand a working version.
     I could understand how it works, but I couldn't come up with code without reference.
    */
    public static void mergeSort(int[] ar, int arLength)
    {
        if (arLength < 2)
        {
            return;
        }
        //Checks the number of elements in the array, if only one element then return

        int mid = arLength / 2;//An int that holds the middle bucket number of ar
        int[] left = new int[mid];
        int[] right = new int[arLength - mid];
        //Creates two arrays meant to hold contents of ar, split down the middle

        for (int start1 = 0; start1 < mid; start1++)
        {
            left[start1] = ar[start1];
        }//This loop adds contents to the left array up until the middle of ar

        for (int start2 = mid; start2 < arLength; start2++)
        {
            right[start2 - mid] = ar[start2];
        }//This loop adds contents to the right array starting from the middle of ar to its end

        mergeSort(left, mid);
        mergeSort(right, arLength - mid);
        //Calls mergesort on left and right, creating new left and right arrays and
        // splitting them at their middle until both arrays only contain one element

        merge(ar, left, right, mid, arLength - mid);
        //Merge is called to take all of the split lists and combine them back into ar
    }

    public static void merge(int[] ar, int[] left, int[] right, int leftPos, int rightPos)
    {
        int currLeft = 0, currMid = 0, currRight = 0;
        //Counters to keep track of what bucket in the split lists the loops are in

        while (currLeft < leftPos && currMid < rightPos)//leftPos and rightPos tell the loops how far into ar to add integers
        {
            if (left[currLeft] <= right[currMid])
            {
                ar[currRight++] = left[currLeft++];
            }
            else
            {
                ar[currRight++] = right[currMid++];
            }
            //Checks whether the int in the current position of left is smaller than the int
            // in the current position of right; if it is, then add left's int back to ar,
            // otherwise add right's integer back
        }
        while (currLeft < leftPos)
        {
            ar[currRight++] = left[currLeft++];
        }

        while (currMid < rightPos)
        {
            ar[currRight++] = right[currMid++];
        }
        //Takes the smaller remaining values from the left and right arrays and puts them back into ar
        //Due to how the integers in the previous loop was sorted, and how the ints were split up into smaller lists
        //by calling mergeSort multiple times, the integers fall into the correct order at this point of the code.
    }

    //recursion refresher example
    //5! = 5 * 4 * 3 * 2 * 1 -> 5 * 4!
    private int factorial(int n)
    {
        if(n == 1)
        {
            return 1;
        }
        return n * this.factorial(n-1);
    }

    public void insertionSortButtonPressed(View vy)
    {
        //perform an insertion sort on the unsortedArray
        this.insertionSort(this.unsortedNumbers);
        this.updateStringArrays();
    }

    public void mergeSortButtonPressed(View vy)
    {
        //perform a merge sort on the unsortedArray
        this.mergeSort(this.unsortedNumbers, this.unsortedNumbers.length);
        this.updateStringArrays();
    }

    public void resetButtonPressed(View austin)
    {
        this.initializeArrays();
    }

    private void initializeArrays()
    {
        //fill my unsorted int array
        this.fillRandomIntArray(this.unsortedNumbers);
        this.copyContentsOfIntArrays(this.unsortedNumbers, this.sortedNumbers);
        this.updateStringArrays();
    }

    private void updateStringArrays()
    {
        //fill my string arrays to mimic the int arrays
        this.copyIntArrayToStringArray(this.unsortedNumbers, this.unsortedStrings);
        this.copyIntArrayToStringArray(this.sortedNumbers, this.sortedStrings);
        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyIntArrayToStringArray(int[] arInts, String[] arStrings)
    {
        for(int i = 0; i < arInts.length; i++)
        {
            arStrings[i] = "" + arInts[i];
        }
    }

    private void copyContentsOfIntArrays(int[] source, int[] destination)
    {
        for(int i = 0; i < source.length; i++)
        {
            destination[i] = source[i];
        }
    }

    private void fillRandomIntArray(int[] ar)
    {
        Random r = new Random();

        for(int i = 0; i < ar.length; i++)
        {
            ar[i] = r.nextInt(500);
        }
    }
}//class
