package model;

import java.util.Stack;

/**
 * stack that extends the stack implementation and overrides it to a specified amount of objects.
 * @param <T> object type.
 */
public class FixedSizeStack<T> extends Stack<T> {

  private final int maxSize;

  /**
   * constructor of {@code FixedSizeStack} that takes in int as field.
   * @param size integer that represents the maximum size of stack.
   */
  public FixedSizeStack(int size) {
    super();
    maxSize = size;
  }

  @Override
  public T push(T object) {
    if (object == null) {
      throw new IllegalArgumentException("Object to be pushed cannot be null.");
    }
    while (this.size() >= maxSize) {
      this.remove(0);
    }
    return super.push(object);
  }
}
