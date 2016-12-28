package com.ghilbut.sample.kafka.client.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Calendar;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Getter
@NoArgsConstructor
public class TestDTO {

	private int number;
	private long timestamp;

	public TestDTO(int number) {
		this.number = number;
		this.timestamp = Calendar.getInstance().getTime().getTime();
	}
}
