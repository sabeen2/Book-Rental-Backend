package com.example.bookrental.Controller;

import com.example.bookrental.Dto.MemberDto;
import com.example.bookrental.Entity.Member;
import com.example.bookrental.Service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("addMember")
    public Member addMember(@RequestBody MemberDto memberDto){
        return memberService.addMember(memberDto);
    }
    @GetMapping("/allMembers")
    public List<Member> allMembers(){
        return memberService.getAllMember();
    }
    @PutMapping("/updateMembers")
    public Member updateMember(@RequestBody MemberDto memberDto){
        return memberService.updateMember(memberDto);
    }
    @DeleteMapping("/removeMember")
    public String deleteMember(@RequestParam long id){
        return memberService.deleteMember(id);
    }
}
