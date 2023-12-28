package com.example.bookrental.Service;

import com.example.bookrental.Dto.MemberDto;
import com.example.bookrental.Entity.Member;

import java.util.List;

public interface MemberService {
    public Member addMember (MemberDto memberDto);
    public Member updateMember (MemberDto memberDto);
    public List<Member> getAllMember ();
    public String DeleteMember (long id);

}
