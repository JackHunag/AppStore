package com.googleplay.ui.holder;

import java.util.ArrayList;

import android.view.View;
import android.widget.ImageView;

import com.googleplay.appstore.R;
import com.googleplay.domain.AppInfo;
import com.googleplay.http.HttpHelper;
import com.googleplay.utils.BitmapHelper;
import com.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class DetailPicHolder extends BaseHolder<AppInfo> {

	private ImageView[] ivPics;
	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {

		View view = UIUtils.inflate(R.layout.layout_detail_picinfo);

		ivPics = new ImageView[5];

		ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
		ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
		ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
		ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
		ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(AppInfo data) {

		ArrayList<String> screen = data.screen;
		mBitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		for (int i = 0; i < 5; i++) {

			// 根据返回网络图片数目显示，最多显示5张
			if (i < screen.size()) {
				// 网络加载图片
				mBitmapUtils.display(ivPics[i], HttpHelper.URL + "image?name="
						+ screen.get(i));
			} else {

				ivPics[i].setVisibility(View.GONE);
			}

		}

	}

}
