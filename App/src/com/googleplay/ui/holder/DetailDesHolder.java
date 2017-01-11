package com.googleplay.ui.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.googleplay.appstore.R;
import com.googleplay.domain.AppInfo;
import com.googleplay.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class DetailDesHolder extends BaseHolder<AppInfo> {

	private TextView tvDes;
	private TextView tvAuthor;
	private ImageView ivArrow;
	private RelativeLayout rlToggle;
	private LinearLayout.LayoutParams params;
	private boolean isOpen = false;

	@Override
	public View initView() {

		View view = UIUtils.inflate(R.layout.layout_detail_desinfo);

		tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
		tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
		ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
		rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);

		rlToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				toggle();
			}
		});

		return view;
	}

	protected void toggle() {

		int longHeight = getHeight();
		int shortHeight = getShortHeight();

		ValueAnimator animator = null;
		// 判断状态显示全部还是7行

		if (isOpen) {
			// 关闭
			isOpen = false;
			// 只有整个长度大于7行时执行
			if (longHeight > shortHeight) {
				animator = ValueAnimator.ofInt(longHeight, shortHeight);
			}
		} else {

			// 打开
			isOpen = true;

			if (longHeight > shortHeight) {
				animator = ValueAnimator.ofInt(shortHeight, longHeight);
			}
		}

		if (animator != null) {

			// 监听动画的更新，动画启动后不断调用
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator arg0) {

					// 修改tvDes的布局参数高度
					int height = (Integer) arg0.getAnimatedValue();

					params.height = height;
					tvDes.setLayoutParams(params);
				}
			});

			// 监听动画状态变化
			animator.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator arg0) {

				}

				@Override
				public void onAnimationRepeat(Animator arg0) {

				}

				@Override
				public void onAnimationEnd(Animator arg0) {

					// 使頁面滑动，找到控件ScrollView，让其自动滚动到底部
					final ScrollView scrollView = getScrollView();

					// 放到消息队列。更安全
					scrollView.post(new Runnable() {

						@Override
						public void run() {

							scrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滚动到底部

						}
					});

					// 箭头的变化
					if (isOpen) {
						ivArrow.setImageResource(R.drawable.arrow_up);
					} else {
						ivArrow.setImageResource(R.drawable.arrow_down);
					}
				}

				@Override
				public void onAnimationCancel(Animator arg0) {

				}
			});

		}

		animator.setDuration(200);
		animator.start();
	}

	@Override
	public void refreshView(AppInfo data) {

		tvDes.setText(data.des);

		tvAuthor.setText(data.author);

		// 开一个消息队列，更新3行却7行高度
		tvDes.post(new Runnable() {

			@Override
			public void run() {
				int height = getShortHeight();
				params = (android.widget.LinearLayout.LayoutParams) tvDes
						.getLayoutParams();

				params.height = height;

				tvDes.setLayoutParams(params);

			}
		});

	}

	/**
	 * 获取描述内容的7行高度
	 * 
	 * @return
	 */
	protected int getShortHeight() {

		int width = tvDes.getMeasuredWidth(); // 获取描述文字宽度

		TextView view = new TextView(UIUtils.getContext());
		view.setText(getData().des); // 设置描述文字
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); // 设置文字大小

		view.setMaxLines(7); // 设置最大行数

		// 制定测量规则

		int makeMeasureSpecWidth = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY); // 宽度不变，确定值 match_parent

		int makeMeasureSpecHeight = MeasureSpec.makeMeasureSpec(2000,
				MeasureSpec.AT_MOST); // 高度包裹内容，最大为2000，也可为屏幕高度

		view.measure(makeMeasureSpecWidth, makeMeasureSpecHeight);
		return view.getMeasuredHeight();
	}

	/**
	 * 描述内容的最高度
	 * 
	 * @return
	 */
	protected int getHeight() {

		int width = tvDes.getMeasuredWidth(); // 获取描述文字宽度

		TextView view = new TextView(UIUtils.getContext());
		view.setText(getData().des); // 设置描述文字
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); // 设置文字大小

		// 制定测量规则

		int makeMeasureSpecWidth = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY); // 宽度不变，确定值 match_parent

		int makeMeasureSpecHeight = MeasureSpec.makeMeasureSpec(2000,
				MeasureSpec.AT_MOST); // 高度包裹内容，最大为2000，也可为屏幕高度

		view.measure(makeMeasureSpecWidth, makeMeasureSpecHeight);
		return view.getMeasuredHeight();

	}

	// 获取ScrollView控件id
	private ScrollView getScrollView() {

		ViewParent parent = tvDes.getParent();

		while (!(parent instanceof ScrollView)) {
			parent = parent.getParent();
		}

		return (ScrollView) parent;
	}

}
