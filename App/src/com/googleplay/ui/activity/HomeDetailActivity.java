package com.googleplay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.googleplay.appstore.R;
import com.googleplay.domain.AppInfo;
import com.googleplay.http.protocol.HomeDetailProtocol;
import com.googleplay.ui.holder.DetailAppInfoHolder;
import com.googleplay.ui.holder.DetailSafeHolder;
import com.googleplay.ui.view.LoadingPage;
import com.googleplay.ui.view.LoadingPage.ResultState;
import com.googleplay.utils.UIUtils;

/**
 * 首页应用详情页
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class HomeDetailActivity extends BaseActivity {

	private LoadingPage mLoadingPage;
	private String packageName;
	private AppInfo data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLoadingPage = new LoadingPage(this) {

			@Override
			public ResultState onLoad() {
				return HomeDetailActivity.this.onLoad();
			}

			@Override
			public View onCreateSuccessView() {
				return HomeDetailActivity.this.onCreateSuccessView();
			}
		};

		// setContentView(R.layout.activity_main);
		setContentView(mLoadingPage);// 直接将一个view对象设置给activity

		// 获取从HomeFragment传递过来的包名
		packageName = getIntent().getStringExtra("packageName");

		// 开始加载网络数据
		mLoadingPage.loadData();
	}

	public View onCreateSuccessView() {
		// 初始化成功的布局
		View view = UIUtils.inflate(R.layout.page_home_detail);

		// 初始化应用信息模块
		FrameLayout flDetailAppInfo = (FrameLayout) view
				.findViewById(R.id.fl_detail_appinfo);
		// 动态给帧布局填充页面
		DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
		flDetailAppInfo.addView(appInfoHolder.getRootView());
		appInfoHolder.setData(data);

		// 初始化安全描述模块
		FrameLayout flDetailSafe = (FrameLayout) view
				.findViewById(R.id.fl_detail_safe);
		DetailSafeHolder safeHolder = new DetailSafeHolder();
		flDetailSafe.addView(safeHolder.getRootView());
		safeHolder.setData(data);

		return view;
	}

	public ResultState onLoad() {
		// 请求网络,加载数据
		HomeDetailProtocol protocol = new HomeDetailProtocol(packageName);
		data = protocol.getData(0);

		if (data != null) {
			return ResultState.STATE_SUCCESS;
		} else {
			return ResultState.STATE_ERROR;
		}
	}
}
