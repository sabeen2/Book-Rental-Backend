package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.entity.Member;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.MembersRepo;
import com.example.bookrental.service.MemberService;
import com.example.bookrental.utils.ExcelGenerator;
import com.example.bookrental.utils.ExcelToDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class MemberServiceImplementation implements MemberService {

    private final MembersRepo membersRepo;
    private final ObjectMapper objectMapper;


    @Override
    public String addMember(MemberDto memberDto) {
        Member member;
        member = objectMapper.convertValue(memberDto, Member.class);
        membersRepo.save(member);
        return "Member added-:"+memberDto.getName();
    }

    @Override
    public String updateMember(MemberDto memberDto) {
        Member member = membersRepo.findById(memberDto.getMemberid()).orElseThrow(() -> new NotFoundException("Member Not Found"));
        BeanUtils.copyProperties(memberDto, member, getNullPropertyNames(memberDto));
        membersRepo.save(member);
        return "Member updated-:"+memberDto.getName();
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
    @Override
    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response,membersRepo.findAll(),"author sheet", Member.class);
        return "downloaded";
    }
@Override
    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Member> members= ExcelToDb.createEntitiesFromExcel(file,Member.class);
        membersRepo.saveAll(members);
        return "excel sheet data added";
    }
}
