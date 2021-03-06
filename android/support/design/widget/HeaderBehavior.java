package android.support.design.widget;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V>
{
  private static final int INVALID_POINTER = -1;
  private int mActivePointerId = -1;
  private Runnable mFlingRunnable;
  private boolean mIsBeingDragged;
  private int mLastMotionY;
  OverScroller mScroller;
  private int mTouchSlop = -1;
  private VelocityTracker mVelocityTracker;

  public HeaderBehavior()
  {
  }

  public HeaderBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void ensureVelocityTracker()
  {
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
  }

  boolean canDragView(V paramV)
  {
    return false;
  }

  final boolean fling(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, float paramFloat)
  {
    if (this.mFlingRunnable != null)
    {
      paramV.removeCallbacks(this.mFlingRunnable);
      this.mFlingRunnable = null;
    }
    if (this.mScroller == null)
      this.mScroller = new OverScroller(paramV.getContext());
    this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(paramFloat), 0, 0, paramInt1, paramInt2);
    if (this.mScroller.computeScrollOffset())
    {
      this.mFlingRunnable = new FlingRunnable(paramCoordinatorLayout, paramV);
      ViewCompat.postOnAnimation(paramV, this.mFlingRunnable);
      return true;
    }
    onFlingFinished(paramCoordinatorLayout, paramV);
    return false;
  }

  int getMaxDragOffset(V paramV)
  {
    return -paramV.getHeight();
  }

  int getScrollRangeForDragFling(V paramV)
  {
    return paramV.getHeight();
  }

  int getTopBottomOffsetForScrollingSibling()
  {
    return getTopAndBottomOffset();
  }

  void onFlingFinished(CoordinatorLayout paramCoordinatorLayout, V paramV)
  {
  }

  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (this.mTouchSlop < 0)
      this.mTouchSlop = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop();
    if ((paramMotionEvent.getAction() == 2) && (this.mIsBeingDragged))
      return true;
    switch (paramMotionEvent.getActionMasked())
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      if (this.mVelocityTracker != null)
        this.mVelocityTracker.addMovement(paramMotionEvent);
      return this.mIsBeingDragged;
      this.mIsBeingDragged = false;
      int m = (int)paramMotionEvent.getX();
      int n = (int)paramMotionEvent.getY();
      if ((!canDragView(paramV)) || (!paramCoordinatorLayout.isPointInChildBounds(paramV, m, n)))
        continue;
      this.mLastMotionY = n;
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      ensureVelocityTracker();
      continue;
      int i = this.mActivePointerId;
      if (i == -1)
        continue;
      int j = paramMotionEvent.findPointerIndex(i);
      if (j == -1)
        continue;
      int k = (int)paramMotionEvent.getY(j);
      if (Math.abs(k - this.mLastMotionY) <= this.mTouchSlop)
        continue;
      this.mIsBeingDragged = true;
      this.mLastMotionY = k;
      continue;
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      if (this.mVelocityTracker == null)
        continue;
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (this.mTouchSlop < 0)
      this.mTouchSlop = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop();
    switch (paramMotionEvent.getActionMasked())
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      if (this.mVelocityTracker != null)
        this.mVelocityTracker.addMovement(paramMotionEvent);
      return true;
      int m = (int)paramMotionEvent.getX();
      int n = (int)paramMotionEvent.getY();
      if ((paramCoordinatorLayout.isPointInChildBounds(paramV, m, n)) && (canDragView(paramV)))
      {
        this.mLastMotionY = n;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        ensureVelocityTracker();
        continue;
      }
      return false;
      int i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
      if (i == -1)
        return false;
      int j = (int)paramMotionEvent.getY(i);
      int k = this.mLastMotionY - j;
      if ((!this.mIsBeingDragged) && (Math.abs(k) > this.mTouchSlop))
      {
        this.mIsBeingDragged = true;
        if (k <= 0)
          break label236;
        k -= this.mTouchSlop;
      }
      while (this.mIsBeingDragged)
      {
        this.mLastMotionY = j;
        scroll(paramCoordinatorLayout, paramV, k, getMaxDragOffset(paramV), 0);
        break;
        label236: k += this.mTouchSlop;
      }
      if (this.mVelocityTracker != null)
      {
        this.mVelocityTracker.addMovement(paramMotionEvent);
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        fling(paramCoordinatorLayout, paramV, -getScrollRangeForDragFling(paramV), 0, f);
      }
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      if (this.mVelocityTracker == null)
        continue;
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  final int scroll(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, getTopBottomOffsetForScrollingSibling() - paramInt1, paramInt2, paramInt3);
  }

  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, paramInt, -2147483648, 2147483647);
  }

  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = getTopAndBottomOffset();
    int j = 0;
    if (paramInt2 != 0)
    {
      j = 0;
      if (i >= paramInt2)
      {
        j = 0;
        if (i <= paramInt3)
        {
          int k = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
          j = 0;
          if (i != k)
          {
            setTopAndBottomOffset(k);
            j = i - k;
          }
        }
      }
    }
    return j;
  }

  private class FlingRunnable
    implements Runnable
  {
    private final V mLayout;
    private final CoordinatorLayout mParent;

    FlingRunnable(V arg2)
    {
      Object localObject1;
      this.mParent = localObject1;
      Object localObject2;
      this.mLayout = localObject2;
    }

    public void run()
    {
      if ((this.mLayout != null) && (HeaderBehavior.this.mScroller != null))
      {
        if (HeaderBehavior.this.mScroller.computeScrollOffset())
        {
          HeaderBehavior.this.setHeaderTopBottomOffset(this.mParent, this.mLayout, HeaderBehavior.this.mScroller.getCurrY());
          ViewCompat.postOnAnimation(this.mLayout, this);
        }
      }
      else
        return;
      HeaderBehavior.this.onFlingFinished(this.mParent, this.mLayout);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.HeaderBehavior
 * JD-Core Version:    0.6.0
 */