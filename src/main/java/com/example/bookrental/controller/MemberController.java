package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Lib/members")
@RequiredArgsConstructor
@SecurityRequirement(name = "bookRental")
public class MemberController extends BaseController {

    private final MemberService memberService;

    @PostMapping("add-Member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Member> addMember(@RequestBody @Valid MemberDto memberDto) {
        return successResponse(memberService.addMember(memberDto), "New Member added");
    }

    @GetMapping("/all-Members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Member>> allMembers() {
        return successResponse(memberService.getAllMember(), "All available members");
    }

    @PutMapping("/update-Members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Member> updateMember(@RequestBody MemberDto memberDto) {
        return successResponse(memberService.updateMember(memberDto), "Member updated");
    }

    @DeleteMapping("/remove-Member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteMember(@RequestParam long id) {
        return successResponse(memberService.deleteMember(id), "Member id-" + id + " has been deleted");
    }
}
