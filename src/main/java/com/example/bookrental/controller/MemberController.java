package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.MemberDto;
import com.example.bookrental.entity.Member;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.MemberService;
import com.example.bookrental.service.serviceimplementation.ReturnDateExceededEmailService;
import com.example.bookrental.utils.MailUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lib/members")
@RequiredArgsConstructor
@SecurityRequirement(name = "bookRental")
@Tag(name = "Member Controller", description = "APIs for managing Members")
public class MemberController extends BaseController {

    private final MemberService memberService;
    private final ReturnDateExceededEmailService emailService;

    @Operation(summary = "Add Member", description = "Add Member to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member added"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping("add-member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> addMember(@RequestBody @Valid MemberDto memberDto) {
        return successResponse(memberService.addMember(memberDto), "New Member added");
    }

    @Operation(summary = "Get all Members", description = "Fetch all available Member detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available Member"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/all-members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<MemberDto>> allMembers() {
        return successResponse(memberService.getAllMember(), "All available members");
    }

    @Operation(summary = "Update Member", description = "update the available Member detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member updated"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PutMapping("/update-members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> updateMember(@RequestBody MemberDto memberDto) {
        return successResponse(memberService.updateMember(memberDto), "Member updated");
    }

    @Operation(summary = "delete Member by id", description = "delete available Member detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found and Deleted"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "402", description = "Member not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping("/remove-member")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteMember(@RequestParam long id) {
        return successResponse(memberService.deleteMember(id), "Member id-: " + id + " has been deleted");
    }

    @Operation(summary = "Get Member by id", description = "Fetch available Member detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/get-by-id")
    public GenericResponse<MemberDto> getById(@RequestParam Long id){
        return successResponse(memberService.findById(id),"Member id-:"+id+"details");
    }



    @Operation(summary = "send mail to users", description = "send mail to specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/send-mail")
    public GenericResponse<String> sendMail(String to,String subject,String body){
        return successResponse(emailService.sendMail(to,subject,body),"mail sent");
    }
    @GetMapping("/download-members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        return successResponse(memberService.getExcel(response),"excelSheet downloaded");
    }
    @Operation(summary = "Upload author details", description = "upload author detail based of excel sheet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author uploaded"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping(value = "/export-to-db-members" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> excelToDb(@ModelAttribute MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        return successResponse(memberService.excelToDb(file),"data exported");
    }
}
