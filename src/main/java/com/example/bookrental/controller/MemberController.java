package com.example.bookrental.controller;

import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Libmembers")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("add-Member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Member> addMember(@RequestBody @Valid MemberDto memberDto) {
        return GenericResponse.<Member>builder()
                .success(true)
                .message("User added")
                .data(memberService.addMember(memberDto))
                .build();
    }
    @GetMapping("/all-Members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Member>> allMembers() {
        return GenericResponse.<List<Member>>builder()
                .success(true)
                .message("All available member")
                .data( memberService.getAllMember())
                .build();
    }
    @PutMapping("/update-Members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public  GenericResponse<Member> updateMember(@RequestBody MemberDto memberDto) {
        return GenericResponse.<Member>builder()
                .success(true)
                .message("Member updated Successfully")
                .data(memberService.updateMember(memberDto))
                .build();
    }
    @DeleteMapping("/remove-Member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteMember(@RequestParam long id) {

        return GenericResponse.<String>builder()
                .success(true)
                .message("User id-:"+id+" Deleted")
                .data(memberService.deleteMember(id))
                .build();
    }
}
