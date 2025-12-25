package com.example.BackEnd.Member.role.service;

import com.example.BackEnd.Member.role.repository.MemberRoleRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import lombok.*;

@Service
@RequiredArgsConstructor
public class MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    public List<String> findRoleNamesByMemberId(Long memberId) {
        return memberRoleRepository.findAllByMemberId(memberId).stream()
                .map(memberRole -> memberRole.getRole().getRoleName())
                .toList();
    }

}
