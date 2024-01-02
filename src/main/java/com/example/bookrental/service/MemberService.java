package com.example.bookrental.service;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;

import java.util.List;

public interface MemberService {
    public Member addMember (MemberDto memberDto);
    public Member updateMember (MemberDto memberDto);
    public List<Member> getAllMember ();
    public String deleteMember (Long id);

}
