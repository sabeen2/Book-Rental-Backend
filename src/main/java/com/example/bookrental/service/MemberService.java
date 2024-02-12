package com.example.bookrental.service;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    public String addMember (MemberDto memberDto);
    public String updateMember (MemberDto memberDto);
    public List<MemberDto> getAllMember ();
    public MemberDto findById(Long id);
    public String deleteMember (Long id);
    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException;
    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException;

}
