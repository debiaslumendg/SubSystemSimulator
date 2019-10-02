package ci4821.subsystemsimulator.software;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SymProcess {
	
	List<Integer> text;
	List<Integer> data;
	int currentInstruction = 0;
	
	public SymProcess(int text, int data) {
		int size = text + data;
		this.text = new ArrayList<>((new Random()).ints(text, 0, size).boxed().collect(Collectors.toList()));
		this.data = new ArrayList<>((new Random()).ints(data, 0, size).boxed().collect(Collectors.toList()));
	}
	
	public Integer nextInstruction() {
		return text.get(currentInstruction++);
	}
	
	public boolean isTerminated() {
		return currentInstruction == text.size();
	}

	public List<Integer> getText() {
		return text;
	}

	public void setText(List<Integer> text) {
		this.text = text;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
