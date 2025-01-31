package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Category;
import com.example.bookrental.entity.Member;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.MemberMapper;
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
import java.util.Optional;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class MemberServiceImplementation implements MemberService {

    private final MembersRepo membersRepo;
    private final ObjectMapper objectMapper;
    private final MemberMapper memberMapper;

    private final CustomMessageSource messageSource;


    @Override
    public String addMember(MemberDto memberDto) {
        Optional<Member> byName = membersRepo.findByName(memberDto.getName());
        if (byName.isPresent()) {
            Member existingMember = byName.get();
            if (existingMember.isDeleted()) {
                existingMember.setDeleted(false);
                membersRepo.save(existingMember);
                return "member already existed so, active status is changed";
            }else {
                throw new NotFoundException(messageSource.get(ExceptionMessages.CONSTRAINT_VIOLATION.getCode()));
            }
        } else {
        Member member;
        member = objectMapper.convertValue(memberDto, Member.class);
        membersRepo.save(member);
        return messageSource.get(ExceptionMessages.SAVE.getCode()) +" "+ memberDto.getName();
    }

}
    @Override
    public String updateMember(MemberDto memberDto) {
        Member member = membersRepo.findById(memberDto.getMemberid())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        BeanUtils.copyProperties(memberDto, member, getNullPropertyNames(memberDto));
        membersRepo.save(member);
        return messageSource.get(ExceptionMessages.UPDATE.getCode())+" "+ memberDto.getName();
    }


    @Override
    public List<MemberDto> getAllMember() {
        return memberMapper.getAllMembers();
    }

    @Override
    public MemberDto findById(Long id) {
        return memberMapper.getMemberById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
    }

    @Override
    public String deleteMember(Long id) {
        Member member = membersRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        membersRepo.delete(member);
        return member.getName()+" "+  messageSource.get(ExceptionMessages.DELETED.getCode());
    }
    @Override
    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response,membersRepo.findAll(),"author sheet", Member.class);
        return messageSource.get(ExceptionMessages.DOWNLOADED.getCode());
    }
@Override
    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Member> members= ExcelToDb.createExcel(file,Member.class);
        membersRepo.saveAll(members);
        return  messageSource.get(ExceptionMessages.EXPORT_EXCEL_SUCCESS.getCode());
    }
}
