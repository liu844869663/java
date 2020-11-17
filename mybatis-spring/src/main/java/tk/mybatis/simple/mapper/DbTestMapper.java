package tk.mybatis.simple.mapper;

import tk.mybatis.simple.model.DbTest;

public interface DbTestMapper {

    DbTest queryById(Integer id);
}
