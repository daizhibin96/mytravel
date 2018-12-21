package com.cn.travel.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private FindFragment findFragment;
	private FirstFragment firstFragment;
	private MeFragment meFragment;
	private NavigationFragment navigationFragment;
	private int Page_Count = 4;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		findFragment = new FindFragment();
		firstFragment = new FirstFragment();
		meFragment = new MeFragment();
		navigationFragment = new NavigationFragment();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch (arg0) {
		case 0:
			fragment = firstFragment;
			break;
		case 1:
			fragment = navigationFragment;
			break;
		case 2:
			fragment = findFragment;
			break;
		case 3:
			fragment = meFragment;
			break;

		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Page_Count;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	

}
