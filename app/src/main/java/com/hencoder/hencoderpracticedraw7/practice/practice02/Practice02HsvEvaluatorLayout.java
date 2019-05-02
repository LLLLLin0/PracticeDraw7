package com.hencoder.hencoderpracticedraw7.practice.practice02;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice02HsvEvaluatorLayout extends RelativeLayout {
    Practice02HsvEvaluatorView view;
    Button animateBt;

    public Practice02HsvEvaluatorLayout(Context context) {
        super(context);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice02HsvEvaluatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
                animator.setEvaluator(new HsvEvaluator()); // 使用自定义的 HsvEvaluator
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {

        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        float[] hsv3 = new float[3];

        // 重写 evaluate() 方法，让颜色按照 HSV 来变化
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            Color.colorToHSV(startValue, hsv1);
            Color.colorToHSV(endValue, hsv3);

            hsv2[1] = hsv1[1] + (hsv3[1] - hsv1[1]) * fraction;
            hsv2[2] = hsv1[2] + (hsv3[2] - hsv1[2]) * fraction;
            hsv2[0] = hsv1[0] + (hsv3[0] - hsv1[0]) * fraction;

            return Color.HSVToColor(hsv2);
        }
    }
}
