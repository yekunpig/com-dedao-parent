package com.sharind.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.sharind.service.BaseService;

//111@Service // 这里是不对的,因为在此类的构造方法中要获取父，但是他没有父，
public class BaseServiceImpl<T> implements BaseService<T> {

	// 泛型注入，spring4和以上版本才可以使用
	@Autowired
	private Mapper<T> mapper;

	// 声明泛型的class
	private Class<T> clazz;

	// 在构造方法中获取泛型的class
	public BaseServiceImpl() {
		// 获取父类的参数的type
		Type type = this.getClass().getGenericSuperclass();

		// 强转type为其子类，目的能够使用子类的方法，获取泛型的class
		ParameterizedType pType = (ParameterizedType) type;

		// 调用方法获取父类的class
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Long id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}

	@Override
	public List<T> queryAll() {
		List<T> list = this.mapper.select(null);
		return list;
	}

	@Override
	public int queryCountByWhere(T t) {
		int count = this.mapper.selectCount(t);
		return count;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		List<T> list = this.mapper.select(t);
		return list;
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// 设置分页参数，第一个参数是当前页码数，第二个参数是每页显示的数据条数
		PageHelper.startPage(page, rows);

		// 执行查询
		List<T> list = this.mapper.select(null);

		return list;
	}

	@Override
	public T queryOne(T t) {
		T result = this.mapper.selectOne(t);
		return result;
	}

	@Override
	public void save(T t) {
		this.mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {

		this.mapper.insertSelective(t);
	}

	@Override
	public void updateById(T t) {
		this.mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// 创建example
		Example example = new Example(this.clazz);

		// 设置删除条件
		example.createCriteria().andIn("id", ids);

		// 执行批量删除
		this.mapper.deleteByExample(example);

	}

}
