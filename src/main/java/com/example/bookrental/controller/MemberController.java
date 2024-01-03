package com.example.bookrental.controller;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;
import com.example.bookrental.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("add-Member")
    public Member addMember(@RequestBody @Valid MemberDto memberDto) {
        return memberService.addMember(memberDto);
    }

    @GetMapping("/all-Members")
    public List<Member> allMembers() {
        return memberService.getAllMember();
    }

    @PutMapping("/update-Members")
    public Member updateMember(@RequestBody MemberDto memberDto) {
        return memberService.updateMember(memberDto);
    }

    @DeleteMapping("/remove-Member")
    public String deleteMember(@RequestParam long id) {
        return memberService.deleteMember(id);
    }
}
