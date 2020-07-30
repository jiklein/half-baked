

public class UIDemo {
	public static UIDemo instance;
	
	public Object lock;
	TestFrame frame;
	
	private UIDemo() throws InterruptedException {
		frame = new TestFrame();
		lock = new Object();
	}
	
	private void run() throws InterruptedException {
		System.out.println("Waiting for click");
		synchronized(lock) {
			lock.wait();
		}
		System.out.println("Doing other stuff");
	}
	
	public static void main(String[] args) throws InterruptedException {
		instance = new UIDemo();
		instance.run();
	}
}
