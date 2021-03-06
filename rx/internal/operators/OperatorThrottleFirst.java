package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable.Operator;
import rx.Scheduler;
import rx.Subscriber;

public final class OperatorThrottleFirst<T>
  implements Observable.Operator<T, T>
{
  final Scheduler scheduler;
  final long timeInMilliseconds;

  public OperatorThrottleFirst(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.timeInMilliseconds = paramTimeUnit.toMillis(paramLong);
    this.scheduler = paramScheduler;
  }

  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    return new Subscriber(paramSubscriber, paramSubscriber)
    {
      private long lastOnNext = -1L;

      public void onCompleted()
      {
        this.val$subscriber.onCompleted();
      }

      public void onError(Throwable paramThrowable)
      {
        this.val$subscriber.onError(paramThrowable);
      }

      public void onNext(T paramT)
      {
        long l = OperatorThrottleFirst.this.scheduler.now();
        if ((this.lastOnNext == -1L) || (l - this.lastOnNext >= OperatorThrottleFirst.this.timeInMilliseconds))
        {
          this.lastOnNext = l;
          this.val$subscriber.onNext(paramT);
        }
      }

      public void onStart()
      {
        request(9223372036854775807L);
      }
    };
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.OperatorThrottleFirst
 * JD-Core Version:    0.6.0
 */