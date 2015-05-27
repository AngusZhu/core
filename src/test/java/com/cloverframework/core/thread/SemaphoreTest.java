package com.cloverframework.core.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {


}
class PrintManager{
	private final Semaphore semaphore;
	private final List<Printer> printers=new ArrayList<Printer>();
	
	public PrintManager(Collection<? extends Printer> printers){
		this.printers.addAll(printers);
		this.semaphore=new Semaphore(this.printers.size(),true);
	}
	public Printer acquirePrinter() throws InterruptedException{
		semaphore.acquire();
		return getAvailablePrinter();
	}
	public void releasePrinter(Printer printer){
		putBackPrinter(printer);
		semaphore.release();
	}
	private void putBackPrinter(Printer printer) {
		printers.add(printer);
	}
	private Printer getAvailablePrinter() {
		Printer printer = printers.get(0);
		printers.remove(0);
		return printer;
	}
}

class Printer{
	
	private String type;
	
	private double price;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}