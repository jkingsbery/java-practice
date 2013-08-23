package sleep;

import dream.*;

public class Sleeper {
  private int level;

  public synchronized int enter(Dream dream) {
    level++;
    try {
      dream.dream(this);
    } finally {
      level--;
      System.out.println("NO");
    }
    return level;
  }
}