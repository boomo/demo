package com.example.demo.mapper;


import com.example.demo.model.SpecialValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SpecialValueMapper {

	public void add(SpecialValue value);
	
	public List<SpecialValue> getByTaskId(int taskId);
	
	public void delByTaskId(int taskId);

	public void batchDelByTaskId(@Param(value = "taskIds") String[] taskIds);

	public void batchAdd(@Param(value = "list") List<SpecialValue> sList);
}
