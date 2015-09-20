package com.pifss.myway;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProfileAdapter extends FragmentPagerAdapter {

	public ProfileAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		
		switch (pos) {
		case 0:
			return new Fragment_Register();
		case 1:
			return new Fragment_Login();

		}
		return new Fragment_Login();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
