package rx;

import rx.internal.util.SubscriptionList;

public abstract class Subscriber<T>
  implements Observer<T>, Subscription
{
  private static final long NOT_SET = -9223372036854775808L;
  private Producer producer;
  private long requested = -9223372036854775808L;
  private final Subscriber<?> subscriber;
  private final SubscriptionList subscriptions;

  protected Subscriber()
  {
    this(null, false);
  }

  protected Subscriber(Subscriber<?> paramSubscriber)
  {
    this(paramSubscriber, true);
  }

  protected Subscriber(Subscriber<?> paramSubscriber, boolean paramBoolean)
  {
    this.subscriber = paramSubscriber;
    if ((paramBoolean) && (paramSubscriber != null));
    for (SubscriptionList localSubscriptionList = paramSubscriber.subscriptions; ; localSubscriptionList = new SubscriptionList())
    {
      this.subscriptions = localSubscriptionList;
      return;
    }
  }

  private void addToRequested(long paramLong)
  {
    if (this.requested == -9223372036854775808L)
    {
      this.requested = paramLong;
      return;
    }
    long l = paramLong + this.requested;
    if (l < 0L)
    {
      this.requested = 9223372036854775807L;
      return;
    }
    this.requested = l;
  }

  public final void add(Subscription paramSubscription)
  {
    this.subscriptions.add(paramSubscription);
  }

  public final boolean isUnsubscribed()
  {
    return this.subscriptions.isUnsubscribed();
  }

  public void onStart()
  {
  }

  protected final void request(long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("number requested cannot be negative: " + paramLong);
    monitorenter;
    try
    {
      if (this.producer != null)
      {
        Producer localProducer = this.producer;
        monitorexit;
        localProducer.request(paramLong);
        return;
      }
      addToRequested(paramLong);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void setProducer(Producer paramProducer)
  {
    monitorenter;
    long l;
    try
    {
      l = this.requested;
      this.producer = paramProducer;
      Subscriber localSubscriber = this.subscriber;
      int i = 0;
      if (localSubscriber != null)
      {
        boolean bool = l < -9223372036854775808L;
        i = 0;
        if (!bool)
          i = 1;
      }
      monitorexit;
      if (i != 0)
      {
        this.subscriber.setProducer(this.producer);
        return;
      }
    }
    finally
    {
      monitorexit;
    }
    if (l == -9223372036854775808L)
    {
      this.producer.request(9223372036854775807L);
      return;
    }
    this.producer.request(l);
  }

  public final void unsubscribe()
  {
    this.subscriptions.unsubscribe();
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.Subscriber
 * JD-Core Version:    0.6.0
 */