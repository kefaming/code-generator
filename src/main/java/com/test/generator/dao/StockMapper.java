package com.test.generator.dao;

import com.test.generator.entity.Stock;
import java.util.List;

public interface StockMapper {
    int deleteByPrimaryKey(Integer product_id);

    int insert(Stock record);

    Stock selectByPrimaryKey(Integer product_id);

    List<Stock> selectAll();

    int updateByPrimaryKey(Stock record);
}