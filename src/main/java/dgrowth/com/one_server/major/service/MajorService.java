package dgrowth.com.one_server.major.service;

import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.major.dto.mapper.MajorMapper;
import dgrowth.com.one_server.major.dto.request.MajorRequest;
import dgrowth.com.one_server.major.dto.response.MajorResponse;
import dgrowth.com.one_server.major.dto.response.UserMajorResponse;
import dgrowth.com.one_server.major.domain.entity.Major;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import dgrowth.com.one_server.notice.domain.repository.NoticeRepository;
import dgrowth.com.one_server.notice.dto.NoticeMapper;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.major.domain.repository.MajorRepository;

import dgrowth.com.one_server.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MajorService {

    private final MajorRepository majorRepository;
    private final AuthService authService;
    private final UserService userService;
    private final NoticeRepository noticeRepository;

    public Major findById(Long id) {
        return majorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public MajorResponse save(MajorRequest majorRequest, HttpServletRequest httpServletRequest) {

        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 생성 및 저장
        Major newMajor = MajorMapper.INSTANCE.requestToEntity(majorRequest);
        Major major = majorRepository.save(newMajor);

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        return majorResponse;
    }

    @Transactional
    public Major update(Major major) {
        Major updatedMajor = majorRepository.save(major);
        return updatedMajor;
    }

    public MajorResponse majorInfoById(HttpServletRequest httpServletRequest, Long id) {
        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 학과 조회
        Major major = findById(id);

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        majorResponse.setNotices(NoticeMapper.multipleToResponses(major.getNotices(), 2));

        return majorResponse;
    }

    public MajorResponse majorInfoByUserId(HttpServletRequest httpServletRequest) {
        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 학과 조회
        User user = userService.findById(userId);
        Major major = user.getMajor();

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        List<Notice> notices = noticeRepository.findByMajor(major, sort);

        majorResponse.setNotices(NoticeMapper.multipleToResponses(notices, 2));

        return majorResponse;
    }

    // User에 학과 등록을 위한 임시 메소드 -> 학생증 OCR 적용 후 삭제 -> OCR 적용 시 사용하기 위해 주석처리
//    @Transactional
//    public UserResponse userAddMajor(HttpServletRequest httpServletRequest, Long majorId) {
//        UserResponse userResponse = null;
//
//        // 1. 헤더에서 토큰 체크
//        String token = authService.getTokenByHeader(httpServletRequest);
//
//        // 2. 토큰 유효성 체크 및 유저 불러오기
//        Long userId = authService.getUserInfoByToken(token).getId();
//
//        // 3. 유저 조회
//        User user = userService.findById(userId);
//
//        Major major = majorRepository.findById(majorId)
//                .orElseThrow(NoSuchElementException::new);
//
//        // 4. 유저에 학과 추가
//        user.setMajor(major);
//
//        // 5. 유저 저장
//        User savedUser = userService.save(user);
//
//        userResponse = savedUser.toResponse();
//
//        return userResponse;
//    }

    public List<UserMajorResponse> findByStudentId(HttpServletRequest httpServletRequest, Long studentId){
        List<UserMajorResponse> userMajorResponses = new ArrayList<>();

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 유저 조회
        User user = userService.findById(userId);

        Major major = user.getMajor();

        List<User> users = major.getUsers();

        for (User userIter : users) {
            if (userIter.getStudentId() == studentId){
                UserMajorResponse userMajorResponse = new UserMajorResponse(userIter.getId(), userIter.getName(), userIter.getProfileImageUrl());
                userMajorResponses.add(userMajorResponse);
            }
        }
        return userMajorResponses;
    }
}
