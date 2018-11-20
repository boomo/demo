package com.example.demo.service.impl;

import com.example.demo.mapper.SpecialValueMapper;
import com.example.demo.model.SpecialValue;
import com.example.demo.service.SpecialValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpecialValueServiceImpl implements SpecialValueService {
	
	@Autowired
	public SpecialValueMapper specialValueMapper;

	@Override
	public void add(SpecialValue value) {
		specialValueMapper.add(value);
	}

	@Override
	public List<SpecialValue> getByTaskId(int taskId) {
		return specialValueMapper.getByTaskId(taskId);
	}

	@Override
	public void delByTaskId(int taskId) {
		specialValueMapper.delByTaskId(taskId);
	}
	
	@Override
	public void batchDelByTaskId(String[] taskIds) {
		specialValueMapper.batchDelByTaskId(taskIds);
		
	}
	
	@Override
	public void batchAdd(List<SpecialValue> sList) {
		specialValueMapper.batchAdd(sList);		
	}
}
