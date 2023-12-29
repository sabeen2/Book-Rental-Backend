package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.MemberDto;
import com.example.bookrental.Entity.Member;
import com.example.bookrental.Repo.MembersRepo;
import com.example.bookrental.Service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class MemberServiceImplementation implements MemberService {

    private final MembersRepo membersRepo;
    private final ObjectMapper objectMapper;


    @Override
    public Member addMember(MemberDto memberDto) {
       Member member;
       member=objectMapper.convertValue(memberDto,Member.class);
       return membersRepo.save(member);
    }
    @Override
    public Member updateMember(MemberDto memberDto) {
      Member member=membersRepo.findById(memberDto.getMemberid()).orElseThrow(()->new RuntimeException("Member Not Found"));
        BeanUtils.copyProperties(memberDto, member, getNullPropertyNames(memberDto));
        return membersRepo.save(member);
    }

    @Override
    public List<Member> getAllMember() {
       return membersRepo.findAll();
    }

    @Override
    public String deleteMember(long id) {
        Member member=membersRepo.findById(id).orElseThrow(()->new RuntimeException("Member not found"));
        membersRepo.delete(member);
        return member.toString()+ "has been deleted";
    }

}
