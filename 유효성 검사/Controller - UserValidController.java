package me.jaewonna.springbootdeveloper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jaewonna.springbootdeveloper.dto.UserRequestDto;
import me.jaewonna.springbootdeveloper.dto.UserResponseDto;
import me.jaewonna.springbootdeveloper.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserValidController {

    private final UserService userService;

    /* 회원가입 */
    @PostMapping("/valid/user")
    public String joinProc(@Valid UserRequestDto userDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("userDto", userDto);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 회원가입 페이지로 다시 리턴 */
            return "signUp";
        }
        userService.save(userDto);
        return "signUp";
    }

    @GetMapping("/valid/user")
    public ResponseEntity<List<UserResponseDto>> findAllUsers(){
        List<UserResponseDto> users = userService.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();

        return ResponseEntity.ok()
                .body(users);
    }
}
