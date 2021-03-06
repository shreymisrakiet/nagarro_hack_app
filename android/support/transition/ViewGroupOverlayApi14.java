package android.support.transition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
class ViewGroupOverlayApi14 extends ViewOverlayApi14
  implements ViewGroupOverlayImpl
{
  ViewGroupOverlayApi14(Context paramContext, ViewGroup paramViewGroup, View paramView)
  {
    super(paramContext, paramViewGroup, paramView);
  }

  static ViewGroupOverlayApi14 createFrom(ViewGroup paramViewGroup)
  {
    return (ViewGroupOverlayApi14)ViewOverlayApi14.createFrom(paramViewGroup);
  }

  public void add(@NonNull View paramView)
  {
    this.mOverlayViewGroup.add(paramView);
  }

  public void remove(@NonNull View paramView)
  {
    this.mOverlayViewGroup.remove(paramView);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.transition.ViewGroupOverlayApi14
 * JD-Core Version:    0.6.0
 */