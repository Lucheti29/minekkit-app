package ar.com.overflowdt.minekkit.util.customView;
/*
 * Copyright (C) 2013 Chaobin Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * @author chaobin
 * @date 10/23/13.
 */
public class RotationView extends View{
    private static final double ROTATING_ACCELERATION = -3;
    private final String TAG = "RotationView";
    private final int REFRESH_INTERVAL = 33;
    private final int ROTATING_SPEED = 15;
    private Paint mPaint;
    private Context mContext;
    private Bitmap mRotateBackground;
    private Matrix mMatrix;
    private int mViewHeight;
    private int mViewWidth;
    private double mRotatedDegree;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mBitmapResourceId;
    private Runnable mRefreshRunnable;
    private RotateWorker mRotateWorker;
    private BitmapFactory.Options mOptions;
    private boolean mAutoRotate;
    private boolean mDetached;
    private boolean mRotating;
    private boolean mRotateable = true;
    private float mMaxProgress = 100.0f;
    private float timeSpent = 0;
    private int mFinishingAngle=0;
    private int mRandomAngle= 23;


    public RotationView(Context context) {
        this(context, null);
    }

    public RotationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RotationView, defStyle, 0);
        mBitmapResourceId = a.getResourceId(R.styleable.RotationView_rotateBackground, android.R.drawable.sym_def_app_icon);
        mAutoRotate = a.getBoolean(R.styleable.RotationView_autoRefresh, false);
        a.recycle();
        intitData(context);
    }

    private void intitData(Context context) {
        mContext = context;
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mRefreshRunnable = new RefreshProgressDeceleratingRunnable();
        setFinishingAngle(180);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRotateBackground == null) {
            mOptions = new BitmapFactory.Options();
            mOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(mContext.getResources(), mBitmapResourceId, mOptions);
            int imageWidth = mOptions.outWidth;
            int imageHeight = mOptions.outHeight;
            Log.d(TAG, "imageHeight = " + imageHeight + ", imageWidth =" + imageWidth);
            int inSampleSize = 1;
            if (imageHeight > mViewHeight || imageWidth > mViewWidth) {
                final int heightRatio = Math.round(imageHeight / (float) mViewHeight);
                final int widthRatio = Math.round(imageWidth / (float) mViewWidth);
                inSampleSize = Math.max(heightRatio, widthRatio);
                Log.d(TAG, "heightRatio =" + heightRatio + ", widthRatio =" + widthRatio + ", inSampleSize =" + inSampleSize);
            }
            mOptions.inSampleSize = inSampleSize;
            mOptions.inJustDecodeBounds = false;
            mRotateBackground = BitmapFactory.decodeResource(mContext.getResources(), mBitmapResourceId, mOptions);
            mBitmapWidth = mOptions.outWidth;
            mBitmapHeight = mOptions.outHeight;
            if (mAutoRotate && mRotateable) startAnimate();
        }
        Log.d(TAG, "RotationView = " + mRotatedDegree);
        canvas.scale(( mViewHeight/ (float)mBitmapHeight ),mViewWidth / (float)mBitmapWidth );
        //canvas.translate(Math.abs((mViewWidth - mBitmapWidth) / 2), Math.abs((mViewHeight - mBitmapHeight) / 2));
        mMatrix.setRotate((int)mRotatedDegree, mBitmapWidth / 2, mBitmapHeight / 2);
        canvas.drawBitmap(mRotateBackground, mMatrix, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG, "mViewHeight =" + mViewHeight + ", mViewWidth =" + mViewWidth);
    }

    private class RefreshProgressRunnable implements Runnable {
        public void run() {
            synchronized (RotationView.this) {
                mRotatedDegree += ROTATING_SPEED;
                if (mRotatedDegree > 360) mRotatedDegree -= 360;
                invalidate();
            }
        }
    }
    public boolean isBetween(double x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    public int getDegreeLeftClockwise(int a, int b) {
        return b>=a ? b-a : b-a+360;
    }
    public void setFinishingAngle(int angle){
        mFinishingAngle=360-angle;
    }
    private class RefreshProgressDeceleratingRunnable implements Runnable {
        boolean finishing=false;
        public void run() {
            synchronized (RotationView.this) {
                timeSpent+=REFRESH_INTERVAL;
                Log.d("Ruleta","TimeSpent: "+String.valueOf(timeSpent)+" GetSpeed: "+String.valueOf(getSpeedThroughTime(timeSpent / 1000)));
                if(getAccelerationThroughTime(timeSpent/ 1000)<0 && getSpeedThroughTime(timeSpent/ 1000)<4){
                    int degreeLeft= getDegreeLeftClockwise((int)mRotatedDegree,mFinishingAngle);
                    if (isBetween(degreeLeft, 270, 360)) {
                        mRotatedDegree += 4+ (double)((double)degreeLeft-270d)/(double)90d;
                    } else if (isBetween(degreeLeft, 180, 270)) {
                        mRotatedDegree += 3+ (double)((double)degreeLeft-180d)/(double)90d;
                    }  else if (isBetween(degreeLeft, 90 , 180)) {
                        mRotatedDegree += 2+ (double)((double)degreeLeft-90d)/(double)90d;
                    }   else if (isBetween(degreeLeft, 0, 90)) {
                        mRotatedDegree += 1+ (double)((double)degreeLeft)/(double)90d;
                    }
                    Log.d("Ruleta","DegreeLeft: "+String.valueOf(getDegreeLeftClockwise((int)mRotatedDegree,mFinishingAngle))+ "mRotatedDregree: " + String.valueOf(mRotatedDegree+"fini angle: "+String.valueOf(mFinishingAngle)));
                    finishing=true;
                }
                else
                    mRotatedDegree += getSpeedThroughTime(timeSpent / 1000);

                //if(getSpeedThroughTime(timeSpent / 1000)<0) {

                if(finishing && isBetween(mRotatedDegree , mFinishingAngle-45+mRandomAngle , mFinishingAngle)){
                    finishing=false;
                    stopAnimate();
                    ShowAlertMessage.showMessage("Rotation Degree:" + String.valueOf(mRotatedDegree),(Activity) mContext);
                }
                if (mRotatedDegree > 360) mRotatedDegree -= 360;

                invalidate();
            }
        }

        public float getSpeedThroughTime(float time){//Duracion de la tirada -2 * ROTATING_SPEED / ROTATING_ACCELERATION
            return (float) (ROTATING_SPEED * time + 0.5* ROTATING_ACCELERATION * time*time);
        }
        public float getAccelerationThroughTime(float time){
            return (float) (ROTATING_SPEED + ROTATING_ACCELERATION *time);
        }
    }

    private class RotateWorker extends Thread {
        private boolean cancelled;

        @Override
        public void run() {
            while (!cancelled && !mDetached && getVisibility() == View.VISIBLE) {
                Log.d(TAG, "post refresh request");
                post(mRefreshRunnable);
                try {
                    Thread.sleep(REFRESH_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cancel() {
            try {
                cancelled = true;
                this.interrupt();
                Log.d(TAG, "cancel RotateWorker " + this.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startAnimate() {
        if (mRotateWorker != null) mRotateWorker.cancel();
        mRotateable = true;
        mRotating = true;
        mRotateWorker = new RotateWorker();
        mRotateWorker.start();
    }

    public void stopAnimate() {
        if (mRotateWorker != null) mRotateWorker.cancel();
        mRotateWorker = null;
        mRotating = false;
        timeSpent=0;

    }

    public void resetAnimate() {
        mRotatedDegree = 0;
        if (mRotateWorker != null) mRotateWorker.cancel();
        invalidate();
    }

    public void setProgress(int progress) {
        mRotatedDegree = (int) ((progress + 0.5) / mMaxProgress * 360);
        startAnimate();
    }

    /**
     * start/stop to animate according to current state
     */
    public void toggle() {
        if (!mRotating) startAnimate();
        else stopAnimate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "detached window");
        mDetached = true;
        stopAnimate();
        if (mRefreshRunnable != null) removeCallbacks(mRefreshRunnable);
    }
}