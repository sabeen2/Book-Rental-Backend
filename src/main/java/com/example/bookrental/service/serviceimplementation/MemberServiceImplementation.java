package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.MembersRepo;
import com.example.bookrental.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class MemberServiceImplementation implements MemberService {

    private final MembersRepo membersRepo;
    private final ObjectMapper objectMapper;


    @Override
    public Member addMember(MemberDto memberDto) {
        Member member;
        member = objectMapper.convertValue(memberDto, Member.class);
        return membersRepo.save(member);
    }

    @Override
    public Member updateMember(MemberDto memberDto) {
        Member member = membersRepo.findById(memberDto.getMemberid()).orElseThrow(() -> new NotFoundException("Member Not Found"));
        BeanUtils.copyProperties(memberDto, member, getNullPropertyNames(memberDto));
        return membersRepo.save(member);
    }

    @Override
    public List<Member> getAllMember() {
        return membersRepo.findAll();
    }

    @Override
    public Member findById(Long id) {
        return membersRepo.findById(id).orElseThrow(()->new NotFoundException("Member does not exist"));
    }

    @Override
    public String deleteMember(Long id) {
        Member member = membersRepo.findById(id).orElseThrow(() -> new NotFoundException("Member not found"));
        membersRepo.delete(member);
        return member.toString() + "has been deleted";
    }
}
