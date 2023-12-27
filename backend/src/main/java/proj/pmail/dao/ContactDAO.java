package proj.pmail.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import proj.pmail.entity.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactDAO extends BaseMapper<Contact> {
    @Select("select * from contact where creator = #{creator}")
    List<Contact> selectListByCreator(String creator);
    @Select("select * from contact where address = #{address} and creator = #{creator}")
    Contact selectByAddressAndCreator(String address, String creator);
}
