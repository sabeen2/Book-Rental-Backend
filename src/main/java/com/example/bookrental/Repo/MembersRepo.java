package com.example.bookrental.Repo;

import com.example.bookrental.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepo extends JpaRepository<Member ,Long> {
}
