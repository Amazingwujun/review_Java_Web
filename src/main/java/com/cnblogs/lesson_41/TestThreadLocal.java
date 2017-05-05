package com.cnblogs.lesson_41;

public class TestThreadLocal {

	private TestThreadLocal() {
	};

	private ThreadLocal<String> tl = new ThreadLocal<>();
	private static TestThreadLocal local = new TestThreadLocal();

	public static TestThreadLocal newInstance() {
		return local;
	}

	public void set(String value) {
		tl.set(value);
	}

	public String get() {
		return tl.get();
	}

	public void remove() {
		tl.remove();
	}

	private void hide() {
	}

	public static void main(String[] args) throws InterruptedException {
		TestThreadLocal ttl = TestThreadLocal.newInstance();
		new Thread(new Runnable() {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				
				ttl.set("----T0");

				while (true) {
					System.out.println(threadName+ttl.get());
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				
				ttl.set("----T1");

				while (true) {
					System.out.println(threadName+ttl.get());
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		Thread.currentThread().join();
	}

}
