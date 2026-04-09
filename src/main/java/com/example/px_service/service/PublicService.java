package com.example.px_service.service;

import com.example.px_service.dto.UserResponse;
import com.example.px_service.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublicService {

    private final UserRepository userRepository;

    public PublicService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending());

        List userList = userRepository.findAll(page)
                .stream()
                .map(UserResponse::from)
                .toList();

        return userList;
    }

}
