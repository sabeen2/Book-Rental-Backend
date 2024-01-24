package com.example.bookrental.service;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Member;

import java.util.List;

public interface MemberService {
    public String addMember (MemberDto memberDto);
    public String updateMember (MemberDto memberDto);
    public List<Member> getAllMember ();
    public Member findById(Long id);
    public String deleteMember (Long id);

}
