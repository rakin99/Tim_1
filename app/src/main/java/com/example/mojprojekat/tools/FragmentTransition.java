package com.example.mojprojekat.tools;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTransition
{
	public static void to(Fragment newFragment, FragmentActivity activity, int layoutViewID)
	{
		to(newFragment, activity, true, layoutViewID);
	}
	
	public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackstack,int layoutViewID)
	{
		FragmentTransaction transaction = activity.getSupportFragmentManager()
			.beginTransaction()
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
			.replace(layoutViewID, newFragment);
		if(addToBackstack) transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public static void remove(Fragment fragment, FragmentActivity activity) // TODO izbaciti fragment parametar
	{
		activity.getSupportFragmentManager().popBackStack();
	}
}
