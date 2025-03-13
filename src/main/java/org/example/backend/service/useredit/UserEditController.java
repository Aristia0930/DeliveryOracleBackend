package org.example.backend.service.useredit;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/edit")
@Tag(name = "유저정보수정 api", description = "유저 정보수정 API")
public class UserEditController {

    private static final Logger logger = LoggerFactory.getLogger(UserEditController.class);

    @Autowired
    private UserEditService userEditService;

    @GetMapping("/change-nickname")
    @Operation(summary = "닉네임 수정 요청", description = "닉네임 수정 요청 api")
    public String changeNickname(@RequestParam("userId") int userId,
                                 @RequestParam("nickname") String nickname) {
        try {
            boolean isNameChanged = userEditService.changeName(userId, nickname);
            if (isNameChanged) {
                return "이름이 변경 되었습니다.";
            } else {
                return "이름 변경에 실패했습니다.";
            }
        } catch (Exception e) {
            logger.error("이름 변경 중 오류 발생", e);
            return "이름 변경 중 오류가 발생했습니다.";
        }
    }

    // 비밀번호 변경 요청을 처리하는 엔드포인트

    @PostMapping("/change-password")
    @Operation(summary = "비밀번호 수정 요청", description = "비밀번호 수정 요청 api")
    public User changePassword(@RequestParam("userId") int userId,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        try {
            return userEditService.changePassword(userId, oldPassword, newPassword);
        } catch (IllegalArgumentException e) {
            logger.error("비밀번호 변경 중 오류가 발생했습니다: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            logger.error("비밀번호 변경 중 예기치 않은 오류가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("비밀번호 변경 중 예기치 않은 오류가 발생했습니다.");
        }
    }
}

//package org.example.backend.service.useredit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/edit")
//public class UserEditController {
//
//    @Autowired
//    private UserEditService userEditService;
//
//    // 비밀번호 변경 요청을 처리하는 엔드포인트
//    @PostMapping("/change-password")
//    public String changePassword(@RequestParam("userId") int userId,
//                                 @RequestParam("oldPassword") String oldPassword,
//                                 @RequestParam("newPassword") String newPassword) {
//        try {
//            boolean isPasswordChanged = userEditService.changePassword(userId, oldPassword, newPassword);
//            if (isPasswordChanged) {
//                return "비밀번호가 성공적으로 변경되었습니다.";
//            } else {
//                return "기존 비밀번호가 일치하지 않습니다.";
//            }
//        } catch (Exception e) {
//            return "비밀번호 변경 중 오류가 발생했습니다.";
//        }
//    }
//}
