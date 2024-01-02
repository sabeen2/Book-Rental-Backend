package com.example.bookrental.repo;

import com.example.bookrental.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepo extends JpaRepository<Member ,Long> {
}
