package com.ghilbut.sample.kafka.client.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Getter
@NoArgsConstructor
public class TestDTO {

	private int id;
	private int number;
	private Date timestamp;

	public TestDTO(int id, int number) {
		this.id = id;
		this.number = number;
		this.timestamp = Calendar.getInstance().getTime();
	}
}
