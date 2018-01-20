package com.hustack.nl.domain.metadata;

public class Visitors {

	private Long count;

	private Long max;

	private Long min;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Visitors [count=" + count + ", max=" + max + ", min=" + min + "]";
	}
}
