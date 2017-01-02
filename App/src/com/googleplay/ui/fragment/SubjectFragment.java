package com.googleplay.ui.fragment;

import java.util.ArrayList;

import android.view.View;

import com.googleplay.domain.SubjectInfo;
import com.googleplay.http.protocol.SubjectProtocol;
import com.googleplay.ui.adapter.MyBaseAdapter;
import com.googleplay.ui.holder.BaseHolder;
import com.googleplay.ui.holder.SubjectHolder;
import com.googleplay.ui.view.LoadingPage.ResultState;
import com.googleplay.ui.view.MyListView;
import com.googleplay.utils.UIUtils;

/**
 * 专题
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class SubjectFragment extends BaseFragment {

	private ArrayList<SubjectInfo> data;

	@Override
	public View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new SubjectAdapter(data));
		return view;
	}

	@Override
	public ResultState onLoad() {
		SubjectProtocol protocol = new SubjectProtocol();
		data = protocol.getData(0);
		return check(data);
	}

	class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder(int position) {
			return new SubjectHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

	}
}
