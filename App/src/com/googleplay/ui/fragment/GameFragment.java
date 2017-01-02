package com.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.googleplay.ui.view.LoadingPage.ResultState;
import com.googleplay.utils.UIUtils;


/**
 * 游戏
 * @author Kevin
 * @date 2015-10-27
 */
public class GameFragment extends BaseFragment {

	@Override
	public View onCreateSuccessView() {
		TextView view = new TextView(UIUtils.getContext());
		view.setText("GameFragment");
		return view;
	}

	@Override
	public ResultState onLoad() {
		return ResultState.STATE_SUCCESS;
	}

}
