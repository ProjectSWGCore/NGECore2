package services.travel;

import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import main.NGECore;
import resources.common.Console;
import engine.resources.scene.Planet;

public class ShuttleCountdown implements Runnable {

	private NGECore core;
	private final AtomicInteger runCount = new AtomicInteger();
	private final Runnable delegate;
	private volatile ScheduledFuture<?> self;
	
	public ShuttleCountdown(Runnable delegate, NGECore core) {
		this.core = core;
		this.delegate = delegate;
	}
	
	@Override
	public void run() {
		delegate.run();
		Console.println("Atomic int: " + runCount.get());
		if (runCount.incrementAndGet() == 60) {
			Console.println("Shutting down countdown!");
			self.cancel(false);
		}
	}
	
	public void startCountdown(ScheduledExecutorService scheduler) {
		self = scheduler.scheduleAtFixedRate(delegate, 0, 1, TimeUnit.SECONDS);
	}
}
