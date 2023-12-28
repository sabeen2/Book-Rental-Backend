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
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
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
    public String DeleteMember(long id) {
        Member member=membersRepo.findById(id).orElseThrow(()->new RuntimeException("Member not found"));
        return "deleted";
    }
}
