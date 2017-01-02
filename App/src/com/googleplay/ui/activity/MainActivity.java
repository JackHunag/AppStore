package com.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.googleplay.appstore.R;
import com.googleplay.ui.fragment.BaseFragment;
import com.googleplay.ui.fragment.FragmentFactory;
import com.googleplay.ui.view.PagerTab;
import com.googleplay.utils.UIUtils;

/**
 * 当项目和appcompat关联在一起时, 就必须在清单文件中设置Theme.AppCompat的主题, 否则崩溃
 * android:theme="@style/Theme.AppCompat.Light"
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class MainActivity extends BaseActivity {

	private PagerTab mPagerTab;
	private ViewPager mViewPager;
	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mAdapter = new MyAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

		mPagerTab.setViewPager(mViewPager);// 将指针和viewpager绑定在一起

		mPagerTab.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = FragmentFactory
						.createFragment(position);
				// 开始加载数据
				fragment.loadData();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	/**
	 * FragmentPagerAdapter是PagerAdapter的子类, 如果viewpager的页面是fragment的话,就继承此类
	 */
	class MyAdapter extends FragmentPagerAdapter {

		private String[] mTabNames;

		public MyAdapter(FragmentManager fm) {
			super(fm);
			mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
		}

		// 返回页签标题
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabNames[position];
		}

		// 返回当前页面位置的fragment对象
		@Override
		public Fragment getItem(int position) {
			BaseFragment fragment = FragmentFactory.createFragment(position);
			return fragment;
		}

		// fragment数量
		@Override
		public int getCount() {
			return mTabNames.length;
		}

	}

}
