package butterknife.internal;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
public @interface ListenerClass
{
  public abstract Class<? extends Enum<?>> callbacks();

  public abstract ListenerMethod[] method();

  public abstract String remover();

  public abstract String setter();

  public abstract String targetType();

  public abstract String type();

  public static enum NONE
  {
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     butterknife.internal.ListenerClass
 * JD-Core Version:    0.6.0
 */