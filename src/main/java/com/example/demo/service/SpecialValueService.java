package com.example.demo.service;


import com.example.demo.model.SpecialValue;

import java.util.List;


public interface SpecialValueService {

	public void add(SpecialValue value);
	
	public List<SpecialValue> getByTaskId(int taskId);
	
	public void delByTaskId(int taskId);

	public void batchDelByTaskId(String[] taskIds);

	public void batchAdd(List<SpecialValue> sList);
}
