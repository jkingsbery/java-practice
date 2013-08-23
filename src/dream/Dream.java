package dream;

import sleep.Sleeper;

public class Dream {
	public void dream(Sleeper s) {

		final Thread thread = Thread.currentThread();
		Runnable wakeUp = new Runnable() {
			@Override
			public void run() {
				thread.interrupt();
				
			}
		};
		new Thread(wakeUp).start();
		
	}
}
