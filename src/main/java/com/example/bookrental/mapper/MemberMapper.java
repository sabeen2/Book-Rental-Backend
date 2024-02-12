package com.example.bookrental.mapper;


import com.example.bookrental.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    @Select("select tm.memberid ,tm.address ,tm.email ,tm.name ,tm.mobile_no  from tbl_member tm where deleted =false ")
    List<MemberDto> getAllMembers();

    @Select("select tm.memberid ,tm.address ,tm.email ,tm.name ,tm.mobile_no  from tbl_member tm where memberid=#{id} and deleted =false")
    Optional<MemberDto> getMemberById(@Param("id") Long id);
}
