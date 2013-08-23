package net.kingsbery.kata.process;

import java.util.ArrayList;
import java.util.List;

public class ProcessScheduler {

	int totalTicks=0;
	private List<Process> allProcessesExecuted=new ArrayList<Process>();
	
	public class Process{

		int ticksToCompleteWork;
		int tickNumberFinishedOn;
		
		public Process(int work){
			ticksToCompleteWork=work;
		}
		
		public void tick() {
			ticksToCompleteWork--;
			if(ticksToCompleteWork==0){
				tickNumberFinishedOn=totalTicks;
			}
		}

		public boolean yield() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean done() {
			return ticksToCompleteWork==0;
		}
	
	}
	public static void main(String args[]){
		ProcessScheduler scheduler = new ProcessScheduler();
		scheduler.add(scheduler.new Process(13));
		scheduler.add(scheduler.new Process(5));
		scheduler.add(scheduler.new Process(8));
		
		while(scheduler.hasWork()){
			scheduler.tick();
		}
		System.out.println("Completed after: " + scheduler.totalTicks);
		System.out.println("Total wait time: " + scheduler.getWaitTime());
	}

	private int getWaitTime() {
		int result=0;
		for(Process p : allProcessesExecuted){
			result+=p.tickNumberFinishedOn;
		}
		return result;
	}

	private boolean hasWork() {
		return !processes.isEmpty();
	}

	private void tick() {
		System.out.println("TICK #" + totalTicks);
		totalTicks++;
		getActiveProcess().tick();
		
		if(getActiveProcess().done()){
			processes.remove(getActiveProcess());
		}
		else if(getActiveProcess().yield() || switchProcesses()){
			Process process = getActiveProcess();
			processes.remove(process);
			processes.add(process);
		}
	}

	private boolean switchProcesses() {
		return false;
	}

	private Process getActiveProcess() {
		return processes.get(0);
	}

	private List<Process> processes=new ArrayList<Process>();
	
	private void add(Process process) {
		this.allProcessesExecuted.add(process);
		this.processes.add(process);
	}
}
