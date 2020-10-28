
public class Process extends Thread {

	public int processID;
	ProcessState status = ProcessState.New;
	
	
	public Process(int m) {
		processID = m;
	}

	public void run() {

		switch (processID) {
		case 1:
			process1();
			break;
		case 2:
			process2();
			break;
		case 3:
			process3();
			break;
		case 4:
			process4();
			break;
		case 5:
			process5();
			break;
		}

	}

	private void process1() {
		
		OperatingSystem.readDataSem.semWait(this);
		OperatingSystem.printText("Enter File Name for Process 1: ");
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));
		Thread unblockedProcPrint = OperatingSystem.readDataSem.semPost();
		if(unblockedProcPrint!=null)
			OperatingSystem.readyQueue.add(unblockedProcPrint);
		setProcessState(this, ProcessState.Terminated);
	}

	private void process2() {

		OperatingSystem.printOnScreenSem.semWait(this);
		OperatingSystem.printText("Enter File Name for Process 2: ");
		OperatingSystem.takeInputFromUserSem.semWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter Data for Process 2: ");
		String data = OperatingSystem.TakeInput();
		Thread unblockedProcPrint = OperatingSystem.printOnScreenSem.semPost();
		if(unblockedProcPrint!=null)
			OperatingSystem.readyQueue.add(unblockedProcPrint);
		Thread unblockedProcInput= OperatingSystem.takeInputFromUserSem.semPost();
		if(unblockedProcInput!=null)
			OperatingSystem.readyQueue.add(unblockedProcInput);
		OperatingSystem.writeIntoDiskSem.semWait(this);
		OperatingSystem.writefile(filename, data);
		Thread unblockedProcWrite = OperatingSystem.writeIntoDiskSem.semPost();
		if(unblockedProcWrite!=null)
			OperatingSystem.readyQueue.add(unblockedProcWrite);
		setProcessState(this, ProcessState.Terminated);
	}

	private void process3() {
		int x = 0;
		OperatingSystem.printOnScreenSem.semWait(this);
		while (x < 301) {
			OperatingSystem.printText(x + "\n");
			x++;
		}
		Thread unblockedProcPrint  = OperatingSystem.printOnScreenSem.semPost();
		if(unblockedProcPrint!=null) {
			OperatingSystem.readyQueue.add(unblockedProcPrint);
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process4() {

		int x = 500;
		OperatingSystem.printOnScreenSem.semWait(this);
		while (x < 1001) {
			OperatingSystem.printText(x + "\n");
			x++;
		}
		Thread unblockedProcPrint = OperatingSystem.printOnScreenSem.semPost();
		if(unblockedProcPrint!=null)
			OperatingSystem.readyQueue.add(unblockedProcPrint);
		setProcessState(this, ProcessState.Terminated);
	}

	private void process5() {

		OperatingSystem.printOnScreenSem.semWait(this);
		OperatingSystem.printText("Enter LowerBound for Process 5: ");
		OperatingSystem.takeInputFromUserSem.semWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter UpperBound for Process 5: ");
		String upper = OperatingSystem.TakeInput();
		int lowernbr = Integer.parseInt(lower);
		int uppernbr = Integer.parseInt(upper);
		String data = "";
		Thread unblockedProcPrint= OperatingSystem.printOnScreenSem.semPost();
		if(unblockedProcPrint!=null)
			OperatingSystem.readyQueue.add(unblockedProcPrint);
		Thread unblockedProcInput = OperatingSystem.takeInputFromUserSem.semPost();
		if(unblockedProcInput!=null)
			OperatingSystem.readyQueue.add(unblockedProcInput);
		while (lowernbr <= uppernbr) {
			data += lowernbr++ + "\n";
		}
		OperatingSystem.writeIntoDiskSem.semWait(this);
		OperatingSystem.writefile("P5.txt", data);
		Thread unblockedProcWriteFile = OperatingSystem.writeIntoDiskSem.semPost();
		if(unblockedProcWriteFile!=null)
			OperatingSystem.readyQueue.add(unblockedProcWriteFile);
		setProcessState(this, ProcessState.Terminated);
	}

	public static void setProcessState(Process p, ProcessState s) {
		p.status = s;
		if (s == ProcessState.Terminated) {
			OperatingSystem.readyQueue.remove(p);
		}
	}

	public static ProcessState getProcessState(Process p) {
		return p.status;
	}
}
